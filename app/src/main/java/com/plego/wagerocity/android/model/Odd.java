package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Odd implements Parcelable {

    @Expose
    private String id;
    @SerializedName("sportsbook_id")
    @Expose
    private String sportsbookId;
    @SerializedName("team_number")
    @Expose
    private String teamNumber;
    @SerializedName("line_type")
    @Expose
    private String lineType;
    @Expose
    private Integer points;
    @SerializedName("time_stamp")
    @Expose
    private String timeStamp;
    @SerializedName("event_number")
    @Expose
    private String eventNumber;
    @Expose
    private Integer money;
    @Expose
    private String ML;
    @SerializedName("over_money")
    @Expose
    private Integer overMoney;
    @SerializedName("under_money")
    @Expose
    private Integer underMoney;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The sportsbookId
     */
    public String getSportsbookId() {
        return sportsbookId;
    }

    /**
     *
     * @param sportsbookId
     * The sportsbook_id
     */
    public void setSportsbookId(String sportsbookId) {
        this.sportsbookId = sportsbookId;
    }

    /**
     *
     * @return
     * The teamNumber
     */
    public String getTeamNumber() {
        return teamNumber;
    }

    /**
     *
     * @param teamNumber
     * The team_number
     */
    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    /**
     *
     * @return
     * The lineType
     */
    public String getLineType() {
        return lineType;
    }

    /**
     *
     * @param lineType
     * The line_type
     */
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    /**
     *
     * @return
     * The points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     *
     * @param points
     * The points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     *
     * @return
     * The timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     *
     * @param timeStamp
     * The time_stamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     *
     * @return
     * The eventNumber
     */
    public String getEventNumber() {
        return eventNumber;
    }

    /**
     *
     * @param eventNumber
     * The event_number
     */
    public void setEventNumber(String eventNumber) {
        this.eventNumber = eventNumber;
    }

    /**
     *
     * @return
     * The money
     */
    public Integer getMoney() {
        return money;
    }

    /**
     *
     * @param money
     * The money
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     *
     * @return
     * The ML
     */
    public String getML() {
        return ML;
    }

    /**
     *
     * @param ML
     * The ML
     */
    public void setML(String ML) {
        this.ML = ML;
    }

    /**
     *
     * @return
     * The overMoney
     */
    public Integer getOverMoney() {
        return overMoney;
    }

    /**
     *
     * @param overMoney
     * The over_money
     */
    public void setOverMoney(Integer overMoney) {
        this.overMoney = overMoney;
    }

    /**
     *
     * @return
     * The underMoney
     */
    public Integer getUnderMoney() {
        return underMoney;
    }

    /**
     *
     * @param underMoney
     * The under_money
     */
    public void setUnderMoney(Integer underMoney) {
        this.underMoney = underMoney;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sportsbookId);
        dest.writeString(this.teamNumber);
        dest.writeString(this.lineType);
        dest.writeValue(this.points);
        dest.writeString(this.timeStamp);
        dest.writeString(this.eventNumber);
        dest.writeValue(this.money);
        dest.writeString(this.ML);
        dest.writeValue(this.overMoney);
        dest.writeValue(this.underMoney);
    }

    public Odd() {
    }

    private Odd(Parcel in) {
        this.id = in.readString();
        this.sportsbookId = in.readString();
        this.teamNumber = in.readString();
        this.lineType = in.readString();
        this.points = (Integer) in.readValue(Integer.class.getClassLoader());
        this.timeStamp = in.readString();
        this.eventNumber = in.readString();
        this.money = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ML = in.readString();
        this.overMoney = (Integer) in.readValue(Integer.class.getClassLoader());
        this.underMoney = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Odd> CREATOR = new Creator<Odd>() {
        public Odd createFromParcel(Parcel source) {
            return new Odd(source);
        }

        public Odd[] newArray(int size) {
            return new Odd[size];
        }
    };
}
