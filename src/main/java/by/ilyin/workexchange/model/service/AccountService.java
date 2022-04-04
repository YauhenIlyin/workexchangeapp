package by.ilyin.workexchange.model.service;

import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.dao.AbstractDao;
import by.ilyin.workexchange.model.dao.EntityTransaction;
import by.ilyin.workexchange.model.dao.UserDao;
import by.ilyin.workexchange.model.dao.impl.UserDaoImpl;
import by.ilyin.workexchange.model.entity.User;
import by.ilyin.workexchange.model.evidence.AccountStatus;
import by.ilyin.workexchange.model.evidence.InfoMessagesKeyWords;
import by.ilyin.workexchange.util.DateTimeManager;
import by.ilyin.workexchange.util.email.EmailManager;
import by.ilyin.workexchange.validator.SignUpDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;

public class AccountService {

    private static Logger logger = LogManager.getLogger();

    //todo правила регистрации:
    /*
    login - a-zA-Z !@#$%^&*? 1234567890
    password - a-zA-Z !@#$%^&*? 1234567890  заглавные + строчные буквы
    email - правильный email
     */
    public SessionRequestContent registerNewAccount(SessionRequestContent sessionRequestContent) throws DaoException {
        logger.debug("start registerNewAccount()");
        boolean isValidData = validateRegistrationData(sessionRequestContent).isCurrentResultSuccessful();
        logger.debug("registerNewAccount() isValidData: " + isValidData);
        if (isValidData) {
            boolean isFreeLogin = isFreeAccountLogin(sessionRequestContent).isCurrentResultSuccessful();
            logger.debug("is free login: " + isFreeLogin);
            if (isFreeLogin) {
                HashMap<String, String[]> paramMap = sessionRequestContent.getRequestParameters();
                HashMap<String, char[]> securityParamMap = sessionRequestContent.getSecurityParameters();
                UserDao userDao = new UserDaoImpl();
                EntityTransaction transaction = new EntityTransaction();
                transaction.initTransaction((AbstractDao) userDao);
                User user = new User();
                user.createInnerBuilder().setFirstName(paramMap.get(RequestParameterName.SIGN_UP_FIRST_NAME)[0]).setLastName(paramMap.get(RequestParameterName.SIGN_UP_LAST_NAME)[0]).setEmail(paramMap.get(RequestParameterName.SIGN_UP_E_MAIL)[0]).setMobileNumber(paramMap.get(RequestParameterName.SIGN_UP_MOBILE_NUMBER)[0]).setAccountStatus(AccountStatus.WAITING_ACTIVATION).setRegistrationDate(DateTimeManager.getInstance().getCurrentLocalDateTime());
                char[] login = securityParamMap.get(RequestParameterName.SIGN_UP_LOGIN);
                char[] password = securityParamMap.get(RequestParameterName.SIGN_UP_PASSWORD_FIRST);
                userDao.addAccountWithoutPassword(user, login);
                userDao.updateAccountPasswordByLogin(login, password);
                String activationCode = userDao.getActivationCodeByUserLogin(login);
                sessionRequestContent.getRequestAttributes().put(RequestParameterName.SIGN_UP_ACTIVATION_CODE, activationCode);
                sessionRequestContent.getRequestAttributes().put(RequestParameterName.SIGN_UP_E_MAIL, user.getEmail());
                sendActivationMail(sessionRequestContent);
                transaction.commit();
                transaction.endTransaction();
            }
        }
        logger.debug("registerNewAccount() result: " + sessionRequestContent.isCurrentResultSuccessful());
        return sessionRequestContent;
    }

    private SessionRequestContent validateRegistrationData(SessionRequestContent sessionRequestContent) throws DaoException {
        HashMap<String, String[]> parametersMap = sessionRequestContent.getRequestParameters();
        HashMap<String, char[]> securityParametersMap = sessionRequestContent.getSecurityParameters();
        char[] login = securityParametersMap.get(RequestParameterName.SIGN_UP_LOGIN);
        char[] passwordFirst = securityParametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_FIRST);
        char[] passwordSecond = securityParametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_SECOND);
        String firstName = parametersMap.get(RequestParameterName.SIGN_UP_FIRST_NAME)[0];
        String lastName = parametersMap.get(RequestParameterName.SIGN_UP_LAST_NAME)[0];
        String eMail = parametersMap.get(RequestParameterName.SIGN_UP_E_MAIL)[0];
        String mobileNumber = parametersMap.get(RequestParameterName.SIGN_UP_MOBILE_NUMBER)[0];
        HashMap<String, Object> requestAttributes = sessionRequestContent.getRequestAttributes();
        SignUpDataValidator validator = SignUpDataValidator.getInstance();
        if (!validator.validateLogin(login)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_LOGIN_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validatePasswordsForEquals(passwordFirst, passwordSecond)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_DIFFERENT_PASSWORDS, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validatePassword(passwordFirst)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_PASSWORD_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateFirstLastName(firstName, lastName)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_FIRST_LAST_NAME_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateRegexEmail(eMail)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_EMAIL_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateRegexPhoneNumber(mobileNumber)) {
            requestAttributes.put(InfoMessagesKeyWords.REGISTRATION_ERROR_MOBILE_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        return sessionRequestContent;
    }

    public SessionRequestContent isFreeAccountLogin(SessionRequestContent sessionRequestContent) throws DaoException {
        char[] login = sessionRequestContent.getSecurityParameters().get(RequestParameterName.SIGN_UP_LOGIN);
        if (login != null && login.length > 0) {
            UserDao userDao = new UserDaoImpl();
            EntityTransaction transaction = new EntityTransaction();
            transaction.initTransaction((AbstractDao) userDao);
            boolean isFreeAccountLogin = userDao.isFreeAccountLogin(login);
            transaction.commit();
            transaction.endTransaction();
            if (!isFreeAccountLogin) {
                sessionRequestContent.setCurrentResultSuccessful(false);
                sessionRequestContent.getRequestAttributes().put(InfoMessagesKeyWords.REGISTRATION_INFO_LOGIN_BUSY, null);
            }
        } else {
            sessionRequestContent.setCurrentResultSuccessful(false);
            sessionRequestContent.getRequestAttributes().put(InfoMessagesKeyWords.REGISTRATION_ERROR_LOGIN_FORMAT, null);
        }
        return sessionRequestContent;
    }

    public SessionRequestContent activateAccount(SessionRequestContent sessionRequestContent) {
        HashMap<String, String[]> parametersMap = sessionRequestContent.getRequestParameters();
        String activationCode = parametersMap.get(RequestParameterName.SIGN_UP_ACTIVATION_CODE)[0];
        if(activationCode != null && activationCode.length() > 0) {
            UserDao userDao = new UserDaoImpl();
            EntityTransaction transaction = new EntityTransaction();
            transaction.initTransaction((AbstractDao) userDao);
            userDao.findAccountIdByActivationCode(sessionRequestContent);
        }
    }

    public SessionRequestContent sendActivationMail(SessionRequestContent sessionRequestContent) {
        logger.debug("SendActivateMail() start");
        HashMap attributesMap = sessionRequestContent.getRequestAttributes();
        if (attributesMap.containsKey(RequestParameterName.SIGN_UP_E_MAIL) && attributesMap.containsKey(RequestParameterName.SIGN_UP_ACTIVATION_CODE) && attributesMap.get(RequestParameterName.SIGN_UP_E_MAIL) != null && attributesMap.get(RequestParameterName.SIGN_UP_ACTIVATION_CODE) != null) {
            logger.debug("Attributes contain email address and activation code");
            String email = (String) sessionRequestContent.getRequestAttributes().get(RequestParameterName.SIGN_UP_E_MAIL);
            String activationCode = (String) sessionRequestContent.getRequestAttributes().get(RequestParameterName.SIGN_UP_ACTIVATION_CODE);
            EmailManager emailManager = new EmailManager();
            emailManager.sendActivationMail(email, activationCode);
        } else {
            logger.debug("Attributes not contain email address and activation code");
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        return sessionRequestContent;
    }

}
