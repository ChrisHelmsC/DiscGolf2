package chris.discgolf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chris on 12/17/2015.
 */
public class CourseList
{
    int numberOfCourses;
    List<Course> courseList;

    public CourseList(int nC, List<Course> cL)
    {
        this.numberOfCourses = nC;
        this.courseList = cL;
    }

    public CourseList()
    {
        this.courseList = new ArrayList<Course>();
        this.numberOfCourses = 0;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
        this.numberOfCourses = courseList.size();
    }

    public String[] getCourseNames()
    {
        String[] courseNames = new String[courseList.size()];
        Iterator<Course> courseIterator = courseList.listIterator();
        int position = 0;

        while(courseIterator.hasNext())
        {
            courseNames[position] = courseIterator.next().getCourseName();
            position++;
        }

        return courseNames;
    }

    public void addCourse(Course c)
    {
        courseList.add(c);
        numberOfCourses++;
    }

    public Course[] getCourseArray()
    {
        Course[] courses = new Course[courseList.size()];
        Iterator<Course> courseIT = courseList.iterator();
        int position = 0;

        while(courseIT.hasNext())
        {
            courses[position] = courseIT.next();
            position++;
        }

        return courses;
    }
}
