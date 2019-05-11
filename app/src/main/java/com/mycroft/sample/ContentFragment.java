package com.mycroft.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

/**
 * @author mycroft
 */
public class ContentFragment extends Fragment {

    private static final String ARGS_ITEM = "item.args";

    public static ContentFragment newInstance(String item) {

        Bundle args = new Bundle();
        args.putString(ARGS_ITEM, item);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new LogLifecycleObserver());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    private static final Random RANDOM = new Random(200);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(getArguments().getString(ARGS_ITEM));

        view.setBackgroundColor(Color.rgb(RANDOM.nextInt(0x100), RANDOM.nextInt(0x100), RANDOM.nextInt(0x100)));

        Log.e("mycroft", "onViewCreated");
    }

}
