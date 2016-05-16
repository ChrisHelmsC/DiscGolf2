package chris.discgolf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectedGameStatisticsActivity extends AppCompatActivity
{
    Game selectedGame;      //Selected Game
    ListView courseScoreListView;
    SelectedGameHistoryAdapter sgha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_game_statistics);

        //Get passed in Game
        Bundle extras = getIntent().getExtras();
        selectedGame = (Game) extras.get("game");
        selectedGame.setCourseScoreList((ArrayList)extras.getParcelableArrayList("courseScores"));

        setTitle(selectedGame.getCourse().getCourseName() + " - " + selectedGame.getDate());

        courseScoreListView = (ListView) findViewById(R.id.selected_game_stats_player_course_scores);
        sgha = new SelectedGameHistoryAdapter(selectedGame.getCourseScoreList(), this);
        courseScoreListView.setAdapter(sgha);
    }
}
