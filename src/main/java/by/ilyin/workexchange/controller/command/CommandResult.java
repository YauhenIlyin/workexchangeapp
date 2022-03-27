package by.ilyin.workexchange.controller.command;

public class CommandResult {

    private Router router;
    private SessionRequestContent sessionRequestContent;

    public CommandResult(Router router, SessionRequestContent sessionRequestContent) {
        this.router = router;
        this.sessionRequestContent = sessionRequestContent;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public SessionRequestContent getSessionRequestContent() {
        return sessionRequestContent;
    }

    public void setSessionRequestContent(SessionRequestContent sessionRequestContent) {
        this.sessionRequestContent = sessionRequestContent;
    }
}
