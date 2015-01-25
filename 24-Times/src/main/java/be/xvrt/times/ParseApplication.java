package be.xvrt.times;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;
import android.content.res.Resources;
import be.xvrt.times.model.Clock;

public final class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Clock.class);

        Parse.enableLocalDatastore(this);

        Resources resources = this.getResources();
        String applicationId = resources.getString(R.string.parse_application_id);
        String clientKey = resources.getString(R.string.parse_client_key);

        Parse.initialize(this, applicationId, clientKey);
    }

}
