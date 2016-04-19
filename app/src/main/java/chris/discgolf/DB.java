package chris.discgolf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/3/2016.
 */
public class DB extends SQLiteOpenHelper
{
    final static int DB_VERSION = 1;                //Holds db version
    final static String DB_NAME = "mydb.s3db";      //db name
    Context context;

    /*Checks if db exists. IF it does not, calls oncreate.
    *If it does, checks for update, calls onUpgrade
    */
    public DB(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);

        //Store context
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create course table
        db.execSQL("CREATE TABLE courses ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, state TEXT NOT NULL, city TEXT NOT NULL)");

        //create holes table
        db.execSQL("CREATE TABLE holes ( course_id INTEGER NOT NULL, number INTEGER NOT NULL, par INTEGER NOT NULL, FOREIGN KEY(course_id) REFERENCES courses(_id), PRIMARY KEY(course_id, number))");

        //create starting point table
        db.execSQL("CREATE TABLE startingPoints ( course_id INTEGER NOT NULL, hole_number INTEGER NOT NULL, name TEXT NOT NULL, length INTEGER NOT NULL, FOREIGN KEY(course_id) REFERENCES holes(course_id), FOREIGN KEY(hole_number) REFERENCES holes(number), PRIMARY KEY(course_id, hole_number, name))");

