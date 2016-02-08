package chris.discgolf;

import java.util.ArrayList;

/**
 * Created by Chris on 1/19/2016.
 */
public class HoleRecordHolder
{
    ArrayList<HoleRecord> records;

    public ArrayList<HoleRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<HoleRecord> records) {
        this.records = records;
    }

    public HoleRecordHolder()
    {
        records = new ArrayList<HoleRecord>();
    }

    public void addRecord(HoleRecord hR)
    {
        records.add(hR);
    }
}
