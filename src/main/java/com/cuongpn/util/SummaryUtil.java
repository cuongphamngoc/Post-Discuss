package com.cuongpn.util;

import org.jsoup.Jsoup;

public class SummaryUtil {
    public static String makeSummary(String context) {
        String textContent = Jsoup.parse(context).text();
        System.out.println(textContent);
        if(textContent.length() >= 297){
            return textContent.substring(0, 297);
        }
        return textContent+"...";
    }
}
