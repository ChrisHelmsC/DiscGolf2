package chris.discgolf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chris on 4/3/2016.
 */
public class DB extends SQLiteOpenHelper
{
    final static int DB_VERSION = 1;                //Holds db version
    static String DB_NAME = "mydb.s3db";      //db name
    Context context;

    final static int NO_RESULTS = -999;                    //Used for when a table has no results

    /*Checks if db exists. IF it does not, calls oncreate.
    *If it does, checks for update, calls onUpgrade
    */
    public DB(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        //Store context
        this.context = context;
    }

    public DB(Context context, String name)
    {
        super(context, name, null, DB_VERSION);
        DB_NAME = name;
        //Store context
        this.context = context;
    }

    public static String getDbName() {
        return DB_NAME;
    }

    /*
    FOR SQL FIDDLE
    CREATE TABLE courses ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, state TEXT NOT NULL, city TEXT NOT NULL);
CREATE TABLE holes ( course_id INTEGER NOT NULL, number INTEGER NOT NULL, par INTEGER NOT NULL, FOREIGN KEY(course_id) REFERENCES courses(_id), PRIMARY KEY(course_id, number));
CREATE TABLE startingPoints ( course_id INTEGER NOT NULL, hole_number INTEGER NOT NULL, name TEXT NOT NULL, length INTEGER NOT NULL, FOREIGN KEY(course_id) REFERENCES holes(course_id), FOREIGN KEY(hole_number) REFERENCES holes(number), PRIMARY KEY(course_id, hole_number, name));
CREATE TABLE players ( _id INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT NOT NULL, lastName TEXT NOT NULL, timesPlayed INTEGER NOT NULL);
CREATE TABLE games (_id INTEGER PRIMARY KEY AUTOINCREMENT, course_id INTEGER NOT NULL, datePlayed DATE , FOREIGN KEY(course_id) REFERENCES courses(_id));
CREATE TABLE startingPointScores ( game_id INTEGER, course_id INTEGER, hole_number INTEGER, startingpoint_name TEXT, INTEGER, player_id INTEGER, score INTEGER, FOREIGN KEY(course_id) REFERENCES courses(_id), FOREIGN KEY(game_id) REFERENCES games(_id), FOREIGN KEY(hole_number) REFERENCES holes(number), FOREIGN KEY(startingpoint_name) REFERENCES startingPoints(name), FOREIGN KEY(player_id) REFERENCES players(id), PRIMARY KEY (course_id, game_id, hole_number, startingpoint_name, player_id));
     */

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

        //Create Game Table
        db.execSQL("CREATE TABLE games (_id INTEGER PRIMARY KEY AUTOINCREMENT, course_id INTEGER NOT NULL, datePlayed DATE , FOREIGN KEY(course_id) REFERENCES courses(_id))");

        //Create startingPointScore table
        db.execSQL("CREATE TABLE startingPointScores ( game_id INTEGER, course_id INTEGER, hole_number INTEGER, startingpoint_name TEXT, player_id INTEGER, score INTEGER, FOREIGN KEY(course_id) REFERENCES courses(_id), FOREIGN KEY(game_id) REFERENCES games(_id), FOREIGN KEY(hole_number) REFERENCES holes(number), FOREIGN KEY(startingpoint_name) REFERENCES startingPoints(name), FOREIGN KEY(player_id) REFERENCES players(id), PRIMARY KEY (course_id, game_id, hole_number, startingpoint_name, player_id))");

        //Create courseScores table
        db.execSQL("CREATE TABLE courseScores ( game_id INTEGER, course_id INTEGER, player_id INTEGER, tee_name TEXT, score INTEGER, FOREIGN KEY(game_id) REFERENCES games(_id), FOREIGN KEY(course_id) REFERENCES courses(_id), FOREIGN KEY (player_id) REFERENCES players(_id), FOREIGN KEY (tee_name) REFERENCES startingPoints(name), PRIMARY KEY (game_id, course_id, player_id, tee_name))");

