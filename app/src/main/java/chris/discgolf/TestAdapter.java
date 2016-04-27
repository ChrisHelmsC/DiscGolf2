package chris.discgolf;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chris on 4/27/2016.
 */
public class TestAdapter extends BaseAdapter
{
    List<TestingClass> testList;
    private static LayoutInflater inflater = null;
    Context context;
    TestingActivity activity;

    public TestAdapter(TestingActivity ta, List<TestingClass> tl)
    {
        context = ta;
        testList = tl;
        activity = ta;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {return testList.size();};

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    private class Holder
    {
        TextView description;
        TextView status;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.test_layout_view, null);
        holder.description = (TextView)rowView.findViewById(R.id.test_layout_test_info);
        holder.status = (TextView)rowView.findViewById(R.id.test_layout_test_status);

        TestingClass temp = testList.get(position);

        holder.description.setText("TestID - " + temp.testID + ", ReqID - " + temp.req_id + ":\n" + temp.description);

        if(temp.runTest(activity.qdb))
        {
            holder.status.setText("Passed");
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.pass_green));
        }
        else
        {
            holder.status.setText("Failed");
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.fail_red));
        }

        return rowView;
    }

}
