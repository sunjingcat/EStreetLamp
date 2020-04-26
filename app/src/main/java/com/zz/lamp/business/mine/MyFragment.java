package com.zz.lamp.business.mine;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.lamp.R;
import com.zz.lamp.base.MyBaseFragment;
import com.zz.lamp.bean.UserBasicBean;
import com.zz.lamp.business.mine.mvp.Contract;
import com.zz.lamp.business.mine.mvp.presenter.MineInfoPresenter;
import com.zz.lamp.utils.GlideUtils;
import com.zz.lib.commonlib.utils.CacheUtility;


import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MyFragment extends MyBaseFragment<Contract.IsetMineInfoPresenter> implements Contract.IMineInfoView {
    Unbinder unbinder;
    private ImageView my_head;
    /**
     * 点击登录
     */
    private static TextView my_subTitle;
    /**
     * 未登录
     */
    private static TextView not_logged;


    public static int expendedtag = 2;

    UserBasicBean userInfo;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getCreateView() {
        return R.layout.fragment_my;
    }


    @Override
    protected void initView(View view) {

        my_head = (ImageView) view.findViewById(R.id.my_head);

        not_logged = (TextView) view.findViewById(R.id.not_logged);
        my_subTitle = (TextView) view.findViewById(R.id.my_subTitle);
//        mPresenter.getMineInfo();

    }

    public void setUserInfo() {
        if (mPresenter == null) {
        } else {
            mPresenter.getMineInfo();
        }

    }

    @Override
    public void showUserInfo(UserBasicBean userInfo) {
        this.userInfo = userInfo;
        CacheUtility.spSave("name", userInfo.getName());

        if (!TextUtils.isEmpty(userInfo.getAvatar())) {

            GlideUtils.loadCircleImage(getActivity(), userInfo.getAvatar(), my_head);
        }
        not_logged.setText(TextUtils.isEmpty(userInfo.getName()) ? "未登录" : userInfo.getName());
//        my_subTitle.setText(TextUtils.isEmpty(CacheUtility.getToken()) ? "点击登录" : TextUtils.isEmpty(s) ? "完善基本资料找工作更快捷" : s);
//        GlideUtils.loadImage(getActivity(), userInfo.getAvatar(), my_head);
    }

    @Override
    public void showIntent() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1401 || requestCode == 1402 || requestCode == 1403 || requestCode == 1404 || requestCode == 1405) {
            if (TextUtils.isEmpty(CacheUtility.getToken())) {
                showUserInfo(new UserBasicBean());
            } else {
                mPresenter.getMineInfo();
            }
        }
    }

    @Override
    public MineInfoPresenter initPresenter() {
        return new MineInfoPresenter(this);
    }

    @Override
    public void onError() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.not_logged, R.id.my_information, R.id.my_ID_card, R.id.my_contract, R.id.my_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.not_logged:
//                if (!TextUtils.isEmpty(CacheUtility.getToken())) {
//                    startActivityForResult(new Intent(getActivity(), MyInformationActivity.class), 1401);
//                } else {
//                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1402);
//                }
//                break;
//
//            case R.id.my_ID_card:
//                startActivity(new Intent(getActivity(), MyCertificateActivity.class));
//                break;
//            case R.id.my_contract:
//                startActivity(new Intent(getActivity(), MyAgreementListActivity.class));
//                break;
//            case R.id.my_about:
//                startActivity(new Intent(getActivity(), AboutActivity.class));
//                break;

        }
    }
}
