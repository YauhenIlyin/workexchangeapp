package by.ilyin.workexchange.util.email;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

class EmailAuthenticator extends Authenticator {

    private String login; //todo char[]
    private String password; //todo char[]

    public EmailAuthenticator(String emailLogin, String emailPass) { //todo char[]
        this.login = emailLogin;
        this.password = emailPass;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }

}
