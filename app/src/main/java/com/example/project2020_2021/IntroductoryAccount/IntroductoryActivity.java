package com.example.project2020_2021.IntroductoryAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2020_2021.LiquidSwipeSkipActivity.MainActivity;
import com.example.project2020_2021.R;

public class IntroductoryActivity extends AppCompatActivity {


    private static int SPLASH_SCREEN = 2000;

    //Variables
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo, slogan;

    SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView1);
        slogan = findViewById(R.id.textView2);
        //splashImg = findViewById(R.id.splashimg);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


      /* splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        slogan.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        image.animate().translationY(1400).setDuration(1000).setStartDelay(4000);*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime =onBoardingScreen.getBoolean("firstTime",true);

                if (isFirstTime)
                {
                    SharedPreferences.Editor editor= onBoardingScreen.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent= new Intent(IntroductoryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);

                }


                else {
                    Intent intent= new Intent(IntroductoryActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();

                    overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);

                }



            }
        }, SPLASH_SCREEN);


    }




}