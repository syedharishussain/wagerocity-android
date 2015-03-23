package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardPlayer implements Parcelable{

    @SerializedName("usr_id")
    @Expose
    private String usrId;
    @Expose
    private String displayname;
    @Expose
    private String email;
    @Expose
    private String username;
    @SerializedName("rak_current_record")
    @Expose
    private String rakCurrentRecord;

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
     * The rakCurrentRecord
     */
    public String getRakCurrentRecord() {
        return rakCurrentRecord;
    }

    /**
     *
     * @param rakCurrentRecord
     * The rak_current_record
     */
    public void setRakCurrentRecord(String rakCurrentRecord) {
        this.rakCurrentRecord = rakCurrentRecord;
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
        dest.writeString(this.rakCurrentRecord);
    }

    public LeaderboardPlayer() {
    }

    private LeaderboardPlayer(Parcel in) {
        this.usrId = in.readString();
        this.displayname = in.readString();
        this.email = in.readString();
        this.username = in.readString();
        this.rakCurrentRecord = in.readString();
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
