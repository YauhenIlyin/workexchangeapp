package by.ilyin.workexchange.controller.command;

public enum PagePath {

    LOGIN_PAGE("pages/login"),
    REGISTRATION_PAGE("pages/registration");

    private final String pagePath;

    PagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPagePath() {
        return pagePath;
    }

}
