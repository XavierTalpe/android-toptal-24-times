package be.xvrt.times.uil;

import static junit.framework.Assert.assertEquals;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import bolts.Task;

public final class ParseTestUtil {

    public static final String TEST_USERNAME = "test";
    public static final String TEST_PASSWORD = "t3st";

    private ParseTestUtil() {
    }

    public static ParseUser getTestUser() throws InterruptedException {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            String username = currentUser.getUsername();

            if (username.equals(TEST_USERNAME)) {
                return currentUser;
            }
        }

        return loginAsTestUser();
    }

    private static ParseUser loginAsTestUser() throws InterruptedException {
        ParseUser.logOut();

        Task<ParseUser> loginTask = ParseUser.logInInBackground(TEST_USERNAME, TEST_PASSWORD);
        loginTask.waitForCompletion();

        return loginTask.getResult();
    }

    public static <T extends ParseObject> void assertDeleted(String objectId, Class<T> clockClass) {
        ParseQuery<T> query = new ParseQuery<T>(clockClass);
        try {
            query.get(objectId);
        } catch (ParseException exception) {
            assertEquals("no results found for query", exception.getMessage());
        }

    }

}
