package com.example.contactList.service.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {
    private Pattern emailPattern;
    private Pattern numberPattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                                                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public Validator() {
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        numberPattern = Pattern.compile(NUMBER_PATTERN);
    }

    public boolean validateEmail(final String hex) {
        matcher = emailPattern.matcher(hex);

        return matcher.matches();
    }

    public boolean validateNumber(final String hex) {
        matcher = numberPattern.matcher(hex);

        return matcher.matches();
    }
}
