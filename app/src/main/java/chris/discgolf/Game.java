package chris.discgolf;

import java.util.ArrayList;

/**
 * Created by Chris on 1/15/2016.
 */
public class Game
{   private String courseName;
    private ArrayList<Hole> holes;
    private ArrayList<Player> playerList;
    private ArrayList<HoleRecord> holeRecords;

    public Game(String name, ArrayList<Hole> holeList, ArrayList<String> players)
    {
        courseName = name;
        this.holes = holeList;
        initializePlayers(players);
        holeRecords = new ArrayList<HoleRecord>();
        initializeHoleRecords();
    }

    //====================================================
    //Adds number of hole records equal to the number of
    //holes in holeList. Allows for correct scorecard
    //functioning.
    //====================================================
    private void initializeHoleRecords()
    {
        int size = this.holes.size();

        for(int i = 0; i < size; i++)
        {
            holeRecords.add(new HoleRecord());
        }
    }

    //====================================================
    //Creates Player list using a list of player names for
    //each Player name. Other player values are initialized
    //to 0.
    //====================================================
    private void initializePlayers(ArrayList<String> playNames)
    {
        int numberOfPlayers = playNames.size();

        for(int i = 0; i < numberOfPlayers; i++)
        {
            this.playerList.add(new Player(playNames.get(i)));
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<HoleRecord> getHoleRecords() {
        return holeRecords;
    }

    public void setHoleRecords(ArrayList<HoleRecord> holeRecords) {
        this.holeRecords = holeRecords;
    }
}
