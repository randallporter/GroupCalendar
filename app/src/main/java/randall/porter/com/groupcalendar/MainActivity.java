package randall.porter.com.groupcalendar;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, NavDrawer.class);
        startActivity(intent);

//        LinearLayout activityLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        activityLayout.setLayoutParams(lp);
//        activityLayout.setOrientation(LinearLayout.VERTICAL);
//        activityLayout.setPadding(16, 16, 16, 16);
//
//
//        final Button apiButton;
//        apiButton = new Button(this);
//        apiButton.setText("Sync Account");
//        apiButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), AccountActivity.class);
//                startActivity(intent);
//            }
//        });
//        activityLayout.addView(apiButton);
//
//        final Button ViewEvents;
//        ViewEvents = new Button(this);
//        ViewEvents.setText("View Events");
//        ViewEvents.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), CalendarEventList.class);
//                startActivity(intent);
//            }
//        });
//        activityLayout.addView(ViewEvents);
//
//        final Button addEvent;
//        addEvent = new Button(this);
//        addEvent.setText("Add Event");
//        addEvent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), AddEvent.class);
//                startActivity(intent);
//            }
//        });
//        activityLayout.addView(addEvent);
//
//        final Button addCalendar;
//        addCalendar = new Button(this);
//        addCalendar.setText("Add Calendar");
//        addCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), ManageCalendarList.class);
//                startActivity(intent);
//            }
//        });
//        activityLayout.addView(addCalendar);
//
//        setContentView(activityLayout);


    }
}
