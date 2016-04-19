package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12/17/2015.
 *
 * 4/3/2016: Removed playerBest, playerAverage, courseAverage integers to better represent course
 *
 * 4/6/2016: Added course id for db functionality
 */

public class Course implements Parcelable {
    private int id;
    private String courseName;
    private String city;
    private String state;
    List<Hole> holeList;

    public Course(int i, String name, String c, String s, List<Hole> holes)
    {
        this.id = i;
        this.courseName = name;
        this.holeList = holes;
        this.state = s;
        this.city = c;
    }

    public Course(String name, String c, String s)
    {
        this.courseName = name;
        this.state = s;
        this.city = c;
        holeList = new ArrayList<Hole>();
    }

    public Course(int i, String name, String c, String s)
    {
        this.id = i;
        this.courseName = name;
        this.state = s;
        this.city = c;
        holeList = new ArrayList<Hole>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        state = state;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Hole> getHoleList() {
        return holeList;
    }

    public void setHoleList(List<Hole> holeList) {
        this.holeList = holeList;
    }

    protected Course(Parcel in) {
        id = in.readInt();
        courseName = in.readString();
        city = in.readString();
        state = in.readString();
        if (in.readByte() == 0x01) {
            holeList = new ArrayList<Hole>();
            in.readList(holeList, Hole.class.getClassLoader());
        } else {
            holeList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(courseName);
        dest.writeString(city);
        dest.writeString(state);
        if (holeList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(holeList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    //Returns the hole after h
    public Hole getNextHole(Hole h)
    {
        //Get index of h
        int index = this.holeList.indexOf(h);

        //Return next hole
        return this.holeList.get(index+1);
    }
}