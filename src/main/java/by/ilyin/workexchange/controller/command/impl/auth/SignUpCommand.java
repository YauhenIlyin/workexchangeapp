package by.ilyin.workexchange.controller.command.impl.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.Router;
import by.ilyin.workexchange.controller.command.SessionRequestContent;
import by.ilyin.workexchange.controller.evidence.PagePath;
import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.exception.DaoException;
import by.ilyin.workexchange.model.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SignUpCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    private AccountService accountService;
    private List<String> allowedRoles;

    public SignUpCommand(AccountService accountService, List<String> allowedRoles) {
        this.accountService = accountService;
        this.allowedRoles = allowedRoles;
    }

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        logger.debug("start execute()");
        Router router = null;
        try {
            accountService.registerNewAccount(sessionRequestContent);
            boolean result = sessionRequestContent.isCurrentResultSuccessful();
            System.out.println(sessionRequestContent.isCurrentResultSuccessful());
            if (result) {
                logger.debug("result is successful, redirect to " + PagePath.LOGIN_PAGE);
                router = new Router(Router.RouteType.REDIRECT, PagePath.LOGIN_PAGE);
            } else {
                logger.debug("result is fail, forward to " + PagePath.REGISTRATION_PAGE);
                router = new Router(Router.RouteType.FORWARD, PagePath.REGISTRATION_PAGE);
            }
        } catch (DaoException e) {
            e.printStackTrace();//todo обработка в случае неуадочного выполнения
        }
        CommandResult commandResult = new CommandResult(router, sessionRequestContent);
        logger.debug("end execute()");
        return commandResult;
    }
}
