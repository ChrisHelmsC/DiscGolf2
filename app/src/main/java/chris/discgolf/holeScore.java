package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 4/24/2016.
 */
public class HoleScore implements Parcelable {
    Player player;      //Player who got the score
    int score;          //Score player got on hole
    int holeNumber;     //Hole number score was on
    String holeName;    //name of tee on hole

    public HoleScore()
    {

    }

    public HoleScore(Player p, int s, int hN, String name)
    {
        player = p;
        score = s;
        holeNumber = hN;
        holeName = name;
    }

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    protected HoleScore(Parcel in) {
        player = (Player) in.readValue(Player.class.getClassLoader());
        score = in.readInt();
        holeNumber = in.readInt();
        holeName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(player);
        dest.writeInt(score);
        dest.writeInt(holeNumber);
        dest.writeString(holeName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HoleScore> CREATOR = new Parcelable.Creator<HoleScore>() {
        @Override
        public HoleScore createFromParcel(Parcel in) {
            return new HoleScore(in);
        }

        @Override
        public HoleScore[] newArray(int size) {
            return new HoleScore[size];
        }
    };
}