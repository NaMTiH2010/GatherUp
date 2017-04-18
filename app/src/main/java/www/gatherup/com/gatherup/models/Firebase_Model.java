package www.gatherup.com.gatherup.models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.User;

/**
 * Created by Matthew Luce on 4/9/2017.
 */

public class Firebase_Model {
    private final String TAG = "FB_SIGNIN";
    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mAuthUser;
    private static final Firebase_Model sFirebase_model = new Firebase_Model();

    public static Firebase_Model get(){
        return sFirebase_model;
    }
    private Firebase_Model(){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "Signed in: " + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "Currently signed out");
                }
            }
        };
    }

    // START Authentication Methods
    public void addAuthListener(){
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void removeAuthListener(){
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public boolean isUserConnected(){
        if(mAuthUser == null)
            mAuthUser = mAuth.getCurrentUser();


        return mAuthUser != null;
    }

    public String getEmail(){
        return mAuthUser.getEmail();
    }
    public String getUserID(){
        return mAuthUser.getUid();
    }
    public FirebaseAuth getAuth(){
        return mAuth;
    }
    /*public DatabaseReference getDatabaseRef(){
        return mDatabase;
    }*/
    // END Authentication Methods

    // START Database Methods
    public void addUserToDataBase(User user,String pass){
        //user.setUserID(mAuthUser.getUid());
        mAuth.signInWithEmailAndPassword(user.getEmail(),pass);
        mAuthUser = mAuth.getCurrentUser();
        mDatabase.child("users").child(mAuthUser.getUid()).setValue(user);
        //mAuthUser = null;
        mAuth.signOut();
    }
    public void setMainUser(){
        isUserConnected();
        mDatabase.child("users").child(getUserID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User tempUser = dataSnapshot.getValue(User.class);
                        UserModel.get().setMainUser(tempUser);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Nothing Done");
                    }
                });

    }

    public void setRegisteredEventListener() {
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    findEventByID(postSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setAllEventListener() {

        mDatabase.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for(DataSnapshot child : children){
                    Event e = child.getValue(Event.class);
                    UserModel.get().addEvent(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Nothing Done");
            }
        });
    }

    public void addEvent(Event e){
        // Get Unique Key For Event
        //mAuthUser = mAuth.getCurrentUser();
        String key = mDatabase.child("events").push().getKey();
        // Add Extra Attributes to Event
        //e.addRegisteredUser(mAuthUser.getUid());
        e.setCreator(mAuthUser.getUid());
        // Send Event To Database
        mDatabase.child("events").push().setValue(e);
        // Add Event To User Who Built Event
        mDatabase.child("rsvp").child("event_users").child(key).child(mAuthUser.getUid()).setValue(true);
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).child(key).setValue(true);

        //mDatabase.child('members')
        // push event to database using that key
        // add reference to that event key with user
        //
                    //.setValue(e);
    }

    public void findEventByID(String key){
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("events").child(key);
        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel.get().addRegisteredEvent(dataSnapshot.getValue(Event.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //mPostReference.removeEventListener();
    }
    public void findUserByID(String key){
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(key);
        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel.get().addFriends(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // END Database Methods

    /// Getters ///


    /// Setters ///

    // Authentication
        // Authenticate User Log In
        // Create New User Authentication
        // Delete Account
        // Change Account Password

    // Database
        //Creation
            // Create New User On Database
            // Create User Profile
            // Create New Friend OR Follow Another User
            // Create DetailedEvent
                // Add Filters
                // Add Location
                // Add Category
                // Add DetailedEvent Info
                // Add Rating
                // Add Attendance
                    // Pending Invites
                    // Going

        // Deletion
            // Remove Friend / Stop Following Another User
            // Delete DetailedEvent
                // Delete Filters
                // Delete Location
                // Delete Category
                // Delete DetailedEvent Info
                // Delete Rating
                // Delete Attendance
                    // Pending Invites
                    // Going
            // Remove User From DetailedEvent
            // Remove An DetailedEvent Filter
            // Delete Account

        // Update
            // Change DetailedEvent
                // Info
                // Location
                // Add Filters
                // Category
                // Your Rating Of DetailedEvent
            // Change Profile Info
            // Change Account
                // Email
                // Alias
                // Phone

        // Fetch
            // Fetch User Information & Update User Model
            // Fetch User Profile
                //
                //
            // Fetch Followed Users / Friends
            // Fetch Registered Events
                // Info
                // Location
                // Filters
                // Category
                // Ratings of DetailedEvent
            // Fetch Events By Location
                // Info
                // Location
                // Filters
                // Category
                // Ratings of DetailedEvent
            //

}
