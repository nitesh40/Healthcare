package com.nitesh.firstapp.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nitesh.firstapp.Homepage.Account;
import com.nitesh.firstapp.Homepage.Ambulance;
import com.nitesh.firstapp.Homepage.Doctor;
import com.nitesh.firstapp.Homepage.Hospital;
import com.nitesh.firstapp.Homepage.MedicalAlarm.Medicalalarm;
import com.nitesh.firstapp.Homepage.Pharmacy;
import com.nitesh.firstapp.Homepage.Setting;
import com.nitesh.firstapp.R;
import com.nitesh.firstapp.Unused.MapActivity;

public class HomePage extends AppCompatActivity {
    //Button mbuttton;

    DrawerLayout mdrwaer;
    NavigationView mnavigation;
    ActionBarDrawerToggle mtoggle;
    //Button mlogout;
    FirebaseAuth mAuth;
    //TextView mview;

    //widgets
//    private TabLayout mTabLayout;
//    public ViewPager mViewPager;

    //vars
//    public SectionsPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        mTabLayout=(TabLayout)findViewById(R.id.tabs);
//        mViewPager=(ViewPager) findViewById(R.id.viewpager_container);
//        setupViewPager();


                //mbuttton=findViewById(R.id.mapView);

        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(HomePage.this,MainActivity.class));
        }

        FirebaseUser user=mAuth.getCurrentUser();

        //Toolbar toolbar=findViewById(R.id.logouttoolbar);
        //setSupportActionBar(toolbar);

        //mlogout=findViewById(R.id.logout);
        //mview=findViewById(R.id.view);
        //mview.setText("Welcome" + user.getEmail());

        mdrwaer=findViewById(R.id.drwaer);
        mtoggle=new ActionBarDrawerToggle(this,mdrwaer,R.string.open,R.string.close);
        mnavigation=findViewById(R.id.navigation);
        mdrwaer.addDrawerListener(mtoggle);
        //View headerlayout=mnavigation.getHeaderView(0);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupnavigation(mnavigation);

//        mlogout.setOnClickListener(new View.OnClickListener() {
//          @Override
//            public void onClick(View v) {
//
//              mAuth.signOut();
//              finish();
//              startActivity(new Intent(HomePage.this,MainActivity.class));
//
//            }
//        });


    }

//    private void setupViewPager(){
//        mPagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
//
//        //add fragment
////       mPagerAdapter.addFragment(new HomeFragment());
////        mPagerAdapter.addFragment(new FirstAidFragment());
////
//
//
//        mViewPager.setAdapter(mPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//        setupViewPager();
//
//        //names of fragments or tabs
//        mTabLayout.getTabAt(0).setText(getString(R.string.fragment_home));
//        mTabLayout.getTabAt(1).setText(getString(R.string.fragment_firstaid));
//
//    }

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
        switch (item.getItemId()){
            case R.id.menulogout:
                mAuth.signOut();
                finish();
                Intent intent=new Intent(HomePage.this,MainActivity.class);
                startActivity(intent);
                default:
                    return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
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
            case R.id.map:
                Intent intent=new Intent(HomePage.this,MapActivity.class);
                startActivity(intent);
        }

        switch (menuItem.getItemId()){
            case R.id.hospital:
                Intent intent=new Intent(HomePage.this,Hospital.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.doctor:
                Intent intent1=new Intent(HomePage.this,Doctor.class);
                startActivity(intent1);
        }
        switch (menuItem.getItemId()){
            case R.id.account:
                Intent intent=new Intent(HomePage.this,Account.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.ambulance:
                Intent intent=new Intent(HomePage.this,Ambulance.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.pharmacy:
                Intent intent=new Intent(HomePage.this,Pharmacy.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.medicinealaram:
                Intent intent=new Intent(HomePage.this,Medicalalarm.class);
                startActivity(intent);
        }
        switch (menuItem.getItemId()){
            case R.id.setting:
                Intent intent=new Intent(HomePage.this,Setting.class);
                startActivity(intent);
        }
    }
//logout option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


}
