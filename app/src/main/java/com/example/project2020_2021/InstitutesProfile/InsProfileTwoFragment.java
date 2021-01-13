package com.example.project2020_2021.InstitutesProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.InstitutesLogIn.InstituteRegistration;
import com.example.project2020_2021.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InsProfileTwoFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    Button insUpdatep, insDeletep;

    //Constructor to get values
    public InsProfileTwoFragment()
    {

    }

    String  inscountry, inscity, insaddress;
    TextInputLayout inscountrytv, inscitytv, insaddresstv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.ins_profile_two, container,false);

        /*Bundle bundle = this.getArguments();
        String _insname = bundle.getString("insname");
        String _instype = bundle.getString("instype");
        text1.setText(_insname);*/

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Institutes").child(userId).child("InstituteDetails");


        //Delete Profile
        insDeletep = (Button) root.findViewById(R.id.deleteInsp);

        insDeletep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Delete Your Profile?");
                builder.setMessage("Deleting Your Profile will result in completely removing your data."+ " And you won't be able to access the App.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getContext(),"Your Profile is Deleted",Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getContext(), InstituteRegistration.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });


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
        });  //Delete Profile

       //Hooks
         inscountrytv = (TextInputLayout) root.findViewById(R.id.pinscountry);
         inscitytv = (TextInputLayout) root.findViewById(R.id.pinscity);
         insaddresstv = (TextInputLayout) root.findViewById(R.id.pinsaddress);


        // Getting Data from Firebase
         reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                UserHelperClass userProfile = snapshot.getValue(UserHelperClass.class);

                if (userProfile != null)
                {
                     inscountry = userProfile.getInscountry();
                     inscity = userProfile.getCity();
                     insaddress = userProfile.getAddress();

                    inscountrytv.getEditText().setText(inscountry);
                    inscitytv.getEditText().setText(inscity);
                    insaddresstv.getEditText().setText(insaddress);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });


        //Update Data
        insUpdatep = (Button) root.findViewById(R.id.insupdatep);

        insUpdatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isInsCountryChanged() | isInsCityChanged() | isInsAddressChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isInsAddressChanged() {

                        if (!insaddress.equals(insaddresstv.getEditText().getText().toString().trim()))
                        {

                            if (insaddresstv.getEditText().getText().toString().trim().isEmpty())
                            {
                                insaddresstv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("address").setValue(insaddresstv.getEditText().getText().toString().trim());
                                insaddress = insaddresstv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isInsCityChanged() {

                        if (!inscity.equals(inscitytv.getEditText().getText().toString().trim()))
                        {

                            if (inscitytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                inscitytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("city").setValue(inscitytv.getEditText().getText().toString().trim());
                                inscity = inscitytv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isInsCountryChanged() {

                        if (!inscountry.equals(inscountrytv.getEditText().getText().toString().trim()))
                        {

                            if (inscountrytv.getEditText().getText().toString().trim().isEmpty())
                            {
                                inscountrytv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("inscountry").setValue(inscountrytv.getEditText().getText().toString().trim());
                                inscountry = inscountrytv.getEditText().getText().toString().trim();
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

        });//Update Data

        return root;



    }

}
