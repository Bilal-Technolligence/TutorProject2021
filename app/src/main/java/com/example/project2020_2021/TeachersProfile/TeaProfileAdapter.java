package com.example.project2020_2021.TeachersProfile;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TeaProfileAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

    public TeaProfileAdapter(FragmentManager fm, Context context, int totaltabs)
    {
        super(fm);
        this.context=context;
        this.totaltabs=totaltabs;
    }



    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
               TeaProfileOneFragment teaProfileOneFragment = new TeaProfileOneFragment();
                return  teaProfileOneFragment;

            case 1:
                TeaProfileTwoFragment teaProfileTwoFragment= new TeaProfileTwoFragment();
                return teaProfileTwoFragment;

            case 2:
                TeaProfileThreeFragment teaProfileThreeFragment= new TeaProfileThreeFragment();
                return teaProfileThreeFragment;

            default:
                return  null;
        }
    }

    @Override
    public int getCount()
    {
        return totaltabs;
    }

}
