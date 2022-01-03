package com.dicoding.newsapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dicoding.newsapp.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {

    public static final String ARG_TAB = "tab_name";
    public static final String TAB_NEWS = "news";
    public static final String TAB_BOOKMARK = "bookmark";
    private FragmentNewsBinding binding;
    private String tabName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_TAB);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

