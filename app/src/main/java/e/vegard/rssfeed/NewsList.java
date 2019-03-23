package e.vegard.rssfeed;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class NewsList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<RssFeedModel> Mlist;
    private final String TAG = "Somekeyfortestimg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleNews);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // here we will get the recyclerview
        Intent i = getIntent();
        Mlist = (ArrayList<RssFeedModel>)i.getSerializableExtra("listOfRss");



        mRecyclerView.setAdapter(new RssFeedListAdapter(Mlist));
}

}
