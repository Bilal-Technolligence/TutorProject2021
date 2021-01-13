package com.example.project2020_2021.TeachersSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project2020_2021.R;
import com.example.project2020_2021.TeachersLogIn.TeacherRegistration;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class TeacherSignUp2 extends AppCompatActivity {

    LinearLayout rlogin;
    ImageView backbtn;
    TextView ttext, subtext, tloginbtn;
    Button teanext2;
    TextInputLayout teacity, teaaddress ,phonenoi;
    CountryCodePicker teacountry, teaphonecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up2);


        backbtn = (ImageView) findViewById(R.id.backbtn);
        ttext = (TextView) findViewById(R.id.register_teacher_title_text);
        subtext = (TextView) findViewById(R.id.register_teacher_text);
        tloginbtn = (TextView)  findViewById(R.id.teacher_loginbtn);
        teanext2 = (Button) findViewById(R.id.register_teacher_next_btn);
        rlogin =(LinearLayout) findViewById(R.id.register_teacher_login_btn);


        tloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.teacher_loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp2.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherSignUp2.this, TeacherSignUp.class);
                startActivity(intent);
            }
        });

        //Hooks
        teacity = (TextInputLayout)findViewById(R.id.teacity);
        teaaddress = (TextInputLayout)findViewById(R.id.teaaddress);
        phonenoi = (TextInputLayout)findViewById(R.id.teapnumber);
        teaphonecode = (CountryCodePicker) findViewById(R.id.teaCountryCode);
        teacountry = (CountryCodePicker) findViewById(R.id.teaCountry);

        teanext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTeaPhoneNumber() | !validateCity() | !validateAddress()){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), TeacherSignUp3.class);

                //getting all values passed from previous screen
                String _teaname = getIntent().getStringExtra("teaname");
                String _teatype = getIntent().getStringExtra("teatype");
                String _teaemail = getIntent().getStringExtra("teaemail");
                String _teapass = getIntent().getStringExtra("teapass");


                //getting fields data
                String teacountryS = teacountry.getSelectedCountryName().toString().trim();
                String teacityS = teacity.getEditText().getText().toString().trim();
                String teaaddressS = teaaddress.getEditText().getText().toString().trim();
                String teaphoneS = phonenoi.getEditText().getText().toString().trim();

                //Remove first zero if entered!
                if (teaphoneS.charAt(0) == '0') {
                    teaphoneS = teaphoneS.substring(1);
                }

                String teaphonefullS = "+" + teaphonecode.getFullNumber() + teaphoneS;

                //passing data
                intent.putExtra("teaname",_teaname);
                intent.putExtra("teatype",_teatype);
                intent.putExtra("teaemail",_teaemail);
                intent.putExtra("teapass",_teapass);
                intent.putExtra("teacountry",teacountryS);
                intent.putExtra("teacity",teacityS);
                intent.putExtra("teaaddress",teaaddressS);
                intent.putExtra("teaphoneno",teaphonefullS);

                //Add Transition
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(ttext, "transition_title_text");
                pairs[1] = new Pair(subtext, "transition_text");
                pairs[2] = new Pair(teanext2, "transition_next_btn");
                pairs[3] = new Pair(rlogin, "transition_login_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp2.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });




    }

    private boolean validateTeaPhoneNumber()
    {
        String val = phonenoi.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            phonenoi.setError("Field can not be Empty");
            return false;
        }
        else
        {
            phonenoi.setError(null);
            phonenoi.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCity()
    {
        String val = teacity.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            teacity.setError("Field can not be Empty");
            return false;
        }
        else
        {
            teacity.setError(null);
            teacity.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress()
    {
        String val = teaaddress.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            teaaddress.setError("Field can not be Empty");
            return false;
        }
        else
        {
            teaaddress.setError(null);
            teaaddress.setErrorEnabled(false);
            return true;
        }
    }

}