package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.model.evidence.RequestParameterName;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterAccountCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String login = request.getParameter(RequestParameterName.REGISTRATION_LOGIN);
        String passwordFirst = request.getParameter(RequestParameterName.REGISTRATION_PASSWORD_FIRST);
        String passwordSecond = request.getParameter(RequestParameterName.REGISTRATION_PASSWORD_SECOND);
        String firstName = request.getParameter(RequestParameterName.REGISTRATION_FIRST_NAME);
        String secondName = request.getParameter(RequestParameterName.REGISTRATION_SECOND_NAME);
        String eMail = request.getParameter(RequestParameterName.REGISTRATION_E_MAIL);
        String mobileNumber = request.getParameter(RequestParameterName.REGISTRATION_MOBILE_NUMBER);
        request.
    }

}
