package com.zz.lamp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zz.lamp.bean.RegionExpandItem;
import com.zz.lamp.net.JsonT;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String json = "{\"code\":200,\"msg\":\"OK\",\"data\":[{\"id\":1,\"areaPid\":null,\"orderNum\":1,\"userId\":null,\"areaName\":\"中智施维\",\"areaLng\":\"117.7947719493379\",\"areaLat\":\"39.164443269461685\",\"childrens\":[{\"id\":2,\"areaPid\":1,\"orderNum\":1,\"userId\":null,\"areaName\":\"展厅演示\",\"areaLng\":null,\"areaLat\":null,\"childrens\":null}]},{\"id\":3,\"areaPid\":null,\"orderNum\":1,\"userId\":null,\"areaName\":\"沧州师范学院\",\"areaLng\":\"116.80318001723468\",\"areaLat\":\"38.311914259085206\",\"childrens\":null},{\"id\":4,\"areaPid\":null,\"orderNum\":1,\"userId\":null,\"areaName\":\"津南工厂（测试）\",\"areaLng\":\"117.33928119982326\",\"areaLat\":\"38.96417220526327\",\"childrens\":null},{\"id\":5,\"areaPid\":null,\"orderNum\":2,\"userId\":null,\"areaName\":\"公司展厅\",\"areaLng\":\"117.17393109273767\",\"areaLat\":\"39.06810118691012\",\"childrens\":null}]}";
//        String json = "{\"id\":5,\"areaPid\":null,\"orderNum\":2,\"userId\":null,\"areaName\":\"公司展厅\",\"areaLng\":\"117.17393109273767\",\"areaLat\":\"39.06810118691012\",\"childrens\":null}";
        Gson gson = new Gson();
        JsonT<List<RegionExpandItem>> statusLs = gson.fromJson(json, new TypeToken<JsonT<List<RegionExpandItem>>>(){}.getType());


//取出 weatherInfo
        Log.v("sj---", statusLs.toString());
    }
}
