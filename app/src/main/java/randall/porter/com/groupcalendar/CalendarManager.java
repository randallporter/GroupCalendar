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

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

// TODO: 3/11/2017 InsertCalendarEvent needs to be done next.
// TODO... Currently it works inserting a static event


public class CalendarManager {

    private static final String[] SCOPES = { CalendarScopes.CALENDAR };

    public CalendarManager() {
    }

    public void getEventsFromApi(GoogleAccountCredential mCredential) {
        new GetCalendarEvents(mCredential).execute();
        //new InsertCalendarEvent(mCredential).execute();
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
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);

            DateTime firstOfMonth = new DateTime(calendar.getTime());
            calendar.set(java.util.Calendar.MONTH, +3);
            calendar.set(java.util.Calendar.DAY_OF_MONTH, -1);
            DateTime lastOfMonth = new DateTime(calendar.getTime());
            Events events = mService.events().list("primary")
                    .setTimeMin(firstOfMonth)
                    .setTimeMax(lastOfMonth)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();
            List<CalendarEvent> calEvents = new ArrayList<>();

            for (Event event : items) {
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
                calEvents.add(calendarEvent);
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

    private class InsertCalendarEvent extends AsyncTask<Void, Void, Boolean> {
        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;

        InsertCalendarEvent(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return InsertEvent();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return false;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */
        private Boolean InsertEvent() throws IOException {
            Event event = new Event()
                    .setSummary("Google I/O 2015")
                    .setLocation("800 Howard St., San Francisco, CA 94103")
                    .setDescription("A chance to hear more about Google's developer products.");

            DateTime startDateTime = new DateTime("2017-03-11T09:00:00-07:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-03-11T10:00:00-07:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setEnd(end);
            String calendarId = "primary";
            mService.events().insert(calendarId, event).execute();
            return true;
        }

        @Override
        protected void onPreExecute() {
//            mOutputText.setText("");
//            mProgress.show();
        }

        @Override
        protected void onPostExecute(Boolean output) {
//            mProgress.hide();
//            if (!output) {
//                mOutputText.setText("Not inserted.");
//            } else {
//                mOutputText.setText("Event Added");
//            }
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