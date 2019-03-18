package com.exam.pk.mobiquitest.View.ListPage;

import android.os.Bundle;

import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.Model.Product;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Category[] mCategories;

    PagerAdapter(FragmentManager fm, Category[] categories) {
        super(fm);
        mCategories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("products",getProductsFromCategory(mCategories,position));
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories[position].getName();
    }

    public Product[] getProductsFromCategory(Category[] categories, int position){
        return categories[position].getProducts().toArray(new Product[0]);
    }
}
