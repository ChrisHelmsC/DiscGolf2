package chris.discgolf;

/**
 * Created by Chris on 1/7/2016.
 */
public class Player
{
    String playerName;
    int totalPlayerScore, holePlayerScore;

    public Player(String g)
    {
        playerName = g;
        totalPlayerScore = 0;
        holePlayerScore = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public void setTotalPlayerScore(int totalPlayerScore) {
        this.totalPlayerScore = totalPlayerScore;
    }

    public int getHolePlayerScore() {
        return holePlayerScore;
    }

    public void setHolePlayerScore(int holePlayerScore) {
        this.holePlayerScore = holePlayerScore;
    }
}
