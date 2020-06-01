package com.zz.lamp.net;




import com.zz.lamp.bean.AlarmBean;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.DeviceType;
import com.zz.lamp.bean.DictBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.bean.LightDevice;
import com.zz.lamp.bean.LightDeviceConBean;
import com.zz.lamp.bean.LightPost;
import com.zz.lamp.bean.LineBean;
import com.zz.lamp.bean.MapListBean;
import com.zz.lamp.bean.RealTimeCtrlGroup;
import com.zz.lamp.bean.RealTimeCtrlTerminal;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.bean.TestPost;
import com.zz.lamp.bean.UsableCode;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.bean.UserInfo;
import com.zz.lamp.bean.Version;
import com.zz.lamp.bean.IpAdress;
import com.zz.lamp.bean.YsConfig;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Observable<JsonT<String>> postTerminal(@QueryMap Map<String, Object> params);

    @POST("/app/light/enclosure/upload")
    @FormUrlEncoded
    Observable<JsonT<List<Integer>>> uploadImgs( @Field("filebase64s") String handleFile);

    @POST("/app/light/terminal/uploadImgs/{id}")
    @FormUrlEncoded
    Observable<JsonT> uploadTerminalImgs(@Path("id") String id, @Field("enclosureIds") String handleFile);

    @POST("/app/light/lightDevice/uploadImgs/{id}")
    @FormUrlEncoded
    Observable<JsonT> uploadLightDeviceImgs(@Path("id") String id,@Field("enclosureIds") String handleFile);

    @GET( "/app/light/terminal/check/terminalAddr")
    Observable<JsonT> checkTerminalAddr(@QueryMap Map<String, Object> params);

    @GET( "/app/light/terminal/check/terminalAddr/{id}")
    Observable<JsonT> checkTerminalAddr(@Path("id") String id,@QueryMap Map<String, Object> params);

    @GET( "/app/light/terminal/check/terminalName")
    Observable<JsonT> checkTerminalName(@QueryMap Map<String, Object> params);

    @GET( "/app/light/terminal/check/terminalName/{id}")
    Observable<JsonT> checkTerminalName(@Path("id") String id,@QueryMap Map<String, Object> params);

    @GET( "/app/light/lightDevice/check/deviceAddr")
    Observable<JsonT> checkDeviceAddr(@QueryMap Map<String, Object> params);

    @GET( "/app/light/lightDevice/check/deviceAddr/{id}")
    Observable<JsonT> checkDeviceAddr(@Path("id") String id,@QueryMap Map<String, Object> params);

    @GET( "/app/light/lightDevice/check/deviceName")
    Observable<JsonT> checkDeviceName(@QueryMap Map<String, Object> params);

  @GET( "/app/light/lightDevice/check/deviceName/{id}")
    Observable<JsonT> checkDeviceName(@Path("id") String id,@QueryMap Map<String, Object> params);

    @GET( "/app/light/lightDevice/check/deviceCode")
    Observable<JsonT> checkDeviceCode(@QueryMap Map<String, Object> params);


    @POST( "/app/light/realTimeCtrl/line/{terminalId}")
    Observable<JsonT> realTimeCtrlLine(@Path("terminalId") String terminalId,@QueryMap Map<String, Object> params,@Query("lineCodes") Integer[] lineCodes);

    @POST( "/app/light/realTimeCtrl/group")
    Observable<JsonT> realTimeCtrlGroup(@QueryMap Map<String, Object> params,@Query("ids") Integer[] ids);

    @POST( "/app/light/realTimeCtrl/lightDevice/{terminalId}")
    Observable<JsonT> realTimeCtrlLightDevice(@Path("terminalId") String terminalId,@QueryMap Map<String, Object> params);

    @POST( "/app/light/lightDevice")
    Observable<JsonT<String>> postLamp(@QueryMap Map<String, Object> params);

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

    @GET("/app/light/alarm/list")
    Observable<JsonT<List<AlarmBean>>> getAlarmList(@QueryMap Map<String, Object> params);

    @POST("/app/light/alarm/handleLightAlarm/{id}")
    @FormUrlEncoded
    Observable<JsonT> handleLightAlarm(@Path("id") String id,@Field("alarmStatus") String alarmStatus, @Field("handleDescription") String handleDescription, @Field("enclosureIds") String handleFile);

    @GET("/app/carame/device/getCameraDevicelist")
    Observable<JsonT<List<CameraBean>>> getCameraDevicelist(@QueryMap Map<String, Object> params);

    @POST("/app/light/gtClientId")
    Observable<JsonT> putClientId(@QueryMap Map<String, Object> params);

    @GET("/app/carame/device/getYsConfig")
    Observable<JsonT<YsConfig>> getYsConfig();


    @GET("/app/light/alarm/getLightAlarmById")
    Observable<JsonT<AlarmBean>> getLightAlarmById(@QueryMap Map<String, Object> params);

    @GET("/app/carame/device/startControl")
    Observable<JsonT> startControl(@QueryMap Map<String, Object> params);

    @GET("/app/carame/device/stopControl")
    Observable<JsonT> stopControl(@QueryMap Map<String, Object> params);

    @GET("/app/light/enclosure/base64/{type}/{modelId}")
    Observable<JsonT<List<ImageBack>>> getImageBase64(@Path("type") String type, @Path("modelId") String modelId);

    @GET("/app/light/map/list")
    Observable<JsonT<List<MapListBean>>> getMapList(@QueryMap Map<String, Object> params);

    @GET("/app/light/map/terminal/{id}")
    Observable<JsonT<ConcentratorBean>> getMapTerminalDetail(@Path("id") String id);

    @GET("/app/light/map/lightDevice/{id}")
    Observable<JsonT<LightDevice>> getMapLampDetail(@Path("id") String id);

    @GET("/app/light/area/checkDelete/{id}")
    Observable<JsonT> checkDelete(@Path("id") String id);

    @DELETE("/app/light/area/{id}")
    Observable<JsonT> areaDelete(@Path("id") String id);

    @GET("/app/light/terminal/checkDelete")
    Observable<JsonT> checkDeleteTerminal(@Query("ids") String ids);

    @DELETE("/app/light/terminal")
    Observable<JsonT> terminalDelete(@Query("ids") String ids);


    @GET("/app/light/line/checkDelete")
    Observable<JsonT> checkDeleteLine(@Query("ids") String ids);

    @DELETE("/app/light/line")
    Observable<JsonT> lineDelete(@Query("ids") String ids);

    @GET("/app/light/lightDevice/checkDelete")
    Observable<JsonT> checkDeleteLight(@Query("ids") String ids);

    @DELETE("app/light/lightDevice")
    Observable<JsonT> lightDelete(@Query("ids") String ids);


    @POST( "/app/light/area/")
    Observable<JsonT> postArea(@QueryMap Map<String, Object> params);

    @POST( "/app/light/line")
    Observable<JsonT> postLine(@QueryMap Map<String, Object> params);

    @GET("/app/light/line/getUsableCode/{terminalId}")
    Observable<JsonT<String[]>> getUsableCode(@Path("terminalId") String terminalId);

    @GET("/app/light")
    Observable<JsonT<UserBasicBean>> getUserDetail();

    @POST("/app/light/logout")
    Observable<JsonT> logout();

    @POST( "/app/light/resetPwd")
    Observable<JsonT> resetPwd(@QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/supervise/safety/item")
    Observable<JsonT> submitSafetyFeedback(@QueryMap Map<String, Object> params);

    @GET("/app/version/latest")
    Observable<JsonT<Version>> getVersion();

    @Multipart
    @POST(URLs.VERSION + "/upload/image")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);

}

