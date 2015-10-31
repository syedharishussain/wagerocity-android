package com.plego.wagerocity.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

public class Game implements Parcelable {

	public static final Creator<Game> CREATOR = new Creator<Game>() {
		public Game createFromParcel (Parcel source) {
			return new Game( source );
		}

		public Game[] newArray (int size) {
			return new Game[size];
		}
	};
	@SerializedName("cst_start_time")
    @Expose
    private String cstStartTime;
    @SerializedName("team_A_number")
    @Expose
    private String teamANumber;
    @SerializedName("team_B_number")
    @Expose
    private String teamBNumber;
    @Expose
    private List<String> sports = new ArrayList<String>();
    @SerializedName("team_A_Odds")
    @Expose
    private Odd teamAOdd;
    @SerializedName("team_B_Odds")
    @Expose
    private Odd teamBOdd;
    @SerializedName("team_A_name")
    @Expose
    private String teamAName;
    @SerializedName("team_B_name")
    @Expose
    private String teamBName;
    @SerializedName("team_A_fullname")
    @Expose
    private String teamAFullname;
    @SerializedName("team_B_fullname")
    @Expose
    private String teamBFullname;
    @SerializedName("team_A_nickname")
    @Expose
    private String teamANickname;
    @SerializedName("team_B_nickname")
    @Expose
    private String teamBNickname;
    @SerializedName("team_A_logo")
    @Expose
    private String teamALogo;
    @SerializedName("team_B_logo")
    @Expose
    private String teamBLogo;
    @Expose
    private String leagueName;
    @SerializedName("user_info")
    @Expose
    private User user;
    @SerializedName("bet_info")
    @Expose
    private Bet bet;
    @SerializedName("is_purchased")
    @Expose
    private Boolean isPurchased;
    @SerializedName("team_A_pitcher")
    @Expose
    private String teamAPitcher;
    @SerializedName("team_B_pitcher")
    @Expose
    private String teamBPitcher;
    private Double poolCredits;
	private ArrayList<OddHolder> oddHolders;
	private String sportsName = "";

	public Game () {
	}

	private Game (Parcel in) {
		this.cstStartTime = in.readString();
		this.teamANumber = in.readString();
		this.teamBNumber = in.readString();
		this.sports = new ArrayList<String>();
		in.readList( this.sports, List.class.getClassLoader() );
		this.oddHolders = new ArrayList<OddHolder>();
		in.readList( this.oddHolders, List.class.getClassLoader() );
		this.teamAOdd = in.readParcelable( Odd.class.getClassLoader() );
		this.teamBOdd = in.readParcelable( Odd.class.getClassLoader() );
		this.user = in.readParcelable( User.class.getClassLoader() );
		this.bet = in.readParcelable( Bet.class.getClassLoader() );
		this.teamAName = in.readString();
		this.teamBName = in.readString();
		this.teamAFullname = in.readString();
		this.teamBFullname = in.readString();
		this.teamANickname = in.readString();
		this.teamBNickname = in.readString();
		this.teamALogo = in.readString();
		this.teamBLogo = in.readString();
		this.sportsName = in.readString();
		this.leagueName = in.readString();
		this.teamAPitcher = in.readString();
		this.teamBPitcher = in.readString();
		this.poolCredits = (Double) in.readValue( Double.class.getClassLoader() );
		this.oddHolders = new ArrayList<OddHolder>();
	}

    public Double getPoolCredits() {
        return poolCredits;
    }

    public void setPoolCredits(Double poolCredits) {
        this.poolCredits = poolCredits;
    }

    public String getTeamAPitcher() {
        return teamAPitcher;
    }

    public void setTeamAPitcher(String teamAPitcher) {
        this.teamAPitcher = teamAPitcher;
    }

    public String getTeamBPitcher() {
        return teamBPitcher;
    }

    public void setTeamBPitcher(String teamBPitcher) {
        this.teamBPitcher = teamBPitcher;
    }

    public ArrayList<OddHolder> getOddHolders() {
        return oddHolders;
    }

