package chris.discgolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayGame extends AppCompatActivity {

    TextView avgPar, avgPlayer, bestPlayer;
    ListView LV;
    CourseAdapter CA;
    Course clickedCourse;
    EditText ET;
    Button playGameButton;

    //Temporary List
    CourseList courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //Set Button
        playGameButton = (Button) findViewById(R.id.play__game_button);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayerNumberPicker(clickedCourse);
            }
        });

        //Add Courses to List for Testing
        courseList = new CourseList();
        courseList.addCourse(new Course("MeadowBrook", 18, 2, 4, 7, "Greenville", "NC"));
        courseList.addCourse(new Course("ECU North Rec.", 18, 5, 7, 12, "Greenville", "NC"));
        courseList.addCourse(new Course("Covenant Church", 18, 2, 8, 9, "Greenville", "NC"));
        courseList.addCourse(new Course("Add New Course", 0, 0, 0, 0, "", ""));

        //Get Stats TextViews
        avgPar = (TextView) findViewById(R.id.play_course_par_avg);
        avgPlayer = (TextView) findViewById(R.id.play_personal_par_avg);
        bestPlayer = (TextView) findViewById(R.id.play_personal_best_par);

        //Listview Stuff
        LV = (ListView) findViewById(R.id.play_list_view);
        CA = new CourseAdapter(this, courseList);
        LV.setAdapter(CA);

        //Get EditText
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

    public void setCourseStats(Course c)
    {
        avgPar.setText(Integer.toString(c.getCourseAverage()));
        avgPlayer.setText(Integer.toString(c.getPlayerAverage()));
        bestPlayer.setText(Integer.toString(c.getPlayerBest()));
        clickedCourse = c;
        ET.setText(c.getCourseName());
    }

    public void filteredAdapter(CourseAdapter CA)
    {
        String s = ET.getText().toString().toLowerCase();
        //Confusing as fuck
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
        CL.addCourse(new Course("Add New Course", 0, 0, 0, 0, "", ""));

        CourseAdapter newAdapter = new CourseAdapter(this, CL);
        LV.setAdapter(newAdapter);
    }

    public void launchPlayerNumberPicker(Course c)
    {
        if(c == null)
        {
            Toast toast = Toast.makeText(this, "No Course Has Been Selected", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(c.getCourseName().equals("Add New Course"))
        {
            Intent IT = new Intent(this, NewCourse.class);
            startActivity(IT);
        }
        else
        {
            Intent IT = new Intent(this, NumberPlayers.class);
            IT.putExtra("course", c);
            IT.putParcelableArrayListExtra("holes", (ArrayList)c.getHoleList());
            startActivity(IT);
        }
    }
}
