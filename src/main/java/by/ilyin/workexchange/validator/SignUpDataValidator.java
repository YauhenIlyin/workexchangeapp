package by.ilyin.workexchange.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpDataValidator {

    private static SignUpDataValidator instance;

    private final String EMAIL_REGEX = "^[^@]+\\@[^@]+\\.[a-zA-Z]{2,}$";
    private final String PHONE_NUMBER_REGEX = "^\\+?\\d+$";
    private final int CHAR_CODE_FIRST_SPECIAL_SIGN = 33;
    private final int CHAR_CODE_LAST_SPECIAL_SIGN = 42;
    private final int CHAR_CODE_0_SIGN = 48;
    private final int CHAR_CODE_9_SIGN = 57;
    private final int CHAR_CODE_A_SIGN = 65;
    private final int CHAR_CODE_Z_SIGN = 90;
    private final int CHAR_CODE_UNDERSCORE_SIGN = 95;
    private final int CHAR_CODE_a_SIGN = 97;
    private final int CHAR_CODE_z_SIGN = 122;

    private final int MIN_LOGIN_LENGTH = 6;
    private final int MAX_LOGIN_LENGTH = 16;
    private final int MIN_PASSWORD_LENGTH = 8;
    private final int MAX_PASSWORD_LENGTH = 32;
    private final int MAX_EMAIL_LENGTH = 60;
    private final int MAX_NUMBER_LENGTH = 30;
    private final int MIN_FIRST_LAST_NAME_LENGTH = 1;
    private final int MAX_FIRST_LAST_NAME_LENGTH = 14;

    //todo правила регистрации:
    /*
    login -  !@#$%^&'()*  1234567890 A-Z _ a-z (от 6 до 16) Буквы в разном регистре
    password - a-zA-Z !@#$%^&*? 1234567890  заглавные + строчные буквы
    email - правильный email
    в email  проверяем, чтобы была только одна @
    (чтобы нельзя было списком передать много адресов для рассылки спама),
    и была минимум одна точка справа hello.world.my@gmail.com - корректный.
    login - 0-9 пропуск A-Z пропуск _ пропуск a-z
    password - !"#$%&'()*+,-./0-9:;  //пропуск ?@ A-Z //пропуск a-z    заглавные + строчные буквы
    email - правильный email

     */

    private SignUpDataValidator() {
    }

    public static SignUpDataValidator getInstance() {
        if (instance == null) {
            instance = new SignUpDataValidator();
        }
        return new SignUpDataValidator();
    }

    public boolean validateLogin(char[] login) {
        if (!charArrValueLengthValidator(login, MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH)) {
            return false;
        }
        int lowerCaseLetterCount = 0;
        int upperCaseLetterCount = 0;
        char ch;
        boolean isCorrectLogin = true;
        int index = 0;
        while (isCorrectLogin && index < login.length) {
            ch = login[index];
            if (CHAR_CODE_A_SIGN <= ch && CHAR_CODE_Z_SIGN >= ch) {
                ++upperCaseLetterCount;
            } else if (CHAR_CODE_a_SIGN <= ch && CHAR_CODE_z_SIGN >= ch) {
                ++lowerCaseLetterCount;
            } else if (CHAR_CODE_0_SIGN > ch || (CHAR_CODE_9_SIGN < ch && ch != CHAR_CODE_UNDERSCORE_SIGN)) {
                isCorrectLogin = false;
            }
            ++index;
        }
        if (lowerCaseLetterCount == 0 || upperCaseLetterCount == 0) {
            isCorrectLogin = false;
        }
        return isCorrectLogin;
    }

    public boolean validatePasswordsForEquals(char[] passwordFirst, char[] passwordSecond) {
        if (passwordFirst == null || passwordSecond == null || passwordFirst.equals(passwordSecond)) {
            return false;
        }
        return true;
    }

    public boolean validatePassword(char[] password) {
        if (!charArrValueLengthValidator(password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH)) {
            return false;
        }
        int lowerCaseLetterCount = 0;
        int upperCaseLetterCount = 0;
        int specialSignCount = 0;
        char ch;
        boolean isCorrectPassword = true;
        int index = 0;
        while (isCorrectPassword && index < password.length) {
            ch = password[index];
            if (CHAR_CODE_A_SIGN <= ch && CHAR_CODE_Z_SIGN >= ch) {
                ++upperCaseLetterCount;
            } else if (CHAR_CODE_a_SIGN <= ch && CHAR_CODE_z_SIGN >= ch) {
                ++lowerCaseLetterCount;
            } else if ((CHAR_CODE_FIRST_SPECIAL_SIGN <= ch && CHAR_CODE_LAST_SPECIAL_SIGN >= ch) || CHAR_CODE_UNDERSCORE_SIGN == ch) {
                ++specialSignCount;
            } else if (CHAR_CODE_0_SIGN > ch || CHAR_CODE_9_SIGN < ch) {
                isCorrectPassword = false;
            }
            ++index;
        }
        if (upperCaseLetterCount == 0 || lowerCaseLetterCount == 0 || specialSignCount == 0) {
            isCorrectPassword = false;
        }
        return isCorrectPassword;
    }

    public boolean validateJakartaEmail(String emailStr) {
        boolean isValid = true;
        try {
            InternetAddress emailAddress = new InternetAddress(emailStr);
            emailAddress.validate();
        } catch (AddressException cause) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validateRegexEmail(String emailStr) {
        String foundedStr = "";
        if (emailStr == null || emailStr.length() <= MAX_EMAIL_LENGTH) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(emailStr);
            while (matcher.find()) {
                foundedStr = emailStr.substring(matcher.start(), matcher.end());
            }
        }
        return emailStr.length() == foundedStr.length();
    }

    public boolean validateGooglePhoneNumber(String phoneNumberStr) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phone = null;
        try {
            phone = phoneNumberUtil.parse(phoneNumberStr, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
        } catch (NumberParseException e) {
            e.printStackTrace(); //todo
        }
        return phoneNumberUtil.isValidNumber(phone);
    }

    public boolean validateRegexPhoneNumber(String phoneNumberStr) {
        if (phoneNumberStr == null || phoneNumberStr.length() <= MAX_NUMBER_LENGTH) {
            return false;
        }
        String foundedStr = "";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumberStr);
        while (matcher.find()) {
            foundedStr = phoneNumberStr.substring(matcher.start(), matcher.end());
        }
        return phoneNumberStr.length() == foundedStr.length();
    }

    public boolean validateFirstLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null ||
                firstName.length() > MAX_FIRST_LAST_NAME_LENGTH || lastName.length() > MAX_FIRST_LAST_NAME_LENGTH) {
            return false;
        }
        int firstNameReallyLength = firstName.trim().length();
        int lastNameReallyLength = lastName.trim().length();
        return !(firstNameReallyLength < MIN_FIRST_LAST_NAME_LENGTH || lastNameReallyLength < MIN_FIRST_LAST_NAME_LENGTH);
    }

    private boolean charArrValueLengthValidator(char[] value, int minLength, int maxLength) {
        return value != null && value.length >= minLength && value.length <= maxLength;
    }

    private boolean stringValueLengthValidator(String value, int minLength, int maxLength) {
        return value != null && value.length() >= minLength && value.length() <= maxLength;
    }
}
