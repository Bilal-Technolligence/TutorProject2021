package com.example.project2020_2021.MainHomeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project2020_2021.IntroductoryAccount.AccountActivity;
import com.example.project2020_2021.R;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variables
    static final float END_SCALE = 0.7f;

    ChipNavigationBar chipNavigationBar;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon, chaticon;
    LinearLayout contentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        chipNavigationBar = (ChipNavigationBar) findViewById(R.id.homebottomnavmenu);
        chipNavigationBar.setItemSelected(R.id.insnav, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.homefragmentcontainer, new HomeInsFragment()).commit();
        bottomMenu();

        //Hooks
        menuIcon = findViewById(R.id.menu_icon);
        chaticon = findViewById(R.id.menu_icon_chat);

        chaticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Chats.class);
                startActivity(intent);
            }
        });

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        contentView = findViewById(R.id.content);

        //Navigation Drawer
        navigationDrawer();

    }


    //Navigation Drawer Functions
    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        drawerLayout.setScrimColor(getResources().getColor(R.color.appblue));

        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                openActivity(HomePage.class);
                break;
            case R.id.nav_notification_setting:
                openActivity(Notification_Settings.class);
                break;
            case R.id.nav_logout:
                openActivity(AccountActivity.class);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share is Clicked",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rateus:
                Toast.makeText(this, "Rate US is Clicked",Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }

        return false;
    }

    private void openActivity(Class homePageClass) {
        startActivity((new Intent(getApplicationContext(),homePageClass)));
    } //Navigation Drawer Functions


    //Bottom Menu
    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.insnav:
                        fragment = new HomeInsFragment();
                        break;

                    case R.id.teanav:
                        fragment = new HomeTeaFragment();
                        break;

                    case R.id.stunav:
                        fragment = new HomeStuFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.homefragmentcontainer, fragment).commit();
            }
        });

    }


}