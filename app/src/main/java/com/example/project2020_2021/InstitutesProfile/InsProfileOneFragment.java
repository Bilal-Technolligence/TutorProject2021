package com.example.project2020_2021.InstitutesProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InsProfileOneFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button update1;

    AutoCompleteTextView autoCompleteTextView;

    String insname, instype, insemail, insphoneno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.ins_profile_one, container,false);

        //for dropdown fields
        autoCompleteTextView = root.findViewById(R.id.autocompletetext);
        String [] option = {"School", "College", "University", "Academy", "Madrassa"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.option_item,option);
        //To set Default Values


        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Institutes").child(userId).child("InstituteDetails");


        //Hooks
        final TextInputLayout insnametv = (TextInputLayout) root.findViewById(R.id.pinsname);
        final TextInputLayout insemailtv = (TextInputLayout) root.findViewById(R.id.pinsemail);
        final TextInputLayout insphonenotv = (TextInputLayout) root.findViewById(R.id.pinsphonenum);


        //Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);

                if (userProfile != null)
                {
                     insname = userProfile.getInsname();
                     instype = userProfile.getInstype();
                     insemail = userProfile.getInsemail();
                     insphoneno = userProfile.getPhoneno();

                    insnametv.getEditText().setText(insname);
                    autoCompleteTextView.setText(instype);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    insemailtv.getEditText().setText(insemail);
                    insphonenotv.getEditText().setText(insphoneno);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });


        //passing data
        /*String insnameS = insnametv.getEditText().getText().toString().trim();
        String instypeS = autoCompleteTextView.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString("insname",insnameS);
        bundle.putString("instype",instypeS);
        InsProfileTwoFragment fragment2 = new InsProfileTwoFragment();
        fragment2.setArguments(bundle);*/


        //Update Data
        update1 = (Button) root.findViewById(R.id.insupdatep1);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isInsNameChanged() | isInsTypeChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isInsNameChanged() {
                        if (!insname.equals(insnametv.getEditText().getText().toString().trim()))
                        {

                            if (insnametv.getEditText().getText().toString().trim().isEmpty())
                            {
                                insnametv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("insname").setValue(insnametv.getEditText().getText().toString().trim());
                                insname = insnametv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    }

                    private boolean isInsTypeChanged() {

                        if (!instype.equals(autoCompleteTextView.getText().toString().trim()))
                        {

                            if (autoCompleteTextView.getText().toString().trim().isEmpty())
                            {
                                autoCompleteTextView.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("instype").setValue(autoCompleteTextView.getText().toString().trim());
                                instype = autoCompleteTextView.getText().toString().trim();
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
