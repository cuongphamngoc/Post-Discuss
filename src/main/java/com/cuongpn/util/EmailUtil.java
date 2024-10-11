package com.cuongpn.util;

public class EmailUtil {
    public static String buildEmailContent(String recipientName, String messageBody, String actionUrl, String actionText) {
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + ".header { text-align: center; padding: 20px 0; }"
                + ".header img { max-width: 100px; }"
                + ".content { padding: 20px; }"
                + ".content p { line-height: 1.6; }"
                + ".button { display: block; width: 200px; margin: 20px auto; padding: 10px 0; text-align: center; background-color: #007bff; color: #ffffff; text-decoration: none; border-radius: 5px; }"
                + ".footer { text-align: center; padding: 20px; font-size: 12px; color: #888888; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "<div class=\"header\">"
                + "</div>"
                + "<div class=\"content\">"
                + "<p>Hi " + recipientName + ",</p>"
                + "<p>" + messageBody + "</p>"
                + "<a href=\"" + actionUrl + "\" class=\"button\">" + actionText + "</a>"
                + "<p>If you did not request this, you can ignore this email.</p>"
                + "<p>Best regards,<br>The Support Team</p>"
                + "</div>"
                + "<div class=\"footer\">"
                + "<p>© 2024 Your Company. All rights reserved.</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
