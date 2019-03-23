package e.vegard.rssfeed;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

// this class takes care of fetching and fetches your link when ur change link
// or fetch a new version of the link every 10 min, to no spam the service
public class RSSPullService extends IntentService {

    private final String TAG = "RSSPullService";
    public final String RSSUPDATE = "ForEvery10MinWeWillRunMainFetchActivity";

    public RSSPullService() {
        super("RSSPullService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.d(TAG, "onHandleIntent");


        String input = workIntent.getStringExtra(RSSUPDATE);

        if(input == "OK") {
            // for every 10 min we will fetch

            SystemClock.sleep(600000);
        }

    }
}
