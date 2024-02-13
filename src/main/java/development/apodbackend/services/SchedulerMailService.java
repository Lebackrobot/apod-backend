package development.apodbackend.services;

import org.springframework.stereotype.Service;

import development.apodbackend.apis.ApodApi;
import development.apodbackend.schemas.MailMessageSchema;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class SchedulerMailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    ApodApi apodApi;

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


    public void sendEmail() {
        
        try {
            
            var subscriptions = subscriptionService.get();
            MailMessageSchema responseApi = apodApi.getAstronomyPicure();
            String htmlBody = readHtmlFile("src/main/java/development/apodbackend/schemas/MailMessageSchema.html");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);


            // Set values
            messageHelper.setSubject("Astronomy Picture of The Day");
            htmlBody = htmlBody.replace("$TITLE$", responseApi.getTitle());
            htmlBody = htmlBody.replace("$DATE$", responseApi.getDate());
            htmlBody = htmlBody.replace("$EXPLANATION$", responseApi.getExplanation());
            htmlBody = htmlBody.replace("$PICTURE$", responseApi.getPicture());


            for (var subscription : subscriptions) {
                try{
                    htmlBody = htmlBody.replace("$USERNAME$", subscription.getUsername());
                    
                    messageHelper.setTo(subscription.getEmail());
                    messageHelper.setText(htmlBody, true);
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

    @Scheduled(fixedDelay=1000 * 60 * 2)
    void taskLog() {
        
        //sendEmail();
    }
}