package com.example.coolwanandroid.view.wx;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.coolwanandroid.R;
import com.example.coolwanandroid.adapter.TabAdapter;
import com.example.coolwanandroid.base.fragment.BaseLoadingFragment;
import com.example.coolwanandroid.contract.wx.WxFragmentContract;
import com.example.coolwanandroid.di.module.fragment.WxFragmentModule;
import com.example.coolwanandroid.model.entity.Tab;
import com.example.coolwanandroid.presenter.wx.WxFragmentPresenter;
import com.example.coolwanandroid.utils.CommonUtils;
import com.example.coolwanandroid.utils.StatusBarUtil;
import com.example.coolwanandroid.view.MainActivity;
import com.example.coolwanandroid.view.search.SearchActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author eco-ryoma
 * @date 2019/01/21
 * @description 公众号模块
 * <p>
 * Copyright (c) 2019, eco-ryoma.
 * All rights reserved.
 */
public class WxFragment extends BaseLoadingFragment<WxFragmentPresenter>
        implements WxFragmentContract.View {

    @Inject
    WxFragmentPresenter mPresenter;
    @Inject
    List<String> mWxTabList;
    @Inject
    List<Fragment> mFragmentList;
    @Inject
    List<Integer> mIdList;
    @BindView(R.id.searchTv)
    EditText mSearchTv;
    @BindView(R.id.searchIv)
    ImageView mSearchIv;
    @BindView(R.id.searchRelative)
    RelativeLayout mSearchRelative;
    @BindView(R.id.wxTabLayout)
    SlidingTabLayout mWxTabLayout;
    @BindView(R.id.wxPager)
    ViewPager mWxViewPager;
    @BindView(R.id.statusBarView)
    View view;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav_wx;
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtil.addStatusBarView(mActivity, view);
        mSearchTv.setOnClickListener(v -> toSearchActivity());
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity())
                .getComponent()
                .getWxFragmentComponent(new WxFragmentModule())
                .inject(this);
    }

    @Override
    protected WxFragmentPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showToast(String msg) {
        CommonUtils.toastShow(msg);
    }

    @Override
    public void showWxTab(List<Tab> tabList) {
        for (Tab tab : tabList) {
            mWxTabList.add(tab.getName());
            mIdList.add(tab.getId());
        }
        for (int i = 0; i < mIdList.size(); i++) {
            mFragmentList.add(WxArticlesFragment.newInstance(mIdList.get(i)));
        }
        TabAdapter adapter = new TabAdapter(getChildFragmentManager(), mFragmentList, mWxTabList);
        mWxViewPager.setAdapter(adapter);
        mWxTabLayout.setViewPager(mWxViewPager);
    }

    @Override
    protected void loadData() {
        mPresenter.loadWxTabData();
    }

    private void toSearchActivity() {
        Intent intent = new Intent(mActivity, SearchActivity.class);

        //适配5.0以下的机型
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity,
                    Pair.create(mSearchTv, getString(R.string.share_edit)),
                    Pair.create(mSearchIv, getString(R.string.share_image))
            );
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

}