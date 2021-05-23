package com.example.redditclone.service;

import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.mail.MailContentBuilder;
import com.example.redditclone.model.NotificationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    public void sendMail(NotificationEmail email) throws SpringRedditException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(mailContentBuilder.build(email.getBody()));
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException ex) {
            throw new SpringRedditException("Exception occured when sending mail to " + email.getRecipient()
                    + ex.getMessage());
        }
    }
}
