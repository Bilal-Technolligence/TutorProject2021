package com.example.project2020_2021.StudentsProfile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.project2020_2021.Databases.StuUserHelperClass;
import com.example.project2020_2021.Databases.TeaUserHelperClass;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StuProfileTwoFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update2;
    String stugender, studate, stuteatype , stussub;

    AutoCompleteTextView autoCompleteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.stu_profile_two, container,false);

        //for dropdown fields
        autoCompleteTextView = root.findViewById(R.id.autocompletetextstuteatype);
        String [] option = {"Home Teacher", "Online Teacher", "Teacher at Academy", "Teacher At My Place"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.option_item,option);


        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Students").child(userId);

        //Hooks
        final TextInputLayout stugendertv = (TextInputLayout) root.findViewById(R.id.pstugender);
        final TextInputLayout studatetv = (TextInputLayout) root.findViewById(R.id.pstuage);
        final TextInputLayout stussubtv = (TextInputLayout) root.findViewById(R.id.pstuss);


        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StuUserHelperClass userProfile = snapshot.getValue(StuUserHelperClass.class);

                if (userProfile != null)
                {
                    stugender = userProfile.getStugender();
                    studate = userProfile.getStudate();
                    stuteatype = userProfile.getStuteachertype();
                    stussub = userProfile.getStussub();

                    stugendertv.getEditText().setText(stugender);
                    studatetv.getEditText().setText(studate);
                    autoCompleteTextView.setText(stuteatype);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    stussubtv.getEditText().setText(stussub);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });

        //Update Data
        update2 = (Button) root.findViewById(R.id.stuupdatep2);
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isStuGenderChanged() | isStuAgeChanged() | isStuTeaTypeChanged() | isStuSSubChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isStuGenderChanged() {
                        if (!stugender.equals(stugendertv.getEditText().getText().toString().trim()))
                        {

                            if (stugendertv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stugendertv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stugender").setValue(stugendertv.getEditText().getText().toString().trim());
                                stugender = stugendertv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }

                    private boolean isStuAgeChanged() {

                        if (!studate.equals(studatetv.getEditText().getText().toString().trim()))
                        {

                            if (studatetv.getEditText().getText().toString().trim().isEmpty())
                            {
                                studatetv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("studate").setValue(studatetv.getEditText().getText().toString().trim());
                                studate = studatetv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isStuTeaTypeChanged() {

                        if (!stuteatype.equals(autoCompleteTextView.getText().toString().trim()))
                        {

                            if (autoCompleteTextView.getText().toString().trim().isEmpty())
                            {
                                autoCompleteTextView.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stuteachertype").setValue(autoCompleteTextView.getText().toString().trim());
                                stuteatype = autoCompleteTextView.getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isStuSSubChanged() {

                        if (!stussub.equals(stussubtv.getEditText().getText().toString().trim()))
                        {

                            if (stussubtv.getEditText().getText().toString().trim().isEmpty())
                            {
                                studatetv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stussub").setValue(stussubtv.getEditText().getText().toString().trim());
                                stussub = studatetv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }



        }); //Update Data

        return root;



    }

}
