package e.vegard.rssfeed;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button mPreferenceButton;
    private Button mNewsButton;

    public static ArrayList<RssFeedModel> mFeedModelList;
    private Timer timer;
    private TimerTask freq;

    private String mFeedTitle;
    private String mFeedDescription;
    private String mFeedLink;

    private boolean ok;
    private boolean firstTimeFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // referencing the buttons to the ui elements
        mPreferenceButton = findViewById(R.id.btn_preference);
        mNewsButton = findViewById(R.id.btn_newsList);

        firstTimeFetch = true;

        // here we will send to the next activity
        mPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Preferences.class);
                startActivity(i);
                if (getSharedPreferences(Preferences.URL, MODE_PRIVATE).contains("url")) {
                    mNewsButton.setEnabled(true);
                }

            }
        });
        mNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the ok boolean helps us check if it was a scheduled
                // or a button press that fetched the rssfeed
                if (firstTimeFetch) {
                    ok = true;
                    firstTimeFetch = false;
                    timer = new Timer();
                    freq = new TimerTask() {
                        @Override
                        public void run() {
                            FetchTheURL();
                        }
                    };

                    // we are setting this timer 60000 is the same as 1 min key values contains values as min
                    timer.schedule(freq, 1, getSharedPreferences(Preferences.URL, MODE_PRIVATE).getInt("frequency", 0) * 60000);
                } else {
                    Intent i = new Intent(MainActivity.this, NewsList.class);
                    i.putExtra("listOfRss", mFeedModelList);
                    startActivity(i);
                }

            }
        });

        //if no link is provided in the sharedPreference
        //the button for news will be disabled
        if (!getSharedPreferences(Preferences.URL, MODE_PRIVATE).contains("url")) {
            mNewsButton.setEnabled(false);
        }

    }

    // just a shorter way to start the asynctask
    public void FetchTheURL() {
       new FetchFeedTask(MainActivity.this).execute((Void) null);
    }

    public ArrayList<RssFeedModel> parseFeed(InputStream inputStream, int amount) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        String img = null;
        boolean isItem = false;
        ArrayList<RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT && amount > 0) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                String attr = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                } else if (name.equalsIgnoreCase("enclosure")) {
                    attr = xmlPullParser.getAttributeValue(xmlPullParser.getNamespace(), "url");
                    img = attr;
                    Log.d(TAG, "chr: " + img);
                }

                if (title != null && link != null && description != null && img != null) {
                    if(isItem) {
                        RssFeedModel item = new RssFeedModel(title, link, description, img);
                        items.add(item);
                        amount--;
                    }
                    else {
                        mFeedTitle = title;
                        mFeedLink = link;
                        mFeedDescription = description;
                    }

                    title = null;
                    link = null;
                    description = null;
                    img = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;
        private Context context;

        private FetchFeedTask(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        protected void onPreExecute() {
            mFeedTitle = null;
            mFeedLink = null;
            mFeedDescription = null;

            //when we have load with fetch we get the value from a preference
            SharedPreferences rssURL;
            rssURL = getSharedPreferences(Preferences.URL, MODE_PRIVATE);
            urlLink = rssURL.getString("url", "");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink)) {
                return false;
            }
            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "https://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                SharedPreferences rssURL;
                rssURL = getSharedPreferences(Preferences.URL, MODE_PRIVATE);
                int amount = rssURL.getInt("amount", 0);
                Log.d(TAG, "" + amount);
                mFeedModelList = parseFeed(inputStream, amount);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            if (success) {
                if (ok) {
                    Intent i = new Intent(MainActivity.this, NewsList.class);
                    i.putExtra("listOfRss", mFeedModelList);
                    startActivity(i);
                    ok = false;
                }


            } else {
                Toast.makeText(MainActivity.this,
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();

            }
        }
    }
}

