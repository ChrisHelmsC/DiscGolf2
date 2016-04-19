package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12/17/2015.
 *
 * 4/3/2016: Removed player stats from hole class to better represent hole.
 *           Added arrayList of starting points so each hole has multiple starting points.
 *           Made Parcelable.
 */

public class Hole implements Parcelable {
    private int holeNumber;
    private int par;
    private List<HoleStartingPoint> startingPoints;

    public Hole(int hn, int par, List<HoleStartingPoint> hps)
    {
        this.holeNumber = hn;
        this.par = par;
        this.startingPoints = hps;
    }

    public List<HoleStartingPoint> getStartingPoints() {
        return startingPoints;
    }

    public void setStartingPoints(List<HoleStartingPoint> startingPoints) {
        this.startingPoints = startingPoints;
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

    protected Hole(Parcel in) {
        holeNumber = in.readInt();
        par = in.readInt();
        if (in.readByte() == 0x01) {
            startingPoints = new ArrayList<HoleStartingPoint>();
            in.readList(startingPoints, HoleStartingPoint.class.getClassLoader());
        } else {
            startingPoints = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(holeNumber);
        dest.writeInt(par);
        if (startingPoints == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(startingPoints);
        }
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

    //Returns names of all starting points in the startingPoints
    public List<String> getStartingPointNames()
    {
        List<String> spNames = new ArrayList<String>();

        for(HoleStartingPoint sp : startingPoints)
        {
            spNames.add(sp.getName());
        }

        return spNames;
    }
}