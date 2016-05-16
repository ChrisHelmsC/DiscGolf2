package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chris on 4/26/2016.
 */
public class CourseScore implements Parcelable {
    Player player;
    int score;
    Course course;
    String gameTee;

    private String defaultTeeName = "White";

    public CourseScore(Player p, Course c)
    {
        player = p;
        course = c;
        score = 0;
        gameTee = defaultTeeName;
    }

    public CourseScore(Player p, Course c, int s)
    {
        player = p;
        course = c;
        score = s;
        gameTee = defaultTeeName;
    }

    public String getGameTee() {
        return gameTee;
    }

    public void setGameTee(String gameTee) {
        this.gameTee = gameTee;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int incrementScore()
    {
        this.score++;
        return this.score;
    }

    public int decrementScore()
    {
        this.score--;
        return this.score;
    }

    protected CourseScore(Parcel in) {
        player = (Player) in.readValue(Player.class.getClassLoader());
        score = in.readInt();
        course = (Course) in.readValue(Course.class.getClassLoader());
        gameTee = in.readString();
        defaultTeeName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(player);
        dest.writeInt(score);
        dest.writeValue(course);
        dest.writeString(gameTee);
        dest.writeString(defaultTeeName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CourseScore> CREATOR = new Parcelable.Creator<CourseScore>() {
        @Override
        public CourseScore createFromParcel(Parcel in) {
            return new CourseScore(in);
        }

        @Override
        public CourseScore[] newArray(int size) {
            return new CourseScore[size];
        }
    };
}