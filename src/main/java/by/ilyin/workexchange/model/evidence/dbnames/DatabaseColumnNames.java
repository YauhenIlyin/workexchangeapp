package by.ilyin.workexchange.model.evidence.dbnames;

public class DatabaseColumnNames {
    //table Users
    public static final String USERS_ID = "id";
    public static final String USERS_FIRST_NAME = "first_name";
    public static final String USERS_LAST_NAME = "last_name";
    public static final String USERS_REGISTRATION_DATE = "registration_date";
    public static final String USERS_LAST_ACTIVITY_DATE = "last_activity_date";
    public static final String USERS_E_MAIL = "e_mail";
    public static final String USERS_MOBILE_NUMBER = "mobile_number";
    public static final String USERS_LOGIN = "login";
    public static final String USERS_USER_PASSWORD = "user_password";
    public static final String USERS_USER_ROLE_ID = "user_role_id";
    public static final String USERS_ACCOUNT_STATUS_ID = "account_status_id";
    //table account_status
    public static final String ACCOUNT_STATUS_ID = "id";
    public static final String ACCOUNT_STATUS_DESCRIPTION = "account_status_description";
    //table user_role
    public static final String USER_ROLE_ID = "id";
    public static final String USER_ROLE_ROLE_DESCRIPTION = "role_description";
    //table messages
    public static final String MESSAGES_ID = "id";
    public static final String MESSAGES_SENDER_ID = "sender_id";
    public static final String MESSAGES_FORMATION_DATE = "formation_date";
    public static final String MESSAGES_SEND_DATE = "send_date";
    public static final String MESSAGES_MESSAGE_STATUS_ID = "message_status_id";
    public static final String MESSAGES_TEXT_CONTENT = "text_content";
    public static final String MESSAGES_IMAGE_CONTENT = "image_content";
    //table message_status
    public static final String MESSAGE_STATUS_ID = "id";
    public static final String MESSAGE_STATUS_MESSAGE_STATUS_DESCRIPTION = "message_status_description";
    //table orders
    public static final String ORDERS_ID = "id";
    public static final String ORDERS_CREATOR_ID = "creator_id";
    public static final String ORDERS_EXECUTANT_ID = "executant_id";
    public static final String ORDERS_MAIN_MESSAGE_ID = "main_message_id";
    public static final String ORDERS_ORDER_STATUS_ID = "order_status_id";
    public static final String ORDERS_PRICE = "price";
    //table order_status
    public static final String ORDER_STATUS_ID = "id";
    public static final String ORDERS_ORDER_STATUS_DESCRIPTION = "order_status_description";
    //table order_chats
    public static final String ORDER_CHATS_ORDER_ID = "order_id";
    public static final String ORDER_CHATS_MESSAGE_ID = "message_id";
    public static final String ORDER_CHATS_MESSAGE_INDEX_NUMBER = "message_index_number";


    private DatabaseColumnNames() {
    }

}
