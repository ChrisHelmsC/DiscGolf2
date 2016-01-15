package chris.discgolf;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chris on 12/17/2015.
 */
public class CourseAdapter extends BaseAdapter
{
    CourseList courseList;
    private static LayoutInflater inflater = null;
    Context context;
    PlayGame currentGame;

    public CourseAdapter(PlayGame playgame, CourseList cl)
    {
        context = playgame;
        courseList = cl;
        currentGame = playgame;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CourseList getCourseList() {
        return courseList;
    }

    public void setCourseList(CourseList courseList) {
        this.courseList = courseList;
    }

    public int getCount()
    {
        return courseList.getNumberOfCourses();
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
        TextView courseNames;
        TextView location;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        final Course[] myCourseNames = courseList.getCourseArray();

        rowView = inflater.inflate(R.layout.course_name_view, null);
        holder.courseNames = (TextView) rowView.findViewById(R.id.name_view_course_name);
        holder.courseNames.setText(myCourseNames[position].getCourseName());
        holder.location = (TextView) rowView.findViewById(R.id.name_view_location);

        if(myCourseNames[position].getCity().equals(""))
        {
            holder.location.setText("Select To Enter a New Game");
        }
        else
        {
            holder.location.setText(myCourseNames[position].getCity() + ", " + myCourseNames[position].getState());
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.setCourseStats(myCourseNames[position]);
            }
        });

        return rowView;
    }
}
