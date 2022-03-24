package by.ilyin.workexchange.controller.command.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;

public class SignInCommand {

    //private static final String PAGE_PATH_SUCCESS = PagePath.MAIN_PAGE.getPagePath();
    //private static final String PAGE_PATH_FAILURE = PagePath.LOGIN_PAGE.getPagePath();
    private final AccountService accountService;

    public SignInCommand(AccountService accountService) {
        this.accountService = accountService;
    }


}

