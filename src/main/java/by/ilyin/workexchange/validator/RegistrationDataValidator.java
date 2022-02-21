package by.ilyin.workexchange.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class RegistrationDataValidator {

    private static RegistrationDataValidator instance;

    private RegistrationDataValidator() {
    }

    public static RegistrationDataValidator getInstance() {
        return new RegistrationDataValidator();
    }

    public boolean validateTwoPasswords(char[] password1, char[] password2) {
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
