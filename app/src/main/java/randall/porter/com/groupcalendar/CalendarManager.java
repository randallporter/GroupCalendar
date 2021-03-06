package randall.porter.com.groupcalendar;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;


public class CalendarManager {

    private static final String[] SCOPES = { CalendarScopes.CALENDAR };
    CalendarEvent calendarEvent;

    public CalendarManager() {
        //this.myApplication = myApplication;
    }

    public void getEventsFromApi(GoogleAccountCredential mCredential) {
        //GoogleAccountCredential mCredential = myApplication.getCurrentCredentials();
        new GetCalendarEvents(mCredential).execute();
    }

    public void insertEvent(CalendarEvent calendarEvent, MyApplication application) {
        GoogleAccountCredential mCredential = application.getCurrentCredentials();
        this.calendarEvent = calendarEvent;
        new InsertCalendarEvent(mCredential).execute();
        new GetCalendarEvents(mCredential).execute(); //insert into db if successful
        //calendarEvent.save();
    }

    private class GetCalendarEvents extends AsyncTask<Void, Void, Boolean> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        GetCalendarEvents(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return false;
            }

        }

        private Boolean getDataFromApi() throws IOException {
            DataProvider dataProvider = new DataProvider();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);


            DateTime firstOfMonth = new DateTime(calendar.getTime());
            calendar.set(java.util.Calendar.MONTH, +3);
            calendar.set(java.util.Calendar.DAY_OF_MONTH, -1);
            DateTime lastOfMonth = new DateTime(calendar.getTime());
            List<CalendarEvent> calEvents = new ArrayList<>();
            //loop through calendars (primary, shared)
            for (String calendarList : dataProvider.getCalendarAccounts(true)){
                Events events = mService.events()
                        .list(calendarList)
                        .setTimeMin(firstOfMonth)
                        .setTimeMax(lastOfMonth)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();
                //loop through events in each calendar. Needs to be nested because from an event
                // no way to access its list id without doing another api call. It is not contained
                // in the event object.
                for (Event event : events.getItems()) {
                    CalendarEvent calendarEvent = new CalendarEvent();
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        // All-day events don't have start times, so just use the start date.
                        start = event.getStart().getDate();
                    }
                    DateTime end = event.getEnd().getDateTime();
                    if (end == null) {
                        // All-day events don't have start times, so just use the start date.
                        end = event.getEnd().getDate();
                    }

                    calendarEvent.setID(event.getId());
                    calendarEvent.setCreatorEmail(event.getCreator().getEmail());
                    calendarEvent.setDescription(event.getDescription());
                    calendarEvent.setSummary(event.getSummary());
                    calendarEvent.setStartTime(new Date(start.getValue()));
                    calendarEvent.setEndTime(new Date(end.getValue()));
                    calendarEvent.setCalendarID(calendarList);
                    //todo figure out how to get cal ID
                    calEvents.add(calendarEvent);
                }
            }




            //batch update DB
            FlowManager.getDatabase(MyDatabase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<CalendarEvent>() {
                                @Override
                                public void processModel(CalendarEvent calendarEvent) {
                                    calendarEvent.save();
                                }
                            }).addAll(calEvents).build())  // add elements (can also handle multiple)
                    .error(new Transaction.Error() {
                        @Override
                        public void onError(Transaction transaction, Throwable error) {
                            Log.e("insert error",transaction.error().toString());
                        }
                    })
                    .success(new Transaction.Success() {
                        @Override
                        public void onSuccess(Transaction transaction) {

                        }
                    }).build().execute();

            return true;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onCancelled() {
//            mProgress.hide();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    startActivityForResult(
//                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                            CalendarManager.REQUEST_AUTHORIZATION);
//                } else {
//                    mOutputText.setText("The following error occurred:\n"
//                            + mLastError.getMessage());
//                }
//            } else {
//                mOutputText.setText("Request cancelled.");
//            }
        }
    }

    private class InsertCalendarEvent extends AsyncTask<Void, Void, Void> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;
        //private CalendarEvent calendarEvent;

        InsertCalendarEvent(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            credential.setSelectedAccountName("randallporter0@gmail.com");
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
            //this.calendarEvent = calendarEvent;
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                return InsertEvent();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        private Void InsertEvent() throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'-07:00'");
            // TODO: 3/14/2017 figure out how to display GMT. X notation doesn't work in android but Z does. Z = '-0700'
            //Format example from google "2017-03-11T10:00:00-07:00"
            Event event = new Event()
                    .setSummary(calendarEvent.getSummary())
                    .setDescription("No Description yet");

            DateTime startDateTime = new DateTime(format.format(calendarEvent.getStartTime()));
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setStart(start);

            DateTime endDateTime = new DateTime(format.format(calendarEvent.getEndTime()));
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setEnd(end);
            //todo get selected calendar id
            String calendarId = calendarEvent.getCalendarID();

            mService.events().insert(calendarId, event).execute();
            return null;
        }

        @Override
        protected void onPreExecute() {
//            mOutputText.setText("");
//            mProgress.show();
        }

        @Override
        protected void onCancelled() {
//            mProgress.hide();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    startActivityForResult(
//                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
//                            AccountActivity.REQUEST_AUTHORIZATION);
//                } else {
//                    mOutputText.setText("The following error occurred:\n"
//                            + mLastError.getMessage());
//                }
//            } else {
//                mOutputText.setText("Request cancelled.");
//            }
        }
    }

}