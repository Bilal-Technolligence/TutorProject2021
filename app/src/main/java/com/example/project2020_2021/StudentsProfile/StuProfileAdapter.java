package com.example.project2020_2021.StudentsProfile;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StuProfileAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

    public StuProfileAdapter(FragmentManager fm, Context context, int totaltabs)
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
                StuProfileOneFragment stuProfileOneFragment = new StuProfileOneFragment();
                return  stuProfileOneFragment;

            case 1:
                StuProfileTwoFragment stuProfileTwoFragment= new StuProfileTwoFragment();
                return stuProfileTwoFragment;

            case 2:
                StuProfileThreeFragment stuProfileThreeFragment= new StuProfileThreeFragment();
                return stuProfileThreeFragment;

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
