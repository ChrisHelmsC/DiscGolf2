package chris.discgolf;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
*   Created 04/06/2016.
*
*   PlayGame is the activity used to play an actual game. It allows players to track their scores on
*   various holes throughout the course. Players can see statistics from their previous games, such as
*
*   Per Hole: Average score, best score, last score.
*   Per Course: Average score, best score, last score.
*
*   Scores are entered for each player. After all of a holes scores are entered, the user should
*   choose to move to the next hole. If an error was made on a previous hole, the previous score can be edited.
*
 */

public class PlayGame extends AppCompatActivity
{
    private PlayerList playerList;          //List of players in game
    private List<holeScore>currentHSList;   //Current list of hole scores
    private Player currentPlayer;           //Current player represented on screen
    private Course course;                  //Course

    private Hole currentHole;               //Current Hole
    private Spinner teeSelectorSpinner;     //Tee Spinner
    private HoleStartingPoint currentSP;    //Currently selected starting point
    private HoleStartingPoint defaultSP;    //Sp that game is played on

    private TextView currentHoleDisplay;    //Displays current Hole
    private TextView lengthDisplay;         //Displays hole length from tee
    private TextView parDisplay;            //Displays hole par

    private TextView playerNameDisplay;     //Displays current player's name
    private TextView playerTeeBestDisplay;  //Displays player's best on Tee
    private TextView playerTeeAvgDisplay;   //Displays player's avg on Tee
    private TextView playerCourAvgDisplay;  //Displays players course avg
    private TextView playerCourBestDisplay; //Displays players course best

    private TextView playerPlusButton;      //Changes to next player
    private TextView playerMinusButton;     //Changes to previousPlayer;
    private TextView holePlusButton;        //Increment Hole
    private TextView holeMinusButton;       //Decrement Hole

    private SQLiteDatabase qdb;             //Database

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Get passed in course
        Bundle extras = getIntent().getExtras();
        course = (Course)extras.get("course");

        super.onCreate(savedInstanceState);
        setTitle(course.getCourseName());
        setContentView(R.layout.activity_play_game);

        //Init Database
        DB db = new DB(this);
        qdb = db.getReadableDatabase();

        //Set player name display, Tee best display, current Hole display
        playerNameDisplay = (TextView)findViewById(R.id.play_game_player_name);
        currentHoleDisplay = (TextView) findViewById(R.id.play_game_hole_number_text);
        playerTeeBestDisplay = (TextView)findViewById(R.id.play_game_hole_best_display);
        playerTeeAvgDisplay = (TextView)findViewById(R.id.play_game_hole_avg_display);
        playerCourAvgDisplay = (TextView)findViewById(R.id.play_game_course_avg_display);
        playerCourBestDisplay = (TextView)findViewById(R.id.play_game_course_best_display);

        //Get passed in playerList
        playerList = new PlayerList((ArrayList<Player>)extras.get("playerList"));

        //Set length and par displays
        lengthDisplay = (TextView)findViewById(R.id.play_game_length_text);
        parDisplay = (TextView)findViewById(R.id.play_game_par_text);

        //Set initial hole, set initial SP. Set associated displays
        currentHole = course.getHoleList().get(0);
        currentSP = currentHole.getStartingPoints().get(0);
        setCurrentHoleDisplay();
        setLengthAndPar();

        //Set initial currentPlayer
        nextPlayer();

        //Get spinner from layout
        teeSelectorSpinner = (Spinner) findViewById(R.id.play_game_tee_spinner);
        setSpinnerOptions(currentHole.getStartingPointNames());
        setTeeSpinnerFunction();

