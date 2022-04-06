package by.ilyin.workexchange.controller.filter;

import by.ilyin.workexchange.controller.command.CommandType;
import by.ilyin.workexchange.controller.evidence.PagePath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"/controller/"})
public class ActivationFilter implements Filter {

    private final String COMMAND_PARAMETER_KEYWORD_NAME = "command";
    private final String COMMAND_SIGN_UP_ACTIVATION_NAME = CommandType.SIGN_UP_ACTIVATION.name().toLowerCase();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<String> optionalCommandName = Optional.ofNullable(request.getParameter(COMMAND_PARAMETER_KEYWORD_NAME));
        if (!optionalCommandName.isEmpty() && optionalCommandName.get().equals(COMMAND_SIGN_UP_ACTIVATION_NAME)) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            //todo права доступа может быть
            ServletContext servletContext = servletRequest.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PagePath.CONTROLLER_SERVLET);
            requestDispatcher.forward(request, response);
            return;//todo
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
