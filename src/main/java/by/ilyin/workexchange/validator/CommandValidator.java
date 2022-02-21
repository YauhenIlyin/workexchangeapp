package by.ilyin.workexchange.validator;

import by.ilyin.workexchange.controller.command.CommandType;

import java.util.HashSet;

public class CommandValidator {

    private static HashSet<String> commandStrHashSet;
    private static CommandValidator instance;

    private CommandValidator() {
        CommandType[] commandTypeArr = CommandType.values();
        commandStrHashSet = new HashSet<>(commandTypeArr.length);
        for (CommandType container : commandTypeArr) {
            commandStrHashSet.add(container.toString());
        }
    }

    public static CommandValidator getInstance() {
        return new CommandValidator();
    }


    public boolean validateCommand(String commandStr) {
        return commandStr != null && commandStrHashSet.contains(commandStr.toUpperCase());
        //todo checking parameters
    }

}