        //Set player next and previous buttons
        playerPlusButton = (TextView)findViewById(R.id.play_game_player_plus_button);
        playerPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayer();
            }
        });
        playerMinusButton = (TextView)findViewById(R.id.play_game_player_minus_button);
        playerMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPlayer();
            }
        });

        //Set hole plus and minus buttons
        holePlusButton = (TextView)findViewById(R.id.play_game_hole_plus_button);
        holePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementHole();
            }
        });
        holeMinusButton = (TextView)findViewById(R.id.play_game_hole_minus_button);
        holeMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementHole();
            }
        });
    }

    private void incrementHole()
    {
        //Change current Hole
        currentHole = course.getNextHole(currentHole);
        //Change current hole display
        setCurrentHoleDisplay();
        setSpinnerOptions(currentHole.getStartingPointNames());
        setTeeSpinnerFunction();

        //Set par and length
        setLengthAndPar();

        //Set player Stats
        setTeeTextDisplays();
        setCourseTextDisplays();
    }

    private void decrementHole()
    {
        //Change current hole to previous hole
        currentHole = course.getPreviousHole(currentHole);

        //Change display
        setCurrentHoleDisplay();
        setSpinnerOptions(currentHole.getStartingPointNames());
        setTeeSpinnerFunction();

        //Set par and length
        setLengthAndPar();

        //Set player Stats
        setTeeTextDisplays();
        setCourseTextDisplays();
    }

    //Sets currentPlayer to the next player in the player list, and
    //Sets player name and stats
    private void nextPlayer()
    {
        //If no player is set, get first player. Else, get next player
        if(currentPlayer == null)
        {
            currentPlayer = playerList.getFirstPlayer();
        }
        else
        {
            currentPlayer = playerList.getNextPlayer(currentPlayer);
        }

        //Set name in display
        playerNameDisplay.setText(getPlayerNameForDisplay(currentPlayer));

        //Need to set stats
        setTeeTextDisplays();
        setCourseTextDisplays();
    }

    //Sets current player to be previous player
    private void previousPlayer()
    {
        currentPlayer = playerList.getPreviousPlayer(currentPlayer);

        //Set name in display
        playerNameDisplay.setText(getPlayerNameForDisplay(currentPlayer));

        //Need to set stats
        setTeeTextDisplays();
        setCourseTextDisplays();
    }

    //Returns player name formated for display
    private String getPlayerNameForDisplay(Player p)
    {
        return p.getFirstName()+"\n"+p.getLastName();
    }

    private void setSpinnerOptions(List<String> startingPointNames)
    {
        ArrayAdapter<String> teeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.tee_spinner_layout, startingPointNames);
        teeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teeSelectorSpinner.setAdapter(teeSpinnerAdapter);
    }

    //Sets length and par
    private void setLengthAndPar()
    {
        lengthDisplay.setText(Integer.toString(currentSP.getLength()) + "ft");
        parDisplay.setText(Integer.toString(currentHole.getPar()) + "st");
    }

    private void setCurrentHoleDisplay()
    {
        currentHoleDisplay.setText(Integer.toString(currentHole.getHoleNumber()));
    }

    //Sets tee average and best displays to reflect current player and tee
    private void setTeeTextDisplays()
    {
        int teeAvg = DB.getStartingPointAvg(course.getId(), currentHole.getHoleNumber(), currentSP.getName(), currentPlayer.getId(), qdb);
        int teeBest = DB.getStartingPointBest(course.getId(), currentHole.getHoleNumber(), currentSP.getName(), currentPlayer.getId(), qdb);

        String avg, best;

        //Set avg
        if(teeAvg!=DB.NO_RESULTS)
        {
            avg = formatStringForScoreDisplay(teeAvg);
        }
        else
        {
            avg = "-";
        }

        //set best
        if(teeBest!=DB.NO_RESULTS)
        {
            best = formatStringForScoreDisplay(teeBest);
        }
        else
        {
            best = "-";
        }

        playerTeeAvgDisplay.setText(avg);
        playerTeeBestDisplay.setText(best);
    }

    //Formats string for displaying in tee and course score stats area
    private String formatStringForScoreDisplay(int score)
    {
        String total = "";
        int finalScore = score;
        if(score > 0)
        {
            total += "+";
        }
        else
        {
            total += "-";
            finalScore *= -1;
        }

        if(score > 9 || score < 9)
        {
            total += " ";
        }

        total += Integer.toString(finalScore);
        return total;
    }

    private void setTeeSpinnerFunction()
    {
        teeSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Set current starting point to starting point selected in spinner
                String selectedItem = teeSelectorSpinner.getSelectedItem().toString();
                currentSP = currentHole.getStartingPointByName(selectedItem);

                //Set length and par displayes based on current starting point
                setLengthAndPar();

                //Set player stats
                setTeeTextDisplays();
                setCourseTextDisplays();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Sets course avg and best displays for a specific player
    private void setCourseTextDisplays()
    {
        String hspName;

        if(defaultSP == null)
        {
            hspName = currentSP.getName();
        }
        else
        {
            hspName = defaultSP.getName();
        }

        setPlayerCourseAvgDisplay(hspName);
        setPlayerCourseBestDisplay(hspName);
    }

    private void setPlayerCourseAvgDisplay(String hspName)
    {
        int courseAverage = DB.getCourseAverageForTee(course.getId(), currentPlayer.getId(), hspName, qdb);

        if(courseAverage == DB.NO_RESULTS)
        {
            playerCourAvgDisplay.setText("-");
        }
        else
        {
            String displayString = formatStringForScoreDisplay(courseAverage);
            playerCourAvgDisplay.setText(displayString);
        }
    }

    private void setPlayerCourseBestDisplay(String hspName)
    {
        int courseBest = DB.getCourseBestForTee(course.getId(), currentPlayer.getId(), hspName, qdb);

        if(courseBest == DB.NO_RESULTS)
        {
            playerCourBestDisplay.setText("-");
        }
        else
        {
            String displayString = formatStringForScoreDisplay(courseBest);
            playerCourBestDisplay.setText(displayString);
        }
    }
}
