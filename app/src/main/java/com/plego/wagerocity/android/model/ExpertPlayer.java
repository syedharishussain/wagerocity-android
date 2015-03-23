package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertPlayer implements Parcelable {

    @Expose
    private String displayname;
    @SerializedName("creation_ip")
    @Expose
    private String creationIp;
    @Expose
    private String username;

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
     * The creationIp
     */
    public String getCreationIp() {
        return creationIp;
    }

    /**
     *
     * @param creationIp
     * The creation_ip
     */
    public void setCreationIp(String creationIp) {
        this.creationIp = creationIp;
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



//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(displayname);
//        dest.writeString(username);
//        dest.writeString(creationIp);
//    }
//
//    public static final Creator<ExpertPlayer> CREATOR = new Creator<ExpertPlayer>() {
//        @Override
//        public ExpertPlayer createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public ExpertPlayer[] newArray(int size) {
//            return new ExpertPlayer[0];
//        }
//    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayname);
        dest.writeString(this.creationIp);
        dest.writeString(this.username);
    }

    public ExpertPlayer() {
    }

    private ExpertPlayer(Parcel in) {
        this.displayname = in.readString();
        this.creationIp = in.readString();
        this.username = in.readString();
    }

    public static final Creator<ExpertPlayer> CREATOR = new Creator<ExpertPlayer>() {
        public ExpertPlayer createFromParcel(Parcel source) {
            return new ExpertPlayer(source);
        }

        public ExpertPlayer[] newArray(int size) {
            return new ExpertPlayer[size];
        }
    };
}
