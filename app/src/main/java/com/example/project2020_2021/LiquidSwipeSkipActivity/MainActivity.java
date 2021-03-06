package com.example.project2020_2021.LiquidSwipeSkipActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;

import com.example.project2020_2021.R;

public class MainActivity extends AppCompatActivity {

    //Variables
    Animation obanim;

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private MainActivity.ScreenSlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new MainActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

       /* obanim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(obanim);*/

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                    return  tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return  tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return  tab3;
            }

            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}