package by.ilyin.workexchange.controller.evidence;

public final class SessionAttributeName { //todo реализовать так везде

    private SessionAttributeName() {
    }

    public static final String AUTH_STATUS = "auth_status"; //todo true false
    public static final String USER_ROLE = "user_role";
    public static final String USER_ID = "user_id";
    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_LOCALE = "user_locale";
    public static final String PREVIOUS_PAGE_PATH = "previous_page_path";
    //в сессии ссылка на общие отображающиеся данные (на синглтон какой-то)
    public static final String REQUEST_COUNTER = "request_counter";


}
