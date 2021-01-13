package com.example.project2020_2021.HomeSignInUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.InstitutesProfile.InstituteProfile;
import com.example.project2020_2021.IntroductoryAccount.AccountActivity;
import com.example.project2020_2021.MainHomeActivity.HomePage;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeRegistration extends AppCompatActivity {

    TextView lregisstu;
    Button forgotpass, login;
    LinearLayout newuser;
    TextInputLayout username, password;
    float v=0;
    ImageView crossopt;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_registration);

        mAuth = FirebaseAuth.getInstance();

        forgotpass = (Button) findViewById(R.id.forgotpass);
        login = (Button) findViewById(R.id.login);
        newuser=(LinearLayout)findViewById(R.id.newuser);
        username=(TextInputLayout) findViewById(R.id.username);
        password=(TextInputLayout) findViewById(R.id.password);

        username.setTranslationX(800);
        password.setTranslationX(800);
        login.setTranslationX(800);
        forgotpass.setTranslationX(800);
        newuser.setTranslationX(800);

        username.setAlpha(v);
        password.setAlpha(v);
        login.setAlpha(v);
        forgotpass.setAlpha(v);
        newuser.setAlpha(v);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        forgotpass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        newuser.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        lregisstu = (TextView) findViewById(R.id.homeregister);

        lregisstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeSignUp.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.homeregister),"thomeregister");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeRegistration.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        crossopt =(ImageView)findViewById(R.id.crossopt);
        crossopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeRegistration.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeRegistration.this, HomeForgotPass.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(forgotpass,"transition_forgot_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeRegistration.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Checking Wifi Connection
                if (!isConnected(this))
                {
                    showCustomDialog();
                }//Checking Wifi Connection


                if (!validateStuLEmail() | !validateStuLPass()){
                    return;
                }

                mAuth.signInWithEmailAndPassword(username.getEditText().getText().toString().trim(),password.getEditText().getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    if (mAuth.getCurrentUser().isEmailVerified())
                                    {
                                        startActivity(new Intent(HomeRegistration.this, HomePage.class));

                                    }
                                    else
                                    {
                                        Toast.makeText(HomeRegistration.this, "Please Verify your Email Address!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(HomeRegistration.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }


            //Checking Wifi Connection
            private boolean isConnected(View.OnClickListener onClickListener) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo wifiCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileCon = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if ((wifiCon != null && wifiCon.isConnected()) || (mobileCon != null && mobileCon.isConnected())) {
                    return true;
                } else {
                    return false;
                }

            }

            private void showCustomDialog() {

                AlertDialog.Builder builder= new AlertDialog.Builder(HomeRegistration.this);
                builder.setMessage("Please Connect to the Internet to proceed further.")
                        .setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }//Checking Wifi Connection

        });
    }

    //Validation Function

    private boolean validateStuLEmail()
    {
        String val = username.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            username.setError("Field can not be Empty");
            return false;
        }
        else
        {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateStuLPass()
    {
        String val = password.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            password.setError("Field can not be Empty");
            return false;
        }
        else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}