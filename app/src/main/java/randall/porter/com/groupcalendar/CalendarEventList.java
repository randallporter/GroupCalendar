package randall.porter.com.groupcalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CalendarEventList extends AppCompatActivity {

    private CalendarEventAdapter calendarEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_list);

        ListView lv = (ListView) findViewById(R.id.lvCalendarEvents);
        calendarEventAdapter  = new CalendarEventAdapter(this);
        lv.setAdapter(calendarEventAdapter);
    }

    public void UpdateList(){
        calendarEventAdapter.notifyDataSetChanged();
    }
}
