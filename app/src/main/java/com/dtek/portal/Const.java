package com.dtek.portal;

import com.dtek.portal.utils.PreferenceUtils;

import static com.dtek.portal.Const.DATABASE.*;

public class Const {
    public static final class HTTP {
        public static final String API_BASE_URL = "https://mobile.dtek.com/";
        public static final String LOGIN = "login";

        public static final String BASE_AUTH = "authorization/auth.svc/";
        public static final String API_AUTH_SMS = BASE_AUTH + "getsms/";
        public static final String API_AUTH_TOKEN = BASE_AUTH + "checksmscode/";
        public static final String API_AUTH_ACCESS = BASE_AUTH + "checkaccess/";
        public static final String API_AUTH_TEXT = BASE_AUTH + "Texts";
        public static final String API_AUTHORITY = "Authority";

        public static final String API_PUSH_ADD_APPID = "/beta/Authorization/auth.svc/appId";
        public static final String API_PUSH_REMOVE_APPID = "/beta/Authorization/auth.svc/appidRemove";
        public static final String PUSH_APP_ID_TOKEN = "strAppID";

        public static final String API_NEWS = "news/api/news/";
        public static final String NEWS_CAT_DTEK = "/12/dtek";
        public static final String NEWS_CAT_NO_MISS = "/12/donotmiss";
        public static final String NEWS_CAT_COMPANY = "/12/enterprises";
        public static final String NEWS_PARAM_IMAGE = "&maxwidth=320&maxheight=160";
        public static final String NEWS_BASE64 = "News/Api/NewsImage/?link=";
        public static final String NEWS_LIKE = "Likes/";

        public static final String API_CAR_LIST = "preprod/cars/Api/CarsOrders/";
        public static final String CAR_CAT_ALL = API_CAR_LIST + "ALL";
        public static final String CAR_CAT_ACTIVE = API_CAR_LIST + "ACTIVE";
        public static final String CAR_CAT_ARCHIVE = API_CAR_LIST + "ARCHIVE";
        public static final String CAR_CAT_DRAFT = API_CAR_LIST + "DRAFT";
        public static final String API_CAR_ITEM = "preprod/cars/Api/CarsOrder/"; // отправить заявку // + id GET принять заявку
        public static final String CAR_CANCEL = "Cancel/"; //+ id отменить заявку
        public static final String CAR_EMPLOYEES = API_CAR_ITEM + "Emp/"; // поиск сотрудников
        public static final String CAR_RATING = API_CAR_ITEM + "Rating/"; // рейтинг

