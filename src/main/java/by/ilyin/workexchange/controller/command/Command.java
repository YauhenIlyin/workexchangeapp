package by.ilyin.workexchange.controller.command;

public interface Command {

    CommandResult execute(SessionRequestContent sessionRequestContent);
    default void refresh(){ //todo protect F5
    }

}
