package com.exam.pk.mobiquitest.View.ListPage;

import android.os.Bundle;


import com.exam.pk.mobiquitest.ListPagePresenter;
import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.R;
import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListPageActivity extends AppCompatActivity  {

    @BindView(R.id.list_tabs)
    TabLayout pageListTabs;

    @BindView(R.id.pager)
    ViewPager viewPager;

    ListPagePresenter listPagePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        ButterKnife.bind(this);
        pageListTabs.setupWithViewPager(viewPager);

    }

    public void applyCategories(){
        Category[] categories = listPagePresenter.getCategoryArray();
        for (Category x:categories) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pageListTabs.addTab(pageListTabs.newTab().setText(x.getName()));
                }
            });

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        listPagePresenter = new ListPagePresenter(this);
    }
}
