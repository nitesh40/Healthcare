package com.nitesh.firstapp.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Nitesh on 6/22/2018.
 */

public interface IGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);
}
