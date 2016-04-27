package chris.discgolf;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class TestingActivity extends AppCompatActivity
{
    Button testButton;  //Button to start tests
    ListView testListView;  //Listview for seeing tests and results

    List<TestingClass> testList;    //Holds tests
    SQLiteDatabase qdb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        testButton = (Button) findViewById(R.id.test_activity_test_button);
        testListView = (ListView) findViewById(R.id.test_activity_test_list);

        TestingClass t = new TestingClass();
        testList = t.getAllTestsAsList();
        final TestAdapter  adapter = new TestAdapter(this, testList);

        //Delete previous testDB
        this.deleteDatabase("testdb.s3db");
        //Init new DB
        DB database = new DB(this, "testdb.s3db");
        qdb = database.getWritableDatabase();

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                testButton.setText(R.string.test_activity_test_button_testing);
                testListView.setAdapter(adapter);
                testButton.setText(R.string.test_activity_test_button);
            }
        });
    }
}
