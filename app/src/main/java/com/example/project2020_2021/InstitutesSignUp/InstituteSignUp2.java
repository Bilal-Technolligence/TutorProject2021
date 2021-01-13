package com.example.project2020_2021.InstitutesSignUp;

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
import android.widget.TextView;

import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class InstituteSignUp2 extends AppCompatActivity {

    //Variables
    TextView rloginbtn;
    Button toopt;
    ImageView backbtn;
    TextInputLayout city, address ,phonenoi;
    CountryCodePicker inscountry, insphonecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_sign_up2);

        //Hooks
        rloginbtn =(TextView)findViewById(R.id.loginbtn);

        toopt =(Button) findViewById(R.id.register_optnext_btn);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        //Back Button
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InstituteSignUp2.this, InstituteSignUp.class);
                startActivity(intent);
            }
        });


        //LogIn Button
        rloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InstituteRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InstituteSignUp2.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        //Hooks
        inscountry = (CountryCodePicker) findViewById(R.id.insCountry);
        city = (TextInputLayout)findViewById(R.id.inscity);
        address = (TextInputLayout)findViewById(R.id.insaddress);
        insphonecode = (CountryCodePicker) findViewById(R.id.insCountryCode);
        phonenoi = (TextInputLayout)findViewById(R.id.insphonenumber);


        //OTP Button
        toopt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Checking Wifi Connection
                if (!isConnected(this))
                {
                    showCustomDialog();
                }//Checking Wifi Connection


                if (!validateCity() | !validateAddress() | !validateInsPhoneNumber()){
                    return;
                }

                Intent intent = new Intent(InstituteSignUp2.this, VerifyOPT.class);

                //getting all values passed from previous screen
                String _insname = getIntent().getStringExtra("insname");
                String _instype = getIntent().getStringExtra("instype");
                String _insemail = getIntent().getStringExtra("insemail");
                String _inspass = getIntent().getStringExtra("inspass");


                //getting fields data
                String inscountryS = inscountry.getSelectedCountryName().toString().trim();
                String inscityS = city.getEditText().getText().toString().trim();
                String insaddressS = address.getEditText().getText().toString().trim();
                String insphoneS = phonenoi.getEditText().getText().toString().trim();

                //Remove first zero if entered!
                if (insphoneS.charAt(0) == '0') {
                    insphoneS = insphoneS.substring(1);
                }

                String insphonefullS = "+" + insphonecode.getFullNumber() + insphoneS;

                //passing data
                intent.putExtra("insname",_insname);
                intent.putExtra("instype",_instype);
                intent.putExtra("insemail",_insemail);
                intent.putExtra("inspass",_inspass);
                intent.putExtra("inscountry",inscountryS);
                intent.putExtra("city",inscityS);
                intent.putExtra("address",insaddressS);
                intent.putExtra("phoneno",insphonefullS);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(toopt,"transition_register_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(InstituteSignUp2.this,pairs);
                startActivity(intent);
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

                AlertDialog.Builder builder= new AlertDialog.Builder(InstituteSignUp2.this);
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

        });//OTP Button

    }

    //Validation Functions
    private boolean validateInsPhoneNumber()
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
        String val = city.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            city.setError("Field can not be Empty");
            return false;
        }
        else
        {
            city.setError(null);
            city.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress()
    {
        String val = address.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            address.setError("Field can not be Empty");
            return false;
        }
        else
        {
            address.setError(null);
            address.setErrorEnabled(false);
            return true;
        }
    }

}