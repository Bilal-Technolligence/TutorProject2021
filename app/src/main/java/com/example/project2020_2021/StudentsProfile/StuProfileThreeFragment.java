package com.example.project2020_2021.StudentsProfile;

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

public class StuProfileThreeFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update3;
    String stucountry, stucity, stuaddress;

    Button  stuDeletep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.stu_profile_three, container,false);


        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Students").child(userId);

        //Hooks
        final TextInputLayout stucountrytv = (TextInputLayout) root.findViewById(R.id.pstucountry);
        final TextInputLayout stucitytv = (TextInputLayout) root.findViewById(R.id.pstucity);
        final TextInputLayout stuaddresstv = (TextInputLayout) root.findViewById(R.id.pstuaddress);

        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StuUserHelperClass userProfile = snapshot.getValue(StuUserHelperClass.class);

                if (userProfile != null)
                {
                    stucountry = userProfile.getStucountry();
                    stucity = userProfile.getStucity();
                    stuaddress = userProfile.getStuaddress();

                    stucountrytv.getEditText().setText(stucountry);
                    stucitytv.getEditText().setText(stucity);
                    stuaddresstv.getEditText().setText(stuaddress);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });

        update3 = (Button) root.findViewById(R.id.stuupdatep3);

        update3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ( isStuCountryChanged() | isStuCityChanged() | isStuAddressChanged() )
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }


                    private boolean isStuAddressChanged() {

                        if (!stuaddress.equals(stuaddresstv.getEditText().getText().toString().trim()))
                        {

                            if (stuaddresstv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stuaddresstv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stuaddress").setValue(stuaddresstv.getEditText().getText().toString().trim());
                                stuaddress = stuaddresstv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isStuCityChanged() {

                        if (!stucity.equals(stucitytv.getEditText().getText().toString().trim()))
                        {

                            if (stucitytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stucitytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stucity").setValue(stucitytv.getEditText().getText().toString().trim());
                                stucity = stucitytv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isStuCountryChanged() {

                        if (!stucountry.equals(stucountrytv.getEditText().getText().toString().trim()))
                        {

                            if (stucountrytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stucountrytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stucountry").setValue(stucountrytv.getEditText().getText().toString().trim());
                                stucountry = stucountrytv.getEditText().getText().toString().trim();
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
        });

       stuDeletep = (Button) root.findViewById(R.id.deleteStup);

        stuDeletep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setMessage("Are You Sure to Delete Your Profile?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContext(),"Your Profile is Deleted",Toast.LENGTH_SHORT).show();

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
        });

        return root;



    }

}
