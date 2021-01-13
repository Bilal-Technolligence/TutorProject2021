package com.example.project2020_2021.StudentsProfile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class StuProfileOneFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update1;

    String stuname, stutype, stuemail, stuphone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.stu_profile_one, container,false);

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Students").child(userId);

        //Hooks
        final TextInputLayout stunametv = (TextInputLayout) root.findViewById(R.id.pstuname);
        final TextInputLayout stutypetv = (TextInputLayout) root.findViewById(R.id.pstutype);
        final TextInputLayout stuemailtv = (TextInputLayout) root.findViewById(R.id.pstuemail);
        final TextInputLayout stuphonenotv = (TextInputLayout) root.findViewById(R.id.pstuphonenum);

        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StuUserHelperClass userProfile = snapshot.getValue(StuUserHelperClass.class);

                if (userProfile != null)
                {
                    stuname = userProfile.getStuname();
                    stutype = userProfile.getStutype();
                    stuemail = userProfile.getStuemail();
                    stuphone = userProfile.getStuphone();

                    stunametv.getEditText().setText(stuname);
                    stutypetv.getEditText().setText(stutype);
                    stuemailtv.getEditText().setText(stuemail);
                    stuphonenotv.getEditText().setText(stuphone);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });


        //Update Data
        update1 = (Button) root.findViewById(R.id.stuupdatep1);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isStuNameChanged() | isStuTypeChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isStuNameChanged() {
                        if (!stuname.equals(stunametv.getEditText().getText().toString().trim()))
                        {

                            if (stunametv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stunametv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stuname").setValue(stunametv.getEditText().getText().toString().trim());
                                stuname = stunametv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }

                    private boolean isStuTypeChanged() {

                        if (!stutype.equals(stutypetv.getEditText().getText().toString().trim()))
                        {

                            if (stutypetv.getEditText().getText().toString().trim().isEmpty())
                            {
                                stutypetv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("stutype").setValue(stutypetv.getEditText().getText().toString().trim());
                                stutype = stutypetv.getEditText().getText().toString().trim();
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
