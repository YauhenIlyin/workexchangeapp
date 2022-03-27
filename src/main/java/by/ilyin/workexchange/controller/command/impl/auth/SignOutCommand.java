package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;

import java.util.List;

public class SignOutCommand implements Command {

    private AccountService accountService;
    private List<String> allowedRolesList;

    public SignOutCommand(AccountService accountService, List<String> allowedRolesList) {
        this.accountService = accountService;
        this.allowedRolesList = allowedRolesList;
    }


    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        return null;
    }
}