        public static final String API_IT_SERVICE = "OrderITSM/api/data";
        public static final String ITSM_KEY_URL = "ItsmUrl"; //поле ключа ссылок
        public static final String ITSM_URL_ALL = "maxrest/rest/os/MOBALLSR/?loginid=~eq~"; // ссылка списка всех заявок
        public static final String ITSM_URL_ACTIVE = "maxrest/rest/os/MOBACTIVESR/?loginid=~eq~"; // ссылка списка активных заявок
        public static final String ITSM_URL_ARCHIVE = "maxrest/rest/os/MOBARCHIVESR/?loginid=~eq~"; // ссылка списка архивных заявок
        public static final String ITSM_URL_USER = "maxrest/rest/os/MOBPERSON/?userid=~eq~"; // ссылка инфо по умолчанию
        public static final String ITSM_URL_WORD = "maxrest/rest/os/MOBADDRESS/?orgid=~eq~org1000&ADDRESS1RUS="; // ссылка списка адресов
        public static final String ITSM_URL_TICKET_ID = "maxrest/rest/os/MOBSR/?ticketid=~eq~"; // ссылка просмотра заявки
        public static final String ITSM_URL_CREATE = "meaweb/services/MXSR"; //ссылка создание заявки
        public static final String ITSM_URL_REVOKED = "meaweb/wf/SR_REVOKED"; //ссылка отмена заявки
        public static final String ITSM_URL_CLOSE = "meaweb/wf/SR_CLOSE"; //ссылка смены статуса после оценки
        public static final String ITSM_URL_QUEUED = "meaweb/wf/SR_QUEUED"; //ссылка возврата заявки
        public static final String ITSM_KEY_RESPONSE = "XsltFileNameResponse"; // поле ключа получения
        public static final String ITSM_KEY_REQUEST = "XsltFileNameRequest"; // поле ключа отправки
        public static final String ITSM_FILE_ALL = "AllSr"; // все заявки
        public static final String ITSM_FILE_ACTIVE = "ActiveSR"; // активные заявки
        public static final String ITSM_FILE_ARCHIVE = "ArchiveSR"; // архивные заявки
        public static final String ITSM_FILE_DEFAULT_INFO = "DefaultInfo"; // данные пользователя по умолчанию
        public static final String ITSM_FILE_ADDRESS = "Address"; // данные адресов
        public static final String ITSM_FILE_GET = "GetSR"; // получение заявки
        public static final String ITSM_FILE_CREATE = "CreateSr"; // создание заявки
        public static final String ITSM_FILE_CREATE_RESP = "CreateSrResponse"; // создание заявки
        public static final String ITSM_FILE_REVOKED = "Revoke"; // отзыв заявки
        public static final String ITSM_FILE_REVOKED_RESP = "RevokeResp"; // отзыв заявки
        public static final String ITSM_FILE_CLOSE = "CloseSR"; // закрыть заявку после оценки
        public static final String ITSM_FILE_CLOSE_RESP = "CloseSRResp"; // закрыть заявку после оценки
        public static final String ITSM_FILE_RATE = "Rate"; // оценка заявки
        public static final String ITSM_FILE_RATE_RESP = "RateResp"; // оценка заявки
        public static final String ITSM_FILE_RETURN_REASON = "SetReturnReason"; // причины возврата заявки
        public static final String ITSM_FILE_RETURN_REASON_RESP = "SetReturnReasonResp"; // причины возврата заявки
        public static final String ITSM_FILE_RETURN = "Return"; // возврат заявки
        public static final String ITSM_FILE_RETURN_RESP = "ReturnResp"; // возврат заявки
        public static final String ITSM_KEY_CONTENT_TYPE = "ItsmContentType"; // создание заявки
        public static final String ITSM_CONTENT_TYPE_SOAP = "application/soap+xml; charset=UTF-8; action=\"urn:processDocument\""; // создание заявки
        public static final String ITSM_CONTENT_TYPE_ENCODED = "application/x-www-form-urlencoded"; // создание заявки

        public static final String ITSM_URL_MANAGER = "maxrest/rest/os/MOBTHWOLINE?USERID=~eq~"; // ссылка менеджера
        public static final String ITSM_URL_NEW = "maxrest/rest/os/MOBWONEW/?USERID=~eq~"; // ссылка списка новых заявок
        public static final String ITSM_URL_IN_WORK = "maxrest/rest/os/MOBWOINPRG?USERID=~eq~"; // ссылка списка В работе заявок
        public static final String ITSM_URL_POSTPONE = "maxrest/rest/os/MOBWOWAI?USERID=~eq~"; // ссылка списка Ожидает заявок
        public static final String ITSM_URL_DETAIL_WORK = "maxrest/rest/os/MOBWO/?WONUM="; // ссылка детали заявки РЗ
        public static final String ITSM_URL_DETAIL_OPERATION = "maxrest/rest/os/MOBWOACT/?WONUM="; // ссылка детали заявки Операции
        public static final String ITSM_URL_DETAIL_INCIDENT = "maxrest/rest/os/MOBINC/?TICKETID="; // ссылка детали заявки Инцидент
        public static final String ITSM_FILE_MANAGER = "IsManager"; // менеджера
        public static final String ITSM_FILE_NEW = "NewWoInc"; // список новых
        public static final String ITSM_FILE_IN_WORK = "InprgWoInc"; // список В работе
        public static final String ITSM_FILE_POSTPONE = "WaitWoInc"; // список Ожидает
        public static final String ITSM_FILE_DETAIL_WORK = "MobWO"; // детали РЗ
        public static final String ITSM_FILE_DETAIL_OPERATION = "MobWOACT"; // детали Операции
        public static final String ITSM_FILE_DETAIL_INCIDENT = "MobINC"; // детали Инцидент

        public static final String ITSM_URL_TAKE_LITE = "meaweb/services/MOBWOLITE"; // ссылка для Всех(тех)

