package by.ilyin.workexchange.controller;

import by.ilyin.workexchange.controller.command.*;
import by.ilyin.workexchange.controller.command.CommandResult;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger();

    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("session:" + request.getSession().getId() + " doGet()");
        processRequest(request, response);

        /*response.setContentType("text/html");

        // Hello
        System.out.println("do in servlet");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");*/
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("session:" + request.getSession().getId() + " doPost()");
        processRequest(request, response);
    }

    //todo обработать эксепшены
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Command command = CommandFactory.getCurrentCommand(request);
        SessionRequestContent sessionRequestContent = new SessionRequestContent(request);
        CommandResult commandResult = command.execute(sessionRequestContent);
        commandResult.getSessionRequestContent().insertAttributesInSessionRequest(request);
        Router router = commandResult.getRouter();
        String pagePath = router.getPagePath();
        switch (router.getRouteType()) {
            case FORWARD:
                logger.debug("session:" + request.getSession().getId() + " FORWARD " + pagePath);
                RequestDispatcher dispatcher = request.getRequestDispatcher(pagePath);
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                logger.debug("session:" + request.getSession().getId() + " REDIRECT " + pagePath);
                response.sendRedirect(pagePath);
                break;
        }
    }

    @Override
    public void destroy() {
    }

}
