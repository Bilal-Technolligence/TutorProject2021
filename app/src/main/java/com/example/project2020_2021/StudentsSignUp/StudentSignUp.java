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

public class StudentSignUp extends AppCompatActivity {

    //Variables
    TextView rtitle, rtext, rloginbtn;
    TextInputLayout stuname, stutype, stuemail, stupass, stuconpass;
    Button rnext;
    LinearLayout rlogin;
    float v=0;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

        //Hooks
        rtitle =(TextView)findViewById(R.id.register_title_text);
        rtext =(TextView)findViewById(R.id.register_text);
        rnext =(Button) findViewById(R.id.register_next_btn);
        rlogin =(LinearLayout) findViewById(R.id.register_login_btn);

        rloginbtn =(TextView)findViewById(R.id.loginbtn);

        stuname=(TextInputLayout) findViewById(R.id.studentname);
        stutype=(TextInputLayout) findViewById(R.id.studentclass);
        stuemail=(TextInputLayout) findViewById(R.id.studentemail);
        stupass=(TextInputLayout) findViewById(R.id.studentpass);
        stuconpass=(TextInputLayout) findViewById(R.id.studentconpass);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentSignUp.this, StudentRegistration.class);
                startActivity(intent);
            }
        });

        //Transitions
        rtitle.setTranslationX(800);
        rtext.setTranslationX(800);
        stuname.setTranslationX(800);
        stutype.setTranslationX(800);
        stuemail.setTranslationX(800);
        stupass.setTranslationX(800);
        stuconpass.setTranslationX(800);
        rnext.setTranslationX(800);
        rlogin.setTranslationX(800);

        rtitle.setAlpha(v);
        rtext.setAlpha(v);
        stuname.setAlpha(v);
        stutype.setAlpha(v);
        stuemail.setAlpha(v);
        stupass.setAlpha(v);
        stuconpass.setAlpha(v);
        rnext.setAlpha(v);
        rlogin.setAlpha(v);

        rtitle.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        rtext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        stuname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        stutype.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        stuemail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        stupass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        stuconpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        rnext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        rlogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();


        //LogIn Text On-Click Listener
        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentSignUp.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        rnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //passing data
                String stunameS = stuname.getEditText().getText().toString().trim();
                String stutypeS = stutype.getEditText().getText().toString().trim();
                String stuemailS = stuemail.getEditText().getText().toString().trim();
                String stupassS = stupass.getEditText().getText().toString().trim();

                if (!validatestuName() | !validatestuclass() | !validatestuEmail() | !validatestuPassword() | !validatestuConPassword()){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), StudentSignUp2.class);

                //passing data
                intent.putExtra("stuname",stunameS);
                intent.putExtra("stutype",stutypeS);
                intent.putExtra("stuemail",stuemailS);
                intent.putExtra("stupass",stupassS);

                //Add Transition
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(rtitle, "transition_title_text");
                pairs[1] = new Pair(rtext, "transition_text");
                pairs[2] = new Pair(rnext, "transition_next_btn");
                pairs[3] = new Pair(rlogin, "transition_login_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(StudentSignUp.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });

    }

    //Validation Function

    private boolean validatestuName()
    {
        String val = stuname.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            stuname.setError("Field can not be Empty");
            return false;
        }
        else
        {
            stuname.setError(null);
            stuname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatestuclass()
    {
        String val = stutype.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            stutype.setError("Field can not be Empty");
            return false;
        }
        else
        {
            stutype.setError(null);
            stutype.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatestuEmail()
    {
        String val = stuemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty())
        {
            stuemail.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkEmail))
        {
            stuemail.setError("Invalid Email");
            return false;
        }
        else
        {
            stuemail.setError(null);
            stuemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatestuPassword()
    {
        String val = stupass.getEditText().getText().toString().trim();
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
            stupass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkPassword))
        {
            stupass.setError("Password must contain at-least 4 characters and 1 digit!");
            return false;
        }
        else
        {
            stupass.setError(null);
            stupass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatestuConPassword()
    {
        String val = stuconpass.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            stuconpass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(stupass.getEditText().getText().toString().trim()))
        {
            stuconpass.setError("Confirm Password must be same as Password");
            return false;
        }
        else
        {
            stuconpass.setError(null);
            stuconpass.setErrorEnabled(false);
            return true;
        }
    }

}