    public void setOddHolders(ArrayList<OddHolder> oddHolders) {
        this.oddHolders = oddHolders;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    /**
     *
     * @return
     * The cstStartTime
     */
    public String getCstStartTime(){
        return cstStartTime;
    }

    /**
     *
     * @param cstStartTime
     * The cst_start_time
     */
    public void setCstStartTime(String cstStartTime) {
        this.cstStartTime = cstStartTime;
    }

    /**
     *
     * @return
     * The teamANumber
     */
    public String getTeamANumber() {
        return teamANumber;
    }

    /**
     *
     * @param teamANumber
     * The team_A_number
     */
    public void setTeamANumber(String teamANumber) {
        this.teamANumber = teamANumber;
    }

    /**
     *
     * @return
     * The teamBNumber
     */
    public String getTeamBNumber() {
        return teamBNumber;
    }

    /**
     *
     * @param teamBNumber
     * The team_B_number
     */
    public void setTeamBNumber(String teamBNumber) {
        this.teamBNumber = teamBNumber;
    }

    /**
     *
     * @return
     * The sports
     */
    public List<String> getSports() {
        return sports;
    }

    /**
     *
     * @param sports
     * The sports
     */
    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    /**
     *
     * @return
     * The teamAOdds
     */
    public Odd getTeamAOdd() {
        return teamAOdd;
    }

    /**
     *
     * @param teamAOdd
     * The team_A_Odds
     */
    public void setTeamAOdd(Odd teamAOdd) {
        this.teamAOdd = teamAOdd;
    }

	public Odd getProxyTeamAOdd () {
		return teamAOdd == null ? new Odd() : teamAOdd;
	}

    /**
     *
     * @return
     * The teamBOdds
     */
    public Odd getTeamBOdd() {
        return teamBOdd;
    }

    /**
     *
     * @param teamBOdd
     * The team_B_Odds
     */
    public void setTeamBOdd(Odd teamBOdd) {
        this.teamBOdd = teamBOdd;
    }

	public Odd getProxyTeamBOdd () {
		return teamBOdd == null ? new Odd() : teamBOdd;
	}

    /**
     *
     * @return
     * The teamAName
     */
    public String getTeamAName() {
        return teamAName;
    }

    /**
     *
     * @param teamAName
     * The team_A_name
     */
    public void setTeamAName(String teamAName) {
        this.teamAName = teamAName;
    }

	public String getTeamADisplayName () {
		return AndroidUtils.isEmpty( teamAName ) ? teamAFullname : teamAName;
	}

    /**
     *
     * @return
     * The teamBName
     */
    public String getTeamBName() {
        return teamBName;
    }

    /**
     *
     * @param teamBName
     * The team_B_name
     */
    public void setTeamBName(String teamBName) {
        this.teamBName = teamBName;
    }

	public String getTeamBDisplayName () {
		return AndroidUtils.isEmpty( teamBName ) ? teamBFullname : teamBName;
	}

    /**
     *
     * @return
     * The teamAFullname
     */
    public String getTeamAFullname() {
        return teamAFullname.equals("") ? getTeamAName() : teamAFullname;
    }

    /**
     *
     * @param teamAFullname
     * The team_A_fullname
     */
    public void setTeamAFullname(String teamAFullname) {
        this.teamAFullname = teamAFullname;
    }

    /**
     *
     * @return
     * The teamBFullname
     */
    public String getTeamBFullname() {
        return teamBFullname.equals("") ? getTeamBName() : teamBFullname;
    }

    /**
     *
     * @param teamBFullname
     * The team_B_fullname
     */
    public void setTeamBFullname(String teamBFullname) {
        this.teamBFullname = teamBFullname;
    }

    /**
     *
     * @return
     * The teamANickname
     */
    public String getTeamANickname() {
        return teamANickname;
    }

    /**
     *
     * @param teamANickname
     * The team_A_nickname
     */
    public void setTeamANickname(String teamANickname) {
        this.teamANickname = teamANickname;
    }

    /**
     *
     * @return
     * The teamBNickname
     */
    public String getTeamBNickname() {
        return teamBNickname;
    }

    /**
     *
     * @param teamBNickname
     * The team_B_nickname
     */
    public void setTeamBNickname(String teamBNickname) {
        this.teamBNickname = teamBNickname;
    }

    /**
     *
     * @return
     * The teamALogo
     */
    public String getTeamALogo() {
        return teamALogo;
    }

    /**
     *
     * @param teamALogo
     * The team_A_logo
     */
    public void setTeamALogo(String teamALogo) {
        this.teamALogo = teamALogo;
    }

    /**
     *
     * @return
     * The teamBLogo
     */
    public String getTeamBLogo() {
        return teamBLogo;
    }

    /**
     *
     * @param teamBLogo
     * The team_B_logo
     */
    public void setTeamBLogo(String teamBLogo) {
        this.teamBLogo = teamBLogo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

	@Override
	public void writeToParcel (Parcel dest, int flags) {
		dest.writeString( this.cstStartTime );
		dest.writeString( this.teamANumber );
		dest.writeString( this.teamBNumber );
		dest.writeList( this.sports );
		dest.writeList( this.oddHolders );
		dest.writeParcelable( this.teamAOdd, 0 );
		dest.writeParcelable( this.teamBOdd, 0 );
		dest.writeParcelable( this.user, 0 );
		dest.writeParcelable( this.bet, 0 );
		dest.writeString( this.teamAName );
		dest.writeString( this.teamBName );
		dest.writeString( this.teamAFullname );
		dest.writeString( this.teamBFullname );
		dest.writeString( this.teamANickname );
		dest.writeString( this.teamBNickname );
		dest.writeString( this.teamALogo );
		dest.writeString( this.teamBLogo );
		dest.writeString( this.sportsName );
		dest.writeString( this.leagueName );
		dest.writeList( this.oddHolders );
		dest.writeString( this.teamAPitcher );
		dest.writeString( this.teamBPitcher );
		dest.writeValue( this.poolCredits );
	}

    public String getTeamNameFromTeamNumber (String teamNumber) {
        return (getTeamANumber().equals(teamNumber)) ? getTeamAName() : getTeamBName();
    }
}

