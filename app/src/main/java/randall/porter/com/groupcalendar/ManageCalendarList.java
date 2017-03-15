package randall.porter.com.groupcalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ManageCalendarList extends AppCompatActivity {

    private CalendarModelAdapter calendarModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_calendar_list);

        ListView lv = (ListView) findViewById(R.id.lvCalendarLists);
        calendarModelAdapter  = new CalendarModelAdapter(this);
        lv.setAdapter(calendarModelAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView txtInsertOrUpdateCalendarID = (TextView) parent.
                        findViewById(R.id.txtInsertOrUpdateCalendarID);
                TextView txtInsertOrUpdateCalendarName = (TextView) parent.
                        findViewById(R.id.txtInsertOrUpdateCalendarName);
                TextView txtCalendarID = (TextView) findViewById(R.id.txtCalendarID);
                TextView txtCalendarName = (TextView) findViewById(R.id.txtCalendarName);

                txtInsertOrUpdateCalendarID.setText(txtCalendarID.getText().toString());
                txtInsertOrUpdateCalendarName.setText(txtCalendarName.getText().toString());
            }
        });
    }

    public void DeleteCalendar(View view) {
        View parent = (View) view.getParent();
        TextView txtCalendarID = (TextView) parent.findViewById(R.id.txtCalendarID);
        TextView txtCalendarName = (TextView) parent.findViewById(R.id.txtCalendarName);
        new CalendarModel(txtCalendarID.getText().toString()
                , txtCalendarName.getText().toString()).delete();
        updatelist();
    }

    public void InsertCalendar(View view) {
        TextView txtCalendarID = (TextView) findViewById(R.id.txtInsertOrUpdateCalendarID);
        TextView txtCalendarName = (TextView) findViewById(R.id.txtInsertOrUpdateCalendarName);
        new CalendarModel(txtCalendarID.getText().toString()
                , txtCalendarName.getText().toString()).save();
        updatelist();
    }

    private void updatelist(){
        calendarModelAdapter.update();
        calendarModelAdapter.notifyDataSetChanged();
    }
}
