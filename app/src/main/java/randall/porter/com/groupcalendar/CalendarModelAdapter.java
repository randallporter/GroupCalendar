package randall.porter.com.groupcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris_000 on 3/11/2017.
 */

public class CalendarModelAdapter extends BaseAdapter {

    private List<CalendarModel> data;
    private Context context;
    private DataProvider dataProvider;


    public CalendarModelAdapter(Context context) {
        super();
        dataProvider = new DataProvider();
        this.data = dataProvider.getCalendarModelList();
        this.context = context;
    }

    public void update(){
        data = dataProvider.getCalendarModelList();
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public CalendarModel getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarModel calendarModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.activity_calendar_object_list_item, parent, false);
        }

        TextView txtCalendarId = (TextView) convertView.findViewById(R.id.txtCalendarID);
        TextView txtCalendarName = (TextView) convertView.findViewById(R.id.txtCalendarName);

        txtCalendarId.setText(calendarModel.getCalendarID());
        txtCalendarName.setText(calendarModel.getCalendarName());

        return convertView;
    }
}
