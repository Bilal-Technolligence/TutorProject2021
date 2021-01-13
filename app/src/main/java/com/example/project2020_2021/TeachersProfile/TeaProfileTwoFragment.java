package com.example.project2020_2021.TeachersProfile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

public class TeaProfileTwoFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update2;
    String teagender, teadate, teacountry, teacity, teaaddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tea_profile_two, container,false);

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Teachers").child(userId);

        //Hooks
        final TextInputLayout teagendertv = (TextInputLayout) root.findViewById(R.id.pteagender);
        final TextInputLayout teadatetv = (TextInputLayout) root.findViewById(R.id.pteaage);
        final TextInputLayout teacountrytv = (TextInputLayout) root.findViewById(R.id.pteacountry);
        final TextInputLayout teacitytv = (TextInputLayout) root.findViewById(R.id.pteacity);
        final TextInputLayout teaaddresstv = (TextInputLayout) root.findViewById(R.id.pteaaddress);

        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TeaUserHelperClass userProfile = snapshot.getValue(TeaUserHelperClass.class);

                if (userProfile != null)
                {
                    teagender = userProfile.getTeagender();
                    teadate = userProfile.getTeadate();
                    teacountry = userProfile.getTeacountry();
                    teacity = userProfile.getTeacity();
                    teaaddress = userProfile.getTeaaddress();

                    teagendertv.getEditText().setText(teagender);
                    teadatetv.getEditText().setText(teadate);
                    teacountrytv.getEditText().setText(teacountry);
                    teacitytv.getEditText().setText(teacity);
                    teaaddresstv.getEditText().setText(teaaddress);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });

        //Update Data
        update2 = (Button) root.findViewById(R.id.teaupdatep2);
        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isTeaGenderChanged() | isTeaAgeChanged() | isTeaCountryChanged() | isTeaCityChanged() | isTeaAddressChanged() )
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isTeaGenderChanged() {
                        if (!teagender.equals(teagendertv.getEditText().getText().toString().trim()))
                        {

                            if (teagendertv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teagendertv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teagender").setValue(teagendertv.getEditText().getText().toString().trim());
                                teagender = teagendertv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }

                    private boolean isTeaAgeChanged() {

                        if (!teadate.equals(teadatetv.getEditText().getText().toString().trim()))
                        {

                            if (teadatetv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teadatetv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teadate").setValue(teadatetv.getEditText().getText().toString().trim());
                                teadate = teadatetv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaAddressChanged() {

                        if (!teaaddress.equals(teaaddresstv.getEditText().getText().toString().trim()))
                        {

                            if (teaaddresstv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teaaddresstv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teaaddress").setValue(teaaddresstv.getEditText().getText().toString().trim());
                                teaaddress = teaaddresstv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaCityChanged() {

                        if (!teacity.equals(teacitytv.getEditText().getText().toString().trim()))
                        {

                            if (teacitytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teacitytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teacity").setValue(teacitytv.getEditText().getText().toString().trim());
                                teacity = teacitytv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaCountryChanged() {

                        if (!teacountry.equals(teacountrytv.getEditText().getText().toString().trim()))
                        {

                            if (teacountrytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teacountrytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teacountry").setValue(teacountrytv.getEditText().getText().toString().trim());
                                teacountry = teacountrytv.getEditText().getText().toString().trim();
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
