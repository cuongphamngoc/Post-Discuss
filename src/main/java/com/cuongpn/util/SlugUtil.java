package com.cuongpn.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

public class SlugUtil {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESHADES = Pattern.compile("(^-|-$)");

    public static String makeSlug(String input){
        if(input == null){
            throw new IllegalArgumentException();
        }

        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESHADES.matcher(slug).replaceAll("");
        slug = slug.toLowerCase(Locale.ENGLISH);
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return slug + "-" + uniqueId;

    }
}
