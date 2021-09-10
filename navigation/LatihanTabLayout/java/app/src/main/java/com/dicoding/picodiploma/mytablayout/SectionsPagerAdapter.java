package com.dicoding.picodiploma.mytablayout;

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
        return HomeFragment.newInstance(position + 1);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

