package com.cuongpn.service;

import com.cuongpn.entity.EmailDetail;
import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {


    @Async
    void sendSimpleMail(EmailDetail detail);


    @Async
    void sendMailWithAttachment(EmailDetail detail);
}
