package com.nitesh.firstapp.MainActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nitesh.firstapp.Model;
import com.nitesh.firstapp.R;

public class EmailVerification extends AppCompatActivity {
    TextView met_num,met_uid,met_status,meerrorfield;
    Button mebtn_send,mebtn_refresh;
    DatabaseReference Ueser;
    RelativeLayout mainLayoutt;
    private FirebaseAuth mAuth;
    String email;
    String phonenum,id,picurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        meerrorfield=findViewById(R.id.eerrorfield);
        met_num=findViewById(R.id.et_num);
        met_uid=findViewById(R.id.et_uid);
        met_status=findViewById(R.id.et_status);
        mebtn_send=findViewById(R.id.ebtn_send);
        mebtn_refresh=findViewById(R.id.ebtn_refresh);
        mainLayoutt = findViewById(R.id.mainlayoutt);

        mAuth= FirebaseAuth.getInstance();




        Ueser= FirebaseDatabase.getInstance().getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/User");

        setinfo();


        mebtn_refresh.setEnabled(false);

        mebtn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mebtn_send.setEnabled(false);
                FirebaseAuth.getInstance().getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                mebtn_send.setEnabled(true);

                                if(task.isSuccessful()) {

                                    Snackbar.make(mainLayoutt, "Verification email sent to : " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT)
                                            .show();

                                    //Toast.makeText(email_verification.this, "Verification email sent to : " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                    mebtn_refresh.setEnabled(true);
                                }
                                else {

                                    Snackbar.make(mainLayoutt, "Failed to send email", Snackbar.LENGTH_SHORT).show();
                                    //Toast.makeText(email_verification.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                                    mebtn_refresh.setEnabled(false);
                                }
                            }
                        });
            }
        });

        mebtn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mebtn_refresh.setEnabled(false);
                final FirebaseUser user =   FirebaseAuth.getInstance().getCurrentUser();
                user.reload()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mebtn_refresh.setEnabled(true);
                                    setinfo();
                                    if (user.isEmailVerified()) {
                                        //Toast.makeText(email_verification.this, "Email verified", Toast.LENGTH_SHORT).show();
                                        Snackbar.make(mainLayoutt, "Email Verified", Snackbar.LENGTH_SHORT).show();

                                        startActivity(new Intent(EmailVerification.this,HomePage.class));
                                        finish();



                                    } else {
                                        //Toast.makeText(email_verification.this, "Email verification failed", Toast.LENGTH_SHORT).show();

                                        Snackbar.make(mainLayoutt, "Email Verification Failed", Snackbar.LENGTH_SHORT).show();
                                        deleteuser();

                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStop() {

        Snackbar.make(mainLayoutt, "Email Verification Failed", Snackbar.LENGTH_SHORT).show();
        deleteuser();

        super.onStop();
    }

    private void deleteuser() {

        //remove data from authentication

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        user.delete()

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            //remove data from database

                            Ueser.child(id).removeValue();
                            Intent sintent=new Intent(EmailVerification.this,Signup.class);
                            startActivity(sintent);
                            finish();
                        }
                    }
                });






    }


    private void setinfo() {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            //Uri photoUrl = user.getPhotoUrl();
            //met_num.setText(new StringBuilder("Email : ").append(user.getDisplayName()));
            met_num.setText(new StringBuilder("Email : ").append(user.getEmail()));
            met_uid.setText(new StringBuilder("UID : ").append(user.getUid()));
            met_status.setText(new StringBuilder("STATUS : ").append(String.valueOf(user.isEmailVerified())));


        }

        Ueser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Model model=new Model();
                    model.setPhone(ds.getValue(Model.class).getPhone());
                    model.setId(ds.getValue(Model.class).getId());
                    model.setFname(ds.getValue(Model.class).getFname());
                    //model.setMimageurl(ds.getValue(Model.class).getMimageurl());
                    id = model.getId();
                    phonenum=model.getPhone();
                    //picurl=model.getMimageurl();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)

                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Quiting Smatr city")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

}
