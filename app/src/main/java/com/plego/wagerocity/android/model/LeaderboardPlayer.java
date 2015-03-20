package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardPlayer {

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

}
