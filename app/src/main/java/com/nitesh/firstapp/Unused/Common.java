package com.nitesh.firstapp.Unused;

import com.nitesh.firstapp.Retrofit.IGoogleApiservices;
import com.nitesh.firstapp.Retrofit.RetrofitClient;

/**
 * Created by Nitesh on 6/22/2018.
 */

public class Common {

    private static final String GOOGlE_API_URL="https://maps.googleapis.com/";
    public static IGoogleApiservices getGoogleAPIservice(){
        return RetrofitClient.getClient(GOOGlE_API_URL).create(IGoogleApiservices.class);
    }


}
