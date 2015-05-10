package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OddHolder implements Parcelable {

    public OddHolder(Double stake, String teamId, String oddId, String teamName, String teamVsteam, String oddValue, String betTypeSPT, String betOT, String betTypeString, String pointSpreadString) {
        this.stake = stake;
        this.teamId = teamId;
        this.oddId = oddId;
        this.teamName = teamName;
        this.teamVsteam = teamVsteam;
        this.oddValue = oddValue;
        this.pointSpreadString = pointSpreadString;
        this.betTypeSPT = betTypeSPT;
        this.betOT = betOT;
        this.betTypeString = betTypeString;
    }

    Double stake;
    String teamId;
    String oddId;
    String teamName;
    String teamVsteam;
    String oddValue;
    String betTypeSPT;
    String betOT;
    String betTypeString;
    String pointSpreadString;

    public String getPointSpreadString() {
        return pointSpreadString;
    }

    public void setPointSpreadString(String pointSpreadString) {
        this.pointSpreadString = pointSpreadString;
    }


    public String getBetTypeString() {
        return betTypeString;
    }

    public void setBetTypeString(String betTypeString) {
        this.betTypeString = betTypeString;
    }


    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getOddId() {
        return oddId;
    }

    public void setOddId(String oddId) {
        this.oddId = oddId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamVsteam() {
        return teamVsteam;
    }

    public void setTeamVsteam(String teamVsteam) {
        this.teamVsteam = teamVsteam;
    }

    public String getOddValue() {
        return oddValue;
    }

    public void setOddValue(String oddValue) {
        this.oddValue = oddValue;
    }

    public String getBetTypeSPT() {
        return betTypeSPT;
    }

    public void setBetTypeSPT(String betTypeSPT) {
        this.betTypeSPT = betTypeSPT;
    }

    public String getBetOT() {
        return betOT;
    }

    public void setBetOT(String betOT) {
        this.betOT = betOT;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.stake);
        dest.writeString(this.teamId);
        dest.writeString(this.oddId);
        dest.writeString(this.teamName);
        dest.writeString(this.teamVsteam);
        dest.writeString(this.oddValue);
        dest.writeString(this.betTypeSPT);
        dest.writeString(this.betOT);
    }

    public OddHolder() {
    }

    private OddHolder(Parcel in) {
        this.stake = (Double) in.readValue(Double.class.getClassLoader());
        this.teamId = in.readString();
        this.oddId = in.readString();
        this.teamName = in.readString();
        this.teamVsteam = in.readString();
        this.oddValue = in.readString();
        this.betTypeSPT = in.readString();
        this.betOT = in.readString();
    }

    public static final Creator<OddHolder> CREATOR = new Creator<OddHolder>() {
        public OddHolder createFromParcel(Parcel source) {
            return new OddHolder(source);
        }

        public OddHolder[] newArray(int size) {
            return new OddHolder[size];
        }
    };
}
