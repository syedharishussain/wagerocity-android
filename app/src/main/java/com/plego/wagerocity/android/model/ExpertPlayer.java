package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertPlayer implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String description;
    @SerializedName("is_follow")
    @Expose
    private Boolean isFollow;
    @Expose
    private String status;
    @Expose
    private String displayname;
    @Expose
    private String username;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

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
     * The description
     */
    public String getDescription() {
        if (description == null) return "";
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The isFollow
     */
    public Boolean getIsFollow() {
        return isFollow;
    }

    /**
     *
     * @param isFollow
     * The is_follow
     */
    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
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
     * The displayname
     */
    public String getDisplayname() {
        if (displayname == null && username == null) return "";
        else if (displayname == null && username != null) return username;
        else return displayname;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.description);
        dest.writeValue(this.isFollow);
        dest.writeString(this.status);
        dest.writeString(this.displayname);
        dest.writeString(this.username);
        dest.writeString(this.imageUrl);
    }

    public ExpertPlayer() {
    }

    private ExpertPlayer(Parcel in) {
        this.userId = in.readString();
        this.description = in.readString();
        this.isFollow = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.status = in.readString();
        this.displayname = in.readString();
        this.username = in.readString();
        this.imageUrl = in.readString();
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
