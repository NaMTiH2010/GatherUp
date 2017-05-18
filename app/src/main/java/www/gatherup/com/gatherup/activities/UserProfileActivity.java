package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.User;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class UserProfileActivity extends AppCompatActivity {

    private TextView aboutMe_TV;
    private TextView age_TV;
    private TextView birthday_TV;
    private TextView gender_TV;
    private TextView job_TV;
    private ArrayList<Double> ratings;
    //private TextView location_TV;
    private final String TAG = "UserProfileActivity";
    private TextView username_TV;
    private TextView fullname_TV;
    private Button edit_BTN;
    private Button add_friend_BTN;
    private RatingBar ratingBar2;
    private int ratingTotal = 0;
    private ChildEventListener ratingListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button viewEventsButton = (Button)findViewById(R.id.profile_view_events_btn);

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MyEventsActivity.class);
                startActivity(intent);
            }
        });

        aboutMe_TV = (TextView)findViewById(R.id.aboutME_TV);
        age_TV = (TextView)findViewById(R.id.age_TV);
        birthday_TV = (TextView)findViewById(R.id.birthday_TV);
        gender_TV = (TextView)findViewById(R.id.gender_TV);
        job_TV = (TextView)findViewById(R.id.job_TV);
        //location_TV = (TextView)findViewById(R.id.location_TV);
        edit_BTN = (Button)findViewById(R.id.edit_BTN);
        add_friend_BTN = (Button)findViewById(R.id.add_friend_BTN);
        username_TV = (TextView)findViewById(R.id.username_TV);
        fullname_TV = (TextView)findViewById(R.id.fullname_TV);
        ratingBar2 = (RatingBar)findViewById(R.id.ratingBar2);
        username_TV.setText(UserModel.get().getCurrentDetailedUser().getUser().getUsername());
        fullname_TV.setText(UserModel.get().getCurrentDetailedUser().getUser().getFullName());
        ratings = new ArrayList<>();
        ratingListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                double rating = dataSnapshot.getValue(double.class);
                //long count = dataSnapshot.getChildrenCount();
                Log.d(TAG, "Amount of Users Who Rated " + rating + ")");
                updateRatingTotal(rating);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        if(UserModel.get().getCurrentDetailedUser() != null){
            if(!UserModel.get().getMainUser().getUserID().equalsIgnoreCase(UserModel.get().getCurrentDetailedUser().getUser().getUserID())){
                edit_BTN.setVisibility(View.INVISIBLE);
            }else{
                add_friend_BTN.setVisibility(View.INVISIBLE);
            }
            if(UserModel.get().isDisplayFriends()){
                add_friend_BTN.setVisibility(View.INVISIBLE);
            }
            if(UserModel.get().getCurrentDetailedUser().getProfile() != null){
                aboutMe_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getAboutMe());
                age_TV.setText(""+UserModel.get().getCurrentDetailedUser().getProfile().getAge());
                birthday_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getBirthday());
                gender_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getGender());
                job_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getJob());
                //location_TV.setText("Jersey Shore");
            }
            else {
                aboutMe_TV.setText("Not Set");
                age_TV.setText("Not Set");
                birthday_TV.setText("Not Set");
                gender_TV.setText("Not Set");
                job_TV.setText("Not Set");
                //location_TV.setText("Not Set");
            }
        }
        else {
            aboutMe_TV.setText("Not Set");
            age_TV.setText("Not Set");
            birthday_TV.setText("Not Set");
            gender_TV.setText("Not Set");
            job_TV.setText("Not Set");
            //location_TV.setText("Not Set");
        }

        edit_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, CreateProfileActivity.class);
                startActivity(intent);
            }
        });
        add_friend_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase_Model.get().addFriend(UserModel.get().getCurrentDetailedUser().getUser().getUserID());
                Toast.makeText(getApplicationContext(),"You succesfully Added Friend", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        ratingTotal = 0;
        ratings.clear();
        Firebase_Model.get().setRatingListener(ratingListener);

    }
    @Override
    public void onPause(){
        super.onPause();
        Firebase_Model.get().removeRatingListener(ratingListener);
    }
    private void updateRatingTotal(double addition){
        ratings.add(addition);
        ratingTotal += addition;
        updateRating();
    }
    private void updateRating(){
        if(ratings.size()>0) {
            float rating = ratingTotal / ratings.size();
            ratingBar2.setRating(rating);
        }
    }
}
