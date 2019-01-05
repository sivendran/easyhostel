package com.example.admin.easyhostel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText etdEmail, etdPassword, etdName,etdReg,etdGender,etdFaculty,etdYear;
    Spinner spinnerFaculty,spinnerGender,spinnerYear;

    ProgressBar progressbar;
    private FirebaseAuth mAuth;
    studentDetails Student;

    FirebaseDatabase database;
    DatabaseReference ref;

    //Member variable for spiner
    /*private ArrayList<FacultySpiner> mfacultylist;
    private FacultySpinerAdapter mAdapter; */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SignUP Part
        setContentView(R.layout.activity_signup);
        etdEmail=(EditText) findViewById(R.id.etd8);
        etdPassword=(EditText) findViewById(R.id.etd9);
        progressbar=(ProgressBar) findViewById(R.id.progressbar1) ;

        //Register Part
        etdName=(EditText)findViewById(R.id.etd3);
        etdReg=(EditText)findViewById(R.id.etd4);


        //Add Spinner
        spinnerFaculty=(Spinner)findViewById(R.id.spinnerFaculty);
        spinnerGender=(Spinner)findViewById(R.id.spinergender);
        spinnerYear=(Spinner)findViewById(R.id.spineryear);

        //Onclick SignUP
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.img1).setOnClickListener(this);

        //Firebase Authantication
        mAuth = FirebaseAuth.getInstance();

        //Create Student DEatiels class Object
        Student=new studentDetails();

        //for Spinner

        /*
         initiList();


        mAdapter= new  FacultySpinerAdapter(this,mfacultylist);
        spinner1.setAdapter(mAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FacultySpiner clickedItem=(FacultySpiner) parent.getItemAtPosition(position);
                String clickfacultyname=clickedItem.getmFacultyname();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void initiList(){
        mfacultylist=new ArrayList<>();
        mfacultylist.add(new FacultySpiner("Faculty",R.drawable.spnrfac1));
        mfacultylist.add(new FacultySpiner("Applied Science",R.drawable.spnrfac1));
        mfacultylist.add(new FacultySpiner("Social Science",R.drawable.spnrfac1));
        mfacultylist.add(new FacultySpiner("Management",R.drawable.spnrfac1));

     */
    }




    //Create method for get Student Details
    public  void getStdValues(){
        //put the values into the studentDetails Class's Attributes
        Student.setRegistarNO(etdReg.getText().toString());
        Student.setName(etdName.getText().toString());

        Student.setGender(spinnerGender.getSelectedItem().toString());
        Student.setYear(spinnerYear.getSelectedItem().toString());
        Student.setFac1(spinnerFaculty.getSelectedItem().toString());


        //add spiner

    }

    //Register Student details to DATABASE
    public void Senddetails() {
        //set the values
        getStdValues();
        //set the UUID from Authantication
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("STUDENT_DETAILS").child(uid);
        ref.setValue(Student);

    }




    //SignUP Registation
    private void registerUser() {

    String email=etdEmail.getText().toString().trim();
    String password=etdPassword.getText().toString().trim();

        if(email.isEmpty()){
            etdEmail.setError("Email is required");
            etdEmail.requestFocus();
            return;
    }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etdEmail.setError("Enter valied email");
            etdEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etdPassword.setError("Password is required");
            etdPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            etdPassword.setError("Minimum length of password should be 6");
            etdPassword.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Senddetails();
                    Toast.makeText(getApplicationContext(),"Successfully registered! please sign in...",Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(signup.this,login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn4:
                    registerUser();
                break;
            case R.id.img1:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }


}
