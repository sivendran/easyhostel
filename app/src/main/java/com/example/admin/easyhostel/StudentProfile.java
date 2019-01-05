package com.example.admin.easyhostel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.example.admin.easyhostel.Constants.Faculty;
import static com.example.admin.easyhostel.Constants.Gender;
import static com.example.admin.easyhostel.Constants.Name;
import static com.example.admin.easyhostel.Constants.User_details;
import static com.example.admin.easyhostel.Constants.Year;

public class StudentProfile extends Fragment {

    private static final int CHOOSE_IMAGE =101 ;
    ImageView Propic;
    TextView txName,txGender,txFaculty,txYear;
    Button btnLogout, btnsave;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ProgressBar progressbar, Progressbarimg;

    Uri uriprofileimage;
    String profileImgurl;

    FirebaseAuth  mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        mAuth=FirebaseAuth.getInstance();

        Propic=(ImageView)view.findViewById(R.id.propic);
        txName=(TextView)view.findViewById(R.id.txname);
        txGender=(TextView)view.findViewById(R.id.txgender);
        txFaculty=(TextView)view.findViewById(R.id.txfaculty);
        txYear=(TextView)view.findViewById(R.id.txyear);
        btnLogout=(Button)view.findViewById(R.id.btnedt);
        btnsave=(Button)view.findViewById(R.id.btnsave);

        progressbar=(ProgressBar)view.findViewById(R.id.progressBar2) ;
        Progressbarimg=(ProgressBar)view.findViewById(R.id.progressbarimg);
        
        loadUserInformation();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //MEthod for REtrive image
                SaveUserInfomation();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment to normal activity
                Intent intent= new Intent(StudentProfile.this.getActivity(),MainActivity.class);
                startActivity(intent);
                progressbar.setVisibility(View.VISIBLE);

            }
        });

        Propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagechooser();
            }
        });



        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //DatabaseReference databaseReference=firebaseDatabase.getReference(uid);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("STUDENT_DETAILS").child(uid);


        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                studentDetails Student = dataSnapshot.getValue(studentDetails.class);
                txName.setText("Name : " + Student.getName());
                txGender.setText("Gender : " + Student.getGender());
                txFaculty.setText("Faculty : " + Student.getFac1());
                txYear.setText("Year : " + Student.getYear());

                //save Details to sharedPreferences
                SharedPreferences.Editor editor= getActivity().getSharedPreferences(User_details,MODE_PRIVATE).edit();
                editor.putString(Name,Student.getName());
                editor.putString(Gender,Student.getGender());
                editor.putString(Year,Student.getYear());
                editor.putString(Faculty,Student.getFac1());
                editor.apply();



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void loadUserInformation() {

        FirebaseUser user=mAuth.getCurrentUser();

        if(user !=null){
            if(user.getPhotoUrl() !=null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(Propic);
            }

        }

    }

    private void SaveUserInfomation() {





        FirebaseUser user= mAuth.getCurrentUser();

        if(user !=null && profileImgurl != null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImgurl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(StudentProfile.this.getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    //s
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data!= null && data.getData()!=null){

            uriprofileimage=data.getData();

            //selected image display to imageview

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uriprofileimage);
                Propic.setImageBitmap(bitmap);
                //call method
                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    //Method for Store image to FirebaseStorage
    private void uploadImageToFirebaseStorage() {
        //FirebaseStorage Referance
        StorageReference profileImageRef= FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
        if(uriprofileimage !=null);{
            Progressbarimg.setVisibility(View.VISIBLE);

            profileImageRef.putFile(uriprofileimage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Progressbarimg.setVisibility(View.GONE);

                    profileImgurl = taskSnapshot.getDownloadUrl().toString();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Progressbarimg.setVisibility(View.GONE);
                    Toast.makeText(StudentProfile.this.getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    private  void Imagechooser(){
     Intent intent= new Intent();
     intent.setType("image/*");
     intent.setAction(Intent.ACTION_GET_CONTENT);
     startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }



}
