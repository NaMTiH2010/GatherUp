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

import java.util.ArrayList;

import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.User;

/**
 * Created by Matthew Luce on 4/9/2017.
 */

public class Firebase_Model {
    private final String TAG = "Firebase_Model";
    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;
    private enum EnumUser{Add,Remove}
    private int childCount;
    private EnumUser mEnum_user = EnumUser.Add;
    private FirebaseAuth mAuth;
    private ChildEventListener mEventAttendeesCountListener;
    private ChildEventListener mAllEventListener;
    private ChildEventListener mRegisteredEventListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mAuthUser;
    private static Firebase_Model sFirebase_model;

    public static Firebase_Model get(){
        if(sFirebase_model == null) {
            sFirebase_model = new Firebase_Model();
            return sFirebase_model;
        }
        else{
            return sFirebase_model;
        }
    }
    private Firebase_Model() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
/*
        mAuth = FirebaseAuth.getInstance();
        SimpleLogin simpleLogin = new SimpleLogin(f, this);
        final SimpleLoginAuthenticatedHandler simpleLoginAuthenticatedHandler = new SimpleLoginAuthenticatedHandler() {
            @Override
            public void authenticated(FirebaseSimpleLoginError error, FirebaseSimpleLoginUser user) {
                System.out.println("Error: " + error);
                System.out.println("User: " + user);
                System.out.println("3rdP: " + user.getThirdPartyUserData());
            }
        };
        simpleLogin.checkAuthStatus(simpleLoginAuthenticatedHandler);*/

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
        mEventAttendeesCountListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Log.d(TAG,"mEventAttendeesCountListener("+key+")");
                findUserByID_AddAttendee(key);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                findUserByID_RemoveAttendee(key);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mRegisteredEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                if(!key.isEmpty()){
                    findEventByID(key);
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mAllEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                Event ev = dataSnapshot.getValue(Event.class);
                UserModel.get().addEvent(ev);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
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
 /*   public void getRegFake(){
        for(Event e : UserModel.get().getEvents()){
            if(e.getCreator() == getUserID()){
                UserModel.get().addRegisteredEvent(e);
            }
        }
    }*/

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
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).addChildEventListener(mRegisteredEventListener);
    }
    public void removeRegisteredEventListener(){
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).removeEventListener(mRegisteredEventListener);
    }
    public void setAllEventListener() {
        mDatabase.child("events").addChildEventListener(mAllEventListener);
    }
    public void removeAllEventListener(){
        mDatabase.child("events").removeEventListener(mAllEventListener);
    }
    public void setEventAttendeesCountListener(String eventKey){
        mDatabase.child("rsvp").child("event_users").child(eventKey).addChildEventListener(mEventAttendeesCountListener);
    }
    public void removeEventAttendeesCountListener(String eventKey){
        mDatabase.child("rsvp").child("event_users").child(eventKey).removeEventListener(mEventAttendeesCountListener);
    }
    public void addEvent(Event e){
        // Get Unique Key For Event
        String key = mDatabase.child("events").push().getKey();
        e.setCreator(mAuthUser.getUid());
        e.setId(key);
        // Send Event To Database
        mDatabase.child("events").child(key).setValue(e);
        // Add Event To User Who Built Event
        mDatabase.child("rsvp").child("event_users").child(key).child(mAuthUser.getUid()).setValue(true);
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).child(key).setValue(true);

    }

    public void findEventByID(String key){
       // mPostReference = FirebaseDatabase.getInstance().getReference()
        ;
        mDatabase.child("events").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event ev = dataSnapshot.getValue(Event.class);
                UserModel.get().addRegisteredEvent(ev);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //mPostReference.removeEventListener();
    }
    public void findUserForFriendsByID(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()

        mDatabase.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                UserModel.get().addFriends(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void findUserByID_RemoveAttendee(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"findUserByID_RemoveAttendee("+key+")");
        mDatabase.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                UserModel.get().getCurrentDetailedEvent().removeAttendee(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void findUserByID_AddAttendee(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"findUserByID_AddAttendee("+key+")");
        mDatabase.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                UserModel.get().getCurrentDetailedEvent().addAttendee(user);
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
