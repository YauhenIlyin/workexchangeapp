package by.ilyin.workexchange.controller.command;

public class Router {

    public enum RouteType {
        FORWARD,
        REDIRECT,
        ERROR;
    }

    public enum PagePath {
        LOGIN_PAGE("pages/login"),
        REGISTRATION_PAGE("pages/registration"),
        MAIN_PAGE("pages/main");

        private String pagePath;

        private PagePath(String pagePath) {
            this.pagePath = pagePath;
        }

        private String getPagePath() {
            return this.pagePath;
        }
    }

    private RouteType routeType;
    private String pagePath;

    public Router(RouteType routeType, PagePath pagePath) {
        this.routeType = routeType;
        this.pagePath = pagePath.getPagePath();
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public String getPagePath() {
        return pagePath;
    }
}
