package by.ilyin.workexchange.validator;

import by.ilyin.workexchange.controller.command.CommandType;

import java.util.HashSet;

public class CommandValidator {

    private static CommandValidator instance;
    private HashSet<String> commandNames;

    private CommandValidator() {
        CommandType[] commandTypeArr = CommandType.values();
        commandNames = new HashSet<>(commandTypeArr.length);
        for (CommandType container : commandTypeArr) {
            commandNames.add(container.name());
        }
    }

    public static CommandValidator getInstance() {
        if (instance == null) {
            instance = new CommandValidator();
        }
        return instance;
    }

    public boolean validateCommandName(String commandName) {
        return commandName != null && commandNames.contains(commandName.toUpperCase());
    }

}
