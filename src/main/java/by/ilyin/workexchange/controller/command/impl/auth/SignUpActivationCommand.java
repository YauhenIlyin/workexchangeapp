package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.PagePath;
import by.ilyin.workexchange.exception.DaoException;
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
        router = new Router(Router.RouteType.REDIRECT, "../" + PagePath.LOGIN_PAGE); //todo можно ли так делать
        sessionRequestContent.setInvalidateSession(true);//todo проверку на auth И не выкидывать, если залогинен на другом окне

        CommandResult commandResult = new CommandResult(router, sessionRequestContent);
        return commandResult;
    }

    @Override
    public void refresh() {
        Command.super.refresh();
    }
}
