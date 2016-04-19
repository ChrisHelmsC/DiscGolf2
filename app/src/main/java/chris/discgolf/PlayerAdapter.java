package chris.discgolf;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
    Context context;
    NumberPlayers numberPlayers;
    private static LayoutInflater inflater = null;

    public PlayerAdapter(NumberPlayers numPlay)
    {
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

    public PlayerList getPlayerList() {
        return numberPlayers.playerList;
    }

    public void setPlayerList(PlayerList playerList) {
        this.numberPlayers.playerList = playerList;
    }

    public int getCount()
    {
        return numberPlayers.playerList.size();
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
        CheckBox checkBox;
        TextView playerName;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final Holder holder = new Holder();
        final View rowView;
        String[] myPlayerNames = stringListToArray(numberPlayers.playerList.getPlayerNames());

        rowView = inflater.inflate(R.layout.player_view, null);
        holder.checkBox = (CheckBox) rowView.findViewById(R.id.player_view_check_box);

        holder.playerName = (TextView) rowView.findViewById(R.id.player_view_player_name);

        holder.playerName.setText(myPlayerNames[position]);
        holder.playerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked());

                if(holder.checkBox.isChecked())
                {
                    numberPlayers.playingPlayers.add(numberPlayers.playerList.get(position));
                }
                else
                {
                    numberPlayers.playingPlayers.remove(numberPlayers.playerList.get(position));
                }
            }
        });

        return rowView;
    }

    private String[] stringListToArray(List<String> myList)
    {
        String[] array = new String[30];
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
