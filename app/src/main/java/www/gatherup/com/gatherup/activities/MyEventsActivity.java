package www.gatherup.com.gatherup.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.fragments.EventListFragment;
import www.gatherup.com.gatherup.models.UserModel;

public class MyEventsActivity extends AppCompatActivity implements EventListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        GlobalAppState appState = (GlobalAppState)getApplicationContext();

        appState.setFilteredEvents(UserModel.get().getRegisteredDetailedEvents());

       /* EventListFragment allEventsListFragment = EventListFragment.newInstance();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.my_events_content, allEventsListFragment).commit();*/
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
