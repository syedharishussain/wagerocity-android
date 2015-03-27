package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @Expose
    private String username;
    @Expose
    private String email;
    @Expose
    private String language;
    @Expose
    private String timezone;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("facebook_uid")
    @Expose
    private String facebookUid;
    @Expose
    private Float score;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String zip;
    @Expose
    private String birthdate;
    @Expose
    private String state;
    @Expose
    private String overallrank;
    @Expose
    private Float credits;
    @Expose
    private Float currentrecord;

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
     * The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone
     * The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     *
     * @return
     * The creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     * The creation_date
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return
     * The facebookUid
     */
    public String getFacebookUid() {
        return facebookUid;
    }

    /**
     *
     * @param facebookUid
     * The facebook_uid
     */
    public void setFacebookUid(String facebookUid) {
        this.facebookUid = facebookUid;
    }

    /**
     *
     * @return
     * The score
     */
    public Float getScore() {
        return score;
    }

    /**
     *
     * @param score
     * The score
     */
    public void setScore(Float score) {
        this.score = score;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     * The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     *
     * @return
     * The birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     *
     * @param birthdate
     * The birthdate
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The overallrank
     */
    public String getOverallrank() {
        return overallrank;
    }

    /**
     *
     * @param overallrank
     * The overallrank
     */
    public void setOverallrank(String overallrank) {
        this.overallrank = overallrank;
    }

    /**
     *
     * @return
     * The credits
     */
    public Float getCredits() {
        return credits;
    }

    /**
     *
     * @param credits
     * The credits
     */
    public void setCredits(Float credits) {
        this.credits = credits;
    }

    /**
     *
     * @return
     * The currentrecord
     */
    public Float getCurrentrecord() {
        return currentrecord;
    }

    /**
     *
     * @param currentrecord
     * The currentrecord
     */
    public void setCurrentrecord(Float currentrecord) {
        this.currentrecord = currentrecord;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.language);
        dest.writeString(this.timezone);
        dest.writeString(this.creationDate);
        dest.writeString(this.facebookUid);
        dest.writeFloat(this.score);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.zip);
        dest.writeString(this.birthdate);
        dest.writeString(this.state);
        dest.writeString(this.overallrank);
        dest.writeFloat(this.credits);
        dest.writeFloat(this.currentrecord);
    }

    public User() {
    }

    private User(Parcel in) {
        this.userId = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.language = in.readString();
        this.timezone = in.readString();
        this.creationDate = in.readString();
        this.facebookUid = in.readString();
        this.score = in.readFloat();
        this.address = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.zip = in.readString();
        this.birthdate = in.readString();
        this.state = in.readString();
        this.overallrank = in.readString();
        this.credits = in.readFloat();
        this.currentrecord = in.readFloat();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
