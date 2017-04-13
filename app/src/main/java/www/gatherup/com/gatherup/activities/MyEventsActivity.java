package www.gatherup.com.gatherup.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.fragments.EventListFragment;
import www.gatherup.com.gatherup.models.UserModel;

public class MyEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        GlobalAppState appState = (GlobalAppState)getApplicationContext();

        //appState.setFilteredDetailedEvents(UserModel.get().getRegisteredDetailedEvents());

        EventListFragment allEventsListFragment = EventListFragment.newInstance(new ArrayList<DetailedEvent>());
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.my_events_content, allEventsListFragment).commit();
    }
}
