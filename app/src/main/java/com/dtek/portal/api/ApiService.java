package com.dtek.portal.api;

import com.dtek.portal.models.business_trips.BT;
import com.dtek.portal.models.business_trips.BTAdditionalService;
import com.dtek.portal.models.business_trips.BTEmployee;
import com.dtek.portal.models.business_trips.BTPreview;
import com.dtek.portal.models.business_trips.BTLocation;
import com.dtek.portal.models.business_trips.BTOrganization;
import com.dtek.portal.models.business_trips.BTTripAim;
import com.dtek.portal.models.business_trips.TripTypes;
import com.dtek.portal.models.car.CarOrder;
import com.dtek.portal.models.car.Employee;
import com.dtek.portal.models.gallery.Folder;
import com.dtek.portal.models.gallery.Photo;
import com.dtek.portal.models.hr_vacation.BoosDenyRequest;
import com.dtek.portal.models.hr_vacation.OrderVacationRequest;
import com.dtek.portal.models.hr_vacation.TotalDaysRequest;
import com.dtek.portal.models.itsm.ItDefaultInfo;
import com.dtek.portal.models.login.Login;
import com.dtek.portal.models.login.LoginHelp;
import com.dtek.portal.models.login.ServicesList;
import com.dtek.portal.models.news.NewsComment;
import com.dtek.portal.models.news.NewsID;
import com.dtek.portal.models.news.NewsList;
import com.dtek.portal.models.newspaper.Newspaper;
import com.dtek.portal.models.push.Push;
import com.dtek.portal.models.push.PushRemove;
import com.dtek.portal.models.qr.QrResp;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.dtek.portal.Const.BusinessTrips.API_BT;
import static com.dtek.portal.Const.BusinessTrips.BT_APPROVE;
import static com.dtek.portal.Const.BusinessTrips.BT_CANCEL;
import static com.dtek.portal.Const.BusinessTrips.BT_SAVE;
import static com.dtek.portal.Const.HTTP.API_AUTH_TEXT;
import static com.dtek.portal.Const.HTTP.API_CAR_ITEM;
import static com.dtek.portal.Const.HTTP.API_GALLERY;
import static com.dtek.portal.Const.HTTP.API_HR_SERVICE;
import static com.dtek.portal.Const.HTTP.API_HR_SERVICE_BETA;
import static com.dtek.portal.Const.HTTP.API_IT_SERVICE;
import static com.dtek.portal.Const.HTTP.API_NEWS;
import static com.dtek.portal.Const.HTTP.API_PUSH_ADD_APPID;
import static com.dtek.portal.Const.HTTP.API_PUSH_REMOVE_APPID;
import static com.dtek.portal.Const.HTTP.CAR_EMPLOYEES;
import static com.dtek.portal.Const.HTTP.HR_VACATION_BOSS_DENY;
import static com.dtek.portal.Const.HTTP.HR_VACATION_CHECK_DATE;


public interface ApiService {

    @GET()
    Call<Login> getAuth(@Url String url);

    @POST(API_PUSH_ADD_APPID)
    Call<String> sendPushToken(@HeaderMap Map<String, String> headers, @Body Push push);

    @POST(API_PUSH_REMOVE_APPID)
    Call<PushRemove> removedPushToken(@HeaderMap Map<String, String> headers, @Body Push push);

    @GET()
    Call<ServicesList> getServices(@Url String url);

    @GET(API_AUTH_TEXT)
    Call<LoginHelp> getMassage();

