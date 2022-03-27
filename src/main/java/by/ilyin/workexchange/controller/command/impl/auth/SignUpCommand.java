package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.model.service.AccountService;

import java.util.HashMap;
import java.util.List;

public class SignUpCommand implements Command {

    private AccountService accountService;
    private List<String> allowedRoles;

    public SignUpCommand(AccountService accountService, List<String> allowedRoles) {
        this.accountService = accountService;
        this.allowedRoles = allowedRoles;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        HashMap<String, String[]> parametersMap = sessionRequestContent.getRequestParameters();
        char[] login = parametersMap.get(RequestParameterName.SIGN_UP_LOGIN)[0].toCharArray();
        char[] passwordFirst = parametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_FIRST)[0].toCharArray();
        char[] passwordSecond = parametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_SECOND)[0].toCharArray();
        String firstName = parametersMap.get(RequestParameterName.SIGN_UP_FIRST_NAME)[0];
        String secondName = parametersMap.get(RequestParameterName.SIGN_UP_SECOND_NAME)[0];
        String eMail = parametersMap.get(RequestParameterName.SIGN_UP_E_MAIL)[0];
        String mobileNumber = parametersMap.get(RequestParameterName.SIGN_UP_MOBILE_NUMBER)[0];
        System.out.println(login);
        System.out.println(passwordFirst);
        System.out.println(eMail);
        this.accountService.registerNewAccount(login, passwordFirst, passwordSecond, firstName, secondName, eMail, mobileNumber);

        CommandResult commandResult = null; //new CommandResult(PagePath.LOGIN_PAGE, PageRouteType.FORWARD);
        return commandResult;
    }
}
