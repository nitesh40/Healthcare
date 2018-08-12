package com.nitesh.firstapp.Map;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nitesh.firstapp.GooglePlaces.Results;
import com.nitesh.firstapp.GooglePlaces.myplaces;
import com.nitesh.firstapp.R;
import com.nitesh.firstapp.Retrofit.IGoogleApiservices;
import com.nitesh.firstapp.Unused.Common;
import com.nitesh.firstapp.Unused.PlaceAutocompleteAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;


    //Play Services
    private static final int MY_PERMISSION_REQUEST_CODE= 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    long PROXIMITY_RADIUS = 15000;
    double latitude,longitude;


    private static int UPDATE_INTERBAL = 5000;
    private static int FASTEST_INTERval = 3000;
    private static int DISPLACEMENT = 10;

    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    ImageButton msearch;
    EditText relLayout1;
    Button mhospital,mschools,mpharmacies;
    DrawerLayout mdrwaer;
    ActionBarDrawerToggle mtoggle;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

//    FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    DrawerLayout mdrawer;
//    ActionBarDrawerToggle mtoggle;
//    NavigationView mnavbar;
//    FrameLayout mframe;
//    EditText msearchfield;
//    ImageView mpro;
//    TextView musername,musermob;
//    DatabaseReference Ueser;
//    StorageReference storageReference;
//    LinearLayout mprofile;
//    ImageButton mmainsearch, mbtnsearch,mbtnback,mbtnreback,mbtnnext,mhospital,mschools,mrestaurants,mbar,mhotel,mhostel,mpharmacies,mambulance,mpizza,mcafe,
//            mbank,matm,mgas_station,mparking,mcarwash,mtaxi,mhaircut,mshowroom,mgarage,mcasino,mcinema,mgym,mshopmall,mlodge,mconsultancy,mswimming;
//    RelativeLayout moption_relative,mreoption_relative;
//
//    GeoFire geoFire;
    Marker mCurrentUser;
    View mapView;
    IGoogleApiservices mservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Map
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        mhospital=findViewById(R.id.maphospital);
        mschools=findViewById(R.id.mapschool);
        mpharmacies=findViewById(R.id.mappharmacies);

        msearch=findViewById(R.id.ic_magnify);

        mdrwaer=findViewById(R.id.drawer);
        mtoggle=new ActionBarDrawerToggle(this,mdrwaer,R.string.open,R.string.close);
        mdrwaer.addDrawerListener(mtoggle);
        mtoggle.syncState();


        //Request runtime permission
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checklocationpermission();
        }*/

        mservice= Common.getGoogleAPIservice();

        setUpLocation();

//        mschools.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nearbyplace("hospital");
//
//            }
//        });
//
//        mpharmacies.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nearbyplace("pharmacies");
//            }
//        });

        //search

//        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected()){
//            try {
//                mGoogleApiClient = new GoogleApiClient
//                        .Builder(this)
//                        .addApi(Places.GEO_DATA_API)
//                        .addApi(Places.PLACE_DETECTION_API)
//                        .enableAutoManage(this,this)
//                        // .addApi(GoogleAuthProvider.GOOGLE_SIGN_IN_METHOD)
//                        .build();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
////        mGoogleApiClient1 = new GoogleApiClient
////                .Builder(this)
////                .addApi(Places.GEO_DATA_API)
////                .addApi(Places.PLACE_DETECTION_API)
////                .enableAutoManage(this, 0, this)
////                .build();
//
//        placeAutocompleteAdapter=new PlaceAutocompleteAdapter(this,Places.getGeoDataClient(this, null),
//                LAT_LNG_BOUNDS,
//                null);
//
//
//        mSearchText.setAdapter(placeAutocompleteAdapter);

        //return mGoogleApiClient;


        //like button but keyboard
