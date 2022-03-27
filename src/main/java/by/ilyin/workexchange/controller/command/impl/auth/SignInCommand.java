package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;

import java.util.List;

public class SignInCommand implements Command {

    //private static final String PAGE_PATH_SUCCESS = PagePath.MAIN_PAGE.getPagePath();
    //private static final String PAGE_PATH_FAILURE = PagePath.LOGIN_PAGE.getPagePath();
    private AccountService accountService;
    private List<String> allowedRoles;

    public SignInCommand(AccountService accountService, List<String> allowedRoles) {
        this.accountService = accountService;
        this.allowedRoles = allowedRoles;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        return null;
    }
}

