package by.ilyin.workexchange.controller;

import by.ilyin.workexchange.controller.command.*;
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
        System.out.println("do in servlet");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(11);

        System.out.println(22);
        processRequest(request, response);


    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getParameter("command"));
        String currentCommandStr = request.getParameter("command");
        Router.RouteType route = null;

        if (CommandValidator.getInstance().validateCommand(currentCommandStr)) {
            Command command = CommandType.valueOf(currentCommandStr.toUpperCase()).getCurrentCommand();
            System.out.println("command is correct");
            SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
            CommandResult commandResult = command.execute(sessionRequestContent);
            commandResult.getSessionRequestContent().insertAttributesInSessionRequest(request);
            route = commandResult.getRouter().getRouteType();
            //response.getWriter().println("fffff");
        } else {
            //Router.RouteType.ERROR
        }
        switch (route) {
            case FORWARD:
                break;
            case REDIRECT:
                break;
            case ERROR:
                break;
        }
    }

    @Override
    public void destroy() {
    }

}
