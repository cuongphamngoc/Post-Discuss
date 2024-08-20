package com.cuongpn.config;

import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@AllArgsConstructor
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    public static String toLocale(String msgCode){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode,null,locale);
    }
}
