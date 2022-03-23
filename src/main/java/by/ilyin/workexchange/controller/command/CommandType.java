package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.controller.command.auth.RegisterAccountCommand;
import by.ilyin.workexchange.controller.command.auth.SignInCommand;
import by.ilyin.workexchange.controller.command.auth.SignOutCommand;

public enum CommandType {

    REGISTER_ACCOUNT(new RegisterAccountCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand());

    Command currentCommand;

    private CommandType(Command command) {
        currentCommand = command;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

}
