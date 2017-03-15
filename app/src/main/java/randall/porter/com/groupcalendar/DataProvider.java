package randall.porter.com.groupcalendar;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris_000 on 3/11/2017.
 */

public class DataProvider {
    public List<CalendarEvent> calendarEventList = new ArrayList<>();


    public DataProvider(){
       //if data gets messed up...
        //Delete.table(CalendarEvent.class);
        new CalendarModel("iv6v1cml8ue6gjljgdlfh7s39c@group.calendar.google.com"
            , "shared cal").save();
        //
        update();
    }

    public void update(){
        calendarEventList = SQLite
                .select()
                .from(CalendarEvent.class)
                .queryList();
    }

    public List<CalendarModel> getCalendarModelList(){
        return SQLite
                .select()
                .from(CalendarModel.class)
                .queryList();
    }
}
