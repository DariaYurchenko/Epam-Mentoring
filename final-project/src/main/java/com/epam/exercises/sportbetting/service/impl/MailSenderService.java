package com.epam.exercises.sportbetting.service.impl;

import com.epam.exercises.sportbetting.exceptions.MailsSenderException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
public class MailSenderService {
    private JavaMailSender javaMailSender;
    private String link;
    private String htmlTemplate;
    private String subject;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender, @Value("${link}")String link, @Value("${html.template}") String htmlTemplate,  @Value("${subject}") String subject) {
        this.javaMailSender = javaMailSender;
        this.link = link;
        this.htmlTemplate = htmlTemplate;
        this.subject = subject;
    }

    public void sendEmail(String email, String token) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(buildContent(email, token));
            javaMailSender.send(mimeMessage);
        } catch (Exception ex) {
            throw new MailsSenderException();
        }
    }

    private Multipart buildContent(String email, String token) throws IOException, MessagingException {
        String formattedLink = String.format(link, token, email);
        String htmlBody = String.format(htmlTemplate, formattedLink);

        Multipart multipart = new MimeMultipart();

        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(htmlBody, "text/html; charset=utf-8");
        multipart.addBodyPart(htmlBodyPart);

        MimeBodyPart imageBodyPart = new MimeBodyPart();
        imageBodyPart.setHeader("Content-ID", "<image>");
        imageBodyPart.setDisposition(MimeBodyPart.INLINE);
        imageBodyPart.attachFile(createFile());
        multipart.addBodyPart(imageBodyPart);

        return multipart;
    }

    private File createFile() throws IOException {
        try(InputStream stream = MailSenderService.class.getClassLoader().getResourceAsStream("lis.jpg")) {
            File file = new File("image");
            FileUtils.copyInputStreamToFile(Objects.requireNonNull(stream), file);
            return file;
        }
    }


}
