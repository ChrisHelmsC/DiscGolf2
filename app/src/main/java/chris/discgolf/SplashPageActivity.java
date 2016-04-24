package chris.discgolf;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashPageActivity extends AppCompatActivity {

    private SQLiteDatabase qdb;            //Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_page);

        //Use for deleting db contents
        this.deleteDatabase("mydb.s3db");

        //Initialize DB
        DB database = new DB(this);
        qdb = database.getWritableDatabase();

        //Check if DB is empty
        if(DB.dbIsEmpty(qdb))
        {
            //Fill DB if empty
            DB.addCourses(qdb);
            DB.addHoles(qdb);
            DB.addStartingPoints(qdb);
            DB.addPlayers(qdb);
            DB.addGames(qdb);
            DB.addStartingPointScores(qdb);
            DB.addCourseScores(qdb);
        }

        //Launch HomeScreen
        Intent homeIntent = new Intent(this, HomeScreen.class);
        startActivity(homeIntent);
    }
}
