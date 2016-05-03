package chris.discgolf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class TestingActivity extends AppCompatActivity
{
    Context context;    //Activity Context
    Button testButton;  //Button to start tests
    Button resetDBButton; //Button to reset db
    ListView testListView;  //Listview for seeing tests and results
    private TestAdapter adapter;    //Listview adapter
    List<TestingClass> testList;    //Holds tests
    SQLiteDatabase qdb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        context = this;

        testButton = (Button) findViewById(R.id.test_activity_test_button);
        resetDBButton = (Button) findViewById(R.id.testing_activity_reset_db_button);
        testListView = (ListView) findViewById(R.id.test_activity_test_list);

        TestingClass t = new TestingClass();
        testList = t.getAllTestsAsList();
        adapter = new TestAdapter(this, testList);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Run the tests on click
                new RunTests().execute();
            }
        });

        //reset database in seperate thread
        resetDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Use for deleting db contents
                        context.deleteDatabase("mydb.s3db");

                        //Initialize DB
                        DB database = new DB(context);
                        qdb = database.getWritableDatabase();

                        //Check if DB is empty, if it is, fill with stock information
                        if (DB.dbIsEmpty(qdb)) {
                            DB.addStockCourses(qdb);
                            DB.addStockHoles(qdb);
                            DB.addStockStartingPoints(qdb);
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();
            }
        });
    }

    private class RunTests extends AsyncTask<Void, Void, Void>
    {
        //Runs tests in background
        protected Void doInBackground(Void... params)
        {
            //Delete previous testDB
            context.deleteDatabase("testdb.s3db");

            //Init new DB
            DB database = new DB(context, "testdb.s3db");
            qdb = database.getWritableDatabase();

            for(int i = 0; i < testList.size(); i++)
            {
                testList.get(i).setPassed(testList.get(i).runTest(qdb));
                cleanUpTestDB();
            }

            return null;
        }

        //Cleans testDb
        public void cleanUpTestDB()
        {
            //Delete previous testDB
            context.deleteDatabase("testdb.s3db");
            //Init new DB
            DB database = new DB(context, "testdb.s3db");
            qdb = database.getWritableDatabase();
        }

        protected void onPostExecute(Void v)
        {
            testListView.setAdapter(adapter);
            testButton.setText(R.string.test_activity_test_button);
        }

        protected void onPreExecute()
        {
            testButton.setText(R.string.test_activity_test_button_testing);
        }
    }
}
