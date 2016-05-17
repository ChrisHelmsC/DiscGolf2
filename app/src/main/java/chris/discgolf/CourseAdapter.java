package chris.discgolf;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
    ChooseCourse currentGame;

    public CourseAdapter(ChooseCourse playgame, CourseList cl)
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
        TextView custom;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        final View rowView;
        final Course[] myCourseNames = courseList.getCourseArray();

        rowView = inflater.inflate(R.layout.course_name_view, null);
        holder.courseNames = (TextView) rowView.findViewById(R.id.name_view_course_name);
        holder.courseNames.setText(myCourseNames[position].getCourseName());
        holder.location = (TextView) rowView.findViewById(R.id.name_view_location);
        holder.custom = (TextView) rowView.findViewById(R.id.name_view_custom_label);

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
                if(currentGame.getClickedCourse() == myCourseNames[position])
                {
                    rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_holo_light));
                    rowView.setBackground(ContextCompat.getDrawable(context, R.drawable.choose_course_list_view_item));
                    currentGame.setClickedCourse(null);
                }
                else
                {
                    currentGame.setClickedCourse(myCourseNames[position]);
                    rowView.requestFocus();
                }
            }
        });

        if(currentGame.getClickedCourse() != null && currentGame.getClickedCourse() == myCourseNames[position])
        {
            rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.trans_orange));
        }
        else
        {
            rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.background_holo_light));
            rowView.setBackground(ContextCompat.getDrawable(context, R.drawable.choose_course_list_view_item));
        }

        //If this course is custom, show the custom label
        if(myCourseNames[position].isCustom())
        {
            holder.custom.setVisibility(View.VISIBLE);
        }

        return rowView;
    }
}
