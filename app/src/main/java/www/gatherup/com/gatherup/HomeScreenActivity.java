package www.gatherup.com.gatherup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import www.gatherup.com.gatherup.activities.CreateEventActivity;
import www.gatherup.com.gatherup.activities.MyEventsActivity;
import www.gatherup.com.gatherup.activities.SearchEventActivity;
import www.gatherup.com.gatherup.activities.UserProfileActivity;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.fragments.EventListFragment;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventListFragment.OnFragmentInteractionListener {

    private static final String TAG = "NewPostActivity";


    ArrayList<Event> mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        // Create mock eventlist
        /*UserModel.get().removeYourEventsFromAllEvents();*/
        mEventList = UserModel.get().getEvents();
        //mEventList = new ArrayList<>();
        /*new ArrayList<>();

        if(UserModel.get().getEvents().size()>0){
            for(Event e : UserModel.get().getEvents()){
                mEventList.add(new DetailedEvent(e));
            }
        }*/

        //mDetailedEventList.add(new DetailedEvent(UserModel.get().getEvents().get(0)));
                //
        /*mDetailedEventList.add(new DetailedEvent(this, "Get together to study Math", 40.7493182, -73.4250478, Calendar.getInstance(), Calendar.getInstance(), "Let's get together in the Library to get ready for the MTH390 Test", "Learning"));
        mDetailedEventList.add(new DetailedEvent(this, "D&D Friday night", 40.7495182, -73.4230478, Calendar.getInstance(), Calendar.getInstance(), "Friday night is game night. Everyone is invited to come and play D&D. Newcomers are welcome.", "Games"));
        mDetailedEventList.add(new DetailedEvent(this, "Looking for lost cat", 40.7483182, -73.4230478, Calendar.getInstance(), Calendar.getInstance(), "Whiskers, my little kitten is lost since yesterday, I am organizing a group to look for him. We'll be walking aroud the neighborhood for 2 hours", "Gathering"));
        mDetailedEventList.add(new DetailedEvent(this, "Programming tutoring session", 40.7490182, -73.4200478, Calendar.getInstance(), Calendar.getInstance(), "Let's get together in the Library to get ready for the MTH390 Test", "Learning"));
        mDetailedEventList.add(new DetailedEvent(this, "FSC LASSO Dance Night (Students only)", 40.7493442, -73.4234478, Calendar.getInstance(), Calendar.getInstance(), "Friday night is game night. Everyone is invited to come and play D&D. Newcomers are welcome.", "Games"));
        mDetailedEventList.add(new DetailedEvent(this, "Hike through Bethpage park", 40.7493456, -73.4250000, Calendar.getInstance(), Calendar.getInstance(), "Whiskers, my little kitten is lost since yesterday, I am organizing a group to look for him. We'll be walking aroud the neighborhood for 2 hours", "Gathering"));
        mDetailedEventList.add(new DetailedEvent(this, "Get together to study Chemistry", 40.7493455, -73.4250111, Calendar.getInstance(), Calendar.getInstance(), "Let's get together in the Library to get ready for the MTH390 Test", "Learning"));
        mDetailedEventList.add(new DetailedEvent(this, "Watch the Packers win ", 43.4, 40.0, Calendar.getInstance(), Calendar.getInstance(), "Friday night is game night. Everyone is invited to come and play D&D. Newcomers are welcome.", "Games"));
        mDetailedEventList.add(new DetailedEvent(this, "Zoo trip", 33.3, 41.0, Calendar.getInstance(), Calendar.getInstance(), "Whiskers, my little kitten is lost since yesterday, I am organizing a group to look for him. We'll be walking aroud the neighborhood for 2 hours", "Gathering"));
*/
        // Create mock category list
        GlobalAppState appState = (GlobalAppState)getApplicationContext();
        appState.getCategories().clear();
        appState.getCategories().add("Any");
        appState.getCategories().add("Food");
        appState.getCategories().add("Sports");
        appState.getCategories().add("Gathering");
        appState.getCategories().add("Music");
        appState.getCategories().add("Learning");
        appState.getCategories().add("Games");



        // TODO this is for trying out GlobalAppState

        //appState.setDetailedEventList((ArrayList<DetailedEvent>) mDetailedEventList.clone());
        appState.setEventList((ArrayList<Event>) mEventList.clone());
        appState.setFilteredEvents(appState.getEventList());




        EventListFragment allEventsListFragment = EventListFragment.newInstance();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_home, allEventsListFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class menuclass = null;

        if (id == R.id.nav_profile) {
            menuclass = UserProfileActivity.class;
        } else if (id == R.id.nav_create_events) {
            menuclass = CreateEventActivity.class;
        } else if (id == R.id.nav_search_events) {
            menuclass = SearchEventActivity.class;
        } else if (id == R.id.nav_my_events) {
            menuclass = MyEventsActivity.class;
        } else if (id == R.id.nav_sign_out) {
            menuclass = LoginActivity.class;
        }

        if (menuclass != null) {
            Firebase_Model.get().getAuth().signOut();
            Intent intent = new Intent(HomeScreenActivity.this, menuclass);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onResume(){
        super.onResume();
        mEventList = UserModel.get().getEvents();
    }

}
