package development.apodbackend.services;

import org.springframework.stereotype.Service;

import development.apodbackend.apis.ApodApi;
import development.apodbackend.models.SentEmailModel;
import development.apodbackend.models.SubscriptionModel;
import development.apodbackend.schemas.ApodApiSchema;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Service
public class SchedulerEmailService {
    private  String today = LocalDate.now().toString();

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ApodApi apodApi;

    @Autowired
    private SentEmailService sentEmailService;

    private String readHtmlFile(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, StandardCharsets.UTF_8);
        } 
        
        catch (IOException error) {
            error.printStackTrace();
            return "";
        }
    }

    public void sendEmail(SubscriptionModel subscription, ApodApiSchema picture) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            
            messageHelper.setTo(subscription.getEmail());
            messageHelper.setSubject("Astronomy Picture of The Day");

            String htmlBody = readHtmlFile("src/main/java/development/apodbackend/schemas/MailMessageSchema.html");
            htmlBody = htmlBody.replace("$TITLE$", picture.getTitle())
                               .replace("$DATE$", picture.getDate())
                               .replace("$DATE$", picture.getDate())
                               .replace("$EXPLANATION$", picture.getExplanation())
                               .replace("$PICTURE$", picture.getPicture())
                               .replace("$USERNAME$", subscription.getUsername());

            messageHelper.setText(htmlBody, true);

            sentEmailService.create(new SentEmailModel(subscription.getId()));
            mailSender.send(message);

            log.info("✅ Success to send email");
            return;
        }

        catch (Exception error) {
            error.printStackTrace();
        }
    }


    @Scheduled(cron = "${spring.cron.time}")
    private void task() {
        var picture = apodApi.getAstronomyPicture();
        var subscriptions = subscriptionService.get();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        if (picture == null) {
            log.info("❌ OOPS! An error occured, APOD API timeout");
            return;
        }
        
        subscriptions.forEach(subscription -> {


            String createdAt = subscription.getCreated_at().toString().split(" ")[0];
            if (!createdAt.equals(today)) {
                scheduler.schedule(() -> {
                    sendEmail(subscription, picture);
                }, 30, TimeUnit.SECONDS);
            }
        });
    }
}