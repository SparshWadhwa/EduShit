package com.example.hp.edushit.Students;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.edushit.MainActivity;
import com.example.hp.edushit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.example.hp.edushit.Portals.status;
import static com.example.hp.edushit.Students.SchoolName.sclname;

public class StudentInfo extends AppCompatActivity {

   public static boolean isStudent=false;
    private EditText etxtPhone;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etxtPhoneCode,nameField;
    private String mVerificationId;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNameDatabaseReference;
    private DatabaseReference mPhnNo_databaseREference;

    public static String student_name,student_phnNo;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        //mNameDatabaseReference=mFirebaseDatabase.getReference().child("users").child("schoolname").child(sclname).child(student_name);
        mPhnNo_databaseREference=mFirebaseDatabase.getReference();
        nameField = (EditText) findViewById(R.id.nameField);
        etxtPhone = (EditText) findViewById(R.id.phoneField);
        etxtPhoneCode = (EditText) findViewById(R.id.codeField);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    //  Toast.makeText(StudentInfo.this, getString(R.string.now_logged_in) + firebaseAuth.getCurrentUser().getProviderId(), Toast.LENGTH_SHORT).show();
                    isStudent=true;
                    Intent intent = new Intent(StudentInfo.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void requestCode(View view) {
        String phoneNumber = etxtPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber))
            return;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, StudentInfo.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        //Called if it is not needed to enter verification code
                        signInWithCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //incorrect phone number, verification code, emulator, etc.
                        Toast.makeText(StudentInfo.this, "onVerificationFailed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        //now the code has been sent, save the verificationId we may need it
                        super.onCodeSent(verificationId, forceResendingToken);

                        mVerificationId = verificationId;
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationId) {
                        //called after timeout if onVerificationCompleted has not been called
                        super.onCodeAutoRetrievalTimeOut(verificationId);
                        Toast.makeText(StudentInfo.this, "onCodeAutoRetrievalTimeOut :" + verificationId, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void signInWithCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(StudentInfo.this, "Success", Toast.LENGTH_SHORT).show();
                            student_name=nameField.getText().toString();
                            student_phnNo=etxtPhone.getText().toString();
                            mPhnNo_databaseREference.child("users").child(sclname).child(student_name).child("phone no").setValue(student_phnNo);
                            if (status==1){
                                mPhnNo_databaseREference.child("users").child(sclname).child(student_name).child("status").setValue("student");

                            }
                        } else {
                            Toast.makeText(StudentInfo.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(View view) {
        String code = etxtPhoneCode.getText().toString();
        if (TextUtils.isEmpty(code))
            return;

        signInWithCredential(PhoneAuthProvider.getCredential(mVerificationId, code));
    }
}
