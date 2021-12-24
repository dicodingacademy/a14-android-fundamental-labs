package com.dicoding.picodiploma.mytablayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {

    public SectionsPagerAdapter(AppCompatActivity activity) {
        super(activity);
    }

//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:
//                fragment = new HomeFragment();
//                break;
//
//            case 1:
//                fragment = new ProfileFragment();
//                break;
//        }
//        return fragment;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 2;
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(HomeFragment.ARG_SECTION_NUMBER, position+1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

