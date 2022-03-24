package by.ilyin.workexchange.controller.command;

public interface Command {

    CommandResult execute(SessionRequestContent sessionRequestContent);

}
