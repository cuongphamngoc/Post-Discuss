package com.cuongpn.service;

import com.cuongpn.entity.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailSenderService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details) ;
}
