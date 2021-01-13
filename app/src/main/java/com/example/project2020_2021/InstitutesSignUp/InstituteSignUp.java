package com.example.project2020_2021.InstitutesSignUp;

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

import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;

public class InstituteSignUp extends AppCompatActivity {

    //Variables
    TextView rtitle, rtext, rloginbtn;
    TextInputLayout insname, instype, insemail, inspass, insconpass;
    Button rnext;
    LinearLayout rlogin;
    float v=0;
    ImageView backbtn;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_sign_up);

        //Hooks
        rtitle =(TextView)findViewById(R.id.register_title_text);
        rtext =(TextView)findViewById(R.id.register_text);
        rnext =(Button) findViewById(R.id.register_next_btn);
        rlogin =(LinearLayout) findViewById(R.id.register_login_btn);

        rloginbtn =(TextView)findViewById(R.id.loginbtn);

        insname=(TextInputLayout) findViewById(R.id.institutename);
        instype=(TextInputLayout) findViewById(R.id.institutetype);
        insemail=(TextInputLayout) findViewById(R.id.instituteemail);
        inspass=(TextInputLayout) findViewById(R.id.institutepass);
        insconpass=(TextInputLayout) findViewById(R.id.instituteconpass);


        //Back Button
        backbtn = (ImageView) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InstituteSignUp.this, InstituteRegistration.class);
                startActivity(intent);
            }
        });

        //for dropdown fields
        autoCompleteTextView = findViewById(R.id.autocompletetext);

        String [] option = {"School", "College", "University", "Academy", "Madrassa"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item,option);
        //To set Default Values
        autoCompleteTextView.setText("Select Institute Type");
        autoCompleteTextView.setAdapter(arrayAdapter);


        //Transitions
        rtitle.setTranslationX(800);
        rtext.setTranslationX(800);
        insname.setTranslationX(800);
        instype.setTranslationX(800);
        insemail.setTranslationX(800);
        inspass.setTranslationX(800);
        insconpass.setTranslationX(800);
        rnext.setTranslationX(800);
        rlogin.setTranslationX(800);

        rtitle.setAlpha(v);
        rtext.setAlpha(v);
        insname.setAlpha(v);
        instype.setAlpha(v);
        insemail.setAlpha(v);
        inspass.setAlpha(v);
        insconpass.setAlpha(v);
        rnext.setAlpha(v);
        rlogin.setAlpha(v);

        rtitle.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        rtext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        insname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        instype.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        insemail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        inspass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        insconpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        rnext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        rlogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();


        //LogIn Text On-Click Listener
        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstituteRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InstituteSignUp.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

    }

    public void callNextSignupScreen(View view){

        //passing data
        String insnameS = insname.getEditText().getText().toString().trim();
        String instypeS = instype.getEditText().getText().toString().trim();
        String insemailS = insemail.getEditText().getText().toString().trim();
        String inspassS = inspass.getEditText().getText().toString().trim();

        if (!validateInsName()  | !validateInsType() | !validateInsEmail() | !validateInsPassword() | !validateInsConPassword()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), InstituteSignUp2.class);

        //passing data
        intent.putExtra("insname",insnameS);
        intent.putExtra("instype",instypeS);
        intent.putExtra("insemail",insemailS);
        intent.putExtra("inspass",inspassS);

        //Add Transition
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair(rtitle, "transition_title_text");
        pairs[1] = new Pair(rtext, "transition_text");
        pairs[2] = new Pair(rnext, "transition_next_btn");
        pairs[3] = new Pair(rlogin, "transition_login_btn");


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InstituteSignUp.this,pairs);
        startActivity(intent,options.toBundle());

    }



    //Validation Function

    private boolean validateInsName()
    {
        String val = insname.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            insname.setError("Field can not be Empty");
            return false;
        }
        else
        {
            insname.setError(null);
            insname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateInsEmail()
    {
        String val = insemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty())
        {
            insemail.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkEmail))
        {
            insemail.setError("Invalid Email");
            return false;
        }
        else
        {
            insemail.setError(null);
            insemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateInsPassword()
    {
        String val = inspass.getEditText().getText().toString().trim();
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
            inspass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkPassword))
        {
            inspass.setError("Password must contain at-least 4 characters and 1 digit!");
            return false;
        }
        else
        {
            inspass.setError(null);
            inspass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateInsConPassword()
    {
        String val = insconpass.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            insconpass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(inspass.getEditText().getText().toString().trim()))
        {
            insconpass.setError("Confirm Password must be same as Password");
            return false;
        }
        else
        {
            insconpass.setError(null);
            insconpass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateInsType() {

        String val = autoCompleteTextView.getText().toString().trim();

        if (val.equals("Select Institute Type"))
        {
            instype.setError("Please Select Institute Type");
            return false;
        }
        else
        {
            instype.setError(null);
            instype.setErrorEnabled(false);
            return true;
        }

    }

}