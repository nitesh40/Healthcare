package com.nitesh.firstapp.MainActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nitesh.firstapp.R;

public class MainActivity extends AppCompatActivity {
 EditText musername,mpassword;
 Button mlogin,msignup,mforget;
 FirebaseAuth mAuth;
 ProgressDialog progressDialog;
 ImageView mlogo;
 TextView muser,mpass;
 //DatabaseReference databaseReference;

    RelativeLayout part1, part2;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            part1.setVisibility(View.VISIBLE);
            part2.setVisibility(View.VISIBLE);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        part1 = (RelativeLayout) findViewById(R.id.part1);
        part2 = (RelativeLayout) findViewById(R.id.part2);

        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

       // databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/user");

        progressDialog=new ProgressDialog(this);

        musername=findViewById(R.id.username);
        mpassword=findViewById(R.id.password);
        muser=findViewById(R.id.user);
        mpass=findViewById(R.id.pass);
        mlogo=findViewById(R.id.logo);
        mlogin=findViewById(R.id.login);
        msignup=findViewById(R.id.signup);
        mforget=findViewById(R.id.forget);
        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            finish();
            Toast.makeText(MainActivity.this,"Welcome !!", Toast.LENGTH_SHORT).show();



            startActivity(new Intent(MainActivity.this,HomePage.class));
        }

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
                //finish();
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Forget.class));
                //finish();
            }
        });

    }

    private void login() {




        mAuth.signInWithEmailAndPassword(musername.getText().toString(),mpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.setMessage("Logging In");
                progressDialog.show();

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Welcome !!", Toast.LENGTH_SHORT).show();



                    startActivity(new Intent(MainActivity.this,HomePage.class));
                }else {
                    Toast.makeText(MainActivity.this,"Error Error Error!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
