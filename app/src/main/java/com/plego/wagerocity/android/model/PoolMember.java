package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PoolMember implements Parcelable {

    @Expose
    private String displayname;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String status;
    @Expose
    private Integer win;
    @Expose
    private Integer lost;
    @Expose
    private Integer tie;
    @Expose
    private Double dollars;
    @Expose
    private Integer rank;

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
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The win
     */
    public Integer getWin() {
        return win;
    }

    /**
     *
     * @param win
     * The win
     */
    public void setWin(Integer win) {
        this.win = win;
    }

    /**
     *
     * @return
     * The lost
     */
    public Integer getLost() {
        return lost;
    }

    /**
     *
     * @param lost
     * The lost
     */
    public void setLost(Integer lost) {
        this.lost = lost;
    }

    /**
     *
     * @return
     * The tie
     */
    public Integer getTie() {
        return tie;
    }

    /**
     *
     * @param tie
     * The tie
     */
    public void setTie(Integer tie) {
        this.tie = tie;
    }

    /**
     *
     * @return
     * The dollars
     */
    public Double getDollars() {
        return dollars;
    }

    /**
     *
     * @param dollars
     * The dollars
     */
    public void setDollars(Double dollars) {
        this.dollars = dollars;
    }

    /**
     *
     * @return
     * The rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayname);
        dest.writeString(this.userId);
        dest.writeString(this.status);
        dest.writeValue(this.win);
        dest.writeValue(this.lost);
        dest.writeValue(this.tie);
        dest.writeValue(this.dollars);
        dest.writeValue(this.rank);
    }

    public PoolMember() {
    }

    private PoolMember(Parcel in) {
        this.displayname = in.readString();
        this.userId = in.readString();
        this.status = in.readString();
        this.win = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tie = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dollars = (Double) in.readValue(Double.class.getClassLoader());
        this.rank = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PoolMember> CREATOR = new Creator<PoolMember>() {
        public PoolMember createFromParcel(Parcel source) {
            return new PoolMember(source);
        }

        public PoolMember[] newArray(int size) {
            return new PoolMember[size];
        }
    };
}
