package com.rizkirian.rsantrian.helper;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class ValidEmail {

    public static boolean isValidEmail (String email) {
        boolean validate;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validate = true;
        } else if (email.matches(emailPattern2)) {
            validate = true;
        } else {
            validate = false;
        }
        return validate;
    }
}