        public static final String ITSM_URL_TAKE_QUEUE = "meaweb/wf/WO_QUEUE"; // ссылка Взять себе в очередь
        public static final String ITSM_URL_TAKE_IN_WORK = "meaweb/wf/WO_MOBINPR"; // ссылка Взять в работу
        public static final String ITSM_URL_REJECT_INCORRECTLY = "maxrest/rest/os/WOCHECK?wonum=~eq~"; // ссылка Отклонить как неправильно классифицирован
//        public static final String ITSM_URL_REJECT_RETURN = "meaweb/wf/WO_REJECT"; // ссылка Отклонить- вернуть на доработку (Заявку на услуги)
        public static final String ITSM_URL_REJECT = "meaweb/wf/WO_REJECT"; // ссылка Отклонить ...
        public static final String ITSM_URL_WAIT = "meaweb/wf/WO_WAIT"; // ссылка Отложить
        public static final String ITSM_URL_DONE = "meaweb/wf/MOBWOCOMPL"; // ссылка Выполнено
        public static final String ITSM_URL_WORK = "meaweb/wf/WO_MOBINPR"; // ссылка В работе

        public static final String ITSM_FILE_TAKE_LITE_RESP_REJECT = "WOWorklogResp"; // отклонить (тех)
        public static final String ITSM_FILE_TAKE_LITE_RESP_QUEUE = "WOQeueResp"; // взять в очередь (тех)
        public static final String ITSM_FILE_TAKE_LITE_RESP = "";
        public static final String ITSM_FILE_TAKE_QUEUE_LITE = "WOQeue"; // файл Взять себе в очередь
        public static final String ITSM_FILE_TAKE_IN_WORK_LITE = "WOInProgress"; // файл Взять себе в работу
        public static final String ITSM_FILE_REJECT_LITE = "WOWorklog"; // файл Отклонить ...
        public static final String ITSM_FILE_RESP_LITE = "WOWorklogResp"; // файл Отклонить

        public static final String ITSM_FILE_TAKE_QUEUE = "WOSetOwner"; // файл Взять себе в очередь
        public static final String ITSM_FILE_TAKE_QUEUE_RESP = "WORespFulResp"; // файл Взять себе в очередь
        public static final String ITSM_FILE_TAKE_IN_WORK = "WOInProgressResp"; // файл Взять в работу
        public static final String ITSM_FILE_TAKE_IN_WORK_OWNER = "WOSetOwner"; // файл Взять в работу
        public static final String ITSM_FILE_TAKE_IN_WORK_OWNER_RESP = "WORespFulResp"; // файл Взять в работу
        public static final String ITSM_FILE_REJECT_INCORRECTLY = "WoCheck"; // файл Отклонить как неправильно классифицирован
        public static final String ITSM_FILE_REJECT_RETURN = ""; // файл Отклонить- вернуть на доработку (Заявку на услуги)
        public static final String ITSM_FILE_REJECT = "WOReject"; // файл Отклонить
        public static final String ITSM_FILE_REJECT_RESP = "WORejectResp"; // файл Отклонить
        public static final String ITSM_FILE_WAIT = "WOSetREASON"; // файл Отложить
        public static final String ITSM_FILE_WAIT_RESP = "WORespFulResp"; // файл Отложить
        public static final String ITSM_FILE_DONE = "WoCompl1"; // файл Выполнено
        public static final String ITSM_FILE_DONE_RESP = "WoComplResp1"; // файл Выполнено
        public static final String ITSM_FILE_WORK = ""; // файл В работе

        //ITSM справки
        public static final String ITSM_URL_REFERENCES_TYPE = "maxrest/rest/os/SPRCLASS?parent=~eq~5622"; // ссылка инфо типы заявок
        public static final String ITSM_FILE_REFERENCES_TYPE = "SRClass";
        public static final String ITSM_URL_PERSON_SEARCH_1 = "maxrest/rest/os/PERSONSPR?STATUS=~eq~ACTIVE&DISPLAYNAME="; // споиск сотрудников
        public static final String ITSM_URL_PERSON_SEARCH_2 = "%&_maxItems=1000&_orderbyasc=DISPLAYNAME"; // споиск сотрудников
        public static final String ITSM_FILE_PERSON_SEARCH = "PersonSearch"; // споиск сотрудников
        public static final String ITSM_CREATE_REFERENCE = "meaweb/services/SRSPRAVNEW"; // создание новой справки
        public static final String ITSM_FILE_REFERENCE_CREATE_REQUEST = "CreateSRSprav";
        public static final String ITSM_FILE_REFERENCE_CREATE_REQUEST_2 = "CreateSRSprav2";
        public static final String ITSM_FILE_REFERENCE_CREATE_RESPONSE = "CreateSRSpravResponse";
        public static final String ITSM_FILE_REFERENCE_CREATE_RESPONSE_2 = "CreateSRSprav2Response";

