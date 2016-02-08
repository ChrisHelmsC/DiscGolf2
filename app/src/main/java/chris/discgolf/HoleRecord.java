package chris.discgolf;

import java.util.ArrayList;

/**
 * Created by Chris on 1/15/2016.
 */
public class HoleRecord
{
    ArrayList<Player> playerRecords;

    public HoleRecord()
    {
        playerRecords = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayerRecords() {
        return playerRecords;
    }

    public void setPlayerRecords(ArrayList<Player> playerRecords) {
        this.playerRecords = playerRecords;
    }

    public void addRecord(Player g)
    {
        Player newPlayer = new Player(g.getPlayerName(), g.getTotalPlayerScore(), g.getHolePlayerScore());
        playerRecords.add(newPlayer);
    }
}
