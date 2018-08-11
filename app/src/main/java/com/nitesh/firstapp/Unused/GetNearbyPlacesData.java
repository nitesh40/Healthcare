package com.nitesh.firstapp.Unused;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nitesh on 6/21/2018.
 */

public class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
    private String googlePlacesData;
    private String url;
    GoogleMap mMap;


    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googlePlacesData=downloadUrl.realUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList=null;
        DataParser parser=new DataParser();
        nearbyPlaceList=parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }
    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList)
    {
        for (int i=0;i<nearbyPlaceList.size();i++)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String> googleplace=nearbyPlaceList.get(i);

            String placeName=googleplace.get("place_name");
            String vicinity=googleplace.get("vicinity");
            double lat=Double.parseDouble(googleplace.get("lat"));
            double lng=Double.parseDouble(googleplace.get("lng"));

            LatLng latLng=new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
