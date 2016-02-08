package chris.discgolf;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumberPlayers extends AppCompatActivity
{
    Button increment, decrement, confirm;
    TextView numberOfPlayers;
    PlayerAdapter playerAdapter;
    List<String> playerList;
    ListView playersListView;
    final int MAX_PLAYERS = 6;
    Course c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_players);

        Bundle extras  = getIntent().getExtras();
        c = (Course) extras.getParcelable("course");

        increment = (Button) findViewById(R.id.number_players_increment);
        decrement = (Button) findViewById(R.id.number_players_decrement);
        numberOfPlayers = (TextView) findViewById(R.id.number_players_number_players);

        increment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int num = Integer.parseInt(numberOfPlayers.getText().toString());
                if(num  < MAX_PLAYERS)
                {
                    numberOfPlayers.setText(Integer.toString(num + 1));
                    playerList.add("");
                    playerAdapter.setPlayerList(playerList);
                    playerAdapter.notifyDataSetChanged();
                }
            }
        });

        decrement.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int num = Integer.parseInt(numberOfPlayers.getText().toString());
                if(num  > 1)
                {
                    numberOfPlayers.setText(Integer.toString(num - 1));
                    playerList = removeLastInList(playerList);
                    playerAdapter.setPlayerList(playerList);
                    playerAdapter.notifyDataSetChanged();
                }
            }
        });

        playerList = new ArrayList<String>();
        playerList.add("Chris");

        playerAdapter = new PlayerAdapter(this, playerList);
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

        //Resize listview


    }

    private List<String> removeLastInList(List<String> strings)
    {
        List<String> newList = new ArrayList<>();
        Iterator<String> i = strings.listIterator();
        int length = strings.size(), position = 1;
        String name;
        while(i.hasNext())
        {
            name = i.next();
            if(position != length)
            {
                newList.add(name);
            }
            position++;
        }
        return newList;
    }

    public void addToPlayerAdapter(int position, String s)
    {
        String [] names = new String[playerList.size()];

        Iterator<String> it = playerList.listIterator();
        int count = 0;

            while (it.hasNext()) {
                if (count == position) {
                    it.next();
                    names[count] = s;
                } else {
                    names[count] = it.next();
                }
                count++;
            }

        playerList = arrayToList(names);
        playerAdapter.setPlayerList(playerList);
        //playerAdapter.notifyDataSetChanged();
    }

    private List<String> arrayToList(String[] s)
    {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < s.length; i++)
        {
            list.add(s[i]);
        }

        return list;
    }

    //===========================================
    //                  startGame
    //Starts game using playerList and course passed from
    //previous activity.
    //===========================================
    private void startGame()
    {
        Intent i = new Intent(this, RunGame.class);
        i.putExtra("course", c);
        i.putParcelableArrayListExtra("holes", (ArrayList) c.getHoleList());
        playerList = clearEmptyPlayers(playerList);
        i.putParcelableArrayListExtra("players", (ArrayList) playerList);

        startActivity(i);
    }

    private List<String> clearEmptyPlayers(List<String> players)
    {
        List<String> newList = new ArrayList<>();
        String temp;
        for(int i = 0; i < players.size(); i++)
        {
            temp = players.get(i);
            if(temp!="")
            {
                newList.add(temp);
            }
        }
        return newList;
    }
}
