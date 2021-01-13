package com.example.project2020_2021.TeachersProfile;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.project2020_2021.Databases.TeaUserHelperClass;
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

import static android.app.Activity.RESULT_OK;

public class TeaProfileThreeFragment extends Fragment {

    Button selectfile;
    TextView notification;
    Uri pdfUri;   //Url for local storage
    AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2 ;

    Button teaUpdatep, teaDeletep;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;

    String teaqua, teadegree, teaexp, teawteach, teafee;

    TextInputLayout teaquatv, teaexptv, teawteachtv, teafeetv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.tea_profile_three, container,false);

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("Teachers").child(userId);

        //for dropdown fields
        autoCompleteTextView = root.findViewById(R.id.autocompletetextclass);
        String [] option = {"Class 1 to 5", "Class 1 to 10", "Class 1 to 12", "BS Level","Matric", "Intermediate", "Matric to Intermediate", "Any Class"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.option_item,option);

        autoCompleteTextView2 = root.findViewById(R.id.autocompletetextfee);
        String [] option2 = {"Fixed", "Negotiable"};
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(getContext(), R.layout.option_item,option2);


        //Hooks
        teaquatv = (TextInputLayout) root.findViewById(R.id.pteaqua);
        teaexptv = (TextInputLayout) root.findViewById(R.id.pteaexp);
        teawteachtv = (TextInputLayout) root.findViewById(R.id.pwanttoteach);
        teafeetv = (TextInputLayout) root.findViewById(R.id.pfee);

        // Getting Data from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                TeaUserHelperClass userProfile = snapshot.getValue(TeaUserHelperClass.class);

                if (userProfile != null)
                {
                    teaqua = userProfile.getTeaqua();
                    teaexp = userProfile.getTeaexp();
                    teawteach = userProfile.getTeawteach();
                    teafee = userProfile.getTeafee();

                    teaquatv.getEditText().setText(teaqua);
                    teaexptv.getEditText().setText(teaexp);
                    autoCompleteTextView.setText(teawteach);
                    autoCompleteTextView.setAdapter(arrayAdapter);
                    autoCompleteTextView2.setText(teafee);
                    autoCompleteTextView2.setAdapter(arrayAdapter);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something Wrong Happened!",Toast.LENGTH_LONG).show();
            }
        });

        notification=(TextView)root.findViewById(R.id.notification);
        selectfile = (Button) root.findViewById(R.id.select_file);

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    selectPdf();
                }
                else
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });




        //Buttons Listeners

        teaUpdatep = (Button) root.findViewById(R.id.teaupdatep3);

       teaUpdatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Are You Sure to Update Your Profile?");
                builder.setMessage("Updating Your Profile will result in completely Update your data.")
                        .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isTeaQuaChanged() | isTeaExpChanged() |  isTeaWTeachChanged() | isTeaFeeChanged())
                        {
                            Toast.makeText(getContext(),"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Data is same and cannot be Updated",Toast.LENGTH_LONG).show();
                        }
                    }

                    private boolean isTeaQuaChanged() {

                        if (!teaqua.equals(teaquatv.getEditText().getText().toString().trim()))
                        {

                            if (teaquatv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teaquatv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teaqua").setValue(teaquatv.getEditText().getText().toString().trim());
                                teaqua = teaquatv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaExpChanged() {

                        if (!teaexp.equals(teaexptv.getEditText().getText().toString().trim()))
                        {

                            if (teaexptv.getEditText().getText().toString().trim().isEmpty())
                            {
                                teaexptv.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teaexp").setValue(teaexptv.getEditText().getText().toString().trim());
                                teaexp = teaexptv.getEditText().getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaWTeachChanged() {

                        if (!teawteach.equals(autoCompleteTextView.getText().toString().trim()))
                        {

                            if (autoCompleteTextView.getText().toString().trim().isEmpty())
                            {
                                autoCompleteTextView.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teawteach").setValue(autoCompleteTextView.getText().toString().trim());
                                teawteach = autoCompleteTextView.getText().toString().trim();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }

                    }

                    private boolean isTeaFeeChanged() {

                        if (!teafee.equals(autoCompleteTextView2.getText().toString().trim()))
                        {

                            if (autoCompleteTextView2.getText().toString().trim().isEmpty())
                            {
                                autoCompleteTextView2.setError("Field can not be Empty");
                                return false;
                            }
                            else
                            {
                                reference.child("teafee").setValue(autoCompleteTextView2.getText().toString().trim());
                                teafee = autoCompleteTextView2.getText().toString().trim();
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

        teaDeletep = (Button) root.findViewById(R.id.deleteteap);

        teaDeletep.setOnClickListener(new View.OnClickListener() {
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

        return root;



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==9 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
        {
            selectPdf();
        }
        else
        {
            Toast.makeText(getContext(),"Please Provide Permission...",Toast.LENGTH_SHORT).show();
        }
    }

    private  void selectPdf(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            notification.setText("A file is selected: " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(getContext(), "Please Select a File", Toast.LENGTH_SHORT).show();
        }
    }
}
