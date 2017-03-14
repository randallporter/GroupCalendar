package randall.porter.com.groupcalendar;

import android.app.Application;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by chris_000 on 3/11/2017.
 */

public class MyApplication extends Application {
    private GoogleAccountCredential googleAccountCredential;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public GoogleAccountCredential getCurrentCredentials(){
        return googleAccountCredential;
    }

    public void setGoogleAccountCredential(GoogleAccountCredential googleAccountCredential){
        this.googleAccountCredential = googleAccountCredential;
    }
}