//        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
//                if(actionId== EditorInfo.IME_ACTION_SEARCH
//                        || actionId==EditorInfo.IME_ACTION_DONE
//                        || keyEvent.getAction()==KeyEvent.ACTION_DOWN
//                        || keyEvent.getAction()==KeyEvent.KEYCODE_ENTER)
//                {
//                    //hideSoftKeyboard();
//
//                    //execute method for searching
//                    geoLocate();
//
//                }
//
//                return false;
//            }
//        });
//        msearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                geoLocate();
//            }
//        });







    }

    public void onSearch(View view){
        String location=relLayout1.getTag().toString();

        if(location.isEmpty()){
            relLayout1.setError("Enter the location");
            relLayout1.requestFocus();
            return;
        }
        List<Address> addressList=null;
        if (location!=null || !location.equals("")){
            Geocoder geocoder=new Geocoder(this);
            try{
                addressList=geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address=addressList.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(location)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(),address.getLongitude()),15.0f));
        }
    }

//    private void geoLocate(){
//       // Log.d(TAG, "geoLocate: geolocation");
//
//        String searchString=mSearchText.getText().toString();
//        //hideSoftKeyboard();
//
//        Geocoder geocoder=new Geocoder(MapsActivity.this);
//        List<Address> list=new ArrayList<>();
//        try{
//            list=geocoder.getFromLocationName(searchString, 1);
//        } catch (IOException e) {
//           // Log.e(TAG, "geoLocate: IOExcepytion:" + e.getMessage());
//        }
//        if(list.size()>0){
//            Address address=list.get(0);
//
//            //Log.d(TAG, "geoLocate: found a location"+address.toString());
//
//            //moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
//            // hideSoftKeyboard();
//
//
//
//        }
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.maphospital:
                nearbyplace("hospital");


        }
        switch (item.getItemId()) {
            case R.id.mapschool:
                nearbyplace("hospital");


        }
        switch (item.getItemId()) {
            case R.id.mappharmacies:
                nearbyplace("pharmacy");


        }

        return super.onOptionsItemSelected(item);
    }

    private void nearbyplace(final String placeType){
        mMap.clear();
        String url=getUrl(latitude,longitude,placeType);
        mservice.getNearByPlaces(url)
                .enqueue(new Callback<myplaces>() {
                    @Override
                    public void onResponse(Call<myplaces> call, Response<myplaces> response) {
                        if(response.isSuccessful()){
                            for(int i=0;i<response.body().getResults().length;i++){
                                MarkerOptions markerOptions=new MarkerOptions();
                                Results googlePlace=response.body().getResults()[i];
                                double lat = Double.parseDouble( googlePlace.getGeometry().getLocation().getLat());
                                double lng = Double.parseDouble( googlePlace.getGeometry().getLocation().getLng());
                                String placeName=googlePlace.getName();
                                String vicinity=googlePlace.getVicinity();
                                LatLng latLng=new LatLng(lat,lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName + " : "+ vicinity);
                                if(placeType.equals("hospital"))
                                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital));
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                                else if(placeType.equals("pharmacy"))
                                    //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pharmacy));
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                                else if (placeType.equals("school"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));


                                mMap.addMarker(markerOptions);
                                //Move Camera
                                mMap.moveCamera(newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<myplaces> call, Throwable t) {

                    }
                });
    }

    private String getUrl(double latitude,double longitude,String placeType){

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&keyword="+placeType);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyDbYb5986R2ZwCYBEetIVwgbZ-gz10QYRg");

        Log.d("MapActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {

                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }

                }
                break;
        }
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            //Request Runtime permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);

        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();

                displayLocation();
            }
        }
        ;

    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            //Add MArker
            if (mCurrentUser != null)
                mCurrentUser.remove();

            mCurrentUser = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Your Location"));



            //Move camera
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));


            Log.d("Update", String.format("Your location has changed: %f/ %f", latitude, longitude));


        } else {
            Log.d("ERROR", "Cannot get your Location");
        }

        //if (mGoogleApiClient!=null){
        //  LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        //}
    }


    @SuppressLint("RestrictedApi")
    private void createLocationRequest(){

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERBAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();


    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError((resultCode)))
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RES_REQUEST).show();
            else
                Toast.makeText(this, "This Device is Not Supported", Toast.LENGTH_SHORT).show();
            finish();
            return (false);
        }
        return (true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);




        //mMap.setInfoWindowAdapter(new CustomInfoWindow(this));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on left bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(70,0,5,5);

        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, (com.google.android.gms.location.LocationListener) this);



    }



    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.maps,menu);

        return true;
    }
}
