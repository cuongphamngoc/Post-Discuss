package com.cuongpn.service;

import com.cuongpn.entity.EmailDetails;

public interface EmailSenderService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
