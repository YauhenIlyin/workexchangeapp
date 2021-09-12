package by.ilyin.workexchange.controller.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    void execute(HttpServletRequest request);

}
