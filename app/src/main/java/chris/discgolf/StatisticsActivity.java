package chris.discgolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity
{
    TextView GameHistory; //Game History Button
    TextView CourseHistory; //Course History Button
    TextView PlayerHistory; //Player history button

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //Set buttons
        GameHistory = (TextView) findViewById(R.id.statistics_games_button);
        GameHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(v.getContext(), GameHistoryActivity.class);
                startActivity(i);
            }
        });
    }
}
