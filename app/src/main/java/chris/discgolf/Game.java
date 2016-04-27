package chris.discgolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/24/2016.
 *
 * Contains all information used in a game.
 */
public class Game
{
    private int ID;                     //Game ID
    private Course course;              //Course
    private PlayerList playerList;      //List of players in a game
    private List <List<HoleScore>> HSLList;     //List of holescore lists
    private List <CourseScore> courseScoreList; //List of player course scores

    public static int NO_RESULT_FOUND = -99;

    public Game(Course c, PlayerList pL)
    {
        course = c;
        playerList = pL;
        HSLList = new ArrayList<List<HoleScore>>();
        courseScoreList = new ArrayList<CourseScore>();

        //Add a course score list for each player
        for(int i = 0; i < playerList.getPlayerList().size(); i++)
        {
            courseScoreList.add(new CourseScore(playerList.getPlayerList().get(i), course));
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<CourseScore> getCourseScoreList() {
        return courseScoreList;
    }

    public void setCourseScoreList(List<CourseScore> courseScoreList) {
        this.courseScoreList = courseScoreList;
    }

    public List<List<HoleScore>> getHSLList() {
        return HSLList;
    }

    public void setHSLList(List<List<HoleScore>> HSLList) {
        this.HSLList = HSLList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    //Returns next holeScore list from main list. If the next holeScore list does not exist,
    //creates a new one, adds to main list, then returns it.
    public List<HoleScore> getNextHoleScoreList(List<HoleScore> hsl, int holeNumber, String teeName)
    {
        //If no holescore lists are in list, or if one has not been created for next hole
        if(hsl == null || HSLList.indexOf(hsl)+1 == HSLList.size())
        {
            //Create new holeScore list
            List <HoleScore> newHSL = new ArrayList<HoleScore>();

            //Add a holeScore for each player in playerlist.
            //Set initial score to 0
            HoleScore newHS;
            for(Player p: playerList.getPlayerList())
            {
                newHS = new HoleScore();
                newHS.setPlayer(p);
                newHS.setHoleNumber(holeNumber);
                newHS.setHoleName(teeName);
                newHS.setScore(0);
                newHSL.add(newHS);
            }

            //add new list of holescores to main list
            HSLList.add(newHSL);

            //Return newly created list
            return newHSL;
        }

        //If HSL already exisits for next hole, return it
        return HSLList.get(HSLList.indexOf(hsl)+1);
    }

    //Returns previous HSL. If passed in HSL is first in list, returns passed in HSL.
    public List<HoleScore> getPreviousHoleScoreList(List <HoleScore> hsl)
    {
        int index = HSLList.indexOf(hsl);
        if(index == 0)
        {
            return hsl;
        }
        else
        {
            return HSLList.get(index-1);
        }
    }

    //Increments a certain player's score in hsList, returns score
    public static int incrementPlayerScore(Player p, List<HoleScore> hsList)
    {
        for(HoleScore hs: hsList)
        {
            if(hs.getPlayer() == p)
            {
                hs.score+=1;
                return hs.score;
            }
        }

        //If player not found, return NO_RESULT_FOUND
        return NO_RESULT_FOUND;
    }

    //Decrements a certain player's score in hsList, returns score
    public static int decrementPlayerScore(Player p, List<HoleScore> hsList)
    {
        for(HoleScore hs: hsList)
        {
            if(hs.getPlayer() == p)
            {
                hs.score-=1;
                return hs.score;
            }
        }

        //If player is not found, return NO_RESULT_FOUND
        return NO_RESULT_FOUND;
    }

    //Gets play p's holeScore from the HoleScore list hsl
    public static int getPlayerHoleScore(Player p, List<HoleScore> hsl)
    {
        for(int i = 0; i < hsl.size(); i++)
        {
            if(hsl.get(i).getPlayer() == p)
            {
                return hsl.get(i).getScore();
            }
        }

        return NO_RESULT_FOUND;
    }

    public int incrementPlayerCourseScore(Player p)
    {
        for(CourseScore cs: this.courseScoreList)
        {
            if(cs.getPlayer() == p)
            {
                return cs.incrementScore();
            }
        }

        return NO_RESULT_FOUND;
    }

    public int decrementPlayerCourseScore(Player p)
    {
        for(CourseScore cs: this.courseScoreList)
        {
            if(cs.getPlayer() == p)
            {
                return cs.decrementScore();
            }
        }

        return NO_RESULT_FOUND;
    }

    public int getPlayerCourseScore(Player p)
    {
        for(CourseScore cs: courseScoreList)
        {
            if(cs.getPlayer() == p)
            {
                return cs.getScore();
            }
        }
        return NO_RESULT_FOUND;
    }

    public boolean isLastHoleInCourse(Hole h)
    {
        Hole temp = course.getHoleList().get(course.getHoleList().size()-1);

        if(temp == h)
        {
           return true;
        }

        return false;
    }
}
