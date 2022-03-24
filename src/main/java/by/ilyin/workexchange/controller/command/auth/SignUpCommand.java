package by.ilyin.workexchange.controller.command.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.evidence.RequestParameterName;
import by.ilyin.workexchange.model.service.AccountService;
import by.ilyin.workexchange.model.service.RegistrationService;

public class SignUpCommand implements Command {

    private final AccountService accountService;

    public SignUpCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {

        char[] login = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_LOGIN)[0].toCharArray();
        char[] passwordFirst = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_PASSWORD_FIRST)[0].toCharArray();
        char[] passwordSecond = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_PASSWORD_SECOND)[0].toCharArray();
        String firstName = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_FIRST_NAME)[0];
        String secondName = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_SECOND_NAME)[0];
        String eMail = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_E_MAIL)[0];
        String mobileNumber = sessionRequestContent.getRequestParameters().get(RequestParameterName.REGISTRATION_MOBILE_NUMBER)[0];
        System.out.println(login);
        System.out.println(passwordFirst);
        System.out.println(eMail);
        this.accountService.registerNewAccount(login, passwordFirst, passwordSecond, firstName, secondName, eMail, mobileNumber);

        CommandResult commandResult = null; //new CommandResult(PagePath.LOGIN_PAGE, PageRouteType.FORWARD);
        return commandResult;
    }
}
