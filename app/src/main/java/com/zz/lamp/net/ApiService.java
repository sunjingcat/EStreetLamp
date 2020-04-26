package com.zz.lamp.net;




import com.zz.lamp.bean.ImageBean;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.bean.Version;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


/**
 * Created by admin on 2018/4/23.
 */

public interface ApiService {

//    @POST("api/wallet/transfer/addressinsert")
//    Observable<JsonT<Status>> addAddress(@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST(URLs.VERSION + "/register")
    Observable<JsonT> getRegister(@Field("mobi") String mobi, @Field("passwd") String password, @Field("vcode") String vcode);


    @GET(URLs.NOVERSION + "/sendVerificationSms")
    Observable<JsonT<UserBasicBean>> sendMessage();

    @GET(URLs.NOVERSION + "/sendVerificationPic")
    Observable<JsonT<String>> getPicture(@Query("account") String account);


    @POST(URLs.VERSION + "/buildings/supervise/safety/item")
    Observable<JsonT> submitSafetyFeedback(@QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/supervise/progress")
    Observable<JsonT> submitProgress(@QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/construction/equipment")
    Observable<JsonT> submitEquipment(@QueryMap Map<String, Object> params);

    @PUT(URLs.VERSION + "/buildings/construction/equipment")
    Observable<JsonT> putEquipment(@QueryMap Map<String, Object> params);


    @DELETE(URLs.VERSION + "/buildings/construction/equipment")
    Observable<JsonT> deleteEquipmentDetail(@QueryMap Map<String, Object> params);



    @POST(URLs.VERSION + "/buildings/construction/diary")
    Observable<JsonT> submitDiary(@QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/office/approve/application/{url}")
    Observable<JsonT> submitMateriel(@Path("url") String url, @QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/material/subscribe")
    Observable<JsonT> submitSubscribe(@QueryMap Map<String, Object> params);

    @POST(URLs.VERSION + "/buildings/material/receiving")
    Observable<JsonT> submitReceiving(@QueryMap Map<String, Object> params);


    @DELETE(URLs.VERSION + "/buildings/construction/diary")
    Observable<JsonT> deleteDiaryDetail(@QueryMap Map<String, Object> params);


    @GET(URLs.VERSION +  "/liveupdate")
    Observable<JsonT<Version>> getVersion(@Query("appName") String appName);

    @Multipart
    @POST(URLs.VERSION + "/upload/image")
    Observable<JsonT<ImageBean>> upload(@Part List<MultipartBody.Part> imgs);


}

