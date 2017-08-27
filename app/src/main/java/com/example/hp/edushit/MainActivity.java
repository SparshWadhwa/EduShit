package com.example.hp.edushit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hp.edushit.Students.Student_MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.hp.edushit.Students.SchoolName.sclname;
import static com.example.hp.edushit.Students.StudentInfo.isStudent;
import static com.example.hp.edushit.Students.StudentInfo.student_name;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private int flag;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mStatusDatabaseReference;
    private String Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mStatusDatabaseReference=mFirebaseDatabase.getReference();





        if(isStudent){flag=1;}

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent = new Intent(MainActivity.this, Portals.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }
                else if(firebaseAuth.getCurrentUser() != null){
                    mStatusDatabaseReference.child("users").child("vps").child("sparsh").child("status").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Status = (String) dataSnapshot.getValue();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    if (Status=="student")
                    {startActivity(new Intent(MainActivity.this,Student_MainActivity.class));}
                }
            }
        };

//        if (isStudent){
//
//        }
        Toast.makeText(this, "status is"+Status, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mAuthListener != null) {
            mAuth.addAuthStateListener(mAuthListener);
        }
       // flag=1;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        //if(isStudent){ flag=1; }
       // flag=1;
    }

}
