package www.gatherup.com.gatherup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;

import www.gatherup.com.gatherup.activities.CreateEventActivity;
import www.gatherup.com.gatherup.activities.CreateProfileActivity;
import www.gatherup.com.gatherup.activities.MyEventsActivity;
import www.gatherup.com.gatherup.activities.SearchEventActivity;
import www.gatherup.com.gatherup.activities.UserProfileActivity;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.JsonTask;
import www.gatherup.com.gatherup.fragments.EventRecyclerViewFragment;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

/*
import www.gatherup.com.gatherup.fragments.EventListFragment;
*/

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{//, EventListFragment.OnFragmentInteractionListener {

    private static final String TAG = "NewPostActivity";
    private GoogleApiClient mClient;

    ArrayList<Event> mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        /*Firebase_Model.get().setAllEventListener();*/
        Firebase_Model.get().setFriendsListener();


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


        Collections.sort(UserModel.get().getEvents());

        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_home, new EventRecyclerViewFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            /*Firebase_Model.get().close();
            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();*/
            super.onBackPressed();
        }
    }

    @Override
    public void onStart(){
        super.onStart();

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
            Firebase_Model.get().findUserForDetailedUser(UserModel.get().getMainUser());
        } else if (id == R.id.nav_create_events) {
            menuclass = CreateEventActivity.class;
        } else if (id == R.id.nav_search_events) {
            menuclass = SearchEventActivity.class;
        } else if (id == R.id.nav_my_events) {
            menuclass = MyEventsActivity.class;
        } else if (id == R.id.nav_sign_out) {
            menuclass = LoginActivity.class;
        }

        if(menuclass == LoginActivity.class){
            Firebase_Model.get().close();
            //Firebase_Model.get().getAuth().signOut();

            Intent intent = new Intent(HomeScreenActivity.this, menuclass);
            startActivity(intent);
            finish();

        }
        else if (menuclass != null) {
            Intent intent = new Intent(HomeScreenActivity.this, menuclass);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


 /*   @Override
    public void onFragmentInteraction(Uri uri) {

    }*/
    @Override
    public void onResume(){
        super.onResume();
        mEventList = UserModel.get().getEvents();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
       /*Firebase_Model.get().removeRegisteredEventListener();*/
    }
}
