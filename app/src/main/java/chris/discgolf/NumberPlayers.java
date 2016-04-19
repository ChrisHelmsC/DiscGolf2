package chris.discgolf;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumberPlayers extends AppCompatActivity
{
    private Button confirm;                             //Buttons for adding, subtracting players, confirming list
    private PlayerAdapter playerAdapter;                //Adapter for player List
    PlayerList playerList;                              //List of all players
    PlayerList playingPlayers;                          //List of players that will be playing in the game
    private ListView playersListView;                   //Listview showing all players
    private Course c;                                   //Selected course
    private SQLiteDatabase qdb;                         //Database

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_players);

        //Init Database
        DB db = new DB(this);
        qdb = db.getReadableDatabase();

        //Create playerlist from DB, create empying playingPlayers list
        playerList = new PlayerList();
        playerList.setWithList(DB.getAllPlayersList(qdb));
        playingPlayers = new PlayerList();

        //Get passed in course
        Bundle extras  = getIntent().getExtras();
        c = (Course) extras.getParcelable("course");

        //Initiliaze player adapter and listview
        playerAdapter = new PlayerAdapter(this);
        playersListView = (ListView) findViewById(R.id.number_players_list_view);
        playersListView.setAdapter(playerAdapter);

        //Set confirm button to launch game activity
        confirm = (Button) findViewById(R.id.number_players_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        //Get course's holes and starting points
        c.setHoleList(DB.getCourseHoles(c.getId() ,qdb));
    }

    //===========================================
    //                  startGame
    //Starts game using playerList and course passed from
    //previous activity.
    //===========================================
    private void startGame()
    {
        //Create intent to run game
        Intent i = new Intent(this, PlayGame.class);

        //Add course to extras extras
        i.putExtra("course", c);

        //Add Players to extras
        i.putExtra("playerList", playingPlayers);

        //Start rungame activity
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Toast.makeText(this, Integer.toString(item.getItemId()), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}