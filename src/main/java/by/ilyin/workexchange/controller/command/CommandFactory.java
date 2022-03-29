package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.validator.CommandValidator;
import jakarta.servlet.http.HttpServletRequest;

public class CommandFactory {

    private static final String REQUEST_PARAMETER_COMMAND_NAME = "command";

    private CommandFactory() {
    }

    public static Command getCurrentCommand(HttpServletRequest request) {
        Command command;
        String currentCommandName = request.getParameter(REQUEST_PARAMETER_COMMAND_NAME);
        if (CommandValidator.getInstance().validateCommandName(currentCommandName)) {
            command = CommandType.valueOf(currentCommandName.toUpperCase()).getCurrentCommand();
        } else {
            command = CommandType.DEFAULT.getCurrentCommand();
        }
        return command;
    }
}
