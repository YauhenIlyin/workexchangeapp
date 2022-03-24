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

    public SessionRequestContent getSessionRequestContent() {
        return sessionRequestContent;
    }
}
