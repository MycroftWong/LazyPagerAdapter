package com.mycroft.sample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ViewPager2
 *
 * @author mycroft
 */
public class ViewPager2Activity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);
        mViewPager2 = findViewById(R.id.view_pager2);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2.setAdapter(new ContentPagerAdapter(getSupportFragmentManager()));

//        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount() * 2 + 1);

        for (int i = 0; i < mData.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText("item: " + i));
        }

        mViewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        mViewPager2.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        super.onDestroy();
    }

    private final List<String> mData = new ArrayList<>();

    {
        for (int i = 0; i < 100; i++) {
            mData.add("item: " + i);
        }
    }

    private class ContentPagerAdapter extends FragmentStateAdapter {

        public ContentPagerAdapter(@NonNull FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ContentFragment.newInstance("item: " + position);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mTabLayout.selectTab(mTabLayout.getTabAt(position), true);
        }
    };
}
