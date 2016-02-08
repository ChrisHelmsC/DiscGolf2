package chris.discgolf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RunGame extends AppCompatActivity
{
    int currentHole;
    ListView playerList;
    HoleScoreAdapter hA;
    HoleRecordHolder holeRecordHolder;
    Course c;
    Button prevHole, nextHole;
    TextView TV_avgPar, TV_plyrAvg, TV_plyrBest;
    TableLayout TL_scoreCard;
    List<String> nameStrings;
    ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_game);

        //Get Bundle Passed from NumberPlayers
        Bundle extras = getIntent().getExtras();
        c = (Course) extras.getParcelable("course");
        c.setHoleList((List) extras.getParcelableArrayList("holes"));
        nameStrings = extras.getStringArrayList("players");//new ArrayList<String>();
        players = makePlayerArrayList(nameStrings);

        //Set UI elements
        playerList = (ListView) findViewById(R.id.run_game_list_view);
        hA = new HoleScoreAdapter(this, players);
        playerList.setAdapter(hA);
        TV_avgPar = (TextView) findViewById(R.id.run_game_average_par);
        TV_plyrAvg = (TextView)findViewById(R.id.run_game_player_average);
        TV_plyrBest = (TextView) findViewById(R.id.run_game_player_best);
        TL_scoreCard = (TableLayout) findViewById(R.id.run_game_table_view);
        nextHole = (Button) findViewById(R.id.run_game_next_hole);

        //Set Current Hole
        currentHole = 1;

        //Set title to current hole, set hole stats
        setTitle("Hole " + Integer.toString(currentHole));
        //set hole stats, -1 for list id offset
        setHoleStats(currentHole-1);

        //Create HoleRecordHolder
        holeRecordHolder = new HoleRecordHolder();

        //initialize scoreCard
        initializeScoreCard();

        //Set on next hole button on click
        nextHole.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addRecordToHolder();
                setPlayerStats();
                currentHole++;

                if(currentHole>c.getHoleList().size())
                {
                    finishGame();
                }
                else
                {
                    setHoleStats(currentHole-1);
                    updateScoreCard(currentHole);
                    setTitle("Hole " + Integer.toString(currentHole));

                    //Change next hole text if on last hole
                    if(currentHole == c.getHoleList().size()) nextHole.setText("Finish Game");
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.run_game_action_bar, menu);
        return true;
    }

    private ArrayList<Player> makePlayerArrayList(List<String> playerNames)
    {
        ArrayList<Player> players = new ArrayList<Player>();

        for(int i = 0; i < playerNames.size(); i++)
        {
            players.add(new Player(playerNames.get(i)));
        }

        return players;
    }

    //Adds a record to recordHolder based on current
    //player stats.
    private void addRecordToHolder()
    {
        HoleRecord hR = new HoleRecord();
        List<Player> currentList = hA.getPlayers();

        for(int i = 0; i < currentList.size(); i++)
        {
            hR.addRecord(currentList.get(i));
        }

        holeRecordHolder.addRecord(hR);
    }

    private void setPlayerStats()
    {
        ArrayList<Player> currentList = hA.getPlayers(), newList = new ArrayList<Player>();
        Player temp;
        for(int i = 0; i < currentList.size(); i++)
        {
            temp = currentList.get(i);
            newList.add(new Player(temp.getPlayerName(), temp.getTotalPlayerScore(), 0));
        }
        players = newList;
        hA.setPlayers(players);
        hA.notifyDataSetChanged();
    }

    private void setHoleStats(int h)
    {
        Hole currentHole = c.getHoleList().get(h);
        TV_avgPar.setText(Integer.toString(currentHole.getAvgPar()));
        TV_plyrBest.setText(Integer.toString(currentHole.getPlayerBest()));
        TV_plyrAvg.setText(Integer.toString(currentHole.getPlayerAvg()));
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                //Do score card stuff
        }
        return true;
    }

    private void initializeScoreCard()
    {
        TableRow inflateTitle = (TableRow) View.inflate(this, R.layout.score_card_title_row, null);
        inflateTitle.setTag("title");
        TL_scoreCard.addView(inflateTitle);
        TextView TV_name, TV_total, TV_firstHole;

        for(int i = 0; i < nameStrings.size(); i++)
        {
            TableRow inflateRow = (TableRow) View.inflate(this,R.layout.score_card_row, null);
            inflateRow.setTag(i);
            TV_name = (TextView)inflateRow.findViewById(R.id.score_card_name);
            TV_total = (TextView)inflateRow.findViewById(R.id.score_card_total);
            TV_firstHole = (TextView)inflateRow.findViewById(R.id.score_card_hole_1);
            TV_name.setText(nameStrings.get(i));
            TV_total.setText(Integer.toString(players.get(i).getTotalPlayerScore()));
            TV_firstHole.setText(Integer.toString(players.get(i).getHolePlayerScore()));
            TL_scoreCard.addView(inflateRow);
        }
    }

    public void updateScoreCard(int hole)
    {
        TextView currentHole, TV_total;
        int score;
        for(int i = 0; i < nameStrings.size(); i++)
        {
            //Get tableRow, offset for title row
            TableRow inflateRow = (TableRow) TL_scoreCard.getChildAt(i+1);
            TV_total = (TextView)inflateRow.findViewById(R.id.score_card_total);
            //get current hole textview, Offset for name textview
            currentHole = (TextView) inflateRow.getChildAt(hole);

            //set current player score
            score = players.get(i).getHolePlayerScore();
            currentHole.setText(Integer.toString(score));

            //Set color depending on hole score
            if(score > 0)
            {
                currentHole.setTextColor(Color.parseColor("#B22222"));
            }
            else if(score < 0)
            {
                currentHole.setTextColor(Color.parseColor("#556B2F"));
            }

            TV_total.setText(Integer.toString(players.get(i).getTotalPlayerScore()));
        }
    }

    public int getCurrentHole() {
        return currentHole;
    }

    public void setCurrentHole(int currentHole) {
        this.currentHole = currentHole;
    }

    //Handle back button pressed during game with a messagebox
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            new AlertDialog.Builder(RunGame.this)
                    .setTitle("Go Back?")
                    .setMessage("If you go back to player entry, your current game will be lost. Are you sure you want to go back?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        return false;
    }

    /*===========================================================
    *                           finishGame
    * ===========================================================
    * Closes current game and starts activity to display final scorecard.
    * Also adds game stats to game history and sends data to server.
    * */
    public void finishGame()
    {
        Intent i = new Intent(this, FinalScoreCard.class);
        startActivity(i);
    }

}
