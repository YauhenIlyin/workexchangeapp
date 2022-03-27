package by.ilyin.workexchange.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Iterator;

public class SessionRequestContent {

    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    private boolean isInvalidateSession = false;

    public SessionRequestContent(HttpServletRequest request) {
        requestParameters = new HashMap<>(request.getParameterMap());
        System.out.println("==============================");//todo delete
        System.out.println("request parameters:");//todo delete
        System.out.println(requestParameters.toString());//todo delete
        requestAttributes = new HashMap<>();
        Iterator<String> iterator;
        String attributeName;
        iterator = request.getAttributeNames().asIterator();
        System.out.println("=============================");//todo delete
        System.out.println("request attributes");//todo delete
        while (iterator.hasNext()) {
            attributeName = iterator.next();
            System.out.println(attributeName + " " + request.getAttribute(attributeName));//todo delete
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }
        System.out.println("===============================");//todo delete
        System.out.println("session attributes:");//todo delete
        HttpSession session = request.getSession();
        iterator = session.getAttributeNames().asIterator();
        sessionAttributes = new HashMap<>();
        while (iterator.hasNext()) {
            attributeName = iterator.next();
            System.out.println(attributeName + " " + session.getAttribute(attributeName));//todo delete
            sessionAttributes.put(attributeName, session.getAttribute(attributeName));
        }
    }

    //todo проверки на null во всем классе
    public void insertAttributesInSessionRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (isInvalidateSession) {
            session.invalidate();
        } else {
            Iterator iterator;
            String attributeName;
            iterator = sessionAttributes.keySet().iterator();
            while (iterator.hasNext()) {
                attributeName = (String) iterator.next();
                session.setAttribute(attributeName, sessionAttributes.get(attributeName));
            }
            iterator = requestParameters.keySet().iterator();
            while (iterator.hasNext()) {
                attributeName = (String) iterator.next();
                request.setAttribute(attributeName, requestParameters.get(attributeName));
            }
        }
    }

    public HashMap<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(HashMap<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(HashMap<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(HashMap<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public boolean isInvalidateSession() {
        return isInvalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        isInvalidateSession = invalidateSession;
    }
}
