package randall.porter.com.groupcalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {
    Calendar date;
    View thisV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Spinner dropdown = (Spinner)findViewById(R.id.spAccount);
        DataProvider dataProvider = new DataProvider();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                dataProvider.getCalendarAccounts(true));
        dropdown.setAdapter(adapter);
    }


    public void showDateTimePicker(View v) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        thisV = v;
        final TextView textView = (TextView) findViewById(v.getId());

        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(thisV.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        textView.setText(format.format(date.getTime()));
                        textView.setTag(date.getTime());
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    public void AddEvent(View v){
        EditText txtSummary = (EditText) findViewById(R.id.txtEventSummary);
        TextView txtStart = (TextView) findViewById(R.id.txtStart);
        TextView txtEnd = (TextView) findViewById(R.id.txtEnd);
        Spinner dropdown = (Spinner)findViewById(R.id.spAccount);

        CalendarEvent calendarEvent = new CalendarEvent();
        calendarEvent.setSummary(txtSummary.getText().toString());
        calendarEvent.setStartTime((Date) txtStart.getTag());
        calendarEvent.setEndTime((Date) txtEnd.getTag());
        calendarEvent.setCalendarID(dropdown.getSelectedItem().toString());

        CalendarManager calendarManager = new CalendarManager();
        //insert event will save and send to google calendar
        calendarManager.insertEvent(calendarEvent, (MyApplication)this.getApplication());
        //finish();
    }

}
