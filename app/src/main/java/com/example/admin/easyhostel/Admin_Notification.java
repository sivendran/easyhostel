package com.example.admin.easyhostel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Admin_Notification extends AppCompatActivity {
    Spinner spinnerFaculty, spinnerGender, spinnerYear;
    EditText setNotification;
    Button SendNotification;
    ProgressBar ProgressBarNotice;

    studentDetails Student;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__notification);


        setNotification = (EditText) findViewById(R.id.Notification);

        spinnerFaculty = (Spinner) findViewById(R.id.spinnerFaculty);

        spinnerGender = (Spinner) findViewById(R.id.spinergender);

        spinnerYear = (Spinner) findViewById(R.id.spineryear);


        SendNotification=(Button) findViewById(R.id.SendNotification);

        ProgressBarNotice=(ProgressBar)findViewById(R.id.ProgressBarNotice) ;



        mAuth = FirebaseAuth.getInstance();



        SendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBarNotice.setVisibility(View.VISIBLE);
                SetNotification();

            }
        });


    }

    private void SetNotification() {



        //get the details from new class WriteNotification
        String SetNOTIFICATION=setNotification.getText().toString().trim();

        //get the details from new class Set_Notification
        String SetFACULTY=spinnerFaculty.getSelectedItem().toString().trim();
        String SetGENDER=spinnerGender.getSelectedItem().toString().trim();
        String SetYEAR=spinnerYear.getSelectedItem().toString().trim();

        // Set the Details
        Set_Notification setnotic= new Set_Notification(SetGENDER,SetFACULTY,SetYEAR);
        //Set the details
        WriteNotification writeNotification=new WriteNotification(SetNOTIFICATION);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        // Set the root(Database title) (Seperate database)
        DatabaseReference ref=database.getReference("Notifications").child(SetGENDER).child(SetYEAR).child(SetFACULTY).child(UUID.randomUUID().toString());
        //Set the Notification
        ref.setValue(writeNotification);

        Toast.makeText(getApplicationContext(),"NOTIFICATION SAVE IN DATABASE",Toast.LENGTH_SHORT).show();
    }


}





