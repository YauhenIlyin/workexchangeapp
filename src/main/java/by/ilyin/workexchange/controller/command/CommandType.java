package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.controller.command.impl.DefaultCommand;
import by.ilyin.workexchange.controller.command.impl.auth.SignInCommand;
import by.ilyin.workexchange.controller.command.impl.auth.SignOutCommand;
import by.ilyin.workexchange.controller.command.impl.auth.SignUpCommand;
import by.ilyin.workexchange.model.evidence.UserRole;
import by.ilyin.workexchange.model.service.AccountService;

import java.util.List;

public enum CommandType {

    DEFAULT(new DefaultCommand()),
    SIGN_UP(new SignUpCommand(new AccountService(), List.of(UserRole.GUEST))),
    SIGN_IN(new SignInCommand(new AccountService(), List.of(UserRole.GUEST))),
    SIGN_OUT(new SignOutCommand(new AccountService(), List.of(UserRole.ADMIN, UserRole.SIMPLE_USER)));

    private Command currentCommand;

    private CommandType(Command command) {
        currentCommand = command;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

}
