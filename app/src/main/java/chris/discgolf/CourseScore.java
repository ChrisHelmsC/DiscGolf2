package chris.discgolf;

/**
 * Created by Chris on 4/26/2016.
 */
public class CourseScore
{
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
}
