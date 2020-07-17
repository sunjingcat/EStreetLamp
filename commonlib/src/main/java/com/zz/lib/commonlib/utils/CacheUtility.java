package com.zz.lib.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zz.lib.commonlib.CommonApplication;

public class CacheUtility {
    private static final String KEY_USERID = "key_userid";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_IndexType = "indexType";
    public static final String KEY_URL = "BASE_URL";
    public static final String KEY_CODE = "key_code";
    private static final String KEY_PHONE = "key_phone";
    private static final String KEY_QQ = "key_qq";
    private static final String KEY_WXCHAT = "key_wxchat";
    private static final String KEY_BIGIMAGE = "key_is_bigimage";
    private static final String KEY_CITY = "key_city";
    private static final String KEY_PRE_CODE = "key_precode";
    private static final String KEY_SEARCH = "key_search";
    public static final String KEY_PROGRAM = "key_program";
    public static final String KEY_PROGRAM_CODE = "key_program_code";
    public static void spSave(String key, String value) {
        SharedPreferences preferences = getPref();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void spSave(String key, int value) {
        SharedPreferences preferences = getPref();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void spSave( String key, Boolean value) {
        SharedPreferences preferences = getPref();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static String spGetOut(String key,String defValue) {
        SharedPreferences preferences = getPref();
        return preferences.getString(key, defValue);
    }

    public static int spGetOut( String key, int defValue) {
        SharedPreferences preferences = getPref();
        return preferences.getInt(key, defValue);
    }

    public static boolean spGetOut( String key, boolean defValue) {
        SharedPreferences preferences = getPref();
        return preferences.getBoolean(key, defValue);
    }

    public static void clear() {
        SharedPreferences preferences = CommonApplication.getAppContext().getSharedPreferences("lamp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        editor.commit();
    }
    public static SharedPreferences getPref() {
        return CommonApplication.getAppContext().getSharedPreferences("lamp", Context.MODE_PRIVATE);
    }

    public static void saveUserId( String userId) {
        spSave( KEY_USERID, userId);
    }

    public static String getUserId() {
        return spGetOut(KEY_USERID,"");
    }

    public static void saveToken(String token) {
        spSave(KEY_TOKEN, token);
    }
    public static void saveIndexType(int indexType) {
        spSave(KEY_IndexType, indexType);
    }
    public static void saveURL(String url) {
        spSave(KEY_URL, url);
    }
    public static void saveCode(String code) {
        spSave(KEY_CODE, code);
    }
    public static String getURL() {
        return spGetOut(KEY_URL,"http://city.tjzzsw.com/");
    }
    public static String getCode() {
        return spGetOut(KEY_CODE,"");
    }
    public static boolean isLogin(){
        if (TextUtils.isEmpty(getToken())){
            return false;
        }else {
            return true;
        }
    }

    public static String getToken() {
        return spGetOut( KEY_TOKEN,"");
    }
    public static int getIndexType() {
        return spGetOut( KEY_IndexType,0);
    }

    public static void saveQQ( String qq) {
        spSave( KEY_QQ, qq);
    }

    public static String getQQ() {
        return spGetOut(KEY_QQ,"");
    }
    public static void saveWxchat(String wxchat) {
        spSave( KEY_WXCHAT, wxchat);
    }

    public static String getWxchat() {
        return spGetOut(KEY_WXCHAT,"");
    }
    public static void saveCity( String city) {
        spSave(KEY_CITY, city);
    }

    public static String getCity() {
        return spGetOut( KEY_CITY,"");
    }
    public static void savePreCode( String preCode) {
        spSave( KEY_PRE_CODE, preCode);
    }

    public static String getPreCode() {
        return spGetOut( KEY_PRE_CODE,"");
    }
    public static void saveIsBigImage(boolean isbigimage) {
        spSave(KEY_BIGIMAGE, isbigimage);
    }

    public static boolean getIsBigImage() {
        return spGetOut( KEY_BIGIMAGE,false);
    }

    public static String getSearch() {
        return spGetOut( KEY_SEARCH,"");
    }
    public static void saveSearch(String search) {
        spSave( KEY_SEARCH, search);
    }

}
