package com.plego.wagerocity.android.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;


/**
 * Created by haris on 11/03/15.
 */
public interface ServiceModel {

    @GET("/getAllPools")
    public void getAllPools(Callback<ArrayList<Pool>> callback);


}



