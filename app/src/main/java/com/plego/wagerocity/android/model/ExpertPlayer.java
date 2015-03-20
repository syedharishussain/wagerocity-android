package com.plego.wagerocity.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertPlayer {

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

}
