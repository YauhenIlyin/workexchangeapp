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
        HashMap<String, char[]> securityParametersMap = sessionRequestContent.getSecurityParameters();
        char[] login = securityParametersMap.get(RequestParameterName.SIGN_UP_LOGIN);
        char[] passwordFirst = securityParametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_FIRST);
        char[] passwordSecond = securityParametersMap.get(RequestParameterName.SIGN_UP_PASSWORD_SECOND);
        String firstName = parametersMap.get(RequestParameterName.SIGN_UP_FIRST_NAME)[0];
        String secondName = parametersMap.get(RequestParameterName.SIGN_UP_SECOND_NAME)[0];
        String eMail = parametersMap.get(RequestParameterName.SIGN_UP_E_MAIL)[0];
        String mobileNumber = parametersMap.get(RequestParameterName.SIGN_UP_MOBILE_NUMBER)[0];
        boolean result = accountService.registerNewAccount(login, passwordFirst, passwordSecond, firstName, secondName, eMail, mobileNumber);

        CommandResult commandResult = null; //new CommandResult(PagePath.LOGIN_PAGE, PageRouteType.FORWARD);
        return commandResult;
    }
}
