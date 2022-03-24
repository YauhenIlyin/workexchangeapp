package by.ilyin.workexchange.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Iterator;

public class SessionRequestContent {

    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    public SessionRequestContent(HttpServletRequest request) {
        requestParameters = new HashMap<>(request.getParameterMap());
        requestAttributes = new HashMap<>();
        Iterator<String> iterator;
        String attributeName;
        iterator = request.getAttributeNames().asIterator();
        while (iterator.hasNext()) {
            attributeName = iterator.next();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }
        HttpSession session = request.getSession();
        iterator = session.getAttributeNames().asIterator();
        while (iterator.hasNext()) {
            attributeName = iterator.next();
            sessionAttributes.put(attributeName, session.getAttribute(attributeName));
        }

    }

    //todo проверки на null во всем классе
    public void insertAttributesInSessionRequest(HttpServletRequest request) {
        Iterator iterator = requestParameters.keySet().iterator();
        String attributeName;
        while (iterator.hasNext()) {
            attributeName = (String) iterator.next();
            request.setAttribute(attributeName, requestParameters.get(attributeName));
        }
        HttpSession session = request.getSession();
        iterator = sessionAttributes.keySet().iterator();
        while (iterator.hasNext()) {
            attributeName = (String) iterator.next();
            session.setAttribute(attributeName, sessionAttributes.get(attributeName));
        }
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
