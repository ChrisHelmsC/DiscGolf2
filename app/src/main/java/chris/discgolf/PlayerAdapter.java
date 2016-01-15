package chris.discgolf;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Chris on 12/20/2015.
 */
public class PlayerAdapter extends BaseAdapter
{
    String ownerName = "Chris";
    List<String> playerList;
    Context context;
    NumberPlayers numberPlayers;
    private static LayoutInflater inflater = null;

    public PlayerAdapter(NumberPlayers numPlay, List<String> pl)
    {
        playerList = pl;
        context = numPlay;
        numberPlayers = numPlay;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        PlayerAdapter.inflater = inflater;
    }

    public NumberPlayers getNumberPlayers() {
        return numberPlayers;
    }

    public void setNumberPlayers(NumberPlayers numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }

    public int getCount()
    {
        return playerList.size();
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
        TextView playerNumber;
        EditText playerName;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Holder holder = new Holder();
        final View rowView;
        String[] myPlayerNames = stringListToArray(playerList);

        rowView = inflater.inflate(R.layout.player_view, null);
        holder.playerNumber = (TextView) rowView.findViewById(R.id.player_view_player_number);
        holder.playerNumber.setText(Integer.toString(position + 1) + ".");

        holder.playerName = (EditText) rowView.findViewById(R.id.player_view_player_name);

        if(myPlayerNames[position]==null)
        {
            holder.playerName.setText("Player " + Integer.toString(position + 1));
        }
        else
        {
            holder.playerName.setText(myPlayerNames[position]);
        }

        holder.playerName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String a = holder.playerName.getText().toString();
                numberPlayers.addToPlayerAdapter(position, a);
            }
        });

        return rowView;
    }

    private String[] stringListToArray(List<String> myList)
    {
        String[] array = new String[8];
        Iterator<String> it = myList.listIterator();
        int position = 0;

        while(it.hasNext())
        {
            array[position] = it.next();
            position++;
        }

        return array;
    }
}
