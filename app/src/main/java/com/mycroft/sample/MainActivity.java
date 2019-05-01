package com.mycroft.sample;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mycroft.lazypageradapter.LazyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), mData));
        tabLayout.setupWithViewPager(viewPager);
    }

    private final List<String> mData = new ArrayList<>();

    {
        for (int i = 0; i < 4; i++) {
            mData.add("item: " + i);
        }
    }

    private class ContentPagerAdapter extends LazyPagerAdapter {
        private final List<String> mData;

        public ContentPagerAdapter(FragmentManager fm, List<String> data) {
            super(fm);
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

        @Override
        public int getPlaceholderView(int position) {
            return R.layout.fragment_placeholder;
        }

        @Override
        public void removePreloadView(ViewGroup view, int position) {
            View progressBar = view.findViewById(R.id.progressBar);
            if (progressBar != null) {
                view.removeView(progressBar);
            }
        }
    }
}
