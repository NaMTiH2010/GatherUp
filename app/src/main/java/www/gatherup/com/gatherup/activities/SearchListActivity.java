package www.gatherup.com.gatherup.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.fragments.EventListFragment;

public class SearchListActivity extends AppCompatActivity implements EventListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        EventListFragment allEventsListFragment = EventListFragment.newInstance();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.search_event_list_content, allEventsListFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
