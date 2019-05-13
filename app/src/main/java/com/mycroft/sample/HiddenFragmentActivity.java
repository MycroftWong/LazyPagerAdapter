package com.mycroft.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class HiddenFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_fragment);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        for (int i = 0; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("item: " + i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                startFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.selectTab(tabLayout.getTabAt(0));
        startFragment(0);
    }

    private Fragment mCurrentFragment;

    private Fragment getItem(int position) {
        return ViewPagerFragment.newInstance();
    }

    private void startFragment(int pos) {
        // 下面是一连串的处理fragment切换的代码
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(makeFragmentName(pos));

        if (fragment == null) {
            fragment = getItem(pos);

            if (mCurrentFragment != null) {
                ft.hide(mCurrentFragment);
            }

            ft.add(R.id.container, fragment, makeFragmentName(pos));
            mCurrentFragment = fragment;
        } else {
            if (fragment != mCurrentFragment) {
                ft.show(fragment);
                if (mCurrentFragment != null) {
                    ft.hide(mCurrentFragment);
                }
                mCurrentFragment = fragment;
            }
        }

        ft.commitAllowingStateLoss();
    }

    private static String makeFragmentName(long id) {
        return "android:switcher:" + R.id.container + ":" + id;
    }

}
