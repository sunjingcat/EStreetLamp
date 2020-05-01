package com.zz.lamp.net;




import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.UsableCode;
import com.zz.lamp.bean.UserInfo;
import com.zz.lamp.bean.Version;
import com.zz.lamp.bean.IpAdress;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by admin on 2018/4/23.
 */

public interface ApiService {

    @GET("/app/light/authCode/getAddress/{authCode}")
    Observable<JsonT<IpAdress>> getAddress(@Path("authCode") String authCode);

    @POST( "/app/light/login")
    Observable<JsonT<UserInfo>> login(@QueryMap Map<String, Object> params);

    @GET("/app/light/terminal/list")
    Observable<JsonT<List<ConcentratorBean>>> getTerminalList(@QueryMap Map<String, Object> params);

    @GET("/app/light/terminal/{id}")
    Observable<JsonT<ConcentratorBean>> getTerminalDetail(@Path("id") String authCode);

    @POST( "/app/light/terminal/")
    Observable<JsonT> postTerminal(@QueryMap Map<String, Object> params);

    @POST( "/app/light/lightDevice")
    Observable<JsonT> postLamp(@QueryMap Map<String, Object> params);

    @GET("/app/light/area/list")
    Observable<JsonT<List<RegionExpandItem>>> getAreaList(@QueryMap Map<String, Object> params);

    @GET("/app/light/line/list/{terminalId}")
    Observable<JsonT<List<LineBean>>> getLineList(@Path("terminalId") String terminalId);

    @GET("/app/light/lightDevice/list")
    Observable<JsonT<List<LightDevice>>> getLightDeviceList(@QueryMap Map<String, Object> params);

    @POST( "/app/light/area/")
    Observable<JsonT> postArea(@QueryMap Map<String, Object> params);

    @POST( "/app/light/line")
    Observable<JsonT> postLine(@QueryMap Map<String, Object> params);

    @GET("/app/light/line/getUsableCode/{terminalId}")
    Observable<JsonT<List<UsableCode>>> getUsableCode(@Path("terminalId") String terminalId);


    @POST(URLs.VERSION + "/buildings/supervise/safety/item")
    Observable<JsonT> submitSafetyFeedback(@QueryMap Map<String, Object> params);

    @GET(URLs.VERSION +  "/liveupdate")
    Observable<JsonT<Version>> getVersion(@Query("appName") String appName);

    @Multipart
    @POST(URLs.VERSION + "/upload/image")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

}

