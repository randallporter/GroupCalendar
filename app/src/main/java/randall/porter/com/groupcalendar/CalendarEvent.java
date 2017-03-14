package randall.porter.com.groupcalendar;

import android.support.annotation.Nullable;


import com.google.api.client.util.DateTime;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;


/**
 * Created by chris_000 on 3/11/2017.
 */

@Table(database = MyDatabase.class)
public class CalendarEvent extends BaseModel {

    @PrimaryKey
    private String ID;
    @Column
    private Date StartTime;
    @Column
    private Date EndTime;
    @Column
    @Nullable
    private String Summary;
    @Column
    @Nullable
    private String Description;
    @Column
    @Nullable
    private String CreatorEmail;
    @Column
    @Nullable
    private String CalendarID;

    public CalendarEvent() {
    }

    public String getCalendarID() {
        return CalendarID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    @Nullable
    public String getSummary() {
        return Summary;
    }

    public void setSummary(@Nullable String summary) {
        Summary = summary;
    }

    @Nullable
    public String getDescription() {
        return Description;
    }

    public void setDescription(@Nullable String description) {
        Description = description;
    }

    @Nullable
    public String getCreatorEmail() {
        return CreatorEmail;
    }

    public void setCreatorEmail(@Nullable String creatorEmail) {
        CreatorEmail = creatorEmail;
    }

    public void setCalendarID(@Nullable String calendarID) {
        CalendarID = calendarID;
    }
}
