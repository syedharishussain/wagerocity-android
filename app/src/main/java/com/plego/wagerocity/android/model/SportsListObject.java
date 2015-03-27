package com.plego.wagerocity.android.model;

public class SportsListObject {
    public String getSportsName() {
        return sportsName;
    }

    public int getSportsImageId() {
        return sportsImageId;
    }

    String sportsName;
    int sportsImageId;

    public SportsListObject (String sportsName, int sportsImage) {
        this.sportsName = sportsName;
        this.sportsImageId = sportsImage;
    }
}
