package e.vegard.rssfeed;


import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;





public class MainActivityTest {
    private static MainActivity mainActivity;
    private static RssFeedModel model;

    @BeforeClass
    public static void BeforeClass() {
        mainActivity = new MainActivity();
        model = new RssFeedModel("Kari", "https://www.kari.no/", "kari", "hdhdhd");
        mainActivity.mFeedModelList = new ArrayList<RssFeedModel>();

    }

    @Before
    public void setUp() throws Exception {
        mainActivity.mFeedModelList.add(model);

    }


    @Test
    public void testAddToArray() throws Exception{
        assertNotNull(mainActivity.mFeedModelList);
    }



}