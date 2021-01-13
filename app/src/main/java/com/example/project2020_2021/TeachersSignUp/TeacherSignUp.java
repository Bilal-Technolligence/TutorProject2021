package com.example.project2020_2021.TeachersSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project2020_2021.R;
import com.example.project2020_2021.TeachersLogIn.TeacherRegistration;
import com.google.android.material.textfield.TextInputLayout;

public class TeacherSignUp extends AppCompatActivity {

    //Variables
    TextView rtitle, rtext, rloginbtn;
    TextInputLayout teaname, teatype, teaemail, teapass, teaconpass;
    Button rnext;
    LinearLayout rlogin;
    float v=0;
    ImageView backbtn;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up);

        //Hooks
        rtitle =(TextView)findViewById(R.id.register_title_text);
        rtext =(TextView)findViewById(R.id.register_text);
        rnext =(Button) findViewById(R.id.register_next_btn);
        rlogin =(LinearLayout) findViewById(R.id.register_login_btn);

        rloginbtn =(TextView)findViewById(R.id.loginbtn);

        teaname=(TextInputLayout) findViewById(R.id.teachername);
        teatype=(TextInputLayout) findViewById(R.id.teachertype);
        teaemail=(TextInputLayout) findViewById(R.id.teacheremail);
        teapass=(TextInputLayout) findViewById(R.id.teacherpass);
        teaconpass=(TextInputLayout) findViewById(R.id.teacherconpass);

        //for dropdown fields
        autoCompleteTextView = findViewById(R.id.autocompletetext);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeacherSignUp.this, TeacherRegistration.class);
                startActivity(intent);
            }
        });

        String [] option = {"Home Teaching", "Online Teaching", "Academy Teaching", "Teaching At My Place"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item,option);
        //To set Default Values
        autoCompleteTextView.setText("Select Teaching Type");
        autoCompleteTextView.setAdapter(arrayAdapter);


        //Transitions
        rtitle.setTranslationX(800);
        rtext.setTranslationX(800);
        teaname.setTranslationX(800);
        teatype.setTranslationX(800);
        teaemail.setTranslationX(800);
        teapass.setTranslationX(800);
        teaconpass.setTranslationX(800);
        rnext.setTranslationX(800);
        rlogin.setTranslationX(800);

        rtitle.setAlpha(v);
        rtext.setAlpha(v);
        teaname.setAlpha(v);
        teatype.setAlpha(v);
        teaemail.setAlpha(v);
        teapass.setAlpha(v);
        teaconpass.setAlpha(v);
        rnext.setAlpha(v);
        rlogin.setAlpha(v);

        rtitle.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        rtext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        teaname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        teatype.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        teaemail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        teapass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        teaconpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        rnext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        rlogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();


        //LogIn Text On-Click Listener
        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }

    public void callNextSignupScreen(View view){

        //passing data
        String teanameS = teaname.getEditText().getText().toString().trim();
        String teatypeS = teatype.getEditText().getText().toString().trim();
        String teaemailS = teaemail.getEditText().getText().toString().trim();
        String teapassS = teapass.getEditText().getText().toString().trim();


        if (!validateTeaName() | !validateTeaType() | !validateTeaEmail() | !validateTeaPassword() | !validateTeaConPassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), TeacherSignUp2.class);

        //passing data
        intent.putExtra("teaname",teanameS);
        intent.putExtra("teatype",teatypeS);
        intent.putExtra("teaemail",teaemailS);
        intent.putExtra("teapass",teapassS);


        //Add Transition
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair(rtitle, "transition_title_text");
        pairs[1] = new Pair(rtext, "transition_text");
        pairs[2] = new Pair(rnext, "transition_next_btn");
        pairs[3] = new Pair(rlogin, "transition_login_btn");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp.this,pairs);
        startActivity(intent,options.toBundle());

    }

    //Validation Function

    private boolean validateTeaName()
    {
        String val = teaname.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            teaname.setError("Field can not be Empty");
            return false;
        }
        else
        {
            teaname.setError(null);
            teaname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaEmail()
    {
        String val = teaemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty())
        {
            teaemail.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkEmail))
        {
            teaemail.setError("Invalid Email");
            return false;
        }
        else
        {
            teaemail.setError(null);
            teaemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaPassword()
    {
        String val = teapass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}"+               //at least 4 characters
                "$";

        if (val.isEmpty())
        {
            teapass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkPassword))
        {
            teapass.setError("Password must contain at-least 4 characters and 1 digit!");
            return false;
        }
        else
        {
            teapass.setError(null);
            teapass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaConPassword()
    {
        String val = teaconpass.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            teaconpass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(teapass.getEditText().getText().toString().trim()))
        {
            teaconpass.setError("Confirm Password must be same as Password");
            return false;
        }
        else
        {
            teaconpass.setError(null);
            teaconpass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaType() {

        String val = autoCompleteTextView.getText().toString().trim();

        if (val.equals("Select Teaching Type"))
        {
            teatype.setError("Please Select Teaching Type");
            return false;
        }
        else
        {
            teatype.setError(null);
            teatype.setErrorEnabled(false);
            return true;
        }

    }
}