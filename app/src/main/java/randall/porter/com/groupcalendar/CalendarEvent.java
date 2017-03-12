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
    @Nullable
    private Date EndTime;
    @Column
    private String Summary;
    @Column
    @Nullable
    private String Description;
    @Column
    private String CreatorEmail;

    public CalendarEvent() {
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

    @Nullable
    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(@Nullable Date endTime) {
        EndTime = endTime;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreatorEmail() {
        return CreatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        CreatorEmail = creatorEmail;
    }


}
