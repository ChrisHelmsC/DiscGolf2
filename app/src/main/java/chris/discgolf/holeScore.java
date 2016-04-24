package chris.discgolf;

/**
 * Created by Chris on 4/24/2016.
 */
public class holeScore
{
    Player player;      //Player who got the score
    int score;          //Score player got on hole
    int holeNumber;     //Hole number score was on
    String holeName;    //name of tee on hole

    public holeScore(Player p, int s, int hNum, String hName)
    {
        player = p;
        score = s;
        holeNumber = hNum;
        holeName = hName;
    }

    public String getHoleName() {
        return holeName;
    }

    public void setHoleName(String holeName) {
        this.holeName = holeName;
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

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }
}
