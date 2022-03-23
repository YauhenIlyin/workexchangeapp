package by.ilyin.workexchange.controller.command.auth;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandResult;
import by.ilyin.workexchange.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) {
        return new CommandResult(PagePath.LOGIN_PAGE, CommandResult.PageTransitionType.FORWARD);
    }

}

