package chris.discgolf;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestingClass
{
    String req_id;
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

        list.add(new testAddStockPlayers());
        list.add(new testAddStockCourses());
        list.add(new testAddStockHoles());
        list.add(new testAddStartingPoints());
        list.add(new testAddGames());
        list.add(new testAddStartingPointScores());
        list.add(new testAddCourseScores());
        list.add(new testDbIsEmpty());
        list.add(new testGameObjectSave());
        list.add(new testNextGameId());
        list.add(new testGetCourseWithID());
        list.add(new testInsertCourseScoreList());
        list.add(new testPlayerCourseAverage());
        list.add(new testPlayerCourseBest());
        list.add(new testGetCourseScoresForGame());
        list.add(new testGetHolesForCourse());
        list.add(new testGetPlayerStartingPointBest());
        list.add(new testGetPlayerStartingPointAvg());
        list.add(new testGetPlayerWithId());
        list.add(new testGetTeesForHole());
        list.add(new holeCanGetStartingPointNames());
        list.add(new holeCanGetSpByName());
        list.add(new testCourseClassCanGextNextAndPrevious());
        list.add(new testCourseScoreCanIncAndDec());
        list.add(new testPlayerListClassCanGetNames());
        list.add(new testPlayerListCanGetFirstPlayer());
        list.add(new testPlayerListCanGetNextAndPrev());
        list.add(new testCourseListCanGetNames());
        list.add(new testCourstListCanAddCourseToList());
        list.add(new testCourseListCanGetCourseArray());
        list.add(new testDatabaseCanClearAllTables());
        list.add(new testNextAndPreviousHoleScoreLists());
        list.add(new testIncrementAndDecrementPlayerCourseScore());
        list.add(new testGetPlayerCourseScore());
        list.add(new testCheckLastHoleInCourse());

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

    /* !!!!!!! TESTING TEMPLATE !!!!!!!
    ===================================
    public class extends TestingClass
    {
        public ()
        {
            req_id = ;
            testID = ;
            description = "";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add

            //
        }

    }

    ==================================
    */

    public class testAddStockPlayers extends TestingClass
    {
        public testAddStockPlayers()
        {
            req_id = "36+59+60";
            testID = 1;
            description = "The database class contains a method for adding stock players, addPlayers.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add in players with method, should add 4 players
        DB.addPlayers(db);

        //Retrieve players from db
        List<Player> playerList = DB.getAllPlayersList(db);

        //If size = 4, all players were added to DB
        if(playerList.size() == 4)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}

    public class testAddStockCourses extends TestingClass
    {
        public testAddStockCourses()
        {
            req_id = "37+48+51";
            testID = 2;
            description = "The database class contains a method for adding stock courses, addCourses.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add courses using method, should add 4 courses
            DB.addCourses(db);

            //get courses from DB
            List<Course> cList = DB.getCourses(db);

            //If list has 4 courses, test succeeds
            if(cList.size() == 4)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

}

    public class testAddStockHoles extends TestingClass
    {
        public testAddStockHoles()
        {
            req_id = "38+95+96";
            testID = 3;
            description = "The database class contains a method for adding stock holes, addHoles.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add courses to database, add holes to database, should be count 6 holes
            DB.addCourses(db);
            DB.addHoles(db);

            //Get all holes in DB
            List<Hole> hList = DB.getAllHoles(db);

            //Return true if there are 6 holes in the list
            if(hList.size() == 6)
            {
                return true;
            }
            return false;
        }

    }

    public class testAddStartingPoints extends TestingClass
    {
        public testAddStartingPoints ()
        {
            req_id = "39+97+98";
            testID = 4;
            description = "The database class contains a method for adding stock startingPoints, addStartingPoints.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add courses, holes, startingpoints. Count of SP = 4
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);

            //Get all starting points from db
            List<HoleStartingPoint> hsList = DB.getAllStartingPoints(db);

            //If size = 4, return true
            if(hsList.size() == 4)
            {
                return true;
            }
            return false;
        }

    }

    public class testAddGames extends TestingClass
    {
        public testAddGames()
        {
            req_id = "40+45+99";
            testID = 5;
            description = "The database class contains a method for adding stock Games, addGames.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add startingPoints, holes, courses, players, games. Games count should be 2
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);
            DB.addPlayers(db);
            DB.addGames(db);

            //Get all games from db as list
            List<Game> gameList = DB.getAllGames(db);

            //If count == 2, return true
            if(gameList.size() == 2)
            {
                return true;
            }
            return false;
        }

    }

    public class testAddStartingPointScores extends TestingClass
    {
        public testAddStartingPointScores()
        {
            req_id = "41+100+104";
            testID = 6;
            description = "The database class contains a method for adding stock startingPointScores, addStartingPointScores.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add courses, holes, startingpoints, players, games, and startingPointScores. Count = 2
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);
            DB.addPlayers(db);
            DB.addGames(db);
            DB.addStartingPointScores(db);

            //Get all Starting point scores from db as list
            List<HoleScore> hsL = DB.getAllStartingPointScores(db);

            //If size = 2, return true
            if(hsL.size() == 2)
            {
                return true;
            }
            return false;
        }

    }

    public class testAddCourseScores extends TestingClass
    {
        public testAddCourseScores()
        {
            req_id = "42+102+105";
            testID = 7;
            description = "The database class contains a method for adding stock Course scores, addCourseScores.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add Courses, holes, starting points, players, games, courseScores to DB. Coursescores count = 2
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addCourseScores(db);

        //Get all coursescores from db
        List<CourseScore> csList = DB.getAllCourseScores(db);

        if(csList.size() == 2)
        {
            return true;
        }
        return false;
    }

}

    public class testDbIsEmpty extends TestingClass
    {
        public testDbIsEmpty()
        {
            req_id = "43";
            testID = 8;
            description = "The database class contains a method for checking if it is empty, dbIsEmpty.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //DB has already been cleaned at this point.

        if(DB.dbIsEmpty(db))
        {
            return true;
        }
        return false;
    }

    }

    public class testGameObjectSave extends TestingClass
    {
        public testGameObjectSave()
        {
            req_id = "44+46+58+106+9+64";
            testID = 9;
            description = "The database class contains a method for saving the data held by a game object.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add course and players to db
        DB.addPlayers(db);
        DB.addCourses(db);

        Course c = DB.getCourses(db).get(0);
        PlayerList pList = new PlayerList();
        pList.setWithList(DB.getAllPlayersList(db));

        //Create course score list
        List<CourseScore> csL = new ArrayList<CourseScore>();
        csL.add(new CourseScore(pList.getFirstPlayer(), c));

        //Create Game Object and data
        Game g = new Game(c, pList);
        g.setCourseScoreList(csL);

        //Save game into DB
        DB.saveGameData(db, g);

        //Get game from db
        Game f = DB.getGameById(db, g.getID());

        //If info matches, return true
        if(f.getCourse().getCourseName().equals(g.getCourse().getCourseName()) && f.getPlayerList().getPlayerList().get(0).getFirstName().equals(g.getPlayerList().getPlayerList().get(0).getFirstName()))
        {
            return true;
        }
        return false;
    }

}

    public class testNextGameId extends TestingClass
    {
        public testNextGameId()
        {
            req_id = "47";
            testID = 10;
            description = "The database class contains a method for getting the next ID a newly inserted game will receive.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add everything games require, as well as 2 games. Next game ID should = 3.
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);
            DB.addPlayers(db);
            DB.addGames(db);

            //Get next game id
            int nextId = DB.getNextGameId(db);

            if(nextId == 3)
            {
                return true;
            }
            return false;
        }

    }

    public class testGetCourseWithID extends TestingClass
    {
        public testGetCourseWithID()
        {
            req_id = "49";
            testID = 11;
            description = "The database contains a method for getting a course object using a course id.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add courses, holes, startingpoints into DB. First course has name = "ECU North Rec. Complex"
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);

        //Get first course out
        Course c = DB.getCourseByID(1, db);

        if(c.getCourseName().equals("ECU North Rec. Complex")) return true;
        return false;
    }

}

    public class testInsertCourseScoreList extends TestingClass
    {
        public testInsertCourseScoreList()
        {
            req_id = "50";
            testID = 12;
            description = "The database class contains a method to insert a list of coursescore objects into the database.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //add everything required for games to db
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);

        //Create a list of course scores
        Course c = DB.getCourses(db).get(0);
        PlayerList pList = new PlayerList();
        pList.setWithList(DB.getAllPlayersList(db));

        //Create course score list, add in two course scores
        List<CourseScore> csL = new ArrayList<CourseScore>();
        csL.add(new CourseScore(pList.getFirstPlayer(), c));
        csL.add(new CourseScore(pList.getNextPlayer(pList.getFirstPlayer()), c));

        Game g = new Game(c, pList);
        g.setCourseScoreList(csL);

        //Insert game comtaining CS into database
        DB.insertCourseScoresIntoDatabase(db, g);

        List<CourseScore> csTestList = DB.getAllCourseScores(db);

        //Check if list has 2 course scores in it
        if(csTestList.size() == 2) return true;
        return false;
    }

    }

    public class testPlayerCourseAverage extends TestingClass
    {
        public testPlayerCourseAverage()
        {
            req_id = "52";
            testID = 13;
            description = "The database class contains a method to get a player's course average from a certain tee.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add courses, holes, startingpoints, players, games, and courseScores to the database. Player should have avg = 4
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addCourseScores(db);

        //Get players avg course score
        int courseScore = DB.getCourseAverageForTee(1, 1, "White", db);

        //Check that course score is correct.
        if(courseScore == 4)
        {
            return true;
        }
        return false;
    }

}

    public class testPlayerCourseBest extends TestingClass
    {
        public testPlayerCourseBest()
        {
            req_id = "53";
            testID = 14;
            description = "The database contains a method to get a player's best course score from a certain tee.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add everything needed for course scores, and course scores. Player's best is 2.
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);
            DB.addPlayers(db);
            DB.addGames(db);
            DB.addCourseScores(db);

            //Get player best
            int playerBest = DB.getCourseBestForTee(1, 1, "White", db);

            if(playerBest == 2)return true;
            return false;
        }

    }

    public class testGetCourseScoresForGame extends TestingClass
    {
        public testGetCourseScoresForGame()
        {
            req_id = "54";
            testID = 15;
            description = "The database class contains a method to get a list of coursescores for a particular game.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add in everything needed for a game, as well as coursescores
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addCourseScores(db);

        //Get corusescores for Game 1
        List<CourseScore> csList = DB.getCourseScoresForGame(1, db);

        //Game 1 should have 1 coursescore
        if(csList.size() == 1)
        {
            return true;
        }
        return false;
    }

}

    public class testGetHolesForCourse extends TestingClass
    {
        public testGetHolesForCourse()
        {
            req_id = "55";
            testID = 16;
            description = "The database class contains a method to get a list of Hole objects pretaining to a specific course.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add courses, holes, startingpoints
            DB.addCourses(db);
            DB.addHoles(db);
            DB.addStartingPoints(db);

            //Course 1 has 3 holes, get hole list
            List<Hole> hl = DB.getCourseHoles(1, db);

            if(hl.size() == 3)
            {
                return true;
            }
            return false;
        }

    }

    public class testGetPlayerStartingPointBest extends TestingClass
    {
        public testGetPlayerStartingPointBest()
        {
            req_id = "56";
            testID = 17;
            description = "The database class contains a method to get a player's best score from a particular starting point on a specific hole.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add everything needed for games, and holescores. Player's best holescore should be 1
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addStartingPointScores(db);

        //Get best hole score
        int best = DB.getStartingPointBest(1, 1, "White", 1, db);

        if(best == 1) return true;
        return false;
    }

}

    public class testGetPlayerStartingPointAvg extends TestingClass
    {
        public testGetPlayerStartingPointAvg()
        {
            req_id = "57";
            testID = 18;
            description = "The database class contains a method to get a player's average score from a particular starting point on a particular hole.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add everything needed for games, and holescores. Player's avg holescore should be 2
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addStartingPointScores(db);

        //Get best hole score
        int avg = DB.getStartingPointAvg(1, 1, "White", 1, db);

        if(avg == 2) return true;
        return false;
    }

}

    public class testGetPlayerWithId extends TestingClass
    {
        public testGetPlayerWithId()
        {
            req_id = "61";
            testID = 19;
            description = "The database class contains a method to get a player object using a player id.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            //Add players to DB
            DB.addPlayers(db);

            //Player 1 has name Chris Helms, get player and check
            Player p = DB.getPlayerById(1, db);

            if(p.getFirstName().equals("Chris"))
            {
                return true;
            }
            return false;
        }

}

    public class testGetTeesForHole extends TestingClass
    {
        public testGetTeesForHole()
        {
            req_id = "63";
            testID = 20;
            description = "The database contains a method for getting all tees for a specific hole.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        //Add Courses, holes, and startingPoints
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);

        //Hole 1 on course 1 has 2 tees
        List<HoleStartingPoint> tees = DB.getHoleStartingPoints(1, 1, db);

        if(tees.size() == 2)
        {
            return true;
        }
        return false;
    }

}

    public class holeCanGetStartingPointNames extends TestingClass
    {
        public holeCanGetStartingPointNames()
        {
            req_id = "67";
            testID = 21;
            description = "The Hole class contains a method for getting all of its starting point names as a list of strings.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        List<HoleStartingPoint> hspL = new ArrayList<HoleStartingPoint>();
        hspL.add(new HoleStartingPoint("White", 360));
        hspL.add(new HoleStartingPoint("Blue", 240));

        //Create hole
        Hole h = new Hole(3, 3, hspL);

        //get list of names
        List<String> nameList = h.getStartingPointNames();

        if(nameList.get(0).equals("White") && nameList.get(1).equals("Blue"))
        {
            return true;
        }
        return false;
    }

}

    public class holeCanGetSpByName extends TestingClass
    {
        public holeCanGetSpByName()
        {
            req_id = "68";
            testID = 22;
            description = "The Hole classs has a method for getting a hole starting point object from its list by name.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        List<HoleStartingPoint> hspL = new ArrayList<HoleStartingPoint>();
        hspL.add(new HoleStartingPoint("White", 360));
        hspL.add(new HoleStartingPoint("Blue", 240));

        //Create hole
        Hole h = new Hole(3, 3, hspL);

        HoleStartingPoint hsp = h.getStartingPointByName("White");

        if(hsp.getName().equals("White"))
        {
            return true;
        }
        return false;
    }

}

    public class testCourseClassCanGextNextAndPrevious extends TestingClass
    {
        public testCourseClassCanGextNextAndPrevious ()
        {
            req_id = "70";
            testID = 23;
            description = "The Course class contains methods for getting the next hole in its list by passing in a hole object, and getting the previous hole in the list by passing in a hole object.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        List<Hole> holeList = new ArrayList<Hole>();

        Hole a = new Hole(1, 3, null);
        Hole b = new Hole(2, 3, null);

        holeList.add(a);
        holeList.add(b);

        Course c = new Course("Course", "City", "State");
        c.setHoleList(holeList);

        if(c.getNextHole(a) == b)
        {
            if(c.getPreviousHole(b) == a)
            {
                return true;
            }
        }

        return false;
    }

}

    public class testCourseScoreCanIncAndDec extends TestingClass
    {
        public testCourseScoreCanIncAndDec()
        {
            req_id = "73";
            testID = 24;
            description = "The CourseScore class has methods for incrementing and decrementing its score.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addPlayers(db);
        DB.addCourses(db);
        CourseScore c = new CourseScore(DB.getPlayerById(1, db), DB.getCourseByID(1, db));
        c.setScore(0);

        if(c.decrementScore() == -1)
        {
            if(c.incrementScore() == 0)
            {
                return true;
            }
        }
        return false;
    }

}

    public class testPlayerListClassCanGetNames extends TestingClass
    {
        public testPlayerListClassCanGetNames()
        {
            req_id = "79";
            testID = 25;
            description = "The PlayerList class has a method for getting a list of strings representing the names of the players in its list.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addPlayers(db);

        PlayerList p = new PlayerList();
        p.setWithList(DB.getAllPlayersList(db));

        List<String> names = p.getPlayerNames();

        if(names.size() == 4)
        {
            return true;
        }
        return false;
    }

}

    public class testPlayerListCanGetFirstPlayer extends TestingClass
    {
        public testPlayerListCanGetFirstPlayer ()
        {
            req_id = "80";
            testID = 26;
            description = "The PlayerList class has a method for getting the first player object in its list.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addPlayers(db);

        PlayerList p = new PlayerList();
        p.setWithList(DB.getAllPlayersList(db));

        if(p.getFirstPlayer().getFirstName().equals("Chris"))
        {
            return true;
        }
        return false;
    }

}

    public class testPlayerListCanGetNextAndPrev extends TestingClass
    {
        public testPlayerListCanGetNextAndPrev ()
        {
            req_id = "81";
            testID = 27;
            description = "The PlayerList class has methods for getting the next and previous players in its list based on a passed in player.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addPlayers(db);
        Player a = DB.getPlayerById(1, db);
        Player b = DB.getPlayerById(2, db);
        List<Player> pList = new ArrayList<Player>();
        pList.add(a);
        pList.add(b);
        PlayerList p = new PlayerList();
        p.setWithList(pList);

        if(p.getNextPlayer(a) == b)
        {
            if(p.getPreviousPlayer(b) == a)
            {
                return true;
            }
        }
        return false;
    }

}

    public class testCourseListCanGetNames extends TestingClass
    {
        public testCourseListCanGetNames ()
        {
            req_id = "83";
            testID = 28;
            description = "The CourseList contains a method for getting an array of strings representing the names of the courses in its list.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addCourses(db);

        CourseList c = new CourseList();
        c.setCourseList(DB.getCourses(db));

        String[] cNames = c.getCourseNames();

        if(cNames.length == 4) return true;
        return false;
    }

}

    public class testCourstListCanAddCourseToList extends TestingClass
    {
        public testCourstListCanAddCourseToList ()
        {
            req_id = "84";
            testID = 29;
            description = "The CourseList contains a method for adding a Course object to its list.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        CourseList c = new CourseList();
        c.setCourseList(new ArrayList<Course>());
        int original = c.getCourseList().size();

        c.addCourse(new Course("Course", "Elon", "NC"));
        int second = c.getCourseList().size();

        if(second - 1 == original)
        {
            return true;
        }
        return false;
    }

}

    public class testCourseListCanGetCourseArray  extends TestingClass
    {
        public testCourseListCanGetCourseArray()
        {
            req_id = "85";
            testID = 30;
            description = "The CourseList contains a method for getting an array of Course objects representing all the Courses in its list.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            DB.addCourses(db);

            CourseList c = new CourseList();
            c.setCourseList(DB.getCourses(db));

            Course[] courses = c.getCourseArray();

            if(courses.length == 4)
            {
                return true;
            }
            return false;
        }

    }

    public class testDatabaseCanClearAllTables extends TestingClass
    {
        public testDatabaseCanClearAllTables()
        {
            req_id = "103";
            testID = 31;
            description = "The Database class should contain a method for cleaning out all tables in DB.";
            passed = false;
        }

    public boolean runTest(SQLiteDatabase db)
    {
        DB.addCourses(db);
        DB.addHoles(db);
        DB.addStartingPoints(db);
        DB.addPlayers(db);
        DB.addGames(db);
        DB.addCourseScores(db);
        DB.addStartingPointScores(db);

        DB.cleanOutDB(db);

        if(DB.dbIsEmpty(db))
        {
            return true;
        }
        return false;
    }

}

    public class testNextAndPreviousHoleScoreLists extends TestingClass
    {
        public testNextAndPreviousHoleScoreLists()
        {
            req_id = "110";
            testID = 32;
            description = "The Game class should have methods for getting the next and previos HoleScore List.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            List<HoleScore> a = new ArrayList<HoleScore>();
            a.add(new HoleScore());

            List<HoleScore> b = new ArrayList<HoleScore>();
            b.add(new HoleScore());
            b.add(new HoleScore());

            List<List<HoleScore>> hs = new ArrayList<List<HoleScore>>();
            hs.add(a);
            hs.add(b);

            DB.addCourses(db);
            DB.addPlayers(db);

            PlayerList p = new PlayerList();
            p.setWithList(DB.getAllPlayersList(db));

            Game g = new Game( DB.getCourses(db).get(1), p);

            g.setHSLList(hs);

            if(g.getNextHoleScoreList(a, 2, "white") == b)
            {
                if(g.getPreviousHoleScoreList(b) == a)
                {
                    return true;
                }
            }
            return false;
        }

    }

    public class testIncrementAndDecrementPlayerCourseScore extends TestingClass
    {
        public testIncrementAndDecrementPlayerCourseScore()
        {
            req_id = "113";
            testID = 33;
            description = "The Game class should have methods for incrementing and decrementing a player's course score.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            DB.addPlayers(db);
            DB.addCourses(db);

            PlayerList p = new PlayerList();
            p.setWithList(DB.getAllPlayersList(db));
            Game g = new Game(DB.getCourseByID(1, db), p );

            if(g.incrementPlayerCourseScore(p.getFirstPlayer()) == 1)
            {
                if(g.decrementPlayerCourseScore(p.getFirstPlayer()) == 0)
                {
                    return true;
                }
            }
            return false;
        }

    }

    public class testGetPlayerCourseScore extends TestingClass
    {
        public testGetPlayerCourseScore ()
        {
            req_id = "112";
            testID = 34;
            description = "The Game class should have a method for getting a players score for the current hole.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db)
        {
            DB.addPlayers(db);
            DB.addCourses(db);

            PlayerList p = new PlayerList();
            p.setWithList(DB.getAllPlayersList(db));
            Game g = new Game(DB.getCourseByID(1, db), p );

            if(g.getPlayerCourseScore(p.getFirstPlayer()) == 0)
            {
                return true;
            }
            return false;
        }
    }

    public class testCheckLastHoleInCourse extends TestingClass {
        public testCheckLastHoleInCourse()
        {
            req_id = "115";
            testID = 35;
            description = "The Game class should have a method for checking whether a hole is the last hole in a course.";
            passed = false;
        }

        public boolean runTest(SQLiteDatabase db) {
            DB.addPlayers(db); //Add everything needed for game
            DB.addCourses(db);
            DB.addHoles(db);

            PlayerList p = new PlayerList();
            p.setWithList(DB.getAllPlayersList(db));
            Game g = new Game(DB.getCourseByID(1, db), p);

            //Check number of holes in course, get last hole
            int size = g.getCourse().getHoleList().size();
            Hole h = g.getCourse().getHoleList().get(size - 1);

            //Pas last hole in to method, check if it returns true
            if (g.isLastHoleInCourse(h)) {
                return true;
            }
            return false;
        }
    }
}
