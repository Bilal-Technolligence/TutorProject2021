package com.example.project2020_2021.TeachersLogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project2020_2021.InstitutesLogIn.InsForgotPass;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class TeaForgotPass extends AppCompatActivity {

    ImageView backbtn;
    Button resetpassemail;
    TextInputLayout fpemail;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_forgot_pass);
        mAuth = FirebaseAuth.getInstance();

        fpemail = (TextInputLayout) findViewById(R.id.fpemail);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeaForgotPass.this, TeacherRegistration.class);
                startActivity(intent);
            }
        });

        resetpassemail = (Button) findViewById(R.id.resetemailpass);

        resetpassemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();

            }
        });

    }

    private  void  resetPassword()
    {
        String email = fpemail.getEditText().getText().toString().trim();
        if (email.isEmpty())
        {
            fpemail.setError("Email is Required!");
            fpemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            fpemail.setError("Please Provide Valid Email!");
            fpemail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(TeaForgotPass.this,"Check Your Email To Reset Your Password!",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(TeaForgotPass.this,"Try Again Something Wrong Had Happened!",Toast.LENGTH_LONG).show();
                }

            }
        });
    } //Reset Email Password
}