        //Create Player Table
        db.execSQL("CREATE TABLE players ( _id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT NOT NULL, lastName TEXT NOT NULL, timesPlayed INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Adds players for testing purposes
    public static void addPlayers(SQLiteDatabase qdb)
    {
        qdb.execSQL("INSERT INTO players (firstName, lastName, timesPlayed) VALUES ('Chris', 'Helms', 2);");
        qdb.execSQL("INSERT INTO players (firstName, lastName, timesPlayed) VALUES ('John', 'Falconer', 1);");
        qdb.execSQL("INSERT INTO players (firstName, lastName, timesPlayed) VALUES ('Nic', 'Brey', 0);");
        qdb.execSQL("INSERT INTO players (firstName, lastName, timesPlayed) VALUES ('Zach', 'Gorsuch', 1);");
    }

    /*
    * Adds courses for testing purposes
     */
    public static void addCourses(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO courses (name, state, city) VALUES ('ECU North Rec. Complex', 'NC', 'Greenville');");
        db.execSQL("INSERT INTO courses (name, state, city) VALUES ('MedowBrook Park', 'NC', 'Greenville');");
        db.execSQL("INSERT INTO courses (name, state, city) VALUES ('Covenant Church', 'NC', 'Greenville');");
        db.execSQL("INSERT INTO courses (name, state, city) VALUES ('Add New Course', 'Select to Add a New Course', '');");
    }

    /*
    * Adds holes for testing purposes
     */
    public static void addHoles(SQLiteDatabase db)
    {
        db.execSQL("INSERT into holes (course_id, number, par) values (1, 1, 3);");
        db.execSQL("INSERT into holes (course_id, number, par) values (1, 2, 3);");
        db.execSQL("INSERT into holes (course_id, number, par) values (1, 3, 3);");

        db.execSQL("INSERT into holes (course_id, number, par) values (2, 1, 3);");
        db.execSQL("INSERT into holes (course_id, number, par) values (2, 2, 3);");
        db.execSQL("INSERT into holes (course_id, number, par) values (2, 3, 3);");
    }

    public static void addStartingPoints(SQLiteDatabase db)
    {
        db.execSQL("INSERT into startingPoints (course_id, hole_number, name, length) values (1, 1, 'White', 360);");
        db.execSQL("INSERT into startingPoints (course_id, hole_number, name, length) values (1, 1, 'Blue', 400);");
    }

    /*
    * returns true if db is empty, and false if not
     */
    public static boolean dbIsEmpty(SQLiteDatabase db)
    {
        Cursor simpleQuery = db.rawQuery("SELECT * FROM courses;", null);
        if(simpleQuery.getCount()==0)
        {
            return true;
        }
        return false;
    }

    /*============================================================================================================
     * !!! THE FOLLOWING FUNCTIONS ARE FOR COURSES
     */

    /*
     *Returns a list of all courses stored in the database
     */
    public static List<Course> getCourses(SQLiteDatabase db)
    {
        //Query DB for all courses
        Cursor courseCursor = db.rawQuery("Select * from courses;", null);

        //Move cursor to beginning of table
        courseCursor.moveToFirst();

        //Create list to hold courses
        List<Course> courseList = new ArrayList<Course>();

        //Add all courses to list
        int tableSize = courseCursor.getCount();
        Course Temp;
        while(courseCursor.getPosition() < tableSize)
        {
            Temp = new Course(courseCursor.getInt(0), courseCursor.getString(1), courseCursor.getString(2), courseCursor.getString(3));
            courseList.add(Temp);
            courseCursor.moveToNext();
        }

        //Return list
        return courseList;
    }

    /*
    * Returns all holes associated with course using its courseId, in
    * the form of a List<Hole>. Holes are orderd by their hole number.
     */
    public static List<Hole> getCourseHoles(int courseId, SQLiteDatabase db)
    {
        //Create list to store holes
        List<Hole> holeList = new ArrayList<Hole>();

        //Get holes corresponding to course from db, order by hole number ascending
        Cursor courseCursor = db.rawQuery("SELECT * FROM holes WHERE holes.course_id = " + Integer.toString(courseId) +
                " ORDER BY number ASC;", null);

        //Move cursor to first position
        courseCursor.moveToFirst();

        //Get size of table, loop through table,
        int tableSize = courseCursor.getCount();
        while(courseCursor.getPosition() < tableSize)
        {
            //Create new hole from table. Get Hole's starting points using the courseId and the Hole's number.
            holeList.add(new Hole(courseCursor.getInt(1), courseCursor.getInt(2), getHoleStartingPoints(courseId, courseCursor.getInt(1), db)));
            courseCursor.moveToNext();
        }

        return holeList;
    }

    /*==================================================================================================================
     * !!!! THE FOLLOWING FUNCTIONS ARE FOR PLAYERS
     */

    /*
     * Get all players sorted by most times played to least
     */
    public static List<Player> getAllPlayersList(SQLiteDatabase db)
    {
        //Get all players from database, move cursor to first row
        Cursor playerCursor = db.rawQuery("SELECT * FROM players ORDER BY timesPlayed DESC;", null);
        playerCursor.moveToFirst();

        //Create new playerList
        List<Player> playerList = new ArrayList<Player>();

        //Add each player to list
        Player temp;
        int tableSize = playerCursor.getCount();
        while(playerCursor.getPosition() < tableSize)
        {
            temp = new Player(playerCursor.getInt(0),playerCursor.getString(1), playerCursor.getString(2), playerCursor.getInt(3));
            playerList.add(temp);
            playerCursor.moveToNext();
        }

        //Return filled list
        return playerList;
    }

    /*=================================================================================================
    * !!!!!! THE FOLLOWING FUNCTIONS ARE FOR STARTINGPOINTS !!!!!!!!
     */

    /*
     * Returns a List of HoleStartingPoint objects associated with the hole number and
     * courseId passed in. The list is in order of distance.
     */
    public static List<HoleStartingPoint> getHoleStartingPoints(int courseId, int holeNumber, SQLiteDatabase db)
    {
        //Create list for storing HoleStartingPoint objects
        List<HoleStartingPoint> spList = new ArrayList<HoleStartingPoint>();

        //Query DB for starting points
        Cursor spCursor = db.rawQuery("SELECT * FROM startingPoints WHERE startingPoints.course_id = " + Integer.toString(courseId) +
                " AND startingPoints.hole_number = " + Integer.toString(holeNumber) + " ORDER BY length ASC;", null);

        //Move cursor to first entry
        spCursor.moveToFirst();

        //Get table size
        int tableSize = spCursor.getCount();

        //Loop through and add all table entries to list
        while(spCursor.getPosition() < tableSize)
        {
            spList.add(new HoleStartingPoint(spCursor.getString(2), spCursor.getInt(3)));
            spCursor.moveToNext();
        }

        //Return list
        return spList;
    }

}
