package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 12/17/2015.
 */
public class Hole implements Parcelable
{
    private int holeNumber;
    private int par;
    private int length;

    public Hole(int hn, int par, int ln)
    {
        this.holeNumber = hn;
        this.par = par;
        this.length = ln;
    }

    public Hole(int hn, int par)
    {
        this.holeNumber = hn;
        this.par = par;
        this.length = 0;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int describeContents() {return 0;}

    public Hole(Parcel in)
    {
        String[] data = new String[3];
        in.readStringArray(data);

        this.holeNumber = in.readInt();
        this.par = in.readInt();
        this.length = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] {Integer.toString(this.holeNumber), Integer.toString(this.par), Integer.toString(this.length)});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Hole createFromParcel(Parcel in) {return new Hole(in);}
        public Hole[] newArray(int size) {return new Hole[size];}
    };
}
