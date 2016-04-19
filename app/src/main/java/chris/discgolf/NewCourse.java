package chris.discgolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class NewCourse extends AppCompatActivity
{

    EditText name, city, state, numberHoles;
    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        name = (EditText) findViewById(R.id.new_course_name);
        city = (EditText) findViewById(R.id.new_course_city);
        state = (EditText) findViewById(R.id.new_course_state);
        numberHoles = (EditText) findViewById(R.id.new_course_holes);

        play = (Button) findViewById(R.id.new_course_play);
        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                createNewCourse();
            }
        });
    }

    private void createNewCourse()
    {
        String n, c, s, holesString;
        int holes;
        Course newCourse;

        n = name.getText().toString();
        c = city.getText().toString();
        s = state.getText().toString();
        holes = Integer.parseInt(numberHoles.getText().toString());

        newCourse = new Course(n, c, s);
        newCourse.setHoleList(new ArrayList<Hole>());

        Intent myIntent = new Intent(this, NumberPlayers.class);
        myIntent.putExtra("course", newCourse);
        myIntent.putParcelableArrayListExtra("holes", (ArrayList)newCourse.getHoleList());
        startActivity(myIntent);
    }
}
