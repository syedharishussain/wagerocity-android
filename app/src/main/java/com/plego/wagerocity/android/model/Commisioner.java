package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commisioner implements Parcelable{

    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String email;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @Expose
    private String displayname;
    @Expose
    private String username;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate The creation_date
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return The displayname
     */
    public String getDisplayname() {
        return displayname;
    }

    /**
     * @param displayname The displayname
     */
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.email);
        dest.writeString(this.creationDate);
        dest.writeString(this.displayname);
        dest.writeString(this.username);
    }

    public Commisioner() {
    }

    private Commisioner(Parcel in) {
        this.userId = in.readString();
        this.email = in.readString();
        this.creationDate = in.readString();
        this.displayname = in.readString();
        this.username = in.readString();
    }

    public static final Creator<Commisioner> CREATOR = new Creator<Commisioner>() {
        public Commisioner createFromParcel(Parcel source) {
            return new Commisioner(source);
        }

        public Commisioner[] newArray(int size) {
            return new Commisioner[size];
        }
    };
}
