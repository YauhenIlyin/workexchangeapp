package by.ilyin.workexchange.controller.command.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;

public class SignOutCommand {

    private final AccountService accountService;

    public SignOutCommand(AccountService accountService) {
        this.accountService = accountService;
    }

}
