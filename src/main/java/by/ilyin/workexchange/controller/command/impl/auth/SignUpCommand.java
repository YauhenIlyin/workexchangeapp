package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.PagePath;
import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.exception.DaoException;
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
        System.out.println("========================================signUpCommand");
        Router router = null;
        try {
            accountService.registerNewAccount(sessionRequestContent);
            boolean result = sessionRequestContent.isCurrentResultSuccessful();
            System.out.println(sessionRequestContent.isCurrentResultSuccessful());
            if (result) {
                router = new Router(Router.RouteType.REDIRECT, PagePath.LOGIN_PAGE);
            } else {
                router = new Router(Router.RouteType.FORWARD, PagePath.REGISTRATION_PAGE);
            }
        } catch (DaoException e) {
            e.printStackTrace();//todo command exception
        }
        CommandResult commandResult = new CommandResult(router, sessionRequestContent);
        return commandResult;
    }
}
