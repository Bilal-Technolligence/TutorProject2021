package com.example.project2020_2021.InstitutesProfile;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class InsProfileAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

    public InsProfileAdapter(FragmentManager fm, Context context, int totaltabs)
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
                InsProfileOneFragment insProfileOneFragment = new InsProfileOneFragment();
                return  insProfileOneFragment;

            case 1:
                InsProfileTwoFragment insProfileTwoFragment= new InsProfileTwoFragment();
                return insProfileTwoFragment;

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
