package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12/17/2015.
 */
public class Course implements Parcelable
{
    private String courseName;
    private int numberOfHoles;
    private int playerBest;
    private int playerAverage;
    private int courseAverage;
    String city, state;
    List<Hole> holeList;

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

    //Parcelable Stuff
    public int describeContents()
    {
        return 0;
    }

    public Course(Parcel in)
    {
        String[] data = new String[7];
        in.readStringArray(data);

        this.courseName = in.readString();
        this.numberOfHoles = in.readInt();
        this.playerBest = in.readInt();
        this.playerAverage = in.readInt();
        this.courseAverage = in.readInt();
        this.city = in.readString();
        this.state = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[] {this.courseName, Integer.toString(this.numberOfHoles), Integer.toString(this.playerBest), Integer.toString(this.playerAverage), Integer.toString(this.courseAverage), this.city, this.state});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Course createFromParcel(Parcel in) {return new Course(in);}
        public Course[] newArray(int size) {return new Course[size];}
    };
}
