package com.plego.wagerocity.android.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by haris on 17/03/15.
 */
public class RestClient {

    private static final String BASE_URL = "http://plego.info/wagerocity_api_v1/api_v1";
//    private static final String BASE_URL = "https://www.wagerocity.com/api_v1";
    private ServiceModel apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(ServiceModel.class);
    }

    public ServiceModel getApiService()
    {
        return apiService;
    }
}