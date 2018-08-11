package com.nitesh.firstapp.Homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nitesh.firstapp.Homepage.MedicalAlarm.Medicalalarm;
import com.nitesh.firstapp.R;

public class Hospital extends AppCompatActivity {

    DrawerLayout mdrwaer;
    NavigationView mnavigation;
    ActionBarDrawerToggle mtoggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        mdrwaer=findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mdrwaer,R.string.open,R.string.close);
        mnavigation=findViewById(R.id.navigation);
        mdrwaer.addDrawerListener(mtoggle);
        //View headerlayout=mnavigation.getHeaderView(0);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupnavigation(mnavigation);

    }

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
                Intent intent=new Intent(Hospital.this,Hospital.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.doctor:
                Intent intent1=new Intent(Hospital.this,Doctor.class);
                startActivity(intent1);
        }
        switch (menuItem.getItemId()){
            case R.id.account:
                Intent intent=new Intent(Hospital.this,Account.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.ambulance:
                Intent intent=new Intent(Hospital.this,Ambulance.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.pharmacy:
                Intent intent=new Intent(Hospital.this,Pharmacy.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.medicinealaram:
                Intent intent=new Intent(Hospital.this,Medicalalarm.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.setting:
                Intent intent=new Intent(Hospital.this,Setting.class);
                startActivity(intent);
        }
    }
}
