package by.ilyin.workexchange.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class SignUpDataValidator {

    private static final String EMAIL_REGEX = "^[^@]+\\@[^@]+\\.[a-zA-Z]{2,}$";

    //todo правила регистрации:
    /*
    login - a-zA-Z !@#$%^&*? 1234567890
    password - a-zA-Z !@#$%^&*? 1234567890  заглавные + строчные буквы
    email - правильный email
    в email  проверяем, чтобы была только одна @
    (чтобы нельзя было списком передать много адресов для рассылки спама),
    и была минимум одна точка справа hello.world.my@gmail.com - корректный.

     */

    private static SignUpDataValidator instance;

    private SignUpDataValidator() {
    }

    public static SignUpDataValidator getInstance() {
        return new SignUpDataValidator();
    }

    public boolean validateLogin(char[] login) {
        return
        //todo patterns if need
    }

    public boolean validatePasswords(char[] password1, char[] password2) {
        return password1.equals(password2);
        //todo patterns if need
    }

    public boolean validateEmail(String emailStr) {
        boolean isValid = true;
        try {
            InternetAddress emailAddress = new InternetAddress(emailStr);
            emailAddress.validate();
        } catch (AddressException ex) {
            isValid = false;
        }
        return isValid;
    }

    public boolean validatePhoneNumber(String numberStr) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phone = null;
        try {
            phone = phoneNumberUtil.parse(numberStr, Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        return phoneNumberUtil.isValidNumber(phone);
    }
}
