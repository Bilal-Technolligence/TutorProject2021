package com.example.project2020_2021.InstitutesVacancies;

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

import com.example.project2020_2021.Databases.UserHelperClass;
import com.example.project2020_2021.Databases.VacancyHelperClass;
import com.example.project2020_2021.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddVacancyOneFragment extends Fragment {

    //Variables
    Button addvacbtn;
    TextInputLayout jobtitle, requirements, dutynres, salary;

    //Firebase
    private FirebaseAuth mAuth;

    private FirebaseUser user;
    DatabaseReference reference;

    private String userId;

    VacancyHelperClass vacancyHelperClass;

    long maxid = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.add_vacancy_tab, container,false);

        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        user = FirebaseAuth.getInstance().getCurrentUser();

        vacancyHelperClass = new VacancyHelperClass();

        userId = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Institutes").child(userId).child("Vacancies");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Hooks
        jobtitle = (TextInputLayout) root.findViewById(R.id.jobtitleV);
        requirements = (TextInputLayout) root.findViewById(R.id.requirementsV);
        dutynres = (TextInputLayout) root.findViewById(R.id.dutyandresponsV);
        salary = (TextInputLayout) root.findViewById(R.id.salaryV);

        //Add Vacancy Button
        addvacbtn = (Button) root.findViewById(R.id.addVbtn);

        addvacbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validateJtitle() | !validateReq() | !validateDuty() | !validateSalary()){
                    return;
                }

                storeNewVacancy();
               // Toast.makeText(getContext(),"Vacancy Added Successfully",Toast.LENGTH_SHORT).show();

            }

            private void storeNewVacancy() {

                // FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
               // DatabaseReference reference = rootNode.getReference(userId);
                //reference.child(userId).child("Vacancy").setValue("1stvacancy");
                //reference.child("Institutes/"+mAuth.getCurrentUser().getUid()+"/Vacancies");
                int sal = Integer.parseInt(salary.getEditText().getText().toString().trim());

                vacancyHelperClass.setJobTitle(jobtitle.getEditText().getText().toString().trim());
                vacancyHelperClass.setJobReq(requirements.getEditText().getText().toString().trim());
                vacancyHelperClass.setJobDutyRes(dutynres.getEditText().getText().toString().trim());
                vacancyHelperClass.setSalary(sal);

                reference.child(String.valueOf("Vacancy "+maxid)).setValue(vacancyHelperClass);
                Toast.makeText(getContext(),"Vacancy Added Successfully",Toast.LENGTH_LONG).show();

            }
        });

        return root;



    }

    //Validation Function
    private boolean validateJtitle()
    {
        String val = jobtitle.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            jobtitle.setError("Field can not be Empty");
            return false;
        }
        else
        {
            jobtitle.setError(null);
            jobtitle.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateReq()
    {
        String val = requirements.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            requirements.setError("Field can not be Empty");
            return false;
        }
        else
        {
            requirements.setError(null);
            requirements.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateDuty()
    {
        String val = dutynres.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            dutynres.setError("Field can not be Empty");
            return false;
        }
        else
        {
            dutynres.setError(null);
            dutynres.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateSalary()
    {
        String val = salary.getEditText().getText().toString().trim();

        if (val.isEmpty())
        {
            salary.setError("Field can not be Empty");
            return false;
        }
        else
        {
            salary.setError(null);
            salary.setErrorEnabled(false);
            return true;
        }
    }


}