package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.Comparator;

public class LeaderboardPlayer implements Parcelable, Comparator<LeaderboardPlayer> {

    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @Expose
    private String displayname;
    @Expose
    private String email;
    @Expose
    private String username;
    @Expose
    private String points;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("win_percentage")
    @Expose
    private String winPercentage;
    @SerializedName("total_picks")
    @Expose
    private String totalPicks;
    @SerializedName("is_follow")
    @Expose
    private Boolean isFollowing;

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean isFollowing) {
        this.isFollowing = isFollowing;
    }


    public String getTotalPicks() {
        return totalPicks;
    }

    public void setTotalPicks(String totalPicks) {
        this.totalPicks = totalPicks;
    }




    /**
     *
     * @return
     * The usrId
     */
    public String getUsrId() {
        return usrId;
    }

    /**
     *
     * @param usrId
     * The usr_id
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     *
     * @return
     * The displayname
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     *
     * @param displayname
     * The displayname
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The points
     */
    public String getPoints() {
        return points;
    }

    /**
     *
     * @param points
     * The points
     */
    public void setPoints(String points) {
        this.points = points;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The winPercentage
     */
    public String getWinPercentage() {
        return winPercentage;
    }

    /**
     *
     * @param winPercentage
     * The win_percentage
     */
    public void setWinPercentage(String winPercentage) {
        this.winPercentage = winPercentage;
    }


    @Override
    public int compare(LeaderboardPlayer lhs, LeaderboardPlayer rhs) {
        return Double.compare(Double.parseDouble(rhs.getPoints()), Double.parseDouble(lhs.getPoints()));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.usrId);
        dest.writeString(this.displayname);
        dest.writeString(this.email);
        dest.writeString(this.username);
        dest.writeString(this.points);
        dest.writeString(this.imageUrl);
        dest.writeString(this.winPercentage);
        dest.writeString(this.totalPicks);
        dest.writeValue(this.isFollowing);
    }

    public LeaderboardPlayer() {
    }

    private LeaderboardPlayer(Parcel in) {
        this.usrId = in.readString();
        this.displayname = in.readString();
        this.email = in.readString();
        this.username = in.readString();
        this.points = in.readString();
        this.imageUrl = in.readString();
        this.winPercentage = in.readString();
        this.totalPicks = in.readString();
        this.isFollowing = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<LeaderboardPlayer> CREATOR = new Creator<LeaderboardPlayer>() {
        public LeaderboardPlayer createFromParcel(Parcel source) {
            return new LeaderboardPlayer(source);
        }

        public LeaderboardPlayer[] newArray(int size) {
            return new LeaderboardPlayer[size];
        }
    };
}
