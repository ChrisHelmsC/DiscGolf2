package chris.discgolf;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/27/2016.
 */
public class TestingClass
{
    int req_id;
    int testID;
    String description;
    boolean passed;

    public boolean runTest(SQLiteDatabase db)
    {
        return false;
    }

    //Used for subclasses
    public TestingClass()
    {

    }

    public List<TestingClass> getAllTestsAsList()
    {
        List<TestingClass> list = new ArrayList<TestingClass>();

        list.add(new canGetAllCourses());
        list.add(new canGetAllPlayers());
        return list;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /*=============================================================================
                        !!!TESTING STARTS HERE!!!!
     */


    public class canGetAllCourses extends TestingClass
    {
        public canGetAllCourses()
        {
            req_id = 1;
            testID = 1;
            description = "Tests whether the database can retrieve a list of all the courses it is currently storing.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Clean out courses
            DB.deleteAllFromCourse(db);

            //Add precreated courses to db, 4 in count
            DB.addCourses(db);

            //Get courses
            List<Course> courseList  = DB.getCourses(db);

            if(courseList.size() == 4) return true;

            return false;
        }
    }

    public class canGetAllPlayers extends TestingClass
    {
        public canGetAllPlayers()
        {
            req_id = 2;
            testID = 2;
            description = "Tests whether the database can retrieve a list of all players it is currently storing.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Delete all players
            DB.deleteAllPlayers(db);

            //Add 4 players to DB
            DB.addPlayers(db);

            //Get all players
            List<Player> pList = DB.getAllPlayersList(db);

            //If 4 players, test passed
            if(pList.size() == 4)
            {
                return true;
            }
            return false;
        }
    }
}
