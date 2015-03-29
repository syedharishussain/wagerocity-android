package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
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

    @GET("/getUser")
    public void getUser(@Query("facebookID") String facebookID, Callback <User> callback) ;

    @Multipart
    @POST("/createUser")
    public void createUser(@Part("firstName") String firstName, @Part("lastName") String lastName, @Part("facebookID") String facebookID, Callback<User> callback);

    @Multipart
    @POST("/buyCredits")
    public void buyCredits(@Part("userId") String userId, @Part("creditAmount") Float creditAmount, Callback<User> callback);

    @Multipart
    @POST("/consumeCredits")
    public void consumeCredits(@Part("userId") String userId, @Part("debitAmount") Float debitAmount, Callback<User> callback);

    @GET("/getGames")
    public void getGames(@Query("leagueName") String leagueName, Callback <ArrayList<Game>> callback) ;
}


