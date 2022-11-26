package com.comikaze;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 ViewPager2;
    ViewPager ViewPager;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        ViewPager2 = findViewById(R.id.viewpager);
        ViewPager = new ViewPager(this);

        ViewPager2.setOffscreenPageLimit(3);
        ViewPager2.setAdapter(ViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }

}
