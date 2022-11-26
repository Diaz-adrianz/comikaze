package com.comikaze;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.comikaze.tabfragments.Fcategory;
import com.comikaze.tabfragments.Fhome;
import com.comikaze.tabfragments.Fprofile;
import com.comikaze.tabfragments.Fsearch;

public class ViewPager extends FragmentStateAdapter {
    public ViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fhome();
            case 1:
                return new Fsearch();
            case 2:
                return new Fcategory();
            case 3:
                return new Fprofile();
            default:
                return new Fhome();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
