package chris.discgolf;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

/*
*   4/3/2016: Removed display of player statistics from course screen based on changes in course and player classes
*
*   ChooseCourse pulls a list of courses from file and displays the courses in a listview to the user. The user can also search
*   for specific courses using the search bar. Once a course is found, the user selects the course, and then clicks the Play button.
*   The play button bundles up the course information, and launches the next activity.
*
*   TODO: 3. Launch course editing activity when icon is clicked
*
*/


public class ChooseCourse extends AppCompatActivity {

    private Context context;                            //Activity context
    private ListView LV;                                //Course listview
    private CourseAdapter CA;                           //Adapter for listview
    private Course clickedCourse;                       //Course that is currently selected by user
    private EditText ET;                                //Area to search for course
    private SQLiteDatabase qdb;                         //Database for grabbing courses
    private CourseList courseList;                      //List of courses

    static final int NEW_COURSE_REQUEST_CODE = 1;       //Request code for returned new course

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_course);
        context = this;

        //Initiliaze listview
        LV = (ListView) findViewById(R.id.play_list_view);

        //Get courses in seperate thread
        CA = new CourseAdapter(this, courseList);
        new GetCoursesFromDatabase().execute();

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
        CA.notifyDataSetChanged();

        invalidateOptionsMenu();

    }

    public Course getClickedCourse() {
        return clickedCourse;
    }

    /*
        * Filters the adapter for the course listview based on what is currently typed into
        * the search bar
         */
    public void filteredAdapter(CourseAdapter CA)
    {
        String s = ET.getText().toString().toLowerCase();
        Iterator<Course> courseIT = courseList.getCourseList().listIterator();
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

        //CourseAdapter newAdapter = new CourseAdapter(this, CL);
        //LV.setAdapter(newAdapter);
        CA.setCourseList(CL);
        CA.notifyDataSetChanged();
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
        else
        {
            //Otherwise, get the selected course, and go to player screen
            Intent IT = new Intent(this, ChoosePlayers.class);
            IT.putExtra("course", c);
            IT.putParcelableArrayListExtra("holes", (ArrayList)c.getHoleList());
            startActivity(IT);
        }
    }

    private class GetCoursesFromDatabase extends AsyncTask<Void, Void, CourseList>
    {
        //Get courses from DB as CourseList
        protected CourseList doInBackground(Void... params)
        {
            //Init DB as readable
            DB database = new DB(context);
            qdb = database.getReadableDatabase();

            //Add Courses to list from DB
            courseList = new CourseList();
            courseList.setCourseList(DB.getCourses(qdb));
            return courseList;
        }

        protected void onPostExecute(CourseList result)
        {
            CA.setCourseList(result);
            CA.notifyDataSetChanged();
            LV.setAdapter(CA);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.choose_course_menu, menu);

        //Check if edit icon should be displayed
        if(clickedCourse != null && clickedCourse.isCustom())
        {
            menu.findItem(R.id.choose_course_menu_edit_icon).setVisible(true);
        }
        else
        {
            menu.findItem(R.id.choose_course_menu_edit_icon).setVisible(false);
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.choose_course_menu_new_course:
                //If new course is chosen, launch new course activity for result
                Intent i = new Intent(this, ActivityNewCourse.class);
                startActivityForResult(i, NEW_COURSE_REQUEST_CODE);
                break;

            case R.id.choose_course_menu_confirm_icon:
                //Confirm course selection
                launchPlayerNumberPicker(clickedCourse);
                break;

            case R.id.choose_course_menu_edit_icon:
                Intent k = new Intent(this, ActivityNewCourse.class);
                clickedCourse.setHoleList(DB.getCourseHoles(clickedCourse.getId(), qdb));
                k.putExtra("editCourse" ,clickedCourse);
                startActivityForResult(k, NEW_COURSE_REQUEST_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Check request
        switch(requestCode)
        {
            case NEW_COURSE_REQUEST_CODE:
                if(resultCode == RESULT_OK)
                {
                    Course newCourse = data.getExtras().getParcelable("newCourse");
                    courseList.addCourse(newCourse);
                    setClickedCourse(newCourse);
                    CA.notifyDataSetChanged();
                }
                break;
        }
    }
}
