package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.controller.command.auth.SignUpCommand;
import by.ilyin.workexchange.controller.command.auth.SignInCommand;
import by.ilyin.workexchange.controller.command.auth.SignOutCommand;
import by.ilyin.workexchange.model.service.AccountService;

public enum CommandType {

    SIGN_UP(new SignUpCommand(new AccountService()));
    //SIGN_IN(new SignInCommand(new AccountService())),
    //SIGN_OUT(new SignOutCommand(new AccountService()));

    Command currentCommand;

    private CommandType(Command command) {
        currentCommand = command;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

}
