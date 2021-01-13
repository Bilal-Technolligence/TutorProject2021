package com.example.project2020_2021.LiquidSwipeSkipActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project2020_2021.IntroductoryAccount.AccountActivity;
import com.example.project2020_2021.R;

public class OnBoardingFragment1 extends Fragment {

    //Variable
    TextView skipbut;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding1, container,false);

        //Skip Button
        skipbut = (TextView) root.findViewById(R.id.skip);
        skipbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        return root;

    }
}
