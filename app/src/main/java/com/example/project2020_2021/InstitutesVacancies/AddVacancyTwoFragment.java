package com.example.project2020_2021.InstitutesVacancies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddVacancyTwoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.existing_vacancy_tab, container, false);


        return root;


    }
}