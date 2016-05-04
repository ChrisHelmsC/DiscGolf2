package chris.discgolf;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class GameHistoryActivity extends AppCompatActivity
{
    ListView gameLV;    //Listview showing all games
    GameHistoryAdapter GHA; //Adapter for listview
    Context context;        //This activity context
    SQLiteDatabase qdb;     //Database connection

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        gameLV = (ListView) findViewById(R.id.game_history_list_view);
        GHA = new GameHistoryAdapter(new ArrayList<Game>(), this, this);

        DB database = new DB(this);
        qdb = database.getReadableDatabase();

        //Get games for game list
        new GetGamesList().execute();
    }

    public void StartSelectedGameStatisticsActivity(Game g)
    {
        Intent i = new Intent(this, SelectedGameStatisticsActivity.class);
        i.putExtra("game", g);
        startActivity(i);
    }

    private class GetGamesList extends AsyncTask<Void, Void, List<Game>>
    {
        //Get games list
        protected List<Game> doInBackground(Void... params)
        {
            List<Game> gameList = DB.getAllGames(qdb);
            return gameList;
        }

        //Set adapter to have games list, update listview
        protected void onPostExecute(List<Game> g)
        {
            GHA.setGameList(g);
            gameLV.setAdapter(GHA);
        }
    }
}
