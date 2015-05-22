package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
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
    public void getAllPools(@Query("userId") String userId, Callback<ArrayList<Pool>> callback);

    @GET("/getLeaderboards")
    public void getLeaderboards(@Query("leagueName") String leagueName, @Query("year") String year, Callback<ArrayList<LeaderboardPlayer>> callback);

    @GET("/getExperts")
    public void getExperts(@Query("userId") String userId, Callback<ArrayList<ExpertPlayer>> callback);

    @GET("/getUser")
    public void getUser(@Query("facebookID") String facebookID, Callback<User> callback);

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
    public void getGames(@Query("leagueName") String leagueName, Callback<ArrayList<Game>> callback);

    @GET("/getBetParent")
    public void getBetParent(Callback<BetParent> callback);

    @Multipart
    @POST("/betOnGame")
    public void betOnGames(
            @Part("userID") String userID,
            @Part("oddID") String oddID,
            @Part("oddVal") String oddVal,
            @Part("position") String position,
            @Part("matchDetail") String matchDetail,
            @Part("oddType") String oddType,
            @Part("stake") String stake,
            @Part("matchID") String matchID,
            @Part("teamName") String teamName,
            @Part("sportsName") String sportsName,
            @Part("bet_type") String bet_type,
            @Part("bet_ot") String bet_ot,
            @Part("bet_parent") String betParent,
            @Part("is_pool_bet") String isPoolBet,
            @Part("bet_result") String betResult,
            @Part("bet_processed") String betProcessed,
            Callback<ArrayList<Pick>> callback
    );

    @GET("/getMyPicks")
    public void getMyPicks(@Query("userId") String userId, Callback<ArrayList<Pick>> callback);

    @Multipart
    @POST("/followExpert")
    public void followExpert(@Part("userId") String userId, @Part("expertID") String expertID, Callback<Response> callback);

    @Multipart
    @POST("/unfollowExpert")
    public void unfollowExpert(@Part("userId") String userId, @Part("expertID") String expertID, Callback<Response> callback);

    @Multipart
    @POST("/joinPool")
    public void joinPool(@Part("userId") String userId, @Part("poolId") String poolId, Callback<ArrayList<Pool>> callback);

    @Multipart
    @POST("/unjoinPool")
    public void unjoinPool(@Part("userId") String userId, @Part("poolId") String poolId, Callback<ArrayList<Pool>> callback);

    @GET("/getMyPools")
    public void getMyPools(@Query("userId") String userId, Callback<ArrayList<MyPool>> callback);

    @GET("/getGamesOfPlayer")
    public void getGamesOfPlayer(@Query("playerID") String playerID, @Query("userID") String userID, Callback<ArrayList<Game>> callback);

    @Multipart
    @POST("/PurchasePicksOfPlayer")
    public void purchasePicksOfPlayer(@Part("userID") String userId, @Part("expertID") String expertId, @Part("betID") String betId, Callback<ArrayList<Pool>> callback);

}

