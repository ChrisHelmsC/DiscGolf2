package chris.discgolf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chris on 5/6/2016.
 */
public class SelectedGameHistoryAdapter extends BaseAdapter
{
    List<CourseScore> courseScoreList;
    Context context;
    SelectedGameStatisticsActivity SGSA;
    private static LayoutInflater inflater;

    public SelectedGameHistoryAdapter(List<CourseScore> csl, SelectedGameStatisticsActivity sg)
    {
        this.context = sg;
        this.SGSA = sg;
        this.courseScoreList = csl;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<CourseScore> getCourseScoreList() {
        return courseScoreList;
    }

    public void setCourseScoreList(List<CourseScore> courseScoreList) {
        this.courseScoreList = courseScoreList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SelectedGameStatisticsActivity getSGSA() {
        return SGSA;
    }

    public void setSGSA(SelectedGameStatisticsActivity SGSA) {
        this.SGSA = SGSA;
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        SelectedGameHistoryAdapter.inflater = inflater;
    }

    public int getCount() {return courseScoreList.size();}

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
        TextView playerScore;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.list_item_course_score, null);
        holder.playerName = (TextView) rowView.findViewById(R.id.list_item_course_score_player_name);
        holder.playerScore = (TextView) rowView.findViewById(R.id.list_item_course_score_player_score);

        holder.playerName.setText(courseScoreList.get(position).getPlayer().getFirstName() + " " + courseScoreList.get(position).getPlayer().getLastName());
        holder.playerScore.setText(Integer.toString(courseScoreList.get(position).getScore()));

        return rowView;
    }
}
