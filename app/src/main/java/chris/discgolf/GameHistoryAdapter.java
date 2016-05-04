package chris.discgolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Chris on 5/3/2016.
 */
public class GameHistoryAdapter extends BaseAdapter
{
    List<Game> gameList;        //List of games
    Context context;            //Current context
    private static LayoutInflater inflater;          //View inflater
    GameHistoryActivity GHA;    //Associated activity

    public GameHistoryAdapter(List<Game> g, Context c, GameHistoryActivity gha)
    {
        gameList = g;
        context = c;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        GHA = gha;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        GameHistoryAdapter.inflater = inflater;
    }

    public int getCount()
    {
        return gameList.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    private class Holder
    {
        TextView courseName;
        TextView date;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;
        final Game[] games = new Game[gameList.size()];
        gameList.toArray(games);

        rowView = inflater.inflate(R.layout.game_history_list_view, null);
        holder.courseName = (TextView) rowView.findViewById(R.id.game_history_list_view_course);
        holder.date = (TextView) rowView.findViewById(R.id.game_history_list_view_date);

        holder.courseName.setText(games[position].getCourse().getCourseName());
        holder.date.setText(games[position].getDate());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GHA.StartSelectedGameStatisticsActivity(games[position]);
            }
        });

        return rowView;
    }

}
