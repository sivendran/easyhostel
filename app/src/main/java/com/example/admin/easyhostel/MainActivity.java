package com.example.admin.easyhostel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
 FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn2:
                startActivity(new Intent(this,login.class));
                finish();
                break;

            case R.id.btn3:
                startActivity(new Intent(this,signup.class));
                finish();
                break;

        }

    }
}
