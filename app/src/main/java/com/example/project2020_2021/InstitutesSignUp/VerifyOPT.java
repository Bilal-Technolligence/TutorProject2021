package com.example.project2020_2021.InstitutesSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOPT extends AppCompatActivity {

    //Variables
    ImageView crossopt;
    PinView insPinfromUser;
    Button insVerify;
    String codebysystem;
    private FirebaseAuth mAuth;
    String insname, instype, insemail, inspass, inscountry, city, address, phoneno;
    //RelativeLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_p_t);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        //Cross Button
        crossopt =(ImageView)findViewById(R.id.crossopt);

        crossopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VerifyOPT.this, InstituteSignUp.class);
                startActivity(intent);
            }
        });

        //Hooks
         insPinfromUser = findViewById(R.id.ins_pin_view);
        //progressbar = findViewById(R.id.pgb);

         //Getting All Field's data
         insname = getIntent().getStringExtra("insname");
         instype = getIntent().getStringExtra("instype");
         insemail = getIntent().getStringExtra("insemail");
         inspass = getIntent().getStringExtra("inspass");
         inscountry = getIntent().getStringExtra("inscountry");
         city = getIntent().getStringExtra("city");
         address = getIntent().getStringExtra("address");
         phoneno = getIntent().getStringExtra("phoneno");

        //Verification Function Call
        sendVerificationCodeToInsUser(phoneno);

        //Verification of Phone Number
        insVerify = findViewById(R.id.insverifyopt);

        insVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = insPinfromUser.getText().toString();
                if(!code.isEmpty())
                {
                    VerifyCode(code);
                }
            }
        });
    }

    private void sendVerificationCodeToInsUser(String phoneno) {

         PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    codebysystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    String code = phoneAuthCredential.getSmsCode();
                    if(code!=null)
                    {
                        insPinfromUser.setText(code);
                        VerifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    Toast.makeText(VerifyOPT.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            };

    private void VerifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebysystem,code);
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            storeNewUsersData();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(VerifyOPT.this,"Verification Not Completed! Try Again.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    //Creation with Email and Password
    private void storeNewUsersData() {

        /*FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Institutes");

        UserHelperClass addNewUser = new UserHelperClass(insname, instype, insemail, inspass, inscountry, city, address, phoneno);

        reference.child(insname).setValue(addNewUser);*/

       // progressbar.setVisibility(View.VISIBLE);

        String email= "chareeb24@gmail.com";
        String pass ="aaa123";

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            UserHelperClass addNewUser = new UserHelperClass(insname, instype, insemail, inspass, inscountry, city, address, phoneno);
                            FirebaseDatabase.getInstance().getReference("Users").child("Institutes")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("InstituteDetails")
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
                                                            Toast.makeText(VerifyOPT.this, "Registration Successful! Check your Email for further Verification", Toast.LENGTH_LONG).show();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(VerifyOPT.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(VerifyOPT.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(VerifyOPT.this,"Registration UnSuccessful!",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

}