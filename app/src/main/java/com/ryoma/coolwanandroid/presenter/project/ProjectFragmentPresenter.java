package com.ryoma.coolwanandroid.presenter.project;

import com.ryoma.coolwanandroid.base.BaseObserver;
import com.ryoma.coolwanandroid.base.presenter.BasePresenter;
import com.ryoma.coolwanandroid.contract.project.ProjectFragmentContract;
import com.ryoma.coolwanandroid.model.DataModel;
import com.ryoma.coolwanandroid.model.entity.Tab;
import com.ryoma.coolwanandroid.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;


/**
 * @author eco-ryoma
 * @date 2019/7/29
 * @description 项目Tab
 * <p>
 * Copyright (c) 2019, eco-ryoma.
 * All rights reserved.
 */
public class ProjectFragmentPresenter extends BasePresenter<ProjectFragmentContract.View>
        implements ProjectFragmentContract.Presenter {
    @Inject
    public ProjectFragmentPresenter(DataModel model) {
        super(model);
    }

    @Override
    public void loadProjectTabData() {
        addRxSubscribe(mModel.getProjectTab()
                .compose(RxUtil.rxSchedulerHelper())
                .compose(RxUtil.handleResult())
                .subscribeWith(new BaseObserver<List<Tab>>(mView) {
                    @Override
                    public void onNext(List<Tab> tabList) {
                        super.onNext(tabList);
                        mView.showProjectTab(tabList);
                    }
                })
        );
    }
}
