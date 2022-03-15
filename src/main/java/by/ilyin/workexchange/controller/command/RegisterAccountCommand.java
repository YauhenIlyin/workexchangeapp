package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.model.evidence.RequestParameterName;
import by.ilyin.workexchange.model.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterAccountCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        RegistrationService registrationService = new RegistrationService();
        char[] login = request.getParameter(RequestParameterName.REGISTRATION_LOGIN).toCharArray();
        char[] passwordFirst = request.getParameter(RequestParameterName.REGISTRATION_PASSWORD_FIRST).toCharArray();
        char[] passwordSecond = request.getParameter(RequestParameterName.REGISTRATION_PASSWORD_SECOND).toCharArray();
        String firstName = request.getParameter(RequestParameterName.REGISTRATION_FIRST_NAME);
        String secondName = request.getParameter(RequestParameterName.REGISTRATION_SECOND_NAME);
        String eMail = request.getParameter(RequestParameterName.REGISTRATION_E_MAIL);
        String mobileNumber = request.getParameter(RequestParameterName.REGISTRATION_MOBILE_NUMBER);
        registrationService.registerNewAccount(login, passwordFirst, passwordSecond, firstName, secondName, eMail, mobileNumber);
    }

}
