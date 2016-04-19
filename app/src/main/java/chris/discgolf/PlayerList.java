package chris.discgolf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/3/2016.
 */
public class PlayerList extends ArrayList<Player>
{
    /*
    * Gets string list of player names
     */
    public List<String> getPlayerNames()
    {
        List<String> playerNames = new ArrayList<String>();
        String temp;
        for(int i = 0; i < this.size(); i++)
        {
            temp = new String(this.get(i).getFirstName() + " " + this.get(i).getLastName());
            playerNames.add(temp);
        }

        return playerNames;
    }

    public void setWithList(List<Player> pList)
    {
        while(!pList.isEmpty())
        {
            this.add(pList.remove(0));
        }
    }
}
