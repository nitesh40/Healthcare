package com.nitesh.firstapp.Unused;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nitesh.firstapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 6/7/2018.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map Is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        //getLocationPermission();

        if (mLocationPermissionsGranted) {
           //getLocationPermission();
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);  //disable the return button

            init();
        }
    }

    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1234;
    private static final float DEFAULT_ZOOM=15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView  mGps;
    ImageButton msearch;
    Button B_hospitals,B_schools,B_resturants,B_to;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient mGoogleApiClient1;
    private GeoDataClient mGeoDataClient;
    private LocationProvider mLocationProvider;
    private LocationSource mLocationSource;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;

    int PROXIMITY_RADIUS = 10000;
  //  double latitude,longitude;
    private Location mLastlocation;
    Marker mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map1);
        mSearchText=findViewById(R.id.input_search);
        mGps=findViewById(R.id.ic_gps);
        msearch=findViewById(R.id.ic_magnify);
        B_hospitals=findViewById(R.id.B_hopistals);
        B_schools=findViewById(R.id.B_schools);
        B_resturants=findViewById(R.id.B_restaurants);
        B_to=findViewById(R.id.B_to);

        getLocationPermission();



    }



    private void init(){
        Log.d(TAG, "init: initializing");

        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected()){
            try {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this,this)
               // .addApi(GoogleAuthProvider.GOOGLE_SIGN_IN_METHOD)
                .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        mGoogleApiClient1 = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(this, 0, this)
//                .build();

        placeAutocompleteAdapter=new PlaceAutocompleteAdapter(this,Places.getGeoDataClient(this, null),
                LAT_LNG_BOUNDS,
                null);


         mSearchText.setAdapter(placeAutocompleteAdapter);

        //return mGoogleApiClient;


        //like button but keyboard
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        || actionId==EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction()==KeyEvent.ACTION_DOWN
                        || keyEvent.getAction()==KeyEvent.KEYCODE_ENTER)
                {
                    //hideSoftKeyboard();

                    //execute method for searching
                    geoLocate();

                }

                return false;
            }
        });

        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
//        B_hospitals.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMap.clear();
//                String hospital = "hospital";
//                String url = getUrl(latitude, longitude, hospital);
//                Object dataTransfer[]=new Object[2];
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//
//                GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(MapActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();
//            }
//        });
//        B_resturants.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMap.clear();
//                String resturant = "resturants";
//                String url = getUrl(latitude, longitude, resturant);
//                Object dataTransfer[]=new Object[2];
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//
//                GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(MapActivity.this, "Showing Nearby Resturants", Toast.LENGTH_SHORT).show();
//            }
//        });
//        B_schools.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mMap.clear();
//                String school = "school";
//                String url = getUrl(latitude, longitude, school);
//                Object dataTransfer[]=new Object[2];
//                dataTransfer[0] = mMap;
//                dataTransfer[1] = url;
//
//                GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
//                getNearbyPlacesData.execute(dataTransfer);
//                Toast.makeText(MapActivity.this, "Showing Nearby Schools", Toast.LENGTH_SHORT).show();
//            }
//        });

        hideSoftKeyboard();
}

private String getUrl(double latitude,double longitude,String nearbyPlace)
{
    StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/jason?");
    googlePlaceUrl.append("location"+latitude+","+longitude);
    googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
    googlePlaceUrl.append("&type="+nearbyPlace);
    googlePlaceUrl.append("&sensor=true");
    googlePlaceUrl.append("&key="+"AIzaSyDfuKg5VlDRgp93XM4WTtoljJlVQl3eCyM");   //place api key

    return googlePlaceUrl.toString();
}

   @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

   @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }



    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocation");

        String searchString=mSearchText.getText().toString();
        //hideSoftKeyboard();

        Geocoder geocoder=new Geocoder(MapActivity.this);
        List<Address> list=new ArrayList<>();
        try{
            list=geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOExcepytion:" + e.getMessage());
        }
        if(list.size()>0){
            Address address=list.get(0);

            Log.d(TAG, "geoLocate: found a location"+address.toString());

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
           // hideSoftKeyboard();

        }
    }

    private void getDeviceLocation(){
        getLocationPermission();
        Log.d(TAG, "getDeviceLocation: getting device current location");
//        mGoogleApiClient=LocationServices.getGoogleApiClient(this);
//        mLocationSource=LocationServices.getLocationSource(this);
       mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try {
            if(mLocationPermissionsGranted){
               // Task location=mGoogleApiClient.getLastLocation();
                //Task location=mLocationSource.getLastLocation();
                final Task location=mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                           // Log.d(TAG, "onComplete: found location");
                           Location currentlocation=(Location) task.getResult();


                            moveCamera(new LatLng(currentlocation.getLatitude(), currentlocation.getLongitude()),
                                    DEFAULT_ZOOM,"Mylocation");


                        }else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }


        //new method

//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED ){
//            return;
//        }
//        mLastlocation= LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if(mLastlocation!=null){
//            latitude=mLastlocation.getLatitude();
//            longitude=mLastlocation.getLongitude();
//        }
//        //Add Marker
//        if(mCurrentUser!=null)
//            mCurrentUser.remove();
//        mCurrentUser=mMap.addMarker(new MarkerOptions()
//        .position(new LatLng(latitude,longitude))
//        .title("My Location"));
//
//        //move camera
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),15.0f));


    }

    private void buildGoogleApiClient(){
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    private void moveCamera(LatLng latLng,float zoom, String title){
      //  Log.d(TAG, "moveCamera: moving camera to : lat:"+latLng.latitude + ",lng: "+latLng.longitude );

        //drop marker
        MarkerOptions options=new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));


        hideSoftKeyboard();

    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");

        String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted=false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i = 0;i < grantResults.length; i++){
                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                            buildGoogleApiClient();
                            getDeviceLocation();
                            mLocationPermissionsGranted=false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted=true;
                    //initialize map
                    initMap();
                }
            }
        }
    }

    //hide keyboard after search
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getDeviceLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }
}
