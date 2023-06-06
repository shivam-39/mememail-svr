package com.nash.memail.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.from}")
    private String from;

    @Value("${email.password}")
    private String password;

    public boolean sendEmail(String subject, String message, String to){

        boolean isSuccess = false;

        //Variable for gmail
        String host = "smtp.gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES: " + properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host); //SMTP host
        properties.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        properties.put("mail.smtp.port", 465); //SMTP Port
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
//        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication

        //Strp-1 - to get the session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        session.setDebug(true);

        //Step-2 - compose the message [text, multimedia]
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            //from email
            mimeMessage.setFrom(from);

            //adding recipient to message
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            mimeMessage.setSubject(subject);

            //adding text to message
            mimeMessage.setText(message);

            //send

            //Step-3 - send the message using transport class
            Transport.send(mimeMessage);

            isSuccess = true;
            System.out.println("Sent Sucess....................");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
