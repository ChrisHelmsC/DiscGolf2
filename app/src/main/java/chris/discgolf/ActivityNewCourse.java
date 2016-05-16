package chris.discgolf;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//TODO: 1. Make state all caps
//TODO: 2. Check that all necessary info is present before allowing submission of course
//TODO: 3. Check that tee distance is numeric, limit tee distance to 9999, > 0
//Todo: 4. Check that course does not exist before submission

public class ActivityNewCourse extends AppCompatActivity
{
    Context context;            //Activity context
    ActivityNewCourse acn;
    EditText courseName;        //Course Name Entry
    EditText courseCity;        //Course city entry
    EditText courseState;       //Course state entry

    Button incHole, decHole;    //Buttons for changing hole amounts
    TextView holeCount;         //Textview showing number of holes
    ExpandableListView holeListView;    //Listview for Holes

    List<Hole> holeList;                //Current List of holes
    private int currentNumberOfHoles;   //Current number of holes
    HoleAdapter HA;                     //Adapter for hole listview

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        context = this;
        acn = this;

        //Get edit texts from layout
        courseName = (EditText) findViewById(R.id.new_course_course_name_edittext);
        courseCity = (EditText) findViewById(R.id.new_course_course_city_edittext);
        courseState = (EditText) findViewById(R.id.new_course_course_state_edittext);

        incHole = (Button) findViewById(R.id.new_course_hole_amount_up_button);
        decHole = (Button) findViewById(R.id.new_course_hole_amount_down_button);
        holeCount = (TextView) findViewById(R.id.new_course_hole_amount_textview);

        //Make state all caps
        courseState.setAllCaps(true);

        //Set initial hole count to be 18
        currentNumberOfHoles = Constants.EIGHTEEN_HOLES;

        incHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNumberOfHoles < Constants.MAX_HOLES)
                {
                    currentNumberOfHoles++;
                    refreshHoleList();
                }
            }
        });

        decHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNumberOfHoles > Constants.MIN_HOLES)
                {
                    currentNumberOfHoles--;
                    refreshHoleList();
                }
            }
        });

        refreshHoleList();
        HA = new HoleAdapter(holeList, this);
        holeListView = (ExpandableListView) findViewById(R.id.new_course_hole_information_expandinglistview);
        holeListView.setAdapter(HA);


    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.new_course_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.create_course_cancel_creation_button:
                //TODO create cancel dialog
                break;
            case R.id.create_course_confirm_creation_button:
                //TODO confirm cancel dialog
                confirmSubmissionDialog().show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshHoleList()
    {
        //Create new list if list does not exist
        if(holeList == null)
        {
            holeList = new ArrayList<Hole>();
        }

        //Get current size of holeList
        int size = holeList.size();

        //If the size of the holeList is less than the current number of Holes
        if(size < currentNumberOfHoles)
        {
            //Get difference
            int difference = currentNumberOfHoles - size;
            List<HoleStartingPoint> hsp;

            //Add Holes to end
            for(int i = 0; i < difference; i++)
            {
                hsp = new ArrayList<HoleStartingPoint>();
                hsp.add(new HoleStartingPoint(Constants.DEFAULT_NAME, 0));
                holeList.add(new Hole(holeList.size()+1, Constants.PAR_THREE, hsp));
            }
        }
        else if(size > currentNumberOfHoles)
        {
            //Remove hole from end
            holeList.remove(holeList.size()-1);
        }

        //Notify adapter
        if(HA!=null)
        {
            HA.notifyDataSetChanged();
        }

        //Set hole count text
        holeCount.setText(Integer.toString(currentNumberOfHoles));
    }

    private Dialog confirmSubmissionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Are you sure you want to submit this course?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing
            }
        });

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO do error checking for new course submission
                String name = courseName.getText().toString();
                String city = courseCity.getText().toString();
                String state = courseState.getText().toString();
                final Course c = new Course(Constants.ID_NOT_SET, name, city, state, holeList);

                Intent i = new Intent();
                i.putExtra("newCourse", c);
                setResult(Activity.RESULT_OK, i);

                Runnable r = new Runnable() {
                    @Override
                    public void run()
                    {
                        DB database = new DB(context);
                        SQLiteDatabase qdb = database.getWritableDatabase();
                        DB.insertCourse(qdb, c);
                    }
                };
                Thread t = new Thread(r);
                t.run();

                finish();
            }
        });

        return builder.create();
    }


    private class HoleAdapter extends BaseExpandableListAdapter
    {
        List<Hole> holeList;
        private LayoutInflater inflater = null;
        Context context;
        ActivityNewCourse acn;

        public HoleAdapter(List<Hole> holes, ActivityNewCourse a)
        {
            this.holeList = holes;
            this.context = a;
            this.acn = a;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ActivityNewCourse getAcn()
        {
            return acn;
        }

        public void setAcn(ActivityNewCourse acn)
        {
            this.acn = acn;
        }

        public List<Hole> getHoleList()
        {
            return holeList;
        }

        public void setHoleList(List<Hole> holeList)
        {
            this.holeList = holeList;
        }

        public LayoutInflater getInflater()
        {
            return inflater;
        }

        public void setInflater(LayoutInflater inflater)
        {
            this.inflater = inflater;
        }

        public Context getContext()
        {
            return context;
        }

        public void setContext(Context context)
        {
            this.context = context;
        }

        @Override
        public int getGroupCount()
        {
            return this.holeList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            return this.holeList.get(groupPosition).getStartingPoints().size()+1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.holeList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.holeList.get(groupPosition).getStartingPoints().get(childPosition-1);
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            Hole h = (Hole) getGroup(groupPosition);
            String holeNUmber = Integer.toString(h.getHoleNumber());

            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_new_course_holes, null);
            }

            TextView holeNumberText = (TextView)convertView.findViewById(R.id.list_item_new_course_holes_hole_number_textview);
            holeNumberText.setText(holeNUmber);

            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(childPosition == 0)
            {
                LayoutInflater inflater = acn.getLayoutInflater();
                /*if (convertView == null) {*/
                    convertView = inflater.inflate(R.layout.list_item_new_course_holes_child_header, null);
                //}

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acn.getTeeEditorDialog(groupPosition, childPosition).show();
                    }
                });
            }
            else
            {
                Hole h = holeList.get(groupPosition);
                HoleStartingPoint hsp = (HoleStartingPoint) getChild(groupPosition, childPosition);
                final String teeName = h.getStartingPointNames().get(childPosition-1);
                final String teeLength = Integer.toString(h.getStartingPoints().get(childPosition-1).getLength()) + "ft";
                LayoutInflater inflater = acn.getLayoutInflater();

                /*if (convertView == null) {*/
                    convertView = inflater.inflate(R.layout.list_item_new_course_holes_child, null);
                //}

                TextView teeNameText = (TextView) convertView.findViewById(R.id.list_item_new_course_holes_child_tee_name);
                teeNameText.setText(teeName);

                TextView teeLengthText = (TextView) convertView.findViewById(R.id.list_item_new_course_holes_child_tee_length);
                teeLengthText.setText(teeLength);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acn.getTeeEditorDialog(groupPosition, childPosition).show();
                    }
                });
            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        private class Holder
        {
            TextView holeNumber;
        }

    }

    public Dialog getTeeEditorDialog(final int groupPosition, final int childPosition)
    {
        //Create builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Set title
        builder.setTitle("Add Tee");

        LayoutInflater inflater = acn.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_add_tee, null);
        builder.setView(v);

        final TextView nameText = (TextView) v.findViewById(R.id.dialog_add_tee_name_edittext);
        final TextView lengthText = (TextView) v.findViewById(R.id.dialog_add_tee_length_edittext);

        if(childPosition!=0)
        {
            final HoleStartingPoint h = holeList.get(groupPosition).getStartingPoints().get(childPosition-1);
            nameText.setText(h.getName());
            lengthText.setText(Integer.toString(h.getLength()));

            builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    holeList.get(groupPosition).getStartingPoints().remove(childPosition-1);
                    refreshHoleList();
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            h.setName(nameText.getText().toString());
                            h.setLength(Integer.parseInt(lengthText.getText().toString()));
                            refreshHoleList();
                            InputMethodManager inputManager =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
            });
        }
        else
        {
            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HoleStartingPoint t = new HoleStartingPoint(nameText.getText().toString(), Integer.parseInt(lengthText.getText().toString()));
                    holeList.get(groupPosition).getStartingPoints().add(t);
                    refreshHoleList();
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Do nothing because user canceled
                InputMethodManager inputManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        return builder.create();
    }

}
