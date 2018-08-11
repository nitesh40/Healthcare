package com.nitesh.firstapp.MainActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitesh.firstapp.Model;
import com.nitesh.firstapp.R;

public class Signup extends AppCompatActivity {
    EditText mfname,mname,mpass,mcpass,memail,mphone,maddress;
    Button mbutton;
    //RadioButton mmale,mfemale;
    TextView msnup;
    //CheckBox mcheckbox,mcheckbox1;
   // DatabaseReference databaseReference;
   DatabaseReference myref;
    FirebaseDatabase databaseReference;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog=new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance();
        myref =databaseReference.getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/User");
        mAuth=FirebaseAuth.getInstance();

        msnup=findViewById(R.id.snup);
        mfname=findViewById(R.id.fname);
        //mname=findViewById(R.id.name);
        mpass=findViewById(R.id.Pass);
        mcpass=findViewById(R.id.CPass);
        memail=findViewById(R.id.email);
        mphone=findViewById(R.id.num);
        maddress=findViewById(R.id.address);
        mbutton=findViewById(R.id.signup);
        //mmale=findViewById(R.id.male);
        //mfemale=findViewById(R.id.female);
        //mcheckbox=findViewById(R.id.checkbox);
        //mcheckbox1=findViewById(R.id.checkbox1);

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
                //finish();

               // startActivity(new Intent(Signup.this,MainActivity.class));

            }

        });

    }

    public void signup(){
        final String fname=mfname.getText().toString().trim();
       // String username1 = mname.getText().toString().trim();
        String password2 = mpass.getText().toString().trim();
        String password3 = mcpass.getText().toString().trim();
        String email3 = memail.getText().toString().trim();
        String phone4 = mphone.getText().toString().trim();
        String address=maddress.getText().toString().trim();
        //String gender=mmale.getText().toString().trim();
        //String gender1=mfemale.getText().toString().trim();
        //String checkbox=mcheckbox.getText().toString().trim();
        //String checkbox1=mcheckbox1.getText().toString().trim();

        if (fname.isEmpty()){
            mfname.setError("Full Name Is Required");
            mfname.requestFocus();
            return;
        }

//        if (username1.isEmpty()){
//           mname.setError("Username Is Required");
//           mname.requestFocus();
//           return;
//        }
        if(password2.isEmpty()){
            mpass.setError("Password Is Required");
            mpass.requestFocus();
            return;
        }
        if(password2.length()<6){
            mpass.setError("Atleast 6 Characters");
            mpass.requestFocus();
            return;
        }
        if(!(password2.equals(password3))){
            mcpass.setError("Conform Password");
            mcpass.requestFocus();
            return;
        }

        if(email3.isEmpty()){
            memail.setError("Email Is Required");
            memail.requestFocus();
            return;
        }
        if(phone4.isEmpty()){
            mphone.setError("Phone Number Is Required");
            mphone.requestFocus();
            return;
        }
        if(address.isEmpty()){
            maddress.setError("Address Is Required");
            maddress.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();




        mAuth.createUserWithEmailAndPassword(memail.getText().toString(),mpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String id = myref.push().getKey();    //unique id
                    Model model = new Model();
                    model.setFname(mfname.getText().toString());
                    //model.setUsername(mname.getText().toString());
                    model.setPassword(mpass.getText().toString());
                    model.setCpassword(mcpass.getText().toString());
                    model.setEmail(memail.getText().toString());
                    model.setPhone(mphone.getText().toString());
                    model.setAddress(maddress.getText().toString());
                    model.setId(id);
                   // model.setMale(mmale.getText().toString());
                    //model.setFemlae(mfemale.getText().toString());
                    //model.setCheckbox(mcheckbox.getText().toString());
                    //model.setCheckbox1(mcheckbox1.getText().toString());

                    myref.child(id).setValue(model);
                    finish();

                    Toast.makeText(Signup.this,"Welcome !!", Toast.LENGTH_SHORT).show();



                    startActivity(new Intent(Signup.this,EmailVerification.class));

                }else{
                    Toast.makeText(Signup.this,"Error Error Error!!", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }

}
