package by.ilyin.workexchange.controller;

import by.ilyin.workexchange.controller.command.*;
import by.ilyin.workexchange.controller.command.CommandResult;
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
        Command command;
        Router router;
        SessionRequestContent sessionRequestContent;
        CommandResult commandResult;
        command = CommandAction.getCurrentCommand(request);
        sessionRequestContent = new SessionRequestContent(request);
        commandResult = command.execute(sessionRequestContent);
        commandResult.getSessionRequestContent().insertAttributesInSessionRequest(request);
        //response.getWriter().println("fffff");
    }

    @Override
    public void destroy() {
    }

}
