package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SignUpActivationCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    private AccountService accountService;
    private List<String> allowedRoles;

    public SignUpActivationCommand(AccountService accountService, List<String> allowedRoles) {
        this.accountService = accountService;
        this.allowedRoles = allowedRoles;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        logger.debug("start execute()");
        Router router = null;
        accountService.activateAccount(sessionRequestContent);
        return null;
    }

    @Override
    public void refresh() {
        Command.super.refresh();
    }
}
