package com.example.project2020_2021.TeachersSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2020_2021.R;
import com.example.project2020_2021.TeachersLogIn.TeacherRegistration;
import com.google.android.material.textfield.TextInputLayout;

public class TeacherSignUp3 extends AppCompatActivity {

    //Variables
    LinearLayout rlogin;
    ImageView backbtn;
    Button selectfile, nextbtn;
    TextView  tloginbtn,ttext, subtext;
    Uri pdfUri;   //Url for local storage
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2 ;
    TextInputLayout qua, exp, wteach, teafee, notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up3);


        notification=(TextInputLayout) findViewById(R.id.notification);
        selectfile = (Button) findViewById(R.id.select_file);

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(TeacherSignUp3.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(TeacherSignUp3.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        //for dropdown fields
        autoCompleteTextView = findViewById(R.id.autocompletetextclass);
        String [] option = {"Class 1 to 5", "Class 1 to 10", "Class 1 to 12", "BS Level","Matric", "Intermediate", "Matric to Intermediate", "Any Class"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.option_item,option);
        //To set Default Values
        autoCompleteTextView.setText("Select Classes You Want to Teach");
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView2 = findViewById(R.id.autocompletetextfee);
        String [] option2 = {"Fixed", "Negotiable"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.option_item,option2);
        //To set Default Values
        autoCompleteTextView2.setText("Select Fee Type");
        autoCompleteTextView2.setAdapter(arrayAdapter2);


        tloginbtn = (TextView)  findViewById(R.id.teacher_loginbtn);
        tloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherRegistration.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(findViewById(R.id.teacher_loginbtn),"tloginbtn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp3.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        backbtn = (ImageView) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherSignUp3.this, TeacherSignUp2.class);
                startActivity(intent);
            }
        });

        //Hooks
        ttext = (TextView) findViewById(R.id.register_teacher_title_text);
        subtext = (TextView) findViewById(R.id.register_teacher_text);

        rlogin =(LinearLayout) findViewById(R.id.register_teacher_login_btn);

        qua = (TextInputLayout) findViewById(R.id.teaqua);
        exp = (TextInputLayout) findViewById(R.id.teaexp);
        wteach = (TextInputLayout) findViewById(R.id.wanttoteach);
        teafee = (TextInputLayout) findViewById(R.id.fee);

        nextbtn=(Button)findViewById(R.id.register_teacher_next_btn);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateTeaQualification() | !validateTeaDegree() | !validateTeaExp() | !validateTeaWantTeach() | !validateTeaFee()){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), TeacherSignUp4.class);

                //getting all values passed from previous screen
                String _teaname = getIntent().getStringExtra("teaname");
                String _teatype = getIntent().getStringExtra("teatype");
                String _teaemail = getIntent().getStringExtra("teaemail");
                String _teapass = getIntent().getStringExtra("teapass");
                String _teacountry = getIntent().getStringExtra("teacountry");
                String _teacity = getIntent().getStringExtra("teacity");
                String _teaaddress = getIntent().getStringExtra("teaaddress");
                String _teaphone = getIntent().getStringExtra("teaphoneno");


                //getting fields data
                String _teaquaS = qua.getEditText().getText().toString().trim();
                String _teaexpS = exp.getEditText().getText().toString().trim();
                //String teadegreeS = address.getEditText().getText().toString().trim();
                String _teawteachS = wteach.getEditText().getText().toString().trim();
                String _teafeeS = teafee.getEditText().getText().toString().trim();


                //passing data
                intent.putExtra("teaname",_teaname);
                intent.putExtra("teatype",_teatype);
                intent.putExtra("teaemail",_teaemail);
                intent.putExtra("teapass",_teapass);
                intent.putExtra("teacountry",_teacountry);
                intent.putExtra("teacity",_teacity);
                intent.putExtra("teaaddress",_teaaddress);
                intent.putExtra("teaphoneno",_teaphone);
                intent.putExtra("teaqua",_teaquaS);
                intent.putExtra("teaexp",_teaexpS);
                intent.putExtra("teawteach",_teawteachS);
                intent.putExtra("teafee",_teafeeS);

                //Add Transition
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(ttext, "transition_title_text");
                pairs[1] = new Pair(subtext, "transition_text");
                pairs[2] = new Pair(nextbtn, "transition_next_btn");
                pairs[3] = new Pair(rlogin, "transition_login_btn");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TeacherSignUp3.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==9 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(TeacherSignUp3.this,"Please Provide Permission...",Toast.LENGTH_SHORT).show();
        }
    }

    private  void selectPdf(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.getEditText().setText("A file is selected: " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(TeacherSignUp3.this, "Please Select a File", Toast.LENGTH_SHORT).show();
        }
    }

    //Validation Function

    private boolean validateTeaQualification()
    {
        String val = qua.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            qua.setError("Field can not be Empty");
            return false;
        }
        else
        {
            qua.setError(null);
            qua.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaExp()
    {
        String val = exp.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            exp.setError("Field can not be Empty");
            return false;
        }
        else
        {
            exp.setError(null);
            exp.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTeaWantTeach() {

        String val = autoCompleteTextView.getText().toString().trim();

        if (val.equals("Select Classes You Want to Teach"))
        {
            wteach.setError("Please Select Classes You Want to Teach");
            return false;
        }
        else
        {
            wteach.setError(null);
            wteach.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validateTeaFee() {

        String val = autoCompleteTextView2.getText().toString().trim();

        if (val.equals("Select Fee Type"))
        {
            teafee.setError("Please Select Fee Type");
            return false;
        }
        else
        {
            teafee.setError(null);
            teafee.setErrorEnabled(false);
            return true;
        }

    }


    private boolean validateTeaDegree() {

        String val = notification.getHint().toString().trim();
        String val2 = notification.getEditText().getText().toString().trim();

        if (val.equals("Select current Degree pdf") && val2.isEmpty())
        {
            notification.setError("Please Select current Degree pdf");
            return false;
        }
        else
        {
            notification.setError(null);
            notification.setErrorEnabled(false);
            return true;
        }

    }

}