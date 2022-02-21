package by.ilyin.workexchange.controller;

import by.ilyin.workexchange.controller.command.Command;
import by.ilyin.workexchange.controller.command.CommandType;
import by.ilyin.workexchange.validator.CommandValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ControllerServlet", urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(11);

        System.out.println(22);

        String currentCommandStr = request.getParameter("command");
        if (CommandValidator.getInstance().validateCommand(currentCommandStr)) {
            CommandType commandType = CommandType.valueOf(currentCommandStr.toUpperCase());
            Command command = commandType.getCurrentCommand();
            System.out.println("command is correct");
            command.execute(request);
            response.getWriter().println("fffff");
        } else {
            //todo error
            System.out.println("this is error");
        }
    }

    @Override
    public void destroy() {
    }

}
