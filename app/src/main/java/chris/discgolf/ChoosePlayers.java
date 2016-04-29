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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ChoosePlayers extends AppCompatActivity
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
        if(playingPlayers.getPlayerList().size() > 0)
        {
            //Create intent to run game
            Intent i = new Intent(this, PlayGame.class);

            //Add course to extras extras
            i.putExtra("course", c);

            //Add Players to extras
            i.putExtra("playerList", playingPlayers.getPlayerList());

            //Start rungame activity
            startActivity(i);
        }
        else
        {
            Toast.makeText(this, "No players have been selected.", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_players_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choose_players_menu_add_new)
        {
            addNewPlayerPopup();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewPlayerPopup()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("New Player");
        alert.setMessage("Enter Player Name");

        final EditText input = new EditText(this);
        alert.setView(input);


        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Add Player from name string
                String[] split = input.getText().toString().split("\\s+");

                if (split.length == 2)
                {
                    //Add player to DB, list
                    Player p = new Player(split[0], split[1]);
                    DB.insertPlayerIntoDb(p, qdb);
                    playerList.getPlayerList().add(p);
                    playingPlayers.getPlayerList().add(p);
                    playerAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Name was entered incorrectly.", Toast.LENGTH_SHORT).show();
                }
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