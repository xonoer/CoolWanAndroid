package com.ryoma.coolwanandroid.presenter.wx;

import com.ryoma.coolwanandroid.base.BaseObserver;
import com.ryoma.coolwanandroid.base.presenter.BasePresenter;
import com.ryoma.coolwanandroid.contract.wx.WxFragmentContract;
import com.ryoma.coolwanandroid.model.DataModel;
import com.ryoma.coolwanandroid.model.entity.Tab;
import com.ryoma.coolwanandroid.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * @author eco-ryoma
 * @date 2019/7/29
 * @description 公众号Tab
 * <p>
 * Copyright (c) 2019, eco-ryoma.
 * All rights reserved.
 */
public class WxFragmentPresenter extends BasePresenter<WxFragmentContract.View>
        implements WxFragmentContract.Presenter {

    @Inject
    public WxFragmentPresenter(DataModel model) {
        super(model);
    }

    @Override
    public void loadWxTabData() {
        addRxSubscribe(
                mModel.getWxTabs()
                        .compose(RxUtil.rxSchedulerHelper())
                        .compose(RxUtil.handleResult())
                        .subscribeWith(new BaseObserver<List<Tab>>(mView) {
                            @Override
                            public void onNext(List<Tab> tabList) {
                                super.onNext(tabList);
                                mView.showWxTab(tabList);
                            }
                        })
        );
    }
}
