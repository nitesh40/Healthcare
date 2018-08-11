package com.nitesh.firstapp.Retrofit;

import com.nitesh.firstapp.GooglePlaces.myplaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Nitesh on 6/22/2018.
 */



public interface IGoogleApiservices {
    @GET
    Call<myplaces> getNearByPlaces (@Url String url);
}
