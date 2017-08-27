package com.example.hp.edushit.Students;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.example.hp.edushit.R;

public class SchoolName extends AppCompatActivity {


    private Button schoolSubmitBtn;
    private EditText schoolnameeditor;
    public static String sclname;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_name);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
        //mMessagesDatabaseReference.keepSynced(true);


        schoolnameeditor = (EditText) findViewById(R.id.schoolname);

        schoolSubmitBtn = (Button) findViewById(R.id.schoolSubmitBtn);

        schoolnameeditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    schoolSubmitBtn.setEnabled(true);
                } else {
                    schoolSubmitBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        schoolnameeditor.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000)});


        schoolSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 sclname=schoolnameeditor.getText().toString();
               // mDatabaseReference.child("users").child("schoolname").setValue(sclname);
                Intent i2 = new Intent(SchoolName.this, StudentInfo.class);
                startActivity(i2);

            }
        });

    }
}
