package chris.discgolf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    Game thisGame;                          //Current game information
    private List<HoleScore>currentHSList;   //Current list of hole scores
    private Player currentPlayer;           //Current player represented on screen
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

    private TextView playerHoleScoreDisp;   //Displays player's hole score
    private TextView playerCourseScoreDisp; //Displays player's course score

    private TextView playerPlusButton;      //Changes to next player
    private TextView playerMinusButton;     //Changes to previousPlayer;
    private TextView holePlusButton;        //Increment Hole
    private TextView holeMinusButton;       //Decrement Hole
    private TextView scorePlusButton;       //Increment score
    private TextView scoreMinusButton;      //Decrement score

    private SQLiteDatabase qdb;             //Database

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Get passed in extras, create new game
        Bundle extras = getIntent().getExtras();
        thisGame = new Game((Course)extras.get("course"), new PlayerList((ArrayList<Player>)extras.get("playerList")));

        super.onCreate(savedInstanceState);
        setTitle(thisGame.getCourse().getCourseName());
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

        //Set hole score and total score displays. Initialize to 0
        playerHoleScoreDisp = (TextView)findViewById(R.id.play_game_hole_score_text);
        playerCourseScoreDisp = (TextView)findViewById(R.id.play_game_total_score);
        initializeHoleAndCourseScore();

        //Set length and par displays
        lengthDisplay = (TextView)findViewById(R.id.play_game_length_text);
        parDisplay = (TextView)findViewById(R.id.play_game_par_text);

        //Set initial hole, set initial SP. Set associated displays
        currentHole = thisGame.getCourse().getHoleList().get(0);
        currentSP = currentHole.getStartingPoints().get(0);
        setCurrentHoleDisplay();
        setLengthAndPar();

        //Get first holeScoreList
        currentHSList = thisGame.getNextHoleScoreList(null, currentHole.getHoleNumber(), currentSP.getName());

        //Get spinner from layout
        teeSelectorSpinner = (Spinner) findViewById(R.id.play_game_tee_spinner);
        setSpinnerOptions(currentHole.getStartingPointNames());
        setTeeSpinnerFunction();

        //Set initial currentPlayer
        nextPlayer();

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

        //Set score plus and minus buttons
        scorePlusButton = (TextView)findViewById(R.id.play_game_hole_score_add_button);
        scorePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When score increment button is clicked, increment players score, change display to reflect score
                setPlayerHoleScoreDisp(Game.incrementPlayerScore(currentPlayer, currentHSList));
                setPlayerCourseScoreDisp(thisGame.incrementPlayerCourseScore(currentPlayer));
            }
        });
        scoreMinusButton = (TextView)findViewById(R.id.play_game_hole_score_minus_button);
        scoreMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When score decrement button is clicked, decrement players score
                setPlayerHoleScoreDisp(Game.decrementPlayerScore(currentPlayer, currentHSList));
                setPlayerCourseScoreDisp(thisGame.decrementPlayerCourseScore(currentPlayer));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.play_game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.play_game_menu_end_game)
        {
            checkEndGameAlert();
        }
        return super.onOptionsItemSelected(item);
    }

    private void incrementHole()
    {
        //Check if current hole is last hole, if it is, end game
        if(thisGame.isLastHoleInCourse(currentHole))
        {
            finishGame();
        }
        else {

            //Change current Hole
            currentHole = thisGame.getCourse().getNextHole(currentHole);
            //Change current hole display
            setCurrentHoleDisplay();
            setSpinnerOptions(currentHole.getStartingPointNames());
            setTeeSpinnerFunction();

            //Set par and length
            setLengthAndPar();

            //Set player Stats
            setTeeTextDisplays();
            setCourseTextDisplays();

            //Get next list of hole scores, set holescore display
            currentHSList = thisGame.getNextHoleScoreList(currentHSList, currentHole.getHoleNumber(), currentSP.getName());
            setPlayerHoleScoreDisp(Game.getPlayerHoleScore(currentPlayer, currentHSList));

            setPlayerCourseScoreDisp(thisGame.getPlayerCourseScore(currentPlayer));
        }
    }

    private void decrementHole()
    {
        //Change current hole to previous hole
        currentHole = thisGame.getCourse().getPreviousHole(currentHole);

        //Change display
        setCurrentHoleDisplay();
        setSpinnerOptions(currentHole.getStartingPointNames());
        setTeeSpinnerFunction();

        //Set par and length
        setLengthAndPar();

        //Set player Stats
        setTeeTextDisplays();
        setCourseTextDisplays();

        currentHSList = thisGame.getPreviousHoleScoreList(currentHSList);
        setPlayerHoleScoreDisp(Game.getPlayerHoleScore(currentPlayer, currentHSList));
        setPlayerCourseScoreDisp(thisGame.getPlayerCourseScore(currentPlayer));
    }

    //Sets currentPlayer to the next player in the player list, and
    //Sets player name and stats
    private void nextPlayer()
    {
        //If no player is set, get first player. Else, get next player
        if(currentPlayer == null)
        {
            currentPlayer = thisGame.getPlayerList().getFirstPlayer();
        }
        else
        {
            currentPlayer = thisGame.getPlayerList().getNextPlayer(currentPlayer);
        }

        //Set name in display
        playerNameDisplay.setText(getPlayerNameForDisplay(currentPlayer));

        //Need to set stats
        setTeeTextDisplays();
        setCourseTextDisplays();

        //Set hole score display
        setPlayerHoleScoreDisp(Game.getPlayerHoleScore(currentPlayer, currentHSList));
        setPlayerCourseScoreDisp(thisGame.getPlayerCourseScore(currentPlayer));
    }

    //Sets current player to be previous player
    private void previousPlayer()
    {
        currentPlayer = thisGame.getPlayerList().getPreviousPlayer(currentPlayer);

        //Set name in display
        playerNameDisplay.setText(getPlayerNameForDisplay(currentPlayer));

        //Need to set stats
        setTeeTextDisplays();
        setCourseTextDisplays();

        //Get hole score display
        setPlayerHoleScoreDisp(Game.getPlayerHoleScore(currentPlayer, currentHSList));
        setPlayerCourseScoreDisp(thisGame.getPlayerCourseScore(currentPlayer));
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
    private void setLengthAndPar() {
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
        int teeAvg = DB.getStartingPointAvg(thisGame.getCourse().getId(), currentHole.getHoleNumber(), currentSP.getName(), currentPlayer.getId(), qdb);
        int teeBest = DB.getStartingPointBest(thisGame.getCourse().getId(), currentHole.getHoleNumber(), currentSP.getName(), currentPlayer.getId(), qdb);

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
        int courseAverage = DB.getCourseAverageForTee(thisGame.getCourse().getId(), currentPlayer.getId(), hspName, qdb);

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

    private void setPlayerCourseBestDisplay(String hspName) {
        int courseBest = DB.getCourseBestForTee(thisGame.getCourse().getId(), currentPlayer.getId(), hspName, qdb);

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

    //Sets hole score display to equal score
    private void setPlayerHoleScoreDisp(int score)
    {
        String displayString = "";

        if(score >= 0)
        {
            displayString += "+";
        }

        displayString += Integer.toString(score);

        playerHoleScoreDisp.setText(displayString);
    }

    private void initializeHoleAndCourseScore()
    {
        playerHoleScoreDisp.setText("+0");
        playerCourseScoreDisp.setText("+0");
    }

    private void setPlayerCourseScoreDisp(int score)
    {
        String displayString = "";

        if(score >= 0)
        {
            displayString += "+";
        }

        displayString += Integer.toString(score);

        playerCourseScoreDisp.setText(displayString);
    }

    //Save all game stats to DB, go to main screen
    private void finishGame()
    {
        DB.saveGameData(qdb, thisGame);

        List<CourseScore> gameList = DB.getCourseScoresForGame(thisGame.getID(), qdb);

        int i = 0;
        Intent startMainScreen = new Intent(this, HomeScreen.class);
        startActivity(startMainScreen);
    }

    private void checkEndGameAlert()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("End Game");
        alert.setMessage("Are you sure you want to end the current game?");

        alert.setPositiveButton("End Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishGame();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });

        alert.show();
    }
}
