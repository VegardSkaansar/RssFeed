package e.vegard.rssfeed;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

public class NewsListTest {
    private static NewsList newsList;
    private static RssFeedModel model;

    @BeforeClass
    public static void BeforeClass() {
        newsList = new NewsList();
        model = new RssFeedModel("Kari", "https://www.kari.no", "kari", "kari");
        newsList.Mlist = new ArrayList<RssFeedModel>();
    }
    @Before
    public void setUp() throws Exception {
        newsList.Mlist.add(model);
    }

    @Test
    public void testFilter(){
        String userinput = "Kari";
        assertNotNull(newsList.filterList(userinput));

    }

    @After
    public void tearDown() throws Exception {
    }
}