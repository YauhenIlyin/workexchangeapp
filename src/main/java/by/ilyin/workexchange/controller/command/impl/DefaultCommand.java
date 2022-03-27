package by.ilyin.workexchange.controller.command.impl;

import by.ilyin.workexchange.controller.command.*;
import by.ilyin.workexchange.controller.evidence.PagePath;

public class DefaultCommand implements Command {

    @Override
    public CommandResult execute(SessionRequestContent sessionRequestContent) {
        sessionRequestContent.setInvalidateSession(true);
        Router router = new Router(Router.RouteType.REDIRECT, PagePath.LOGIN_PAGE);
        return new CommandResult(router, sessionRequestContent);
    }
}
