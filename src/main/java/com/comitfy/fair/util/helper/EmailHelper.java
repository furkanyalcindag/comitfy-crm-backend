package com.comitfy.fair.util.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {

    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String to, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        //msg.setTo("to_1@gmail.com", "to_2@gmail.com", "to_3@yahoo.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }


}
