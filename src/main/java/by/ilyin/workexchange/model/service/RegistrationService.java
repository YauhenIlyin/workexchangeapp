package by.ilyin.workexchange.model.service;

public class RegistrationService {
    //todo
    public boolean registerNewAccount(char[] login, char[] passwordFirst, char[] passwordSecond,
                                      String firstName, String secondName, String eMail, String mobileNumber) {
        if (!passwordFirst.equals(passwordSecond)) {
            return false;
        }
        
    }
}
