package by.ilyin.workexchange.controller.filter;

import by.ilyin.workexchange.controller.command.CommandType;
import by.ilyin.workexchange.controller.evidence.PagePath;
import by.ilyin.workexchange.controller.evidence.SessionAttributeName;
import by.ilyin.workexchange.model.evidence.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;

@WebFilter(urlPatterns = {"/pages/*"})
public class AuthFilter implements Filter {

    private final Logger logger = LogManager.getLogger();
    private final String DEFAULT_USER_ROLE = UserRole.GUEST;
    private final String SESSION_AUTH_ATTRIBUTE_NAME = SessionAttributeName.AUTH_STATUS;
    private final String ALLOWED_AUTH_PARAMETER_VALUE = "true";
    private final String NOT_ALLOWED_AUTH_PARAMETER_VALUE = "false"; //todo
    private final String COMMAND_PARAMETER_KEYWORD_NAME = "command";
    private HashSet<String> allowedCommandNames;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedCommandNames = new HashSet<>();
        allowedCommandNames.add(CommandType.SIGN_UP_ACTIVATION.name().toLowerCase());
        allowedCommandNames.add(CommandType.SIGN_IN.name().toLowerCase());
        allowedCommandNames.add(CommandType.SIGN_UP.name().toLowerCase());
        allowedCommandNames.add(CommandType.SIGN_OUT.name().toLowerCase()); //todo нужно ли тут?
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String authStatusStrValue = (String) session.getAttribute(SESSION_AUTH_ATTRIBUTE_NAME);
        String commandNameStrValue = request.getParameter(COMMAND_PARAMETER_KEYWORD_NAME);
        //todo убрать == Null если equals учитывает,  проверил instanceof учитывает
        if (!allowedCommandNames.contains(commandNameStrValue) && (authStatusStrValue == null || !authStatusStrValue.equals(ALLOWED_AUTH_PARAMETER_VALUE))) {
            session.setAttribute(SessionAttributeName.USER_ROLE, DEFAULT_USER_ROLE);
            session.setAttribute(SessionAttributeName.AUTH_STATUS, NOT_ALLOWED_AUTH_PARAMETER_VALUE);
            //cookie получать локаль, до авторизации
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PagePath.LOGIN_PAGE);
            dispatcher.forward(request, response);
            return;//todo
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
