package com.mycroft.sample;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.orhanobut.logger.Logger;

/**
 * 记录声明周期
 *
 * @author mycroft
 */
public class LogLifecycleObserver implements DefaultLifecycleObserver {

    public LogLifecycleObserver() {
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Logger.e("onCreate");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Logger.e("onStart");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Logger.e("onResume");

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Logger.e("onPause");

    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Logger.e("onStop");

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Logger.e("onDestroy");
    }
}
