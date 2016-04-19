package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 4/3/2016.
 */
public class Player implements Parcelable {
    private int id;
    private String firstName;
    private String lastName;
    private int timesPlayed;

    public Player(int id, String f, String n, int tp)
    {
        this.timesPlayed = id;
        this.firstName = f;
        this.lastName = n;
        this.timesPlayed = tp;
    }

    public Player(String f, String n)
    {
        this.firstName = f;
        this.lastName = n;
        this.timesPlayed = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    protected Player(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        timesPlayed = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(timesPlayed);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}