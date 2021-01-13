package com.example.project2020_2021.IntroductoryAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2020_2021.HomeSignInUp.HomeRegistration;
import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.R;
import com.example.project2020_2021.StudentsLogIn.StudentRegistration;
import com.example.project2020_2021.TeachersLogIn.TeacherRegistration;

public class AccountActivity extends AppCompatActivity {

    //Variables
    ImageView bgnew, rins, rtea, rstu, rhome;
    Animation bgnewanim, frombottom;
    LinearLayout textsplash, texthome, menus, leafr;
    TextView rinstitutetext, rteachertext,rstudenttext, rhometext;

    private  long backpressedtime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account);

        //Hooks
        bgnew = (ImageView) findViewById(R.id.bgnew);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        leafr = (LinearLayout) findViewById(R.id.leafr);


        rinstitutetext = (TextView) findViewById(R.id.RInstitutetext);
        rstudenttext = (TextView) findViewById(R.id.RStudenttext);
        rteachertext = (TextView) findViewById(R.id.RTeachertext);
        rhometext = (TextView) findViewById(R.id.RHometext);


        rins = (ImageView) findViewById(R.id.RInstitute);
        rtea = (ImageView) findViewById(R.id.RTeacher);
        rstu = (ImageView) findViewById(R.id.RStudent);
        rhome = (ImageView) findViewById(R.id.RHome);

        //Register Institutes
        rins.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(AccountActivity.this, InstituteRegistration.class);

               Pair[] pairs = new Pair[2];
               pairs[0] = new Pair<View,String>(rins,"tins");
               pairs[1] = new Pair<View,String>(rinstitutetext,"tinst");

               ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountActivity.this,pairs);
               startActivity(intent,options.toBundle());

           }
       });

        //Register Teachers
        rtea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, TeacherRegistration.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(rtea,"ttea");
                pairs[1] = new Pair<View,String>(rteachertext,"tteat");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        //Register Students
        rstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, StudentRegistration.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(rstu,"tstu");
                pairs[1] = new Pair<View,String>(rstudenttext,"tstut");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        //Register To Home
        rhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, HomeRegistration.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(rhome,"thome");
                pairs[1] = new Pair<View,String>(rhometext,"thomet");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AccountActivity.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });


        //Animations
        bgnewanim = AnimationUtils.loadAnimation(this,R.anim.bgnewanim);
        frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        bgnew.animate().translationY(-1200).setDuration(1000).setStartDelay(1800);
        textsplash.animate().translationY(140).alpha(0).setDuration(1000).setStartDelay(1800);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);
        leafr.startAnimation(frombottom);



    }

    //Progress Bar
    @Override
    public void onBackPressed() {
       if (backpressedtime + 2000 > System.currentTimeMillis())
       {
           super.onBackPressed();
           return;
       }
       else
       {
           Toast.makeText(this,"Press Back again to Exit",Toast.LENGTH_SHORT).show();
       }
       backpressedtime= System.currentTimeMillis();

    }
}