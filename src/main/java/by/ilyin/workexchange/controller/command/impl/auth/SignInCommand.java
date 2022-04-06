package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.model.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SignInCommand implements Command {

    //private static final String PAGE_PATH_SUCCESS = PagePath.MAIN_PAGE.getPagePath();
    //private static final String PAGE_PATH_FAILURE = PagePath.LOGIN_PAGE.getPagePath();
    private final Logger logger = LogManager.getLogger();

    private AccountService accountService;
    private List<String> allowedRoles;

    public SignInCommand(AccountService accountService, List<String> allowedRoles) {
        this.accountService = accountService;
        this.allowedRoles = allowedRoles;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        logger.debug("start execute()");
        Router router;
        accountService.signIn(sessionRequestContent);

        return null;
    }
}

