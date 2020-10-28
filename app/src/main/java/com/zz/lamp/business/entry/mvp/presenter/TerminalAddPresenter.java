package com.zz.lamp.business.entry.mvp.presenter;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zz.lamp.bean.ConcentratorBean;
import com.zz.lamp.bean.ImageBack;
import com.zz.lamp.business.entry.mvp.Contract;
import com.zz.lamp.net.ApiService;
import com.zz.lamp.net.JsonT;
import com.zz.lamp.net.MyBasePresenterImpl;
import com.zz.lamp.net.RequestObserver;
import com.zz.lamp.net.RxNetUtils;

import java.util.List;
import java.util.Map;

public class TerminalAddPresenter extends MyBasePresenterImpl<Contract.IGetTerminalAddView> implements Contract.IsetTerminalAddPresenter {

    public TerminalAddPresenter(Contract.IGetTerminalAddView view) {
        super(view);
    }

    @Override
    public void postTerminal(Map<String, Object> params) {
        RxNetUtils.request(getCApi(ApiService.class).postTerminal(params), new RequestObserver<JsonT<String>>(this) {
            @Override
            protected void onSuccess(JsonT<String> data) {
                if (data.isSuccess()) {
                    view.showIntent(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<String> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getTerminalDetail(String id) {
        RxNetUtils.request(getCApi(ApiService.class).getTerminalDetail(id), new RequestObserver<JsonT<ConcentratorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ConcentratorBean> data) {
                if (data.isSuccess()) {
                    view.showTerminalDetail(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<ConcentratorBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void checkTerminalAddr(String id,Map<String, Object> params) {
        if (TextUtils.isEmpty(id)) {
            RxNetUtils.request(getCApi(ApiService.class).checkTerminalAddr(params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckAddrIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckAddrIntent(userInfoJsonT);
                }
            }, mDialog);
        }else {

            RxNetUtils.request(getCApi(ApiService.class).checkTerminalAddr(id,params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckAddrIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckAddrIntent(userInfoJsonT);
                }
            }, mDialog);
        }
    }

    @Override
    public void checkTerminalName(String id,Map<String, Object> params) {
        if (TextUtils.isEmpty(id)) {
            RxNetUtils.request(getCApi(ApiService.class).checkTerminalName(params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckNameIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckNameIntent(userInfoJsonT);
                }
            }, mDialog);
        }else {
            RxNetUtils.request(getCApi(ApiService.class).checkTerminalName(id,params), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT data) {
                    if (data.isSuccess()) {
                        view.showCheckNameIntent(data);
                    } else {

                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    view.showCheckNameIntent(userInfoJsonT);
                }
            }, mDialog);
        }
    }

    @Override
    public void deleteTerminal(String ids) {
        RxNetUtils.request(getCApi(ApiService.class).checkDeleteTerminal(ids), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    delete(ids);
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void postImage(int position,String file) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(file), new RequestObserver<JsonT<String>>(this) {
            @Override
            protected void onSuccess(JsonT<String> data) {
                if (data.isSuccess()) {
                    view.showPostImage(position,data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<String> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);

    }
    @Override
    public void uploadImgs(String id,String files) {
        RxNetUtils.request(getCApi(ApiService.class).uploadTerminalImgs(id,files), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showResult();
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    @Override
    public void getImage(String type, String modelId) {
        RxNetUtils.request(getCApi(ApiService.class).getImageBase64(type,modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    view.showImage(data.getData());
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }

    public void delete(String id) {
        RxNetUtils.request(getCApi(ApiService.class).terminalDelete(id), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showDeleteIntent();
                }else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);
    }
}