        //Create played_in table
        db.execSQL("CREATE TABLE playedIN(game_id INTEGER, player_id INTEGER, FOREIGN KEY (game_id) REFERENCES games(_id), FOREIGN KEY (player_id) REFERENCES players(_id), PRIMARY KEY (game_id, player_id))");
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

        db.execSQL("INSERT into startingPoints (course_id, hole_number, name, length) values (1, 2, 'White', 290);");
        db.execSQL("INSERT into startingPoints (course_id, hole_number, name, length) values (1, 2, 'Blue', 320);");
    }

    public static void addGames(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO games (course_id, datePlayed) values (2, '2008/08/02');");
    }

    public static void addStartingPointScores(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO startingPointScores VALUES ( 1, 1, 1, 'White',  1, 1);");
        db.execSQL("INSERT INTO startingPointScores VALUES ( 2, 1, 1, 'White',  1, 2);");
    }

    public static void addCourseScores(SQLiteDatabase db)
    {
        db.execSQL("INSERT INTO courseScores VALUES ( 1, 1, 1, 'White', 6);");
        db.execSQL("INSERT INTO courseScores VALUES ( 2, 1, 1, 'White', 2);");
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

    public static void saveGameData(SQLiteDatabase db, Game game)
    {
        insertGameIntoDatabase(db, game);
        insertCourseScoresIntoDatabase(db, game);
        insertHoleScoresIntoDatabase(db, game);
    }

    /*============================================================================================================
     * !!! THE FOLLOWING FUNCTIONS ARE FOR GAMES
     */

    public static List<Game> getAllGames(SQLiteDatabase db)
    {
        List<Game> gameList = new ArrayList<Game>();

        Cursor gameCursor = db.rawQuery("SELECT * FROM GAMES;", null);

        gameCursor.moveToFirst();

        PlayerList pl;
        Game temp;
        while(gameCursor.getPosition() < gameCursor.getCount())
        {
            //Set player list with players for game
            pl = new PlayerList();
            pl.setWithList(getPlayersInGame(gameCursor.getInt(0), db));

            //Add new game to list
            temp = new Game(getCourseByID(gameCursor.getInt(1), db), pl);
            temp.setID(gameCursor.getInt(0));
            gameList.add(temp);
            gameCursor.moveToNext();
        }

        return gameList;
    }

    public static void insertGameIntoDatabase(SQLiteDatabase db, Game game)
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).substring(0, 8);
        String finalTimeStamp = timeStamp.substring(0,4) + "/" + timeStamp.substring(4, 6) + "/" + timeStamp.substring(6, 8);
        int courseId = game.getCourse().getId();

        //Set game ID because it does not yet have one
        game.setID(getNextGameId(db));

        db.execSQL("INSERT INTO games (course_id, datePlayed) values (" + Integer.toString(courseId) + ", " + finalTimeStamp + ");");
        insertPlayersIntoPlayedIn(game.getPlayerList().getPlayerList(), game.getID(), db);
    }

    //Gets ID that will be given to next game inserted into DB
    private static int getNextGameId(SQLiteDatabase db)
    {
        Cursor gameCursor = db.rawQuery("SELECT * FROM GAMES", null);
        gameCursor.moveToLast();
        return gameCursor.getInt(0) + 1;
    }

    /*============================================================================================================
     * !!! THE FOLLOWING FUNCTIONS ARE FOR COURSES
     */

    //Deletes all records from course table
    public static void deleteAllFromCourse(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM COURSES");
    }

    //Returns course with _id equal to id;
    private static Course getCourseByID(int id, SQLiteDatabase db)
    {
        Cursor courseCursor = db.rawQuery("SELECT * FROM COURSES WHERE _id = " + Integer.toString(id)+";", null);

        courseCursor.moveToFirst();

        return new Course(courseCursor.getInt(0), courseCursor.getString(1), courseCursor.getString(3), courseCursor.getString(2), getCourseHoles(courseCursor.getInt(0), db));
    }

    //Inserts the course scores from game g into the database
    private static void insertCourseScoresIntoDatabase(SQLiteDatabase db, Game g)
    {
        List<CourseScore> csl = g.getCourseScoreList();

        String gameID = Integer.toString(g.getID());
        String courseID = Integer.toString(g.getCourse().getId());
        String playerID, teeName, score;


        for(CourseScore cs: csl)
        {
            playerID = Integer.toString(cs.getPlayer().getId());
            teeName = cs.getGameTee();
            score = Integer.toString(cs.getScore());

            db.execSQL("INSERT INTO courseScores VALUES (" + gameID + ", " + courseID + ", " + playerID + ", '" + teeName + "', " + score +  ");");
            int i = 0;
        }
    }

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

    //Returns course average from a certain Tee
    public static int getCourseAverageForTee(int course_id, int player_id, String tee_name, SQLiteDatabase db) {
        Cursor testCursor = db.rawQuery("SELECT score FROM courseScores WHERE course_id = " + Integer.toString(course_id) +
                " AND player_id = " + Integer.toString(player_id) + " AND tee_name = '" + tee_name + "';", null);

        if(testCursor.getCount() == 0)
        {
            return NO_RESULTS;
        }

        Cursor courseAvgCursor = db.rawQuery("SELECT avg(score) FROM courseScores WHERE course_id = " + Integer.toString(course_id) +
                " AND player_id = " + Integer.toString(player_id) + " AND tee_name = '" + tee_name + "';", null);

        courseAvgCursor.moveToFirst();

        double roundedScore = Math.round(courseAvgCursor.getDouble(0));

        return (int)roundedScore;
    }

    //Returns course best for specific tee on specific player
    public static int getCourseBestForTee(int course_id, int player_id, String tee_name, SQLiteDatabase db)
    {
        Cursor bestCursor = db.rawQuery("SELECT score FROM courseScores WHERE course_id = " + Integer.toString(course_id) +
                " AND player_id = " + Integer.toString(player_id) + " AND tee_name = '" + tee_name + "' ORDER BY score ASC;", null);

        if(bestCursor.getCount() <= 0)
        {
            return NO_RESULTS;
        }

        bestCursor.moveToFirst();

        return bestCursor.getInt(0);
    }

    //Returns all course scores for a specific game
    public static List<CourseScore> getCourseScoresForGame(int gameID, SQLiteDatabase db)
    {
        List<CourseScore> csList = new ArrayList<CourseScore>();

        Cursor csCursor = db.rawQuery("SELECT * FROM COURSESCORES WHERE game_id = " + Integer.toString(gameID)+";" ,null);

        csCursor.moveToFirst();

        while(csCursor.getPosition() < csCursor.getCount())
        {
            csList.add(new CourseScore(getPlayerById(csCursor.getInt(2), db), getCourseByID(csCursor.getInt(1), db)));
            csCursor.moveToNext();
        }

        return csList;
    }

    /*==================================================================================================================
     * !!!! THE FOLLOWING FUNCTIONS ARE FOR HOLES
     */

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

    //Returns best score a player got on a specific starting point
    public static int getStartingPointBest(int courseId, int holeNumber, String spName, int playerId, SQLiteDatabase db)
    {
        Cursor bestCursor = db.rawQuery("Select startingPointScores.score " +
                "FROM startingPointScores " +
                "WHERE course_id = " + Integer.toString(courseId) +
                " AND hole_number = " + Integer.toString(holeNumber) +
                " AND startingpoint_name = '" + spName + "'" +
                " AND player_id = " + Integer.toString(playerId) +
                " ORDER BY score ASC;", null);

        if(bestCursor.getCount() ==0)
        {
            return NO_RESULTS;
        }

        //Move cursor to first position
        bestCursor.moveToFirst();

        return bestCursor.getInt(0);
    }

    //Returns players average score on a specific hole from a certain starting point
    public static int getStartingPointAvg(int courseId, int holeNumber, String spName, int playerId, SQLiteDatabase db)
    {
        Cursor checkCursor = db.rawQuery("Select score " +
                "FROM startingPointScores " +
                "WHERE course_id = " + Integer.toString(courseId) +
                " AND hole_number = " + Integer.toString(holeNumber) +
                " AND startingpoint_name = '" + spName + "'" +
                " AND player_id = " + Integer.toString(playerId) +
                ";", null);

        if(checkCursor.getCount() == 0)
        {
            return NO_RESULTS;
        }

        Cursor avgCursor = db.rawQuery("Select avg(score) " +
                "FROM startingPointScores " +
                "WHERE course_id = " + Integer.toString(courseId) +
                " AND hole_number = " + Integer.toString(holeNumber) +
                " AND startingpoint_name = '" + spName + "'" +
                " AND player_id = " + Integer.toString(playerId) +
                ";", null);

        avgCursor.moveToFirst();

        double avgScore = Math.round(avgCursor.getDouble(0));

        return (int)avgScore;
    }

    //Inserts each of the holescores from game into the database
    private static void insertHoleScoresIntoDatabase(SQLiteDatabase db, Game game)
    {
        List<List<HoleScore>> hsLL = game.getHSLList();

        String gameID = Integer.toString(game.getID());
        String courseID = Integer.toString(game.getCourse().getId());
        String holeNumber, startingPointName, playerID, score;

        for(List<HoleScore> HSL: hsLL)
        {
            for(HoleScore hs: HSL)
            {
                holeNumber = Integer.toString(hs.getHoleNumber());
                startingPointName = hs.getHoleName();
                playerID = Integer.toString(hs.getPlayer().getId());
                score = Integer.toString(hs.getScore());

                db.execSQL("INSERT INTO startingpointscores VALUES (" + gameID + ", " + courseID + ", " + holeNumber + ", '" + startingPointName + "', " + playerID + ", " + score + ");");
            }
        }
    }

    /*==================================================================================================================
     * !!!! THE FOLLOWING FUNCTIONS ARE FOR PLAYERS
     */

    //Deletes all players from DB
    public static void deleteAllPlayers(SQLiteDatabase db)
    {
        db.execSQL("DELETE FROM PLAYERS;");
    }

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

    //Gets a specific player by id
    public static Player getPlayerById(int id, SQLiteDatabase db)
    {
        Cursor playerCursor = db.rawQuery("SELECT * FROM PLAYERS WHERE _id = " + Integer.toString(id) + ";", null);

        playerCursor.moveToFirst();

        return new Player(playerCursor.getInt(0), playerCursor.getString(1), playerCursor.getString(2), playerCursor.getInt(3));
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

        /*==================================================================================================================
     * !!!! THE FOLLOWING FUNCTIONS ARE FOR PLAYED_IN
     */

        //Returns a list of players that played in a specific game
        private static List<Player> getPlayersInGame(int gameID, SQLiteDatabase db)
        {
            List<Player> pList = new ArrayList<Player>();
            Cursor playerCursor = db.rawQuery("SELECT players._id, players.firstName, players.lastName, players.timesPlayed FROM playedin, players where playedin.game_id = " + Integer.toString(gameID) + " AND playedin.player_id = players._id;", null);

            playerCursor.moveToFirst();

            while(playerCursor.getPosition() < playerCursor.getCount())
            {
                pList.add(new Player(playerCursor.getInt(0), playerCursor.getString(1), playerCursor.getString(2), playerCursor.getInt(3)));
                playerCursor.moveToNext();
            }

            return pList;
        }

        //Inserts a list of players into the playedin table along with a corresponding game id
        private static void insertPlayersIntoPlayedIn(List<Player> pList, int gameId, SQLiteDatabase db)
        {
            for(int i = 0; i < pList.size(); i++)
            {
                db.execSQL("INSERT INTO playedin values (" + Integer.toString(gameId) + ", " + pList.get(i).getId() + ");");
            }
        }
}
