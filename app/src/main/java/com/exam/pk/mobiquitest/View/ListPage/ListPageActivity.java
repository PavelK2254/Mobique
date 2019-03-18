package com.exam.pk.mobiquitest.View.ListPage;

import android.os.Bundle;


import com.exam.pk.mobiquitest.ListPageVM;
import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.R;
import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListPageActivity extends AppCompatActivity  {

    @BindView(R.id.list_tabs)
    TabLayout pageListTabs;

    @BindView(R.id.pager)
    ViewPager viewPager;

    ListPageVM listPageVM;
    PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        pageListTabs.setupWithViewPager(viewPager);

    }

    public void applyCategories(Category[] categories){
            runOnUiThread(() -> {
                initViewPager(categories);
            });
    }

    private void initViewPager(Category[] categories){
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),categories);
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listPageVM = ViewModelProviders.of(this).get(ListPageVM.class);
        listPageVM.getCategories(getString(R.string.baseUrl)).observe(this, categories -> {
            applyCategories(categories);

        });

    }
}
