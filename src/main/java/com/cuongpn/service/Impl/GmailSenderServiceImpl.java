package com.cuongpn.service.Impl;

import com.cuongpn.entity.EmailDetails;
import com.cuongpn.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        // Sending the mail
        javaMailSender.send(mailMessage);
        return "Mail Sent Successfully...";
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setText(details.getMsgBody(), true);
            if (details.getAttachmentPath() != null && !details.getAttachmentPath().isEmpty()) {
                File attachment = new File(details.getAttachmentPath());
                if (attachment.exists()) {
                    mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                } else {
                    throw new RuntimeException("Attachment file not found: " + details.getAttachmentPath());
                }
            }

            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
