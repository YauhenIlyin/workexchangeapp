package by.ilyin.workexchange.controller.command;

public class CommandResult {

    enum PageTransitionType {
        FORWARD,
        REDIRECT
    }

    private PagePath pagePath;
    private PageTransitionType pageTransitionType;

    public CommandResult(PagePath pagePath, PageTransitionType pageTransitionType) {
        this.pagePath = pagePath;
        this.pageTransitionType = pageTransitionType;
    }

    public PagePath getPagePath() {
        return this.pagePath;
    }

    public PageTransitionType getPageTransitionType() {
        return this.pageTransitionType;
    }

}
