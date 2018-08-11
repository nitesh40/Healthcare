package com.nitesh.firstapp.Unused;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nitesh.firstapp.Homepage.Account;
import com.nitesh.firstapp.Homepage.Ambulance;
import com.nitesh.firstapp.Homepage.Doctor;
import com.nitesh.firstapp.Homepage.Hospital;
import com.nitesh.firstapp.MainActivity.MainActivity;
import com.nitesh.firstapp.Homepage.MedicalAlarm.Medicalalarm;
import com.nitesh.firstapp.Homepage.Pharmacy;
import com.nitesh.firstapp.R;
import com.nitesh.firstapp.Homepage.Setting;

public class H extends AppCompatActivity {
    //Button mbuttton;

    DrawerLayout mdrwaer;
    NavigationView mnavigation;
    ActionBarDrawerToggle mtoggle;
    Button mlogout;
    FirebaseAuth mAuth;
    TextView mview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_homepage);

        //mbuttton=findViewById(R.id.mapView);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(H.this,MainActivity.class));
        }

        FirebaseUser user=mAuth.getCurrentUser();

        //Toolbar toolbar=findViewById(R.id.logouttoolbar);
        //setSupportActionBar(toolbar);

       // mlogout=findViewById(R.id.logout);
        //mview=findViewById(R.id.view);
        mview.setText("Welcome"+user.getEmail());

        mdrwaer=findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mdrwaer,R.string.open,R.string.close);
        mnavigation=findViewById(R.id.navigation);
        mdrwaer.addDrawerListener(mtoggle);
        //View headerlayout=mnavigation.getHeaderView(0);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupnavigation(mnavigation);

        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                finish();
                startActivity(new Intent(H.this,MainActivity.class));

            }
        });


    }

    //public boolean onCreatOption(Menu menu){

//        MenuInflater inflater=getMenuInflater();
  //      inflater.inflate(R.menu.menu, menu);

//        return true;

  //  }

    //public boolean onOptionItemSelected(MenuItem item){

      //  switch (item.getItemId()){
        //    case R.id.menulogout:
        //}

        //return true;
    //}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupnavigation(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectitem(item);

                return true;
            }

        });
    }
    public void selectitem(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.hospital:
                Intent intent=new Intent(H.this,Hospital.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.doctor:
                Intent intent1=new Intent(H.this,Doctor.class);
                startActivity(intent1);
        }
        switch (menuItem.getItemId()){
            case R.id.account:
                Intent intent=new Intent(H.this,Account.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.ambulance:
                Intent intent=new Intent(H.this,Ambulance.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.pharmacy:
                Intent intent=new Intent(H.this,Pharmacy.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.medicinealaram:
                Intent intent=new Intent(H.this,Medicalalarm.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.setting:
                Intent intent=new Intent(H.this,Setting.class);
                startActivity(intent);
        }
    }
}
