package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by haris on 11/03/15.
 */
public interface ServiceModel {

    @GET("/getAllPools")
    public void getAllPools(Callback<ArrayList<Pool>> callback);

    @GET("/getLeaderboards")
    public void getLeaderboards(Callback<ArrayList<LeaderboardPlayer>> callback);

    @GET("/getExperts")
    public void getExperts(Callback<ArrayList<ExpertPlayer>> callback);

    @GET("/getUser?facebookID={facebookID}")
    public void getUser(@Query("facebookID") String facebookID, Callback <User> callback) ;

//    @GET("/users/{user}/repos" "getUser?facebookID")
//    List<Repo> listRepos(@Path("user") String user);
}

