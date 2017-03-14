package randall.porter.com.groupcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by chris_000 on 3/11/2017.
 */

public class CalendarEventAdapter extends BaseAdapter {

    private List<CalendarEvent> data;
    private Context context;
    private DataProvider dataProvider;


    public CalendarEventAdapter(Context context) {
        super();
        dataProvider = new DataProvider();
        this.data = dataProvider.calendarEventList;
        this.context = context;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public CalendarEvent getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarEvent calendarEvent = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_calendar_event_list_item, parent, false);
        }

        TextView txtName = (TextView) convertView.findViewById(R.id.txtSummary);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtTime);

        txtName.setText(calendarEvent.getSummary());
        //SimpleDateFormat format = new SimpleDateFormat("MM/dd hh:mm aa");
        //txtDate.setText(format.format(calendarEvent.getStartTime()) + " to " +
                //format.format(calendarEvent.getEndTime()));
        if (calendarEvent.getStartTime() != null && calendarEvent.getEndTime() != null) {
            txtDate.setText(calendarEvent.getStartTime().toString() + " to " +
                    calendarEvent.getEndTime().toString());
        }

        return convertView;
    }
}
