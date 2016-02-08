package chris.discgolf;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12/17/2015.
 */
public class Course implements Parcelable {
    private String courseName;
    private int numberOfHoles;
    private int playerBest;
    private int playerAverage;
    private int courseAverage;
    private String city;
    private String state;
    List<Hole> holeList;

    public Course(String name, int nH, int pB, int pA, int cA, String c, String s, List<Hole> holes)
    {
        this.courseName = name;
        this.numberOfHoles = nH;
        this.playerBest = pB;
        this.playerAverage = pA;
        this.holeList = holes;
        this.courseAverage = cA;
        this.state = s;
        this.city = c;
    }

    public Course(String name, int nH, int pB, int pA, int cA, String c, String s)
    {
        this.courseName = name;
        this.numberOfHoles = nH;
        this.playerBest = pB;
        this.playerAverage = pA;
        this.courseAverage = cA;
        holeList = new ArrayList<Hole>();
        this.state = s;
        this.city = c;
    }

    public Course(String name, String c, String s, int nH)
    {
        this.courseName = name;
        this.numberOfHoles = nH;
        this.state = s;
        this.city = c;
        holeList = new ArrayList<Hole>();
        this.playerBest = -1;
        this.playerAverage = -1;
        this.courseAverage = -1;
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

    public int getCourseAverage() {
        return courseAverage;
    }

    public void setCourseAverage(int courseAverage) {
        this.courseAverage = courseAverage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getNumberOfHoles() {
        return numberOfHoles;
    }

    public void setNumberOfHoles(int numberOfHoles) {
        this.numberOfHoles = numberOfHoles;
    }

    public int getPlayerBest() {
        return playerBest;
    }

    public void setPlayerBest(int playerBest) {
        this.playerBest = playerBest;
    }

    public int getPlayerAverage() {
        return playerAverage;
    }

    public void setPlayerAverage(int playerAverage) {
        this.playerAverage = playerAverage;
    }

    public List<Hole> getHoleList() {
        return holeList;
    }

    public void setHoleList(List<Hole> holeList) {
        this.holeList = holeList;
    }

    protected Course(Parcel in) {
        courseName = in.readString();
        numberOfHoles = in.readInt();
        playerBest = in.readInt();
        playerAverage = in.readInt();
        courseAverage = in.readInt();
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
        dest.writeString(courseName);
        dest.writeInt(numberOfHoles);
        dest.writeInt(playerBest);
        dest.writeInt(playerAverage);
        dest.writeInt(courseAverage);
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
}