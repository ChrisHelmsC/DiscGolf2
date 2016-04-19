package chris.discgolf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    PlayerList playerList;
    Course course;
    Hole currentHole;

    Spinner teeSelectorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Get passed in course
        Bundle extras = getIntent().getExtras();
        course = (Course)extras.get("course");

        super.onCreate(savedInstanceState);
        setTitle(course.getCourseName());
        setContentView(R.layout.activity_play_game);

        //Get passed in playerList
        //playerList = (PlayerList)extras.get("playerList");

        //Get spinner from layout
        currentHole = course.getHoleList().get(0);
        teeSelectorSpinner = (Spinner) findViewById(R.id.play_game_tee_spinner);
        ArrayAdapter<String> teeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.tee_spinner_layout, currentHole.getStartingPointNames());
        teeSpinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teeSelectorSpinner.setAdapter(teeSpinnerAdapter);
    }
}
