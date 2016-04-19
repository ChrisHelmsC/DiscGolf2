
package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 4/3/2016.
 */

public class HoleStartingPoint implements Parcelable {
    private String name;    //Represents name of point, ex: blue;
    private int length;     //Represents length to hole

    public HoleStartingPoint(String n, int l)
    {
        this.name = n;
        this.length = l;
    }

    protected HoleStartingPoint(Parcel in) {
        name = in.readString();
        length = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(length);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HoleStartingPoint> CREATOR = new Parcelable.Creator<HoleStartingPoint>() {
        @Override
        public HoleStartingPoint createFromParcel(Parcel in) {
            return new HoleStartingPoint(in);
        }

        @Override
        public HoleStartingPoint[] newArray(int size) {
            return new HoleStartingPoint[size];
        }
    };
}