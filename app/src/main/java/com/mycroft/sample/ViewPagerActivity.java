package com.mycroft.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用新的{@link androidx.fragment.app.FragmentPagerAdapter}
 *
 * @author mycroft
 */
public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.addLogAdapter(new AndroidLogAdapter());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), mData));
        tabLayout.setupWithViewPager(viewPager);

//        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount() * 2 + 1);
    }

    private final List<String> mData = new ArrayList<>();

    {
        for (int i = 0; i < 4; i++) {
            mData.add("item: " + i);
        }
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter {
        private final List<String> mData;

        public ContentPagerAdapter(FragmentManager fm, List<String> data) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ContentFragment.newInstance(mData.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData.get(position);
        }

    }
}
