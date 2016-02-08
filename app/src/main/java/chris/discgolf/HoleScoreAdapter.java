package chris.discgolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chris on 1/15/2016.
 */
public class HoleScoreAdapter extends BaseAdapter
{
    ArrayList<Player> players;
    Context context;
    RunGame runGame;
    private static LayoutInflater inflater = null;

    public HoleScoreAdapter(RunGame game, ArrayList<Player> p)
    {
        players = p;
        runGame = game;
        context = game;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        HoleScoreAdapter.inflater = inflater;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RunGame getRunGame() {
        return runGame;
    }

    public void setRunGame(RunGame runGame) {
        this.runGame = runGame;
    }

    public int getCount()
    {
        return players.size();
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
        TextView playerName;
        TextView totalScore;
        TextView holeScore;

        Button add, subtract;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Holder holder = new Holder();
        final View rowView;

        rowView = inflater.inflate(R.layout.hole_score_view, null);

        holder.playerName = (TextView) rowView.findViewById(R.id.hole_score_player_name);
        holder.totalScore = (TextView) rowView.findViewById(R.id.hole_score_total_score);
        holder.holeScore = (TextView) rowView.findViewById(R.id.hole_score_hole_score);
        holder.add = (Button) rowView.findViewById(R.id.hole_score_add_button);
        holder.subtract = (Button) rowView.findViewById(R.id.hole_score_sub_button);

        final Player temp = players.get(position);
        //runGame.addPlayer(temp);

        holder.playerName.setText(temp.getPlayerName());
        holder.totalScore.setText(Integer.toString(temp.getTotalPlayerScore()));
        holder.holeScore.setText(Integer.toString(temp.getHolePlayerScore()));

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.setHolePlayerScore(temp.holePlayerScore + 1);
                temp.setTotalPlayerScore(temp.totalPlayerScore + 1);
                holder.holeScore.setText(Integer.toString(temp.getHolePlayerScore()));
                holder.totalScore.setText(Integer.toString(temp.getTotalPlayerScore()));
                runGame.updateScoreCard(runGame.getCurrentHole());
            }
        });

        holder.subtract.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                temp.setHolePlayerScore(temp.holePlayerScore - 1);
                temp.setTotalPlayerScore(temp.totalPlayerScore - 1);
                holder.holeScore.setText(Integer.toString(temp.getHolePlayerScore()));
                holder.totalScore.setText(Integer.toString(temp.getTotalPlayerScore()));
                runGame.updateScoreCard(runGame.getCurrentHole());
            }
        });

        return rowView;
    }

}
