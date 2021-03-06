package com.example.admin.easyhostel;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.admin.easyhostel.Constants.Faculty;
import static com.example.admin.easyhostel.Constants.Gender;
import static com.example.admin.easyhostel.Constants.Name;
import static com.example.admin.easyhostel.Constants.User_details;
import static com.example.admin.easyhostel.Constants.Year;

public class login extends AppCompatActivity implements View.OnClickListener {
EditText etdEmail,etdPassword;
ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etdEmail=(EditText)findViewById(R.id.etd8);
        etdPassword=(EditText)findViewById(R.id.etd9);
        progressbar=(ProgressBar) findViewById(R.id.progressbar) ;
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.img2).setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
    }

    private void userlogin() {
        String email = etdEmail.getText().toString().trim();
        String password = etdPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etdEmail.setError("Email is required");
            etdEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etdEmail.setError("Enter valied email");
            etdEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etdPassword.setError("Password is required");
            etdPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etdPassword.setError("Minimum length of password should be 6");
            etdPassword.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        //Set GO to Admin page
        String Adminemail = etdEmail.getText().toString().trim();
        String Adminpassword = etdPassword.getText().toString().trim();

        if (Adminemail.equals("Hostel01@gmail.com") && Adminpassword.equals("Hostel123")) {
            Intent intent1 = new Intent(login.this, Admin_Notification.class);
            startActivity(intent1);

            finish();
        } else
        {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressbar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        saveUserData();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(login.this, "Login failed.",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img2:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;

            case R.id.btn1:
                userlogin();
                break;

        }
    }


    private void saveUserData(){
        firebaseDatabase= FirebaseDatabase.getInstance();

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DatabaseReference databaseReference=firebaseDatabase.getReference(uid);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("STUDENT_DETAILS").child(uid);


        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                studentDetails Student = dataSnapshot.getValue(studentDetails.class);

                //save Details to sharedPreferences
                SharedPreferences.Editor editor= getSharedPreferences(User_details,MODE_PRIVATE).edit();
                editor.putString(Name,Student.getName());
                editor.putString(Gender,Student.getGender());
                editor.putString(Year,Student.getYear());
                editor.putString(Faculty,Student.getFac1());
                editor.apply();

                Intent intent = new Intent(login.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}




