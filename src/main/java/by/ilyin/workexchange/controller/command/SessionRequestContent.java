package by.ilyin.workexchange.controller.command;

import by.ilyin.workexchange.controller.evidence.RequestParameterName;
import by.ilyin.workexchange.util.SecurityDataCleaner;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class SessionRequestContent {

    private static HashSet<String> securityParameterNames = new HashSet<>(
            List.of(RequestParameterName.SIGN_UP_LOGIN,
                    RequestParameterName.SIGN_UP_PASSWORD_FIRST,
                    RequestParameterName.SIGN_UP_PASSWORD_SECOND,
                    RequestParameterName.SIGN_IN_LOGIN,
                    RequestParameterName.SIGN_IN_PASSWORD));

    private HashMap<String, char[]> securityParameters; //login password1 password2 ( просто комментарий)
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;
    private boolean isInvalidateSession = false;

    public SessionRequestContent(HttpServletRequest request) {
        Iterator<String> iterator;
        String keyWord;
        requestParameters = new HashMap<>();
        securityParameters = new HashMap<>();
        iterator = request.getParameterNames().asIterator();
        while (iterator.hasNext()) {
            keyWord = iterator.next();
            if (securityParameterNames.contains(keyWord)) {
                securityParameters.put(keyWord, request.getParameter(keyWord).toCharArray());
            } else {
                requestParameters.put(keyWord, request.getParameterValues(keyWord));
            }
        }
        System.out.println("==============================");//todo delete
        System.out.println("request parameters:");//todo delete
        System.out.println(requestParameters.toString());//todo delete
        requestAttributes = new HashMap<>();

        iterator = request.getAttributeNames().asIterator();
        System.out.println("=============================");//todo delete
        System.out.println("request attributes");//todo delete
        while (iterator.hasNext()) {
            keyWord = iterator.next();
            System.out.println(keyWord + " " + request.getAttribute(keyWord));//todo delete
            requestAttributes.put(keyWord, request.getAttribute(keyWord));
        }
        System.out.println("===============================");//todo delete
        System.out.println("session attributes:");//todo delete
        HttpSession session = request.getSession();
        iterator = session.getAttributeNames().asIterator();
        sessionAttributes = new HashMap<>();
        while (iterator.hasNext()) {
            keyWord = iterator.next();
            System.out.println(keyWord + " " + session.getAttribute(keyWord));//todo delete
            sessionAttributes.put(keyWord, session.getAttribute(keyWord));
        }
    }

    //todo проверки на null во всем классе
    public void insertAttributesInSessionRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Iterator iterator;
        String keyWord;
        if (isInvalidateSession) {
            session.invalidate();
        } else {
            iterator = sessionAttributes.keySet().iterator();
            while (iterator.hasNext()) {
                keyWord = (String) iterator.next();
                session.setAttribute(keyWord, sessionAttributes.get(keyWord));
            }
            iterator = requestParameters.keySet().iterator();
            while (iterator.hasNext()) {
                keyWord = (String) iterator.next();
                request.setAttribute(keyWord, requestParameters.get(keyWord));
            }
        }
        iterator = securityParameters.keySet().iterator();
        while (iterator.hasNext()) {
            keyWord = (String) iterator.next();
            SecurityDataCleaner.cleanCharArrays(securityParameters.get(keyWord));
        }
    }

    public static HashSet<String> getSecurityParameterNames() {
        return securityParameterNames;
    }

    public static void setSecurityParameterNames(HashSet<String> securityParameterNames) {
        SessionRequestContent.securityParameterNames = securityParameterNames;
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

    public HashMap<String, char[]> getSecurityParameters() {
        return securityParameters;
    }

    public void setSecurityParameters(HashMap<String, char[]> securityParameters) {
        this.securityParameters = securityParameters;
    }

    public boolean isInvalidateSession() {
        return isInvalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        isInvalidateSession = invalidateSession;
    }
}
