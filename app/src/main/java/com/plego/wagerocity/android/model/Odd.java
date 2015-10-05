package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plego.wagerocity.utils.AndroidUtils;

import static com.plego.wagerocity.utils.AndroidUtils.getSignedOddValue;
import static com.plego.wagerocity.utils.AndroidUtils.isEmpty;

public class Odd implements Parcelable {

    @Expose
    private String id;
    @SerializedName("point_id")
    @Expose
    private String pointId;
    @Expose
    private String point;
    @SerializedName("point_mid")
    @Expose
    private String pointMid;
    @SerializedName("total_id")
    @Expose
    private String totalId;
    @Expose
    private String over;
    @Expose
    private String under;
    @SerializedName("total_mid")
    @Expose
    private String totalMid;
    @SerializedName("money_id")
    @Expose
    private String moneyId;
    @Expose
    private String money;

    public String getPointSpreadString() {
		if(!hasPointSpread()) return "-";

		String pointValue = " (" + AndroidUtils.getSignedOddValue( point ) + ")";

		return isEmpty( pointMid ) ? pointValue : AndroidUtils.getSignedOddValue( pointMid ) + pointValue;
	}

	public boolean hasPointSpread () {
		return !isEmpty( point );
	}


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

    public Odd withId(String id) {
        this.id = id;
        return this;
    }

    /**
     *
     * @return
     * The pointId
     */
    public String getPointId() {
        return pointId;
    }

    /**
     *
     * @param pointId
     * The point_id
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Odd withPointId(String pointId) {
        this.pointId = pointId;
        return this;
    }

    /**
     *
     * @return
     * The point
     */
    public String getPoint() {
        return point;
    }

    /**
     *
     * @param point
     * The point
     */
    public void setPoint(String point) {
        this.point = point;
    }

    public Odd withPoint(String point) {
        this.point = point;
        return this;
    }

    /**
     *
     * @return
     * The pointMid
     */
    public String getPointMid() {
        return ( pointMid == null ) ? "" : pointMid;
    }

    /**
     *
     * @param pointMid
     * The point_mid
     */
    public void setPointMid(String pointMid) {
        this.pointMid = pointMid;
    }

    public Odd withPointMid(String pointMid) {
        this.pointMid = pointMid;
        return this;
    }

    /**
     *
     * @return
     * The totalId
     */
    public String getTotalId() {
        return totalId;
    }

    /**
     *
     * @param totalId
     * The total_id
     */
    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public Odd withTotalId(String totalId) {
        this.totalId = totalId;
        return this;
    }

    /**
     *
     * @return
     * The over
     */
    public String getOver() {
        return over;
    }

	public String getOverString () {
		return isEmpty( over ) ? "-" : getSignedOddValue( over );
	}

	public boolean hasOver () {
		return !isEmpty( over );
	}

    /**
     *
     * @param over
     * The over
     */
    public void setOver(String over) {
        this.over = over;
    }

    public Odd withOver(String over) {
        this.over = over;
        return this;
    }

    /**
     *
     * @return
     * The under
     */
    public String getUnder() {
        return under;
    }

	public String getUnderString () {
		return isEmpty( under ) ? "-" : getSignedOddValue( under );
	}

	public boolean hasUnder () {
		return !isEmpty( under );
	}

    /**
     *
     * @param under
     * The under
     */
    public void setUnder(String under) {
        this.under = under;
    }

    public Odd withUnder(String under) {
        this.under = under;
        return this;
    }

    /**
     *
     * @return
     * The totalMid
     */
    public String getTotalMid() {
        return totalMid;
    }

	public String getTotalMidString () {
		return isEmpty( totalMid ) ? "-" : getSignedOddValue( totalMid );
	}

    /**
     *
     * @param totalMid
     * The total_mid
     */
    public void setTotalMid(String totalMid) {
        this.totalMid = totalMid;
    }

    public Odd withTotalMid(String totalMid) {
        this.totalMid = totalMid;
        return this;
    }

    /**
     *
     * @return
     * The moneyId
     */
    public String getMoneyId() {
        return moneyId;
    }

    /**
     *
     * @param moneyId
     * The money_id
     */
    public void setMoneyId(String moneyId) {
        this.moneyId = moneyId;
    }

    public Odd withMoneyId(String moneyId) {
        this.moneyId = moneyId;
        return this;
    }

    /**
     *
     * @return
     * The money
     */
    public String getMoney() {
        return money;
    }

	public String getMoneyLineString () {
		return isEmpty( money ) ? "-" : getSignedOddValue( money );
	}

	public boolean hasMoney () {
		return !isEmpty( money );
	}

    /**
     *
     * @param money
     * The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    public Odd withMoney(String money) {
        this.money = money;
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pointId);
        dest.writeString(this.point);
        dest.writeString(this.pointMid);
        dest.writeString(this.totalId);
        dest.writeString(this.over);
        dest.writeString(this.under);
        dest.writeString(this.totalMid);
        dest.writeString(this.moneyId);
        dest.writeString(this.money);
    }

    public Odd() {
    }

    private Odd(Parcel in) {
        this.id = in.readString();
        this.pointId = in.readString();
        this.point = in.readString();
        this.pointMid = in.readString();
        this.totalId = in.readString();
        this.over = in.readString();
        this.under = in.readString();
        this.totalMid = in.readString();
        this.moneyId = in.readString();
        this.money = in.readString();
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
