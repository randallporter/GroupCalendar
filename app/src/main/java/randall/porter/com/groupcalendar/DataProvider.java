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
        update();
    }

    public void update(){
        calendarEventList = SQLite
                .select()
                .from(CalendarEvent.class)
                .queryList();
    }
}
