package com.example.project2020_2021.TeachersSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2020_2021.R;
import com.example.project2020_2021.TeachersLogIn.TeacherRegistration;

import java.util.Calendar;

public class TeacherSignUp4 extends AppCompatActivity {

    RadioGroup teaGender;
    RadioButton selectedGender;
    DatePicker teadatePicker;
    Button toopt;
    ImageView backbtn;
    TextView tloginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up4);

        toopt =(Button) findViewById(R.id.register_optnext_btn);

        teaGender = (RadioGroup) findViewById(R.id.teagenderrg);
        teadatePicker = (DatePicker) findViewById(R.id.teaagepicker);

        toopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTeaGender() | !validateTeaAge()){
                    return;
                }

                selectedGender = (RadioButton) findViewById(teaGender.getCheckedRadioButtonId());


                Intent intent = new Intent(TeacherSignUp4.this, VerifyOPT2.class);

                //getting all values passed from previous screen
                String _teaname = getIntent().getStringExtra("teaname");
                String _teatype = getIntent().getStringExtra("teatype");
                String _teaemail = getIntent().getStringExtra("teaemail");
                String _teapass = getIntent().getStringExtra("teapass");
                String _teacountry = getIntent().getStringExtra("teacountry");
                String _teacity = getIntent().getStringExtra("teacity");
                String _teaaddress = getIntent().getStringExtra("teaaddress");
                String _teaphone = getIntent().getStringExtra("teaphoneno");
                String _teaqua = getIntent().getStringExtra("teaqua");
                String _teaexp = getIntent().getStringExtra("teaexp");
                String _teawteach = getIntent().getStringExtra("teawteach");
                String _teafee = getIntent().getStringExtra("teafee");


                int day = teadatePicker.getDayOfMonth();
                int month = teadatePicker.getMonth();
                int year = teadatePicker.getYear();

                //getting fields data
                String teagender = selectedGender.getText().toString();
                String teadate = day+"/"+month+"/"+year;

                //passing data
                intent.putExtra("teaname",_teaname);
                intent.putExtra("teatype",_teatype);
                intent.putExtra("teaemail",_teaemail);
                intent.putExtra("teapass",_teapass);
                intent.putExtra("teacountry",_teacountry);
                intent.putExtra("teacity",_teacity);
                intent.putExtra("teaaddress",_teaaddress);
                intent.putExtra("teaphoneno",_teaphone);
                intent.putExtra("teaqua",_teaqua);
                intent.putExtra("teaexp",_teaexp);
                intent.putExtra("teawteach",_teawteach);
                intent.putExtra("teafee",_teafee);
                intent.putExtra("teagender",teagender);
                intent.putExtra("teadate",teadate);


                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(toopt,"transition_register_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp4.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        backbtn = (ImageView) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherSignUp4.this, TeacherSignUp3.class);
                startActivity(intent);
            }
        });

        tloginbtn = (TextView)  findViewById(R.id.teacher_loginbtn);
        tloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.teacher_loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp4.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });


    }

    public boolean validateTeaGender()
    {
        if (teaGender.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this,"Please Select Gender",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean validateTeaAge()
    {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = teadatePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 18)
        {
            Toast.makeText(TeacherSignUp4.this,"You are not Eligible to register as teacher",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }


}