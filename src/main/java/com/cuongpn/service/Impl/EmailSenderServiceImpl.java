package com.cuongpn.service.Impl;

import com.cuongpn.entity.EmailDetail;
import com.cuongpn.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Async
    @Override
    public void sendSimpleMail(EmailDetail detail) {
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(detail.getRecipient());
        mailMessage.setText(detail.getMsgBody());
        mailMessage.setSubject(detail.getSubject());

        // Sending the mail
        javaMailSender.send(mailMessage);

    }
    @Async
    @Override
    public void sendMailWithAttachment(EmailDetail detail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(detail.getRecipient());
            mimeMessageHelper.setSubject(detail.getSubject());
            mimeMessageHelper.setText(detail.getMsgBody(), true);
            if (detail.getAttachmentPath() != null && !detail.getAttachmentPath().isEmpty()) {
                File attachment = new File(detail.getAttachmentPath());
                if (attachment.exists()) {
                    mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                } else {
                    throw new RuntimeException("Attachment file not found: " + detail.getAttachmentPath());
                }
            }

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }
}
