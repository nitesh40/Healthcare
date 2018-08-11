package com.nitesh.firstapp.MainActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nitesh.firstapp.R;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class Forget extends AppCompatActivity {
    Text mrec;
    EditText mphone,mentercode;
    Button mgetcode,mrecover;
    FirebaseAuth mAuth;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        mAuth=FirebaseAuth.getInstance();

        mphone=findViewById(R.id.phone);
        mentercode=findViewById(R.id.entercode);
        mgetcode=findViewById(R.id.getcode);
        mrecover=findViewById(R.id.recover);

        mgetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationcode();
            }
        });

        mrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode(){
        String code=mentercode.getText().toString();
        if(code.isEmpty()){
            mentercode.setError("Enter Code");
            mentercode.requestFocus();
            return;
        }

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Forget.this,"Welcome !!", Toast.LENGTH_SHORT).show();



                            startActivity(new Intent(Forget.this,HomePage.class));
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");

                           // FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                           if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                               Toast.makeText(Forget.this,"Invalid Verification Code", Toast.LENGTH_SHORT).show();
                           }
                                // The verification code entered was invalid
                            }
                        }
                    //}
                });
    }


    private void sendVerificationcode(){

        String phone = mphone.getText().toString();
        if(phone.isEmpty()){
            mphone.setError("Enter Phone Number");
            mphone.requestFocus();
            return;
        }

        if(phone.length()<10){
            mphone.setError("Enter Valid Phone Number");
            mphone.requestFocus();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mphone.getText().toString(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeSent = s;
        }
    };

}
