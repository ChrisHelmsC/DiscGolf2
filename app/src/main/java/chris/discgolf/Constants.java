package chris.discgolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/30/2016.
 */
public class Constants
{
    public static final String GAME_SAVE_FILE = "temp_game_save";

    //Used to get a list of us states
    public static final List<String> stateList()
    {
        List<String> states = new ArrayList<>();

        states.add("AL");
        states.add("AK");
        states.add("AZ");
        states.add("AR");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DE");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("IA");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("ME");
        states.add("MD");
        states.add("MA");
        states.add("MI");
        states.add("MN");
        states.add("MS");
        states.add("MO");
        states.add("MT");
        states.add("NE");
        states.add("NV");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("NY");
        states.add("NC");
        states.add("ND");
        states.add("OH");
        states.add("OK");
        states.add("OR");
        states.add("PA");
        states.add("RI");
        states.add("SC");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VT");
        states.add("VA");
        states.add("WA");
        states.add("WV");
        states.add("WI");
        states.add("WY");

        return states;
    }

    //Used to set starting amount of holes in course creation
    public static final int EIGHTEEN_HOLES = 18;

    //used to set par to 3 initially in course creation
    public static final int PAR_THREE = 3;

    //Max and minimum number of holes for a course
    public static final int MAX_HOLES = 18;
    public static final int MIN_HOLES = 1;

    //Default tee name for course creation
    public static final String DEFAULT_NAME = "Default";

    //ID not set constant
    public static final int ID_NOT_SET = -500;
}
