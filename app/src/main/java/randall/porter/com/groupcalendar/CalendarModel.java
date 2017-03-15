package randall.porter.com.groupcalendar;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by chris_000 on 3/14/2017.
 */
@Table(database = MyDatabase.class)
public class CalendarModel extends BaseModel {

    @PrimaryKey
    @Column
    private String CalendarID;
    @Column
    private String CalendarName;

    public CalendarModel() {
    }

    public CalendarModel(String calendarID, String calendarName) {
        CalendarID = calendarID;
        CalendarName = calendarName;
    }

    public String getCalendarID() {
        return CalendarID;
    }

    public void setCalendarID(String calendarID) {
        CalendarID = calendarID;
    }

    public String getCalendarName() {
        return CalendarName;
    }

    public void setCalendarName(String calendarName) {
        CalendarName = calendarName;
    }
}
