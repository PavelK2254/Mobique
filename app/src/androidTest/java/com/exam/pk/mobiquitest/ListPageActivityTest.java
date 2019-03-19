package com.exam.pk.mobiquitest;

import android.widget.TextView;

import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class ListPageActivityTest {

    @Rule
    public ActivityTestRule<ListPageActivity> mActivityTestRule = new ActivityTestRule<>(ListPageActivity.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private ListPageActivity mActivity;
    private String mBaseUrl;
    private CountDownLatch latch = new CountDownLatch(1);

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        mBaseUrl = mActivity.getString(R.string.baseUrl);
    }

    @Test
    public void viewPagerNotNull(){
        assertNotNull(mActivity.getViewPager());
    }

    @Test
    public void tabsNotNull(){
        assertNotNull(mActivity.getPageListTabs());
    }

    @Test
    public void showNoConnectionIfInternetNotConnected() {
        mActivity.refreshData(false, mBaseUrl, categories -> assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("noInternetDialog")));
    }


    @Test
    public void didAdapterReceivedCategories() throws InterruptedException {
        mActivity.refreshData(true, mBaseUrl, categories -> {
            mActivity.applyCategories(categories);
            assertEquals(2, Objects.requireNonNull(mActivity.getViewPager().getAdapter()).getCount());
            latch.countDown();
        });
        latch.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void isTabDataCorrect() throws InterruptedException {
        mActivity.refreshData(true, mBaseUrl, categories -> {
            mActivity.applyCategories(categories);
            for (int i = 0; i < categories.length; i++) {
                assertEquals(categories[i].getName(),mActivity.getViewPager().getAdapter().getPageTitle(i));
            }

            latch.countDown();
        });
        latch.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void clickProduct() throws InterruptedException {

        mActivity.refreshData(true, mBaseUrl, categories -> {
            mActivity.applyCategories(categories);
            onView(allOf(isDisplayed(),withId(R.id.productList))).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
            assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("detailFragment"));
            latch.countDown();
        });
        latch.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void isClickedItemNameMatchesCurrentProduct() throws InterruptedException {
        mActivity.refreshData(true, mBaseUrl, categories -> {
            int currentItemIndex = 2;
            mActivity.applyCategories(categories);
            String currentName = categories[mActivity.getViewPager().getCurrentItem()].getProducts().get(currentItemIndex).getName();
            onView(allOf(isDisplayed(),withId(R.id.productList))).perform(RecyclerViewActions.actionOnItemAtPosition(currentItemIndex,click()));
            assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("detailFragment"));
            assertEquals(currentName,((TextView)mActivity.findViewById(R.id.detail_product_name)).getText().toString());
            latch.countDown();
        });
        latch.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void isClickedItemPriceMatchesCurrentProduct() throws InterruptedException {
        mActivity.refreshData(true, mBaseUrl, categories -> {
            int currentItemIndex = 2;
            mActivity.applyCategories(categories);
            String currentPrice = categories[mActivity.getViewPager().getCurrentItem()].getProducts().get(currentItemIndex).getSalePrice().getAmount();
            String currentCurrency = categories[mActivity.getViewPager().getCurrentItem()].getProducts().get(currentItemIndex).getSalePrice().getCurrency();
            onView(allOf(isDisplayed(),withId(R.id.productList))).perform(RecyclerViewActions.actionOnItemAtPosition(currentItemIndex,click()));
            assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("detailFragment"));
            assertEquals(currentPrice + " " + currentCurrency,((TextView)mActivity.findViewById(R.id.detail_product_price)).getText().toString());
            latch.countDown();
        });
        latch.await(2000, TimeUnit.MILLISECONDS);
    }
}