        public static final String ROOMS_URL_ALL = "maxrest/rest/os/MOBALLSRROOMS/?loginid=~eq~"; // ссылка списка всех заявок
        public static final String ROOMS_URL_ACTIVE = "maxrest/rest/os/MOBACTIVESRROOMS/?loginid=~eq~"; // ссылка списка активных заявок
        public static final String ROOMS_URL_ARCHIVE = "maxrest/rest/os/MOBARCHIVESRROOMS/?loginid=~eq~"; // ссылка списка архивных заявок
        public static final String ROOMS_FILE_ALL = "AllSrrooms"; // все заявки
        public static final String ROOMS_FILE_ACTIVE = "ActiveSRrooms"; // активные заявки
        public static final String ROOMS_FILE_ARCHIVE = "ArchiveSRrooms"; // архивные заявки
        public static final String ROOMS_FILE_CREATE = "CreateSrRooms"; // создание заявки

        public static final String API_HR_SERVICE = "beta/hr/Api/Data";
        public static final String API_HR_SERVICE_BETA = "beta/hr/Api/Data"; // todo Test
//        public static final String API_HR_SERVICE_BETA = "beta/HR"; // todo Test
        public static final String HR_IS_MANAGER = "IsManager/"; // шеф?
        public static final String HR_VACATION_LIMITS = "GetVacationLimitsList/"; // лимиты
        public static final String HR_VACATION_HISTORY = "GetVacationHistory/"; // история
        public static final String HR_VACATION_GET = "EditVacation/"; // получение заявления /{userLogin}/{id}
        public static final String HR_VACATION_SUBORDINATE = "GetVacationOnBossSigne/"; // подчиненные
        public static final String HR_VACATION_USER_DENY = "UserDenyVacation/"; // отмена пользователем /{userLogin}/{id}
        public static final String HR_VACATION_BOSS_AGREE = "BossAgreeVacation/"; // согласование начальником /{userLogin}/{id}
        public static final String HR_VACATION_BOSS_DENY = API_HR_SERVICE + "/BossDenyVacation"; // отмена начальником
        public static final String HR_VACATION_CHECK_DATE = API_HR_SERVICE + "/CheckDate"; // количество дней

        public static final String API_QR_SERVICE = "qr/api/qr";

        public static final String API_NEWSPAPER = "Media/Api/NewsPaper/";
        public static final String NEWSPAPER_ARCHIVE = "Archive";

        public static final String API_GALLERY = "Media/api/PhotoAlbum";

        public static final String NEWSPAPER_URL = "newspaper_url";

    }

    public static final class DATABASE {

        public static final String DB_NAME = "NewsDB.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME_NEWS_DTEK = "news_dtek";
        public static final String TABLE_NAME_NEWS_NO_MISS = "news_no_miss";
        public static final String TABLE_NAME_NEWS_COMPANY = "news_company";

        public static final String DROP_QUERY = "DROP TABLE IF EXISTS ";

        public static final String GET_NEWS_QUERY = "SELECT * FROM ";

        public static final String NEWS_ID = "news_id";
        public static final String TITLE = "title";
        public static final String CATEGORY = "category";
        public static final String SUBTITLE = "subtitle";
        public static final String PUBLISHED_DATE = "published_date";
        public static final String IMAGE_URL = "image_url";
        public static final String PHOTO = "photo";

//        public static final String CREATE_TABLE_DTEK = createTable(TABLE_NAME_NEWS_DTEK);
//        public static final String CREATE_TABLE_NO_MISS = createTable(TABLE_NAME_NEWS_NO_MISS);
//        public static final String CREATE_TABLE_COMPANY = createTable(TABLE_NAME_NEWS_COMPANY);
    }

    public static String createTable(String strName) {
        String str = "CREATE TABLE IF NOT EXISTS " + strName + "" +
//                "(" + NEWS_ID + " INTEGER PRIMARY KEY not null," +
                "(_id INTEGER PRIMARY KEY autoincrement," +
                NEWS_ID + " TEXT not null," +
                TITLE + " TEXT not null," +
                CATEGORY + " TEXT," +
                SUBTITLE + " TEXT," +
                PUBLISHED_DATE + " TEXT," +
                IMAGE_URL + " TEXT," +
                PHOTO + " blob" +
                ")";
        return str;
    }

