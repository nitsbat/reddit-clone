package com.example.redditclone.service;

import com.example.redditclone.builder.MailContentBuilder;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    //    @Async
    public void sendMail(NotificationEmail email) throws SpringRedditException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getBody());
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Activation Mail Sent");
        } catch (MailException ex) {
            throw new SpringRedditException("Exception occured when sending mail to " + email.getRecipient()
                    + ex.getMessage());
        }
    }
}
