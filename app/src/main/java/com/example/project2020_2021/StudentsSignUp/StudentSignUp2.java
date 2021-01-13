package com.example.project2020_2021.StudentsSignUp;

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
import com.example.project2020_2021.StudentsLogIn.StudentRegistration;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class StudentSignUp2 extends AppCompatActivity {

    LinearLayout rlogin;
    ImageView backbtn;
    TextView ttext, subtext, tloginbtn;
    Button teanext3;
    TextInputLayout stucity, stuaddress ,stuphoneno;
    CountryCodePicker stucountry, stuphonecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up2);

        backbtn = (ImageView) findViewById(R.id.backbtn);
        ttext = (TextView) findViewById(R.id.register_teacher_title_text);
        subtext = (TextView) findViewById(R.id.register_teacher_text);
        tloginbtn = (TextView)  findViewById(R.id.student_loginbtn);
        teanext3 = (Button) findViewById(R.id.register_student_next_btn);


        tloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.student_loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentSignUp2.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentSignUp2.this, StudentSignUp.class);
                startActivity(intent);
            }
        });

        rlogin =(LinearLayout) findViewById(R.id.register_teacher_login_btn);

        stucity = (TextInputLayout)findViewById(R.id.stucity);
        stuaddress = (TextInputLayout)findViewById(R.id.stuaddress);
        stuphoneno = (TextInputLayout)findViewById(R.id.stupno);
        stuphonecode = (CountryCodePicker) findViewById(R.id.stuCountryCode);
        stucountry = (CountryCodePicker) findViewById(R.id.stuCountry);

        teanext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCity() | !validateAddress() | !validateStuPhoneNumber()){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), StudentSignUp3.class);

                //getting all values passed from previous screen
                String _stuname = getIntent().getStringExtra("stuname");
                String _stutype = getIntent().getStringExtra("stutype");
                String _stuemail = getIntent().getStringExtra("stuemail");
                String _stupass = getIntent().getStringExtra("stupass");


                //getting fields data
                String stucountryS = stucountry.getSelectedCountryName().toString().trim();
                String stucityS = stucity.getEditText().getText().toString().trim();
                String stuaddressS = stuaddress.getEditText().getText().toString().trim();
                String stuphoneS = stuphoneno.getEditText().getText().toString().trim();

                //Remove first zero if entered!
                if (stuphoneS.charAt(0) == '0') {
                    stuphoneS = stuphoneS.substring(1);
                }

                String stuphonefullS = "+" + stuphonecode.getFullNumber() + stuphoneS;

                //passing data
                intent.putExtra("stuname",_stuname);
                intent.putExtra("stutype",_stutype);
                intent.putExtra("stuemail",_stuemail);
                intent.putExtra("stupass",_stupass);
                intent.putExtra("stucountry",stucountryS);
                intent.putExtra("stucity",stucityS);
                intent.putExtra("stuaddress",stuaddressS);
                intent.putExtra("stuphoneno",stuphonefullS);

                //Add Transition
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(ttext, "transition_title_text");
                pairs[1] = new Pair(subtext, "transition_text");
                pairs[2] = new Pair(teanext3, "transition_next_btn");
                pairs[3] = new Pair(rlogin, "transition_login_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentSignUp2.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });


    }

    private boolean validateStuPhoneNumber()
    {
        String val = stuphoneno.getEditText().getText().toString().trim();
        if (val.isEmpty())
        {
            stuphoneno.setError("Field can not be Empty");
            return false;
        }
        else
        {
            stuphoneno.setError(null);
            stuphoneno.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCity()
    {
        String val = stucity.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            stucity.setError("Field can not be Empty");
            return false;
        }
        else
        {
            stucity.setError(null);
            stucity.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress()
    {
        String val = stuaddress.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            stuaddress.setError("Field can not be Empty");
            return false;
        }
        else
        {
            stuaddress.setError(null);
            stuaddress.setErrorEnabled(false);
            return true;
        }
    }
}