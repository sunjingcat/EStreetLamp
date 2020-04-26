package com.zz.lamp;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
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
import com.zz.lamp.base.BasePresenter;
import com.zz.lamp.base.MyBaseActivity;
import com.zz.lamp.business.mine.MyFragment;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.RxNetUtils;
import com.zz.lamp.utils.LogUtils;
import com.zz.lamp.utils.SystemUtils;
import com.zz.lamp.utils.UpdateManager;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.IDeviceId;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.core.http.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import static com.zz.lamp.base.BasePresenter.getApi;


public class HomeActivity extends MyBaseActivity {

    private static final String TAG = "MainActivity";
    //    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    //    @BindView(R.id.main_tablayout)
    TabLayout mainTablayout;

    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.tab_work, R.string.tab_contact, R.string.tab_mine};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_work, R.drawable.tab_contact, R.drawable.tab_mine};
    //Fragment 数组
    private Fragment[] TAB_FRAGMENTS;
    //融云会话列表fragment
    private Fragment mConversationListFragment;
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;

    ImmersionBar immersionBar;
    private MainTabViewPagerAdapter mainAdapter;
    private IUnReadMessageObserver mTagUnread;

    private long mExitTime = 0;
    private int mConveraTionListSelectPage;//从ConversationListActivity 传来的页码
    public int unReadPublic = 0;
    private WorkFragment workFragment;
    private MyFragment myFragment;

    public void setUnReadPublic(int unReadPublic) {
        this.unReadPublic = unReadPublic;
    }

    public double longitude;
    public double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied() {

            }
        });
        new UpdateManager(this).checkUpdate();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (workFragment !=null){
            workFragment.onRefresh();}
            if (myFragment !=null){
                myFragment.setUserInfo();}

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG + "-loc", "onDestroy");
        mLocationClient.onDestroy();
        //MyReactInstanceManager.getInstance().destroy();
        RongIM.getInstance().disconnect();//融云断开连接，但是不会彻底断开，会和后台有长连接
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(mTagUnread);//注销已注册的未读消息数变化监听器。
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    protected void init() {

        initRongYunListener();
        RongYunUtils.registerRongYun(CacheUtility.spGetOut(CacheUtility.KEY_IMTOKEN, ""));
        //如果绑定成功记录下次不再绑定
//        boolean isPushBind = (boolean) SPUtils.get(Constant.SP_KEY_PUSH_BIND, false);
//        String tokenStr = (String) SPUtils.get(CacheUtility.KEY_IMTOKEN, "");
//        if (!isPushBind) {
//            PushAgent.getInstance(this).addAlias(tokenStr, "site", new UTrack.ICallBack() {
//                @Override
//                public void onMessage(boolean isbind, String s) {
//                    //记录推送绑定结果
//                    SPUtils.put(Constant.SP_KEY_PUSH_BIND, isbind);
//                    Log.e("king===>", isbind + "," + s);
//                }
//            });
//        }

        mConversationListFragment = initConversationList();
        workFragment = new WorkFragment();
        myFragment = new MyFragment();
        TAB_FRAGMENTS = new Fragment[]{workFragment, mConversationListFragment, myFragment};
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus){
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        CacheUtility.clear();
                        PushUtils.deletetAlis(App.context);
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
            }
        });
    }


    @Override
    protected void initView() {
        immersionBar = ImmersionBar.with(HomeActivity.this);
        immersionBar.statusBarDarkFont(true)
                .navigationBarColor(R.color.transparent)
                .init();
//        ImmersionBar.with(HomeActivity.this).statusBarDarkFont(true).init();
        mainTablayout = findViewById(R.id.main_tablayout);
        mainViewpager = findViewById(R.id.main_viewpager);

        init();
        initData();
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
        mConveraTionListSelectPage = getIntent().getIntExtra("switchPage", 0);

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
        App.getHandler().postDelayed(new Runnable() {
            public void run() {
                if (mConveraTionListSelectPage != 0 && mainViewpager != null) {
                    mainViewpager.setCurrentItem(mConveraTionListSelectPage, false);
                    mConveraTionListSelectPage = 0;
                }
            }
        }, 1000);

    }

    protected void initData() {

        getConversationPush();// 获取 push 的 id 和 target

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
    public void refreshTabBadge(int position, boolean isShowBadge) {
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

    private View getBadgeTabItemView(int position, boolean isShowBadge) {

        View view = LayoutInflater.from(this).inflate(R.layout.view_tab_item, null);
        TextView tvTitle = view.findViewById(R.id.tab_item_tv);
        tvTitle.setText(TAB_TITLES[position]);
        ImageView imgTab = view.findViewById(R.id.tab_item_img);
        imgTab.setImageResource(TAB_IMGS[position]);
        ImageView tvUnread = view.findViewById(R.id.tab_item_unread);

        if (isShowBadge) {
            tvUnread.setVisibility(View.VISIBLE);
        } else {
            tvUnread.setVisibility(View.INVISIBLE);
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSuccessComment(EventBusSimpleInfo event) {

        String info = event.getStringData();
        if ("mineIsHaveNoHandleMsg".equals(info)) {
            refreshTabBadge(3, event.isBooleanData());
        } else if ("contactsIsHaveNoHandleMsg".equals(info)) {
            refreshTabBadge(2, event.isBooleanData());
        } else if ("refreshConversationList".equals(info)) {//创建群聊刷新页面的

            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .build();
            MyConversationListFragment fragment = (MyConversationListFragment) mConversationListFragment;
            fragment.setUri(uri);
        } else if ("refreshWork".equals(info)) {
            workFragment.onRefresh();
        }
    }




    private void setStatusBarColor(int position) {
        switch (position) {
            case 0:
                immersionBar.statusBarDarkFont(true)
                        .navigationBarColor(R.color.transparent)
                        .init();
                break;
            case 1:
                immersionBar.statusBarDarkFont(false)
                        .navigationBarColor(R.color.colorThemeYellow)
                        .init();
                break;
            case 2:
                immersionBar.statusBarDarkFont(false)
                        .navigationBarColor(R.color.colorThemeYellow)
                        .init();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Fragment fragment = mainAdapter.getItem(0);
            /*然后在碎片中调用重写的onActivityResult方法*/
            Log.v("", "");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


}