package com.example.admin.easyhostel;


import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.easyhostel.Constants.Faculty;
import static com.example.admin.easyhostel.Constants.Gender;
import static com.example.admin.easyhostel.Constants.User_details;
import static com.example.admin.easyhostel.Constants.Year;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends Fragment {

    TextView txtGetNOTICE;

    Spinner spinnerFaculty, spinnerGender, spinnerYear;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    private SharedPreferences preferences;
    private List<WriteNotification> notifications;
    private RecyclerView recyclerView;
    private NotificationAdapter mAdapter;

    public Notifications() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        notifications = new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        preferences=getActivity().getSharedPreferences(User_details,MODE_PRIVATE);
        String gender = preferences.getString(Gender,"");
        String faculty = preferences.getString(Faculty,"");
        String year = preferences.getString(Year,"");

        mAuth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();

        // Database title

        DatabaseReference ref=database.getReference("Notifications").child(gender).child(year).child(faculty);

        // Retrive method
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get details from WriteNotification Class
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    WriteNotification writeNotification= data.getValue(WriteNotification.class);
                    notifications.add(writeNotification);
                }

                notifications.size();
                mAdapter = new NotificationAdapter(notifications);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;

    }


}


