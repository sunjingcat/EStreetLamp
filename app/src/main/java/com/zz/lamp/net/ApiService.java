package com.zz.lamp.net;




import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.RealTimeCtrlGroup;
import com.zz.lamp.bean.RealTimeCtrlTerminal;
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

    @GET("/app/light/lightDevice/{id}")
    Observable<JsonT<LightDevice>> getLampDetail(@Path("id") String id);

    @POST( "/app/light/terminal/")
    Observable<JsonT> postTerminal(@QueryMap Map<String, Object> params);


    @POST( "/app/light/realTimeCtrl/line/{terminalId}")
    Observable<JsonT> realTimeCtrlLine(@Path("terminalId") String terminalId,@QueryMap Map<String, Object> params);

    @POST( "/app/light/realTimeCtrl/group")
    Observable<JsonT> realTimeCtrlGroup(@QueryMap Map<String, Object> params);

    @POST( "/app/light/realTimeCtrl/lightDevice")
    Observable<JsonT> realTimeCtrlLightDevice(@QueryMap Map<String, Object> params);

    @POST( "/app/light/lightDevice")
    Observable<JsonT> postLamp(@QueryMap Map<String, Object> params);

    @GET( "/app/light/line/check/lineName")
    Observable<JsonT> checkLamp(@QueryMap Map<String, Object> params);

    @GET("/app/light/area/list")
    Observable<JsonT<List<RegionExpandItem>>> getAreaList(@QueryMap Map<String, Object> params);

   @GET("/app/light/dict/getDicts")
    Observable<JsonT<List<DictBean>>> getLighTypet(@QueryMap Map<String, Object> params);

   @GET("/app/light/lightDeviceType/list")
    Observable<JsonT<List<DeviceType>>> getLightDeviceTypet();

    @GET("/app/light/line/list/{terminalId}")
    Observable<JsonT<List<LineBean>>> getLineList(@Path("terminalId") String terminalId);

    @GET("/app/light/realTimeCtrl/line/{terminalId}")
    Observable<JsonT<List<LineBean>>> getRealTimeCtrlLineList(@Path("terminalId") String terminalId);

    @GET("/app/light/realTimeCtrl/group/{terminalId}")
    Observable<JsonT<List<RealTimeCtrlGroup>>> getRealTimeCtrlGroupList(@Path("terminalId") String terminalId);

    @GET("/app/light/realTimeCtrl/lightDevice/{terminalId}")
    Observable<JsonT<List<LightDeviceConBean>>> getRealTimeCtrlLightDeviceList(@Path("terminalId") String terminalId, @QueryMap Map<String, Object> params);

    @GET("/app/light/lightDevice/list/{terminalId}")
    Observable<JsonT<List<LightDevice>>> getLightDeviceList(@Path("terminalId") String terminalId,@QueryMap Map<String, Object> params);


    @GET("/app/light/realTimeCtrl/terminal")
    Observable<JsonT<List<RealTimeCtrlTerminal>>> getRealTimeCtrlTerminalList(@QueryMap Map<String, Object> params);

    @GET("/app/carame/device/getCameraDevicelist")
    Observable<JsonT<List<CameraBean>>> getCameraDevicelist(@QueryMap Map<String, Object> params);

    @POST( "/app/light/area/")
    Observable<JsonT> postArea(@QueryMap Map<String, Object> params);

    @POST( "/app/light/line")
    Observable<JsonT> postLine(@QueryMap Map<String, Object> params);

    @GET("/app/light/line/getUsableCode/{terminalId}")
    Observable<JsonT<String[]>> getUsableCode(@Path("terminalId") String terminalId);


    @POST(URLs.VERSION + "/buildings/supervise/safety/item")
    Observable<JsonT> submitSafetyFeedback(@QueryMap Map<String, Object> params);

    @GET(URLs.VERSION +  "/liveupdate")
    Observable<JsonT<Version>> getVersion(@Query("appName") String appName);

    @Multipart
    @POST(URLs.VERSION + "/upload/image")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

}

