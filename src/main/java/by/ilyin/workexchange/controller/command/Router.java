package by.ilyin.workexchange.controller.command;

public class Router {

    public enum RouteType {
        FORWARD,
        REDIRECT,
        ERROR;
    }

    private RouteType routeType;
    private String pagePathStr;

    public Router(RouteType routeType, String pagePathStr) {
        this.routeType = routeType;
        this.pagePathStr = pagePathStr;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public String getPagePath() {
        return pagePathStr;
    }
}