    public static final class REFERENCE {
        public static final String POST = Config.PACKAGE_NAME + ".news";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.dtek.portal";
    }

    public static final class PDF_NEWSPAPER {
        public static final String RUS_LANGUAGE = "Русский";
        public static final String UKR_LANGUAGE = "Украинский";
        public static final String NEWSPAPER_NAME = "newspaper_name";
        public static final String FILE_NAME = "newspaper";
    }

    public static final class QR {
        public static final String QR_LINK = "https://mobile.dtek.com/MobileApp?";
        public static final String QR_DATA = "url_data";
    }

    public static final class YOUTUBE {
        public static final String API_KEY = "AIzaSyAhRQtD1xYrrVqGj2GsM2wUbuavSBXPyfk";
        public static final String URL_VIDEO = "url_video";
    }

    public static final class PUSH {
        public static final String DATA_TYPE = "type";
        public static final String JSON_BODY = "jsonBody";
        public static final String TYPE_HISTORY = "history";
        public static final String TYPE_OPEN = "open";
    }

    public static final class BusinessTrips {
//        public static final String TEST_TOKEN = "57547f56-313d-4810-b5a7-eddf67d14eda";
        public static final String TEST_TOKEN = PreferenceUtils.getToken();
        public static final String API_BT = "btrips/";
        public static final String BT_GET = "bt";
        public static final String BT_GET_ALL = "bt";
        public static final String BT_SAVE = "bt";
        public static final String BT_CANCEL = "bt/cancel";
        public static final String BT_APPROVE = "bt/approv";
        public static final String BT_GET_EMPLOYEE = "directories/employee";
        public static final String BT_GET_EMPLOYEES = "directories/employees";
        public static final String BT_GET_TRIP_TYPES = "directories/trip-types";
        public static final String BT_GET_LOCATIONS = "directories/locations?query=";
        public static final String BT_GET_ORGANIZATION = "directories/organization?query=";
        public static final String BT_GET_TRIP_AIM = "directories/trip-aim";
        public static final String BT_GET_COMPENSATION_NUMBERS = "directories/bukrs/compensation";
        public static final String BT_GET_ADDITIONAL_SERVICES = "directories/additional-services-types";

        public static final String BT_STATUS_APPROVED = "APPROVED";
        public static final String BT_STATUS_CANCELED = "CANCELED";
        public static final String BT_STATUS_APPROVING = "APPROVING";
        public static final String BT_STATUS_NEW = "NEW";
        public static final String BT_STATUS_WAITRATING = "WAITRATING";
        public static final String BT_STATUS_CLOSED = "CLOSED";
        public static final String BT_STATUS_REJECTED = "REJECTED";
    }

    public static final class References {
        public static final String REFERENCE_URL_ALL = "maxrest/rest/os/ALLSRSPRAV/?loginid=~eq~"; // ссылка списка всех справок
        public static final String REFERENCE_FILE_GET_ALL = "AllSRSprav"; // файл списка всех справок
        public static final String REFERENCE_URL_ACTIVE = "maxrest/rest/os/ACTIVESRSPRAV/?loginid=~eq~"; // ссылка списка активных справок
        public static final String REFERENCE_FILE_GET_ACTIVE = "ActiveSRSprav"; // файл списка активных справок
        public static final String REFERENCE_URL_ARCHIVE = "maxrest/rest/os/ARCHIVESRSPRAV/?loginid=~eq~"; // ссылка списка архивных справок
        public static final String REFERENCE_FILE_GET_ARCHIVE = "ArchiveSRSprav"; // файл списка активных справок
        public static final String REFERENCE_URL_GET_DETAIL = "maxrest/rest/os/SRSPRAV/?ticketid=~eq~"; // ссылка для получения конкретной справки
        public static final String REFERENCE_FILE_GET_DETAIL = "GetSRSprav"; // файл получения конкретной справки
        public static final String REFERENCE_DESIRE_DATE_OF_RECEIPT = "ЖЕЛАЕМАЯ ДАТА ПОЛУЧЕНИЯ";
    }
}
