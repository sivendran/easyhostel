package com.example.admin.easyhostel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileDemo extends AppCompatActivity {
    ImageView Propic;
    TextView txName,txGender,txFaculty,txYear;
    Button btnEdt;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_demo);

        Propic=(ImageView)findViewById(R.id.propic);
        txName=(TextView)findViewById(R.id.txname);
        txGender=(TextView)findViewById(R.id.txgender);
        txFaculty=(TextView)findViewById(R.id.txfaculty);
        txYear=(TextView)findViewById(R.id.txyear);
        btnEdt=(Button)findViewById(R.id.btnedt);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DatabaseReference databaseReference=firebaseDatabase.getReference(uid);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("STUDENT_DETAILS").child(uid);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                studentDetails Student=dataSnapshot.getValue(studentDetails.class);
                txName.setText("Name : "+Student.getName());
                txGender.setText("Gender : "+Student.getGender());
                txFaculty.setText("Faculty : "+Student.getFac1());
                txYear.setText("Year : "+Student.getYear());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
