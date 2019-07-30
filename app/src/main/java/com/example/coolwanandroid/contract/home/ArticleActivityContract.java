package com.example.coolwanandroid.contract.home;

import com.example.coolwanandroid.base.presenter.IPresenter;
import com.example.coolwanandroid.base.view.BaseView;

/**
 * @author eco-ryoma
 * @date 2019/7/29
 * @description <p>
 * Copyright (c) 2019, eco-ryoma.
 * All rights reserved.
 */
public interface ArticleActivityContract {
    interface View extends BaseView {
        void showCollectSuccess(); //收藏成功

        void showUnCollectSuccess();//取消收藏成功
    }

    interface Presenter extends IPresenter<View> {
        void collectArticles(int id); //收藏首页文章

        void unCollectArticles(int id);//取消收藏
    }
}