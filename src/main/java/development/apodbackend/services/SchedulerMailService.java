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
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo("*****************");
            messageHelper.setSubject("Astronomy Picture of The Day");


            String htmlBody = readHtmlFile("src/main/java/development/apodbackend/schemas/MailMessageSchema.html");

            if (htmlBody.equals("")) {
                System.out.println("Fail to send email!");
                return;
            }

            MailMessageSchema responseApi = apodApi.getAstronomyPicure();

            htmlBody = htmlBody.replace("$TITLE$", responseApi.getTitle());
            htmlBody = htmlBody.replace("$DATE$", responseApi.getDate());
            htmlBody = htmlBody.replace("$EXPLANATION$", responseApi.getExplanation());
            htmlBody = htmlBody.replace("$USERNAME$", "Pedro Fernandes");

            System.out.println(htmlBody);


            messageHelper.setText(htmlBody, true);
            mailSender.send(message);

            System.out.println("Success to send email!");
        }

        catch (MessagingException error) {
            error.printStackTrace();
        }
    
        

    }


    @Scheduled(fixedDelay=1000 * 60 * 2)
    void taskLog() {
        sendEmail();
    }
}