    @GET()
    Call<NewsList> getListNews(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<NewsID> getIdNews(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<String> getImageHtml(@HeaderMap Map<String, String> headers, @Url String url);

    @PUT()
    Call<List<NewsComment>> getListComment(@HeaderMap Map<String, String> headers, @Url String url);

    @POST(API_NEWS)
    Call<String> sendComment(@HeaderMap Map<String, String> headers, @Body NewsComment comment);

    @POST()
    Call<Boolean> sendLike(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<CarOrder>> getListOrderCar(@HeaderMap Map<String, String> headers, @Url String url);

//    @GET(API_CAR_ITEM +"/{order_id}")
//    Call<CarOrder> getOrderCarId(@HeaderMap Map<String, String> headers, @Path(value = "order_id", encoded = true) String orderId);

    @GET()
    Call<CarOrder> getOrderCarId(@HeaderMap Map<String, String> headers, @Url String url);

    @POST(API_CAR_ITEM)
    Call<CarOrder> sendOrderCar(@HeaderMap Map<String, String> headers, @Body CarOrder carOrder);

    @POST(CAR_EMPLOYEES)
    Call<List<Employee>> sendEmployee(@HeaderMap Map<String, String> headers, @Body Employee employee);

    @PUT()
    Call<CarOrder> updateOrderCar(@HeaderMap Map<String, String> headers, @Body CarOrder carOrder, @Url String url);

    @PUT()
    Call<CarOrder> cancelOrderCar(@HeaderMap Map<String, String> headers, @Url String url);

    @GET(API_IT_SERVICE)
    Call<String> loadItStringJson(@HeaderMap Map<String, String> headers);

    @POST(API_IT_SERVICE)
    Call<String> sendItOrder(@HeaderMap Map<String, String> headers, @Body String body);

    @GET(API_IT_SERVICE)
    Call<String> sendItOrderGet(@HeaderMap Map<String, String> headers);

    @GET(API_HR_SERVICE + "/{link}")
    Call<String> loadHrStringJson(@HeaderMap Map<String, String> headers, @Path(value = "link", encoded = true) String link);

    @POST(API_HR_SERVICE)
    Call<String> sendOrderVacation(@HeaderMap Map<String, String> headers, @Body OrderVacationRequest vacation);

    @POST(API_HR_SERVICE_BETA)
    Call<String> sendOrderVacationBeta(@HeaderMap Map<String, String> headers, @Body OrderVacationRequest vacation);

    @POST(HR_VACATION_CHECK_DATE)
    Call<String> getCountDays(@HeaderMap Map<String, String> headers, @Body TotalDaysRequest totalDays);

    @POST(HR_VACATION_BOSS_DENY)
    Call<String> sendBossDeny(@HeaderMap Map<String, String> headers, @Body BoosDenyRequest boosDeny);

    @GET(API_GALLERY)
    Call<List<Folder>> loadFolder(@HeaderMap Map<String, String> headers);

    @GET(API_GALLERY + "/{link}")
    Call<List<Photo>> loadPhoto(@HeaderMap Map<String, String> headers, @Path(value = "link", encoded = true) String link);

//    @GET()
//    Call<QrResp> getQR(@Url String url);

    @GET()
    Call<QrResp> getQR(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<Newspaper>> getNewspaperList(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<String> getImgForFirstPageNewspaper(@HeaderMap Map<String, String> headers, @Url String url);

    @GET
    @Streaming
    Call <ResponseBody> getNewspaper(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call <BT> getBT(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call <List<BTPreview>> getAllBT(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<BTEmployee> getBTEmployee(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<BTEmployee>> getBTEmployees(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<TripTypes>> getTripTypes(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<BTLocation>> getBSLocations(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<BTOrganization>> getBSOrganization(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<BTTripAim>> getTripAim(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<String>> getCompensationNumbers(@HeaderMap Map<String, String> headers, @Url String url);

    @GET()
    Call<List<BTAdditionalService.AdditionalService>> getAdditionalServices(@HeaderMap Map<String, String> headers, @Url String url);

    @POST(API_BT + BT_SAVE)
    Call<BT> saveBT(@HeaderMap Map<String, String> headers, @Body BT bt);

    @PUT(API_BT + BT_CANCEL)
    Call<ResponseBody> cancelBT(@HeaderMap Map<String, String> headers, @Body BT bt);

    @PUT(API_BT + BT_SAVE)
    Call<ResponseBody> saveBTChanges(@HeaderMap Map<String, String> headers, @Body BT bt);

    @POST(API_BT + BT_APPROVE)
    Call<ResponseBody> approveBT(@HeaderMap Map<String, String> headers, @Body BT bt);

    @GET(API_IT_SERVICE)
    Call<String> getReferencesType(@HeaderMap Map<String, String> headers);

    @GET(API_IT_SERVICE)
    Call<String> getPersons(@HeaderMap Map<String, String> headers);

    @POST(API_IT_SERVICE)
    Call<String> createReference(@HeaderMap Map<String, String> headers, @Body String body);

    @GET(API_IT_SERVICE)
    Call<String> getReference(@HeaderMap Map<String, String> headers);
}
