package com.zz.lamp;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.previewlibrary.ZoomMediaLoader;
import com.zz.lamp.base.BasePresenter;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.bean.CameraBean;
import com.zz.lamp.bean.EventBusSimpleInfo;
import com.zz.lamp.business.alarm.AlarmFragment;
import com.zz.lamp.business.control.ControlFragment;
import com.zz.lamp.business.entry.EntryFragment;
import com.zz.lamp.business.main.MainFragment;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.CustomViewPager;
import com.zz.lamp.utils.ImageLoader;
import com.zz.lamp.utils.SystemUtils;
import com.zz.lamp.widget.DragPointView;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.core.http.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zz.lamp.net.RxNetUtils.getCApi;


public class HomeActivity extends MyBaseActivity {

    private static final String TAG = "MainActivity";
    CustomViewPager mainViewpager;
    TabLayout mainTablayout;

    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.tab_home, R.string.tab_control, R.string.tab_alarm, R.string.tab_entry};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_home, R.drawable.tab_control, R.drawable.tab_alarm, R.drawable.tab_entry};
    //Fragment 数组
    private Fragment[] TAB_FRAGMENTS;

    //Tab 数目
    private final int COUNT = TAB_TITLES.length;

    ImmersionBar immersionBar;
    private MainTabViewPagerAdapter mainAdapter;


    private ControlFragment controlFragment;
    private AlarmFragment alarmFragment;
    private MainFragment mainFragment;
    private EntryFragment entryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        new UpdateManager(this).checkUpdate();

        ZoomMediaLoader.getInstance().init(new ImageLoader());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (controlFragment != null) {
            controlFragment.onRefresh();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    protected void init() {
        controlFragment = new ControlFragment();
        alarmFragment = new AlarmFragment();
        mainFragment = new MainFragment();
        entryFragment = new EntryFragment();
        TAB_FRAGMENTS = new Fragment[]{mainFragment, controlFragment, alarmFragment, entryFragment};
    }


    @Override
    protected void initView() {
        immersionBar = ImmersionBar.with(HomeActivity.this);
        immersionBar.statusBarDarkFont(true)
                .navigationBarColor(R.color.colorAccent)
                .init();
        mainTablayout = findViewById(R.id.main_tablayout);
        mainViewpager = findViewById(R.id.main_viewpager);
        mainViewpager.setScanScroll(false);
        init();

        setTabs(mainTablayout, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);

        mainAdapter = new MainTabViewPagerAdapter(getSupportFragmentManager());
        mainViewpager.setOffscreenPageLimit(TAB_FRAGMENTS.length - 1);
        mainViewpager.setAdapter(mainAdapter);
        mainViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mainTablayout));

        mainTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewpager.setCurrentItem(tab.getPosition(), false);
                if (tab.getPosition() == 0) {

                } else {
                    SystemUtils.handleStatusBarColor(HomeActivity.this, R.color.colorPrimary, false);

                }
                if (tab.getPosition() == 2) {
                    getData();
                }
                setStatusBarColor(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setStatusBarColor(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        String tabStr = getIntent().getStringExtra("tab");
        if (!TextUtils.isEmpty(tabStr)) {
            int tab = Integer.parseInt(tabStr);
            if (tab > 0) {
                mainTablayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainTablayout.getTabAt(tab).select();
                    }
                }, 200);
            }
        }
        putClientId();

    }

    @Override
    protected void initToolBar() {

    }

    /**
     * app被杀死后，推送的消息进来会直接打开对话列表fragment
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        App.getHandler().postDelayed(new Runnable() {
//            public void run() {
//
//            }
//        }, 1000);

    }


    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {

            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.view_tab_item, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.tab_item_tv);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = view.findViewById(R.id.tab_item_img);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }

    }

    /**
     * 设置TAB角标方法
     *
     * @param position
     * @param isShowBadge
     */
    public void refreshTabBadge(int position, int isShowBadge) {
        TabLayout.Tab tab = mainTablayout.getTabAt(position);
        View customView = tab.getCustomView();
        if (customView != null) {
            ViewParent parent = customView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(customView);
            }
        }
        tab.setCustomView(getBadgeTabItemView(position, isShowBadge));
    }

    private View getBadgeTabItemView(int position, int isShowBadge) {

        View view = LayoutInflater.from(this).inflate(R.layout.view_tab_item, null);
        TextView tvTitle = view.findViewById(R.id.tab_item_tv);
        tvTitle.setText(TAB_TITLES[position]);
        ImageView imgTab = view.findViewById(R.id.tab_item_img);
        imgTab.setImageResource(TAB_IMGS[position]);
        DragPointView imgTabNum = view.findViewById(R.id.rc_msg_num);
        if (isShowBadge>0){
            imgTabNum.setVisibility(View.VISIBLE);
        }else {
            imgTabNum.setVisibility(View.GONE);
        }
        if (isShowBadge>99){
            imgTabNum.setText("99+");
        }else {
            imgTabNum.setText(isShowBadge + "");
        }
        return view;

    }


    private class MainTabViewPagerAdapter extends FragmentPagerAdapter {
        public MainTabViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSuccessComment(EventBusSimpleInfo event) {

        String info = event.getStringData();
        if ("mineIsHaveNoHandleMsg".equals(info)) {
            refreshTabBadge(3, 0);
        } else if ("contactsIsHaveNoHandleMsg".equals(info)) {
            refreshTabBadge(2, 0);
        } else if ("refreshWork".equals(info)) {
            controlFragment.onRefresh();
        }else if ("putCid".equals(info)) {
            putClientId();
        }
    }


    private void setStatusBarColor(int position) {
//        switch (position) {
//            case 0:
//                immersionBar.statusBarDarkFont(true)
//                        .navigationBarColor(R.color.transparent)
//                        .init();
//                break;
//            case 1:
//                immersionBar.statusBarDarkFont(false)
//                        .navigationBarColor(R.color.colorThemeYellow)
//                        .init();
//                break;
//            case 2:
//                immersionBar.statusBarDarkFont(true)
//                        .navigationBarColor(R.color.transparent)
//                        .init();
//                break;
//
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Fragment fragment = mainAdapter.getItem(mainTablayout.getSelectedTabPosition());
            /*然后在碎片中调用重写的onActivityResult方法*/
            Log.v("", "");
            fragment.onActivityResult(requestCode, resultCode, data);

        }
    }

    void getData() {
        RxNetUtils.request(getCApi(ApiService.class).getNoHandleCount(), new RequestObserver<JsonT<Integer>>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    refreshTabBadge(2, (int)data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<Integer> jsonT) {
                super.onFail2(jsonT);
//                showToast(jsonT.getMessage());
            }
        }, null);
    }
    public void putClientId() {
        Map<String,Object> map = new HashMap<>();
        String cId = CacheUtility.spGetOut("cId", "");
        if (TextUtils.isEmpty(cId)){
            return;
        }
        map.put("cId", cId);
        RxNetUtils.request(getCApi(ApiService.class).putClientId(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT login_data) {
                if (login_data.isSuccess()) {

                }else {

                }
            }
            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
            }
        }, null);
    }

}