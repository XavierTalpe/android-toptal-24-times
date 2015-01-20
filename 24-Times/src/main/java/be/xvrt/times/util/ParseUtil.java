package be.xvrt.times.util;

import com.parse.Parse;

import android.content.Context;
import android.content.res.Resources;
import be.xvrt.clocks.app.R;

public final class ParseUtil {

    private ParseUtil() {
    }

    public static void initParse(Context context) {
        Resources resources = context.getResources();
        String applicationId = resources.getString(R.string.parse_application_id);
        String clientKey = resources.getString(R.string.parse_client_key);

        Parse.initialize(context, applicationId, clientKey);
    }

}
