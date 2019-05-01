package com.mycroft.lazypageradapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 想完成的功能，Fragment 不用特意去实现懒加载，完全在 PagerAdapter 中处理懒加载
 *
 * @author wangqiang
 */
public abstract class LazyPagerAdapter extends PagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

    private View mCurrentPrimaryItem = null;

    /**
     * 存储每个位置的View，以免每次都创建
     */
    private final List<View> mViews = new ArrayList<>();

    public LazyPagerAdapter(@NonNull FragmentManager fm) {
        mFragmentManager = fm;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position position
     * @return 当前position的Fragment
     */
    @NonNull
    public abstract Fragment getItem(int position);

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        while (mViews.size() <= position) {
            mViews.add(null);
        }

        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(container.getContext()).inflate(getPlaceholderView(position), container, false);
            mViews.set(position, view);
            container.addView(view);
            view.setId(View.generateViewId());
        }

        return view;
    }

    /**
     * 获取占位View
     *
     * @param position position
     * @return
     */
    @LayoutRes
    public abstract int getPlaceholderView(int position);

    /**
     * 移除预加载的View
     *
     * @param view     预加载的view
     * @param position position
     */
    public abstract void removePreloadView(ViewGroup view, int position);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        final int itemId = getItemId(position);
        // Do we already have this fragment?
        String name = makeFragmentName(((View) object).getId(), itemId);

        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurTransaction.hide(fragment);
        }
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewGroup actualView = (ViewGroup) object;
        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        if (actualView != mCurrentPrimaryItem) {
            try {
                removePreloadView(actualView, position);
            } catch (Exception e) {
                // 避免多次 remove views
            }

            // 隐藏 actualView 中的 fragment
            final int itemId = getItemId(position);
            // Do we already have this fragment?
            String name = makeFragmentName(actualView.getId(), itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment != null) {
                mCurTransaction.show(fragment);
            } else {
                fragment = getItem(position);
                mCurTransaction.add(actualView.getId(), fragment, makeFragmentName(actualView.getId(), itemId));
            }

            mCurrentPrimaryItem = actualView;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * Return a unique identifier for the item at the given position.
     *
     * @return Unique identifier for the item at position
     */
    private int getItemId(int position) {
        return position;
    }

    private static String makeFragmentName(int viewId, int id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
