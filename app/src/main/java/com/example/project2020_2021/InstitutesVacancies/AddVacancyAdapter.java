package com.example.project2020_2021.InstitutesVacancies;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AddVacancyAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

    public AddVacancyAdapter(FragmentManager fm, Context context, int totaltabs)
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
                AddVacancyOneFragment addVacancyOneFragment = new AddVacancyOneFragment();
                return  addVacancyOneFragment;

            case 1:
                AddVacancyTwoFragment addVacancyTwoFragment= new AddVacancyTwoFragment();
                return addVacancyTwoFragment;

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