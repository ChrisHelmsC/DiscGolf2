package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 12/17/2015.
 */
public class Hole implements Parcelable {
    private int holeNumber;
    private int par;
    private int length;

    //Player and public stats
    private int avgPar;
    private int playerAvg;
    private int playerBest;

    public Hole(int hn, int par, int ln)
    {
        this.holeNumber = hn;
        this.par = par;
        this.length = ln;
        this.avgPar = 0;
        this.playerAvg = 0;
        this.playerBest = 0;
    }

    public Hole(int hn, int par)
    {
        this.holeNumber = hn;
        this.par = par;
        this.length = 0;
        this.avgPar = 0;
        this.playerAvg = 0;
        this.playerBest = 0;
    }

    public Hole(int hn, int par, int ln, int aP, int pA, int pB)
    {
        this.holeNumber = hn;
        this.par = par;
        this.length = ln;
        this.avgPar = aP;
        this.playerAvg = pA;
        this.playerBest = pB;
    }

    public int getPlayerBest() {
        return playerBest;
    }

    public void setPlayerBest(int playerBest) {
        this.playerBest = playerBest;
    }

    public int getAvgPar() {
        return avgPar;
    }

    public void setAvgPar(int avgPar) {
        this.avgPar = avgPar;
    }

    public int getPlayerAvg() {
        return playerAvg;
    }

    public void setPlayerAvg(int playerAvg) {
        this.playerAvg = playerAvg;
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

    protected Hole(Parcel in) {
        holeNumber = in.readInt();
        par = in.readInt();
        length = in.readInt();
        avgPar = in.readInt();
        playerAvg = in.readInt();
        playerBest = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(holeNumber);
        dest.writeInt(par);
        dest.writeInt(length);
        dest.writeInt(avgPar);
        dest.writeInt(playerAvg);
        dest.writeInt(playerBest);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hole> CREATOR = new Parcelable.Creator<Hole>() {
        @Override
        public Hole createFromParcel(Parcel in) {
            return new Hole(in);
        }

        @Override
        public Hole[] newArray(int size) {
            return new Hole[size];
        }
    };
}