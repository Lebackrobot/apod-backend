package development.apodbackend.services;

import org.springframework.stereotype.Service;

import development.apodbackend.apis.ApodApi;
import development.apodbackend.apis.HealthCheckApi;
import development.apodbackend.models.SentEmailModel;
import development.apodbackend.models.SubscriptionModel;
import development.apodbackend.schemas.ApodApiSchema;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Service
public class SchedulerEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ApodApi apodApi;

    @Autowired
    private HealthCheckApi healthCheckApi;

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


    public void sendEmail(SubscriptionModel subscription, ApodApiSchema responseApi) {
        try {
            while (responseApi == null) {
                SentEmailModel sentEmail = sentEmailService.getLastSendBySubscriptionId(subscription.getId());

                if (sentEmail != null && sentEmail.isToday()) {
                    return;
                }
                
                log.error("❌ OOPS! An error occured, APOD API timeout");
                log.info("Thread sleep (10 minutes)");
                
                Thread.sleep(TimeUnit.MINUTES.toMillis(10));
                responseApi = apodApi.getAstronomyPicture();
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            
            messageHelper.setTo(subscription.getEmail());
            messageHelper.setSubject("Astronomy Picture of The Day");

            String htmlBody = readHtmlFile("src/main/java/development/apodbackend/schemas/MailMessageSchema.html");
            htmlBody = htmlBody.replace("$TITLE$", responseApi.getTitle())
                               .replace("$DATE$", responseApi.getDate())
                               .replace("$DATE$", responseApi.getDate())
                               .replace("$EXPLANATION$", responseApi.getExplanation())
                               .replace("$PICTURE$", responseApi.getPicture())
                               .replace("$USERNAME$", subscription.getUsername());

            messageHelper.setText(htmlBody, true);

            
            
            Thread.sleep(TimeUnit.SECONDS.toMillis(30));
            mailSender.send(message);
            sentEmailService.create(new SentEmailModel(subscription.getId()));
            log.info("✅ Success to send email");
            return;
        }

        catch (Exception error) {
            error.printStackTrace();
        }
    }


    @Scheduled(cron = "0 0 8 ? * *", zone = "America/Sao_Paulo")    
    private void taskRotine() {
        log.info("TASK ROUTINE");
        var responseApi = apodApi.getAstronomyPicture();
        var subscriptions = subscriptionService.get();

        subscriptions.forEach(subscription -> {

            SentEmailModel sentEmail = sentEmailService.getLastSendBySubscriptionId(subscription.getId());

            if (sentEmail == null || !sentEmail.isToday()) {
                log.info("🟠 Send email");
                sendEmail(subscription, responseApi);
            }
        });
    }


    @Scheduled(fixedDelay = 10000)
    private void healthChecker() {
        log.info("SEND health-check");
        healthCheckApi.getHealthCheck();
    }
}