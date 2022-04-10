package by.ilyin.workexchange.model.service;

import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.controller.evidence.SessionAttributeName;
import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.dao.AbstractDao;
import by.ilyin.workexchange.model.dao.EntityTransaction;
import by.ilyin.workexchange.model.dao.UserDao;
import by.ilyin.workexchange.model.dao.impl.UserDaoImpl;
import by.ilyin.workexchange.model.entity.User;
import by.ilyin.workexchange.model.evidence.AccountStatus;
import by.ilyin.workexchange.model.evidence.InfoKeyWordMessage;
import by.ilyin.workexchange.util.DateTimeManager;
import by.ilyin.workexchange.util.email.EmailManager;
import by.ilyin.workexchange.validator.SignUpDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

public class AccountService {

    private static Logger logger = LogManager.getLogger();

    //todo правила регистрации:
    /*
    login - a-zA-Z !@#$%^&*? 1234567890
    password - a-zA-Z !@#$%^&*? 1234567890  заглавные + строчные буквы
    email - правильный email
     */
    public SessionRequestContent registerNewAccount(SessionRequestContent sessionRequestContent) throws DaoException {
        EntityTransaction transaction = null;
        UserDao userDao;
        User user;
        HashMap<String, String[]> paramMap;
        HashMap<String, char[]> securityParamMap;
        logger.debug("start registerNewAccount()");
        validateRegistrationData(sessionRequestContent);
        boolean isValidData = sessionRequestContent.isCurrentResultSuccessful();
        logger.debug("registerNewAccount() isValidData: " + isValidData);
        if (isValidData) {
            isFreeAccountLogin(sessionRequestContent);
            boolean isFreeLogin = sessionRequestContent.isCurrentResultSuccessful();
            logger.debug("is free login: " + isFreeLogin);
            if (isFreeLogin) {
                paramMap = sessionRequestContent.getRequestParameters();
                securityParamMap = sessionRequestContent.getSecurityParameters();
                try {
                    userDao = new UserDaoImpl();
                    transaction = new EntityTransaction();
                    transaction.initTransaction((AbstractDao) userDao);
                    user = new User();
                    user.createInnerBuilder()
                            .setFirstName(paramMap.get(RequestParameterName.SIGN_UP_FIRST_NAME)[0])
                            .setLastName(paramMap.get(RequestParameterName.SIGN_UP_LAST_NAME)[0])
                            .setEmail(paramMap.get(RequestParameterName.SIGN_UP_E_MAIL)[0])
                            .setMobileNumber(paramMap.get(RequestParameterName.SIGN_UP_MOBILE_NUMBER)[0])
                            .setAccountStatus(AccountStatus.WAITING_ACTIVATION)
                            .setRegistrationDate(DateTimeManager.getInstance().getCurrentLocalDateTime());
                    char[] login = securityParamMap.get(RequestParameterName.SIGN_UP_LOGIN);
                    char[] password = securityParamMap.get(RequestParameterName.SIGN_UP_PASSWORD_FIRST);
                    userDao.addAccountWithoutPassword(user, login);
                    userDao.updateAccountPasswordByLogin(login, password);
                    String activationCode = userDao.getActivationCodeByUserLogin(login);
                    sessionRequestContent.getRequestAttributes().put(RequestParameterName.SIGN_UP_ACTIVATION_CODE, activationCode);
                    sessionRequestContent.getRequestAttributes().put(RequestParameterName.SIGN_UP_E_MAIL, user.getEmail());
                    sendActivationMail(sessionRequestContent);
                    transaction.commit();
                } catch (DaoException cause) {
                    transaction.rollback();
                    sessionRequestContent.setCurrentResultSuccessful(false);
                } finally {
                    transaction.endTransaction();
                }
            }
        }
        logger.debug("registerNewAccount() result: " + sessionRequestContent.isCurrentResultSuccessful());
        return sessionRequestContent;
    }

    private SessionRequestContent validateRegistrationData(SessionRequestContent sessionRequestContent) {
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
        if (!validator.fullValidateLogin(login)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_LOGIN_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validatePasswordsForEquals(passwordFirst, passwordSecond)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_DIFFERENT_PASSWORDS, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.fullValidatePassword(passwordFirst)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_PASSWORD_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateFirstLastName(firstName, lastName)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_FIRST_LAST_NAME_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateRegexEmail(eMail)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_EMAIL_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        if (!validator.validateRegexPhoneNumber(mobileNumber)) {
            requestAttributes.put(InfoKeyWordMessage.REGISTRATION_ERROR_MOBILE_FORMAT, null);
            sessionRequestContent.setCurrentResultSuccessful(false);
        }
        return sessionRequestContent;
    }

    public SessionRequestContent isFreeAccountLogin(SessionRequestContent sessionRequestContent) {
        char[] login = sessionRequestContent.getSecurityParameters().get(RequestParameterName.SIGN_UP_LOGIN);
        boolean isFreeLogin = false;
        if (!SignUpDataValidator.getInstance().fullValidateLogin(login)) {
            sessionRequestContent.getRequestAttributes().put(InfoKeyWordMessage.REGISTRATION_ERROR_LOGIN_FORMAT, null);
        } else {
            EntityTransaction transaction = null;
            UserDao userDao;
            try {
                userDao = new UserDaoImpl();
                transaction = new EntityTransaction();
                transaction.initTransaction((AbstractDao) userDao);
                isFreeLogin = userDao.isFreeAccountLogin(login);
                transaction.commit();
            } catch (DaoException cause) {
                logger.error("Exception was generated when trying to check isFree login." + cause);
            } finally {
                transaction.endTransaction();
            }
            if (!isFreeLogin) {
                sessionRequestContent.getRequestAttributes().put(InfoKeyWordMessage.REGISTRATION_INFO_LOGIN_BUSY, null);
            }
        }
        sessionRequestContent.setCurrentResultSuccessful(isFreeLogin);
        return sessionRequestContent;
    }

    public SessionRequestContent activateAccount(SessionRequestContent sessionRequestContent) {
        HashMap<String, String[]> parametersMap = sessionRequestContent.getRequestParameters();
        String activationCode = parametersMap.get(RequestParameterName.SIGN_UP_ACTIVATION_CODE)[0];
        System.out.println("activationCode " + activationCode);
        boolean result = false;
        if (SignUpDataValidator.getInstance().validateActivationCodeLength(activationCode)) {
            EntityTransaction transaction = new EntityTransaction();
            UserDao userDao = new UserDaoImpl();
            Optional<Long> optionalUserId;
            try {
                transaction.initTransaction((AbstractDao) userDao);
                optionalUserId = userDao.findAccountIdByActivationCode(activationCode);
                if (!optionalUserId.isEmpty()) {
                    userDao.activateAccountById(optionalUserId.get());
                    transaction.commit();
                    result = true;
                }
            } catch (DaoException cause) {
                transaction.rollback();
                logger.error("Exception was generated when trying to activate." + cause);
            } finally {
                transaction.endTransaction();
            }
        }
        sessionRequestContent.setCurrentResultSuccessful(result);
        return sessionRequestContent;
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

    public SessionRequestContent signIn(SessionRequestContent sessionRequestContent) {
        char[] login = sessionRequestContent.getSecurityParameters().get(RequestParameterName.SIGN_UP_LOGIN);
        char[] password = sessionRequestContent.getSecurityParameters().get(RequestParameterName.SIGN_IN_PASSWORD);
        SignUpDataValidator validator = SignUpDataValidator.getInstance();
        boolean operationResult = false;
        if (validator.simpleValidateLogin(login) && validator.simpleValidatePassword(password)) {
            EntityTransaction transaction = new EntityTransaction();
            UserDao userDao = new UserDaoImpl();
            User user = null;
            try {
                transaction.initTransaction((AbstractDao) userDao);
                Optional<User> optionalUser = userDao.findEntityByLogin(login);
                if (optionalUser.isPresent()) {
                    user = optionalUser.get();
                    String accountStatus = user.getAccountStatus();
                    if (accountStatus.equals(AccountStatus.ACTIVATED)) {
                        long userId = user.getId();
                        operationResult = userDao.checkAuthAccountLoginPass(login, password, userId);
                    }
                }
                HashMap sessionAttr = sessionRequestContent.getSessionAttributes();
                if (operationResult) {
                    sessionAttr.put(SessionAttributeName.AUTH_STATUS, "true");
                    sessionAttr.put(SessionAttributeName.USER_ID, user.getId());
                    sessionAttr.put(SessionAttributeName.USER_FIRST_NAME, user.getFirstName());
                    sessionAttr.put(SessionAttributeName.USER_LAST_NAME, user.getLastName());
                    sessionAttr.put(SessionAttributeName.USER_ROLE, user.getRole());
                } else {
                    sessionAttr.put()
                }
            } catch (DaoException cause) {
                operationResult = false;
                logger.error("Auth exception. ", cause);
            } finally {
                transaction.endTransaction();
            }
        }
        sessionRequestContent.setCurrentResultSuccessful(operationResult);
        return sessionRequestContent;
    }

}
