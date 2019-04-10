package com.ivan.pazar.web.constants;

public final class WebConstants {



    private WebConstants() {

    }

    // View Constants
    public static final String INVALID_USER_FORM = "invalidUserForm";
    public static final String ERRORS = "errors";
    public static final String USER = "user";
    public static final String CHANGE_PASSWORD = "changePassword";
    public static final String PASSWORDS_NOT_MATCH = "passwordsNotMatch";
    public static final String USERNAME = "username";
    public static final String ADVERT = "advert";
    public static final String ADVERTS = "adverts";
    public static final String REVIEW = "review";
    public static final String PAGES = "pages";
    public static final String PAGE = "page";
    public static final String CATEGORY = "cat";
    public static final String TOTAL_PAGES = "totalPages";
    public static final String REGION = "region";
    public static final String TOWN = "town";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final int DEFAULT_ELEMENTS_SIZE = 6;
    public static final String TRUE = "true";
    public static final String LOGIN_ERROR = "loginError";
    public static final String ADDED_ON = "addedOn";
    public static final int DEFAULT_USERS_SIZE = 50;
    public static final String ID = "id";
    public static final String MESSAGE = "message";
    public static final String SENDER = "sender";
    public static final int DEFAULT_MESSAGE_SIZE = 20;
    public static final int ONE_DAY = 1000 * 60 * 60 * 24;
    public static final String SENT_MESSAGES = "sentMessages";
    public static final String RECEIVED_MESSAGES = "receivedMessages";
    public static final String TOTAL_SENT_MESSAGES_PAGES = "totalSentMessagePages";
    public static final String TOTAL_RECEIVED_MESSAGES_PAGES = "totalReceivedMessagePages";
    public static final String SENT_MESSAGE_PAGE = "sentMessagePage";
    public static final String RECEIVED_MESSAGE_PAGE = "receivedMessagePage";
    public static final String TOTAL_SENT_MESSAGES_PAGES_COUNT = "sentMessagePageCount";
    public static final String TOTAL_RECEIVED_MESSAGES_PAGES_COUNT = "receivedMessagePageCount";
    public static final String ADVERTISED = "advertised";

    // Pages
    public static final String VIEW_INDEX = "views/index";
    public static final String VIEWS_USER_REGISTER = "views/users/register";
    public static final String VIEWS_USER_LOGIN = "views/users/login";
    public static final String VIEWS_SUBCATEGORY_ADD = "views/subcategories/add-subcategory";
    public static final String VIEWS_CATEGORY_ADD = "views/categories/add-category";
    public static final String VIEWS_USER_PROFILE = "views/users/profile";
    public static final String VIEWS_USER_EDIT = "views/users/edit";
    public static final String VIEWS_CHANGE_PASSWORD = "views/users/change-password";
    public static final String VIEWS_ADMIN_PANEL = "views/admins/panel";
    public static final String VIEWS_CHANGE_USER_ROLE = "views/admins/change-user-role";
    public static final String VIEWS_CHANGE_USER_ROLE_CONFIRM = "views/admins/change-user-role-confirm";
    public static final String VIEWS_NEW_ADVERT = "views/adverts/new-advert";
    public static final String VIEWS_EDIT_ADVERT = "views/adverts/edit-advert";
    public static final String VIEWS_SINGLE_ADVERT = "views/adverts/single-advert";
    public static final String VIEWS_REVIEW_ADD = "views/reviews/review-add";
    public static final String VIEWS_DELETE_PROFILE = "views/users/delete";
    public static final String VIEWS_ADVERTS_HOME = "views/adverts/adverts-home";
    public static final String VIEWS_ADD_REGION = "views/regions/add-region";
    public static final String VIEWS_ADD_TOWN = "views/towns/add-town";
    public static final String VIEWS_VIEW_AND_REPLY_MESSAGE = "views/messages/view-and-reply";
    public static final String VIEWS_MY_ADVERTS = "views/users/my-adverts";
    public static final String VIEWS_EDIT_REVIEW = "views/reviews/edit-review";
    public static final String VIEWS_OTHER_USER_PROFILE = "views/users/other-profile";
    public static final String VIEWS_ADVERTISE_REGISTER = "views/advert-register";

    // Redirects
    public static final String REDIRECT_INDEX = "";
    public static final String REDIRECT_USERS_REGISTER = "users/register";
    public static final String REDIRECT_CATEGORY_ADD = "categories/new";
    public static final String REDIRECT_SUBCATEGORY_ADD = "subcategories/new";
    public static final String REDIRECT_USER_EDIT = "users/edit";
    public static final String REDIRECT_USER_PROFILE = "users/profile";
    public static final String REDIRECT_ADMIN_CHANGE = "admin/change-user-role";
    public static final String REDIRECT_TO_ADVERT = "adverts/%s";
    public static final String REDIRECT_TO_ADVERT_REGISTER = "/advertise/register";

    // Errors
    public static final String NOT_FOUND = "errors/not-found";
    public static final String INTERNAL_SERVER_ERROR = "errors/internal-server-error";

}
