package development.apodbackend.services;

import org.springframework.stereotype.Service;

import development.apodbackend.apis.ApodApi;
import development.apodbackend.models.SentEmailModel;
import development.apodbackend.models.SubscriptionModel;
import development.apodbackend.schemas.MailMessageSchema;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class SchedulerEmailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    ApodApi apodApi;

    @Autowired
    SentEmailService sentEmailService;

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


    public void sendEmail(SubscriptionModel newSubscription) {
        try {
            
            MailMessageSchema responseApi = apodApi.getAstronomyPicure();

            if (responseApi == null) {
                System.out.println("Oops! An error occurred, APOD API timeout.");
                return;
            }

            String htmlBody = readHtmlFile("src/main/java/development/apodbackend/schemas/MailMessageSchema.html");
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            
            // Set values
            messageHelper.setSubject("Astronomy Picture of The Day");
            htmlBody = htmlBody.replace("$TITLE$", responseApi.getTitle());
            htmlBody = htmlBody.replace("$DATE$", responseApi.getDate());
            htmlBody = htmlBody.replace("$EXPLANATION$", responseApi.getExplanation());
            htmlBody = htmlBody.replace("$PICTURE$", responseApi.getPicture());
            
            System.out.println(newSubscription.getId());
            if (newSubscription != null) {
                System.out.println(newSubscription.getId());
                htmlBody = htmlBody.replace("$USERNAME$", newSubscription.getUsername());

                messageHelper.setTo(newSubscription.getEmail());
                messageHelper.setText(htmlBody, true);


                System.out.println(newSubscription.getId());

                sentEmailService.create(new SentEmailModel(newSubscription.getId()));
                mailSender.send(message);
                System.out.println("Success to send email!");
                return;
            }
            
            var subscriptions = subscriptionService.get();
            for (var subscription : subscriptions) {
                try{
                    if (subscription.getCreated_at().toString().split(" ")[0].equals(LocalDate.now().toString())) {
                        System.out.println("signature already has email today");
                        continue;
                    }

                    htmlBody = htmlBody.replace("$USERNAME$", subscription.getUsername());
                    
                    messageHelper.setTo(subscription.getEmail());
                    messageHelper.setText(htmlBody, true);
                    sentEmailService.create(new SentEmailModel(subscription.getId()));
                    mailSender.send(message);
                    System.out.println("Success to send email!");
                }

                catch (Exception error) {
                    error.printStackTrace();
                    continue;
                }
            }
        }

        catch (MessagingException error) {
            error.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 12 ? * *")
    void taskLog() {
        sendEmail(null);
    }
}