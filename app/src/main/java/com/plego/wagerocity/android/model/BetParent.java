package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BetParent implements Parcelable {

    @SerializedName("bet_parent")
    @Expose
    private String betParent;

    /**
     *
     * @return
     * The betParent
     */
    public String getBetParent() {
        return betParent;
    }

    /**
     *
     * @param betParent
     * The bet_parent
     */
    public void setBetParent(String betParent) {
        this.betParent = betParent;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.betParent);
    }

    public BetParent() {
    }

    private BetParent(Parcel in) {
        this.betParent = in.readString();
    }

    public static final Creator<BetParent> CREATOR = new Creator<BetParent>() {
        public BetParent createFromParcel(Parcel source) {
            return new BetParent(source);
        }

        public BetParent[] newArray(int size) {
            return new BetParent[size];
        }
    };
}
