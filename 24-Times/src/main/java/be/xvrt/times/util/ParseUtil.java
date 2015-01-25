package be.xvrt.times.util;

import com.parse.Parse;
import com.parse.ParseObject;

import android.content.Context;
import android.content.res.Resources;
import be.xvrt.times.R;
import be.xvrt.times.model.Clock;

public final class ParseUtil {

    private ParseUtil() {
    }

    public static void initParse(Context context) {
        ParseObject.registerSubclass(Clock.class);

        Resources resources = context.getResources();
        String applicationId = resources.getString(R.string.parse_application_id);
        String clientKey = resources.getString(R.string.parse_client_key);

        Parse.initialize(context, applicationId, clientKey);
    }

}
