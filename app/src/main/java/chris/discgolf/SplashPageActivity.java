package chris.discgolf;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/*
    This activity is started when the application is launched. If the database does not exist,
    meaning the user has not ever used the application, a new database is created and seeded
    with stock information. Once this activity finishes, it cannot be returned to from the
    home screen.
 */

public class SplashPageActivity extends AppCompatActivity {

    private SQLiteDatabase qdb;            //Database
    private Context context;                //Activity context

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        //Set context
        context = this;

        //Run database initilization in seperate thread
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                //Use for deleting db contents
                //context.deleteDatabase("mydb.s3db");

                //Initialize DB
                DB database = new DB(context);
                qdb = database.getWritableDatabase();

                //Check if DB is empty, if it is, fill with stock information
                if (DB.dbIsEmpty(qdb)) {
                    DB.addStockCourses(qdb);
                    DB.addStockHoles(qdb);
                    DB.addStockStartingPoints(qdb);
                    //DB.addPlayers(qdb);
                    //DB.addGames(qdb);
                    //DB.addStartingPointScores(qdb);
                    //DB.addCourseScores(qdb);
                }
            }
        };
        Thread t = new Thread(run);
        t.start();

        //Launch HomeScreen
        Intent homeIntent = new Intent(this, HomeScreen.class);
        startActivity(homeIntent);

        //Wait for thread,Finishes activity so user cannot return to it from the homepage
        try{
            t.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finish();
    }
}
