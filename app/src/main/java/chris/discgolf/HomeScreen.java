package chris.discgolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity
{
    //Buttons for playing game, game history, my discs
    TextView playButton, myGamesButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //Set play button to start the chooseCourse activity
        playButton = (TextView) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(v.getContext(), ChooseCourse.class);
                startActivity(playIntent);
            }
        });

        //Set settings button to launch settings activity
        settingsButton = (TextView) findViewById(R.id.home_settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent settingsIntent = new Intent(v.getContext(), ApplicationSettings.class);
                startActivity(settingsIntent);
            }
        });
    }
}
