package com.example.project2020_2021.HomeSignInUp;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.project2020_2021.Databases.HomeUserHelperClass;
import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.InstitutesSignUp.VerifyOPT;
import com.example.project2020_2021.MainHomeActivity.HomePage;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeSignUp extends AppCompatActivity {

    //Variables
    TextView rtitle, rtext, rloginbtn;
    TextInputLayout homename, homeemail, homepass, homeconpass;
    Button signup;
    LinearLayout rlogin;
    float v=0;
    ImageView backbtn;
    String husername, huseremail, huserpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_sign_up);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Hooks
        rtitle =(TextView)findViewById(R.id.register_title_text);
        rtext =(TextView)findViewById(R.id.register_text);
        rlogin =(LinearLayout) findViewById(R.id.register_login_btn);

        rloginbtn =(TextView)findViewById(R.id.loginbtn);

        homename=(TextInputLayout) findViewById(R.id.homename);
        homeemail=(TextInputLayout) findViewById(R.id.homeemail);
        homepass=(TextInputLayout) findViewById(R.id.homepass);
        homeconpass=(TextInputLayout) findViewById(R.id.homeconpass);
        signup = (Button) findViewById(R.id.signuphome_next_btn);

        //Transitions
        rtitle.setTranslationX(800);
        rtext.setTranslationX(800);
        homename.setTranslationX(800);
        homeemail.setTranslationX(800);
        homepass.setTranslationX(800);
        homeconpass.setTranslationX(800);
        signup.setTranslationX(800);
        rlogin.setTranslationX(800);

        rtitle.setAlpha(v);
        rtext.setAlpha(v);
        homename.setAlpha(v);
        homeemail.setAlpha(v);
        homepass.setAlpha(v);
        homeconpass.setAlpha(v);
        signup.setAlpha(v);
        rlogin.setAlpha(v);

        rtitle.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        rtext.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        homename.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        homeemail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        homepass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        homeconpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();
        rlogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1100).start();




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                husername = homename.getEditText().getText().toString().trim();
                huseremail = homeemail.getEditText().getText().toString().trim();
                huserpassword = homepass.getEditText().getText().toString().trim();

                if (!validateHomeName() | !validateHomeEmail() | !validateHomePassword() | !validateHomeConPassword()){
                    return;
                }

                storeNewUsersData();


                /*Intent intent = new Intent(getApplicationContext(), HomePage.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.signuphome_next_btn),"tsignuphome_next_btn");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeSignUp.this,pairs);
                startActivity(intent,options.toBundle());*/

            }
        });


        backbtn = (ImageView) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeSignUp.this, HomeRegistration.class);
                startActivity(intent);
            }
        });

        //LogIn Text On-Click Listener
        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeSignUp.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

    }

    //Creation with Email and Password
    private void storeNewUsersData() {

        /*FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users").child("HomeUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        HomeUserHelperClass addNewUser = new HomeUserHelperClass(husername, huseremail, huserpassword);

        reference.setValue(addNewUser);*/

        // progressbar.setVisibility(View.VISIBLE);

        String email= "ch.imtinan@gmail.com";
        String pass ="ddd123";

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            HomeUserHelperClass addNewUser = new HomeUserHelperClass(husername, huseremail, huserpassword);
                            FirebaseDatabase.getInstance().getReference("Users").child("HomeUsers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(addNewUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>()
                                                {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(HomeSignUp.this, "Registration Successful! Check your Email for further Verification", Toast.LENGTH_LONG).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(HomeSignUp.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(HomeSignUp.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(HomeSignUp.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }




    //Validation Function

    private boolean validateHomeName()
    {
        String val = homename.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            homename.setError("Field can not be Empty");
            return false;
        }
        else
        {
            homename.setError(null);
            homename.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateHomeEmail()
    {
        String val = homeemail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty())
        {
            homeemail.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkEmail))
        {
            homeemail.setError("Invalid Email");
            return false;
        }
        else
        {
            homeemail.setError(null);
            homeemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateHomePassword()
    {
        String val = homepass.getEditText().getText().toString().trim();
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
            homepass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(checkPassword))
        {
            homepass.setError("Password must contain at-least 4 characters and 1 digit!");
            return false;
        }
        else
        {
            homepass.setError(null);
            homepass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateHomeConPassword()
    {
        String val = homeconpass.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            homeconpass.setError("Field can not be Empty");
            return false;
        }
        else if (!val.matches(homepass.getEditText().getText().toString().trim()))
        {
            homeconpass.setError("Confirm Password must be same as Password");
            return false;
        }
        else
        {
            homeconpass.setError(null);
            homeconpass.setErrorEnabled(false);
            return true;
        }
    }

}