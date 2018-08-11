package com.nitesh.firstapp.Map;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.nitesh.firstapp.R;
import com.nitesh.firstapp.Unused.MapActivity;

public class Map extends AppCompatActivity {

    Button btnMap;

    private static final String TAG = "Map";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(isServicesOK()){
            init();
        }
    }

    private void init(){

        btnMap=findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Map.this,MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google servies version");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Map.this);
        if(available== ConnectionResult.SUCCESS){
//everything is fine and can make map request
            Log.d(TAG, "isServicesOK: Google Play Services is working");

            return true;

        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//error occur but can b fixed
            Log.d(TAG, "isServicesOK: an error occur but we can fix it");

            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(Map.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(this,"You cannot make request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}



//API Key      -- AIzaSyDHf_zPvEKiYCCadZQmr60ceN44ibhalF0
//PLACES API KEY    --AIzaSyDfuKg5VlDRgp93XM4WTtoljJlVQl3eCyM
