package chris.discgolf;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/*
*   4/3/2016: Removed display of player statistics from course screen based on changes in course and player classes
*
*   ChooseCourse pulls a list of courses from file and displays the courses in a listview to the user. The user can also search
*   for specific courses using the search bar. Once a course is found, the user selects the course, and then clicks the Play button.
*   The play button bundles up the course information, and launches the next activity.
*/


public class ChooseCourse extends AppCompatActivity {

    private ListView LV;                                //Course listview
    private CourseAdapter CA;                           //Adapter for listview
    private Course clickedCourse;                       //Course that is currently selected by user
    private EditText ET;                                //Area to search for course
    private Button playGameButton;                      //Choose current course for play
    private SQLiteDatabase qdb;                         //Database for grabbing courses
    private CourseList courseList;                      //List of courses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_course);

        //Init DB as readable
        DB database = new DB(this);
        qdb = database.getReadableDatabase();

        //Add Courses to list from DB
        courseList = new CourseList();
        courseList.setCourseList(DB.getCourses(qdb));

        //Initiliaze listview and adapter using courseList
        LV = (ListView) findViewById(R.id.play_list_view);
        CA = new CourseAdapter(this, courseList);
        LV.setAdapter(CA);

        //Set Button
        playGameButton = (Button) findViewById(R.id.play__game_button);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayerNumberPicker(clickedCourse);
            }
        });

        //Get EditText for searchbox
        ET = (EditText) findViewById(R.id.play_edit);
        ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                filteredAdapter(CA);
            }
        });
    }

    /*
      Sets current clicked course to c.
    */
    public void setClickedCourse(Course c)
    {
        clickedCourse = c;
        ET.setText(c.getCourseName());
    }

    /*
    * Filters the adapter for the course listview based on what is currently typed into
    * the search bar
     */
    public void filteredAdapter(CourseAdapter CA)
    {
        String s = ET.getText().toString().toLowerCase();
        Iterator<Course> courseIT = CA.getCourseList().getCourseList().listIterator();
        CourseList CL = new CourseList();

        Course temp;
        while(courseIT.hasNext())
        {
            temp = courseIT.next();
            if(temp.getCourseName().toLowerCase().contains(s) || temp.getCity().toLowerCase().contains(s) || temp.getState().toLowerCase().contains(s))
            {
                if(!temp.getCourseName().equals("Add New Course"))
                {
                    CL.addCourse(temp);
                }
            }
        }
        CL.addCourse(new Course("Add New Course", "Select to Add a New Course", ""));

        CourseAdapter newAdapter = new CourseAdapter(this, CL);
        LV.setAdapter(newAdapter);
    }

    /*Launches next activity using the selected course. If no course
     * has been selected a toast message appears. */
    public void launchPlayerNumberPicker(Course c)
    {
        if(c == null)
        {
            //If no course is selected, display toast
            Toast toast = Toast.makeText(this, "No Course Has Been Selected", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(c.getCourseName().equals("Add New Course"))
        {
            //If we are adding a new course, start new course activity
            Intent IT = new Intent(this, NewCourse.class);
            startActivity(IT);
        }
        else
        {
            //Otherwise, get the selected course, and go to player screen
            Intent IT = new Intent(this, NumberPlayers.class);
            IT.putExtra("course", c);
            IT.putParcelableArrayListExtra("holes", (ArrayList)c.getHoleList());
            startActivity(IT);
        }
    }
}
