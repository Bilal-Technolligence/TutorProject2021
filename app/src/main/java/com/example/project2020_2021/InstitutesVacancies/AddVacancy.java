package com.example.project2020_2021.InstitutesVacancies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.project2020_2021.InstitutesProfile.InstituteProfile;
import com.example.project2020_2021.R;
import com.google.android.material.tabs.TabLayout;

public class AddVacancy extends AppCompatActivity implements TabLayout.OnTabSelectedListener  {

    //Variables
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView vbackbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vacancy);

        //Hooks
        tabLayout = findViewById(R.id.vacancytablayout);
        viewPager = findViewById(R.id.vacancyviewpager);

        tabLayout.addTab(tabLayout.newTab().setText("Add Vacancy"));
        tabLayout.addTab(tabLayout.newTab().setText("Existing Vacancies"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final AddVacancyAdapter adapter = new AddVacancyAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(this);

        //Back Button
        vbackbtn = (ImageView) findViewById(R.id.vacancybackbtn);
        vbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVacancy.this, InstituteProfile.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
