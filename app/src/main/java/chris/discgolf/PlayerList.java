package chris.discgolf;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/3/2016.
 */
public class PlayerList implements Parcelable {
    ArrayList<Player> playerList;

    public PlayerList(ArrayList<Player> p) {playerList = p;};

    public PlayerList()
    {
        playerList = new ArrayList<Player>();
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    /*
        * Gets string list of player names
         */
    public List<String> getPlayerNames()
    {
        List<String> playerNames = new ArrayList<String>();
        String temp;
        for(int i = 0; i < this.playerList.size(); i++)
        {
            temp = new String(this.playerList.get(i).getFirstName() + " " + this.playerList.get(i).getLastName());
            playerNames.add(temp);
        }

        return playerNames;
    }

    public void setWithList(List<Player> pList)
    {
        while(!pList.isEmpty())
        {
            this.playerList.add(pList.remove(0));
        }
    }

    //returns first player in list
    public Player getFirstPlayer()
    {
        return this.playerList.get(0);
    }

    //Returns next player in list. If p is last player in
    //list, returns first player in list.
    public Player getNextPlayer(Player p)
    {
        int index = this.playerList.indexOf(p);

        if(index == this.playerList.size()-1)
        {
            return this.playerList.get(0);
        }

        return this.playerList.get(index + 1);
    }

    //Returns previous player in list compared to p,
    //if p is first player in list, returns last player.
    public Player getPreviousPlayer(Player p)
    {
        int index = this.playerList.indexOf(p);

        if(index == 0)
        {
            return this.playerList.get(this.playerList.size() - 1);
        }

        return this.playerList.get(index - 1);
    }

    protected PlayerList(Parcel in) {
        if (in.readByte() == 0x01) {
            playerList = new ArrayList<Player>();
            in.readList(playerList, Player.class.getClassLoader());
        } else {
            playerList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (playerList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(playerList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PlayerList> CREATOR = new Parcelable.Creator<PlayerList>() {
        @Override
        public PlayerList createFromParcel(Parcel in) {
            return new PlayerList(in);
        }

        @Override
        public PlayerList[] newArray(int size) {
            return new PlayerList[size];
        }
    };
}