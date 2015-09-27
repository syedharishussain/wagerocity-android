package com.plego.wagerocity.android.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plego.wagerocity.BuildConfig;
import com.plego.wagerocity.android.model.ServiceModel;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
@Module
@Singleton
public class ApiModule {

    @Provides
    @Singleton
    public ServiceModel providesServiceModel() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson));
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        RestAdapter restAdapter = builder.build();

        return restAdapter.create(ServiceModel.class);
    }
}
