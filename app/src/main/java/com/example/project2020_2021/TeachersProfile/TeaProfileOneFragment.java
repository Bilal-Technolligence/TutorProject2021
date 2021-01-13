package com.example.project2020_2021.TeachersProfile;

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

import com.example.project2020_2021.Databases.TeaUserHelperClass;
import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeaProfileOneFragment extends Fragment {

    AutoCompleteTextView autoCompleteTextView;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update1;

    String teaname, teatype, teaemail, teaphoneno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tea_profile_one, container,false);

        //for dropdown fields
        autoCompleteTextView = root.findViewById(R.id.autocompletetext);
        String [] option = {"Home Teaching", "Online Teaching", "Academy Teaching", "Teaching At My Place"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.option_item,option);

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Teachers").child(userId);

        //Hooks
        final TextInputLayout teanametv = (TextInputLayout) root.findViewById(R.id.pteaname);
        final TextInputLayout teaemailtv = (TextInputLayout) root.findViewById(R.id.pteaemail);
        final TextInputLayout teaphonenotv = (TextInputLayout) root.findViewById(R.id.pteaphonenum);

        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TeaUserHelperClass userProfile = snapshot.getValue(TeaUserHelperClass.class);

                if (userProfile != null)
                {
                    teaname = userProfile.getTeaname();
                    teatype = userProfile.getTeatype();
                    teaemail = userProfile.getTeaemail();
                    teaphoneno = userProfile.getTeaphone();

                    teanametv.getEditText().setText(teaname);
                    autoCompleteTextView.setText(teatype);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    teaemailtv.getEditText().setText(teaemail);
                    teaphonenotv.getEditText().setText(teaphoneno);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });

        //Update Data
        update1 = (Button) root.findViewById(R.id.teaupdatep1);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isTeaNameChanged() | isTeaTypeChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isTeaNameChanged() {
                        if (!teaname.equals(teanametv.getEditText().getText().toString().trim()))
                        {

                            if (teanametv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teanametv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teaname").setValue(teanametv.getEditText().getText().toString().trim());
                                teaname = teanametv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }

                    private boolean isTeaTypeChanged() {

                        if (!teatype.equals(autoCompleteTextView.getText().toString().trim()))
                        {

                            if (autoCompleteTextView.getText().toString().trim().isEmpty())
                            {
                                autoCompleteTextView.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teatype").setValue(autoCompleteTextView.getText().toString().trim());
                                teatype = autoCompleteTextView.getText().toString().trim();
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
