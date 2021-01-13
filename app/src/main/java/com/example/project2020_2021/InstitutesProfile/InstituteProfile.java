package com.example.project2020_2021.InstitutesProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.project2020_2021.InstitutesVacancies.AddVacancy;
import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class InstituteProfile extends AppCompatActivity implements TabLayout.OnTabSelectedListener  {

    TabLayout tabLayout;
    ViewPager viewPager;
    private CircleImageView insprofileimage;
    private static final int PICK_IMAGE =1;
    Uri imageUri;
    FloatingTextButton insaddVacancy, insLogout;

    //Login Session
    boolean session;


    //Database
    FirebaseAuth mAuth;
    String userId;
    FirebaseUser user;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_profile);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("Users/"+"Institutes/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(insprofileimage);
            }
        });

        //Hooks
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();

        tabLayout = findViewById(R.id.instablayout);
        viewPager = findViewById(R.id.insviewpager);

        tabLayout.addTab(tabLayout.newTab().setText("PAGE 1"));
        tabLayout.addTab(tabLayout.newTab().setText("PAGE 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Logout
        insLogout = findViewById(R.id.inslogoutbtn);
        insLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(InstituteProfile.this, InstituteRegistration.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        final InsProfileAdapter adapter = new InsProfileAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(this);

        insprofileimage = findViewById(R.id.insprofileimage);

        insprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select Image"), PICK_IMAGE);
;            }
        });

        //Add Vacancy
        insaddVacancy = (FloatingTextButton) findViewById(R.id.insaddvacancy);
        insaddVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddVacancy.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data!=null) {
            imageUri = data.getData();

            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    //insprofileimage.setImageBitmap(bitmap);

                    uploadImageToFirebase(resultUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void uploadImageToFirebase(Uri imageUri) {

                //Upload Image to firebase
       final StorageReference fileRef = storageReference.child("Users/"+"Institutes/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(insprofileimage);
                    }
                });
                //Toast.makeText(InstituteProfile.this,"Image Uploaded",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InstituteProfile.this,"Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}