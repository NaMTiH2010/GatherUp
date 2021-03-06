package www.gatherup.com.gatherup.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.LoginActivity;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.DetailedUser;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.JsonTask;
import www.gatherup.com.gatherup.data.Profile;
import www.gatherup.com.gatherup.data.User;

/**
 * Created by Matthew Luce on 4/9/2017.
 */

public class Firebase_Model {
    private final String TAG = "Firebase_Model";
    private DatabaseReference mDatabase;
    private DatabaseReference mPostReference;

    public void sendReport(String key) {
        mDatabase.child("reports").child("event").child(key).child(mAuthUser.getUid()).setValue(true);
    }

    public void sendRating(float rating,Event e) {
        mDatabase.child("rating").child(e.getCreator()).child(mAuthUser.getUid()+"_"+e.getId()).setValue(rating);
    }
    public void setRatingListener(ChildEventListener listener){
        mDatabase.child("rating").child(UserModel.get().getCurrentDetailedUser().getUser().getUserID()).addChildEventListener(listener);
    }
    public void removeRatingListener(ChildEventListener listener){
        mDatabase.child("rating").child(UserModel.get().getCurrentDetailedUser().getUser().getUserID()).removeEventListener(listener);
    }
    public void setProfile(Profile profile) {
        mDatabase.child("profiles").child(mAuthUser.getUid()).setValue(profile);
    }

    public void setHasProfileToTrue() {
        mDatabase.child("users").child(mAuthUser.getUid()).child("hasProfile").setValue(true);
        mDatabase.child("users").child(mAuthUser.getUid()).child("userID").setValue(mAuthUser.getUid());
    }


    private enum EnumUser{Add,Remove}
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageRef = storage.getReference();
    private int childCount;
    private EnumUser mEnum_user = EnumUser.Add;
    private FirebaseAuth mAuth;
    private ChildEventListener mEventAttendeesCountListener;
    /*private ChildEventListener mAllEventListener;*/
    private ChildEventListener mRegisteredEventListener;
    private ChildEventListener mFriendsListener;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mAuthUser;
    private static Firebase_Model sFirebase_model;

    public static Firebase_Model get(){
        if(sFirebase_model == null) {
            sFirebase_model = new Firebase_Model();

        }
            return sFirebase_model;
    }
    private Firebase_Model() {
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mAuthUser = mAuth.getCurrentUser();

        /*mAllEventListener = new ChildEventListener() {
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
        };*/

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //FirebaseUser user = firebaseAuth.getCurrentUser();
                if (mAuth.getCurrentUser() != null){
                    mAuthUser = mAuth.getCurrentUser();

                    //Firebase_Model.get().getRegFake();
                    //setRegisteredEventListener();
                    Log.d(TAG, "Signed in HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH: " + mAuthUser.getUid());
                } else if(mAuth.getCurrentUser() == null) {
                    //removeAllEventListener();
                    UserModel.get().refresh();
                    Log.d(TAG, "Currently signed out HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                }
            }
        };
/*        mEventAttendeesCountListener = new ChildEventListener() {
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
        };*/

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
        mFriendsListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                if(!key.isEmpty()){
                    findAndAddFriend(key);
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

    public DatabaseReference getRef(){
        return mDatabase;
    }

    public boolean isUserConnected(){
/*        if(mAuthUser == null)
            mAuthUser = mAuth.getCurrentUser();*/
        return mAuth.getCurrentUser() != null;
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
    public void addUserToDataBase(User user,String pass){
        //user.setUserID(mAuthUser.getUid());
        mAuth.signInWithEmailAndPassword(user.getEmail(),pass);
        mAuthUser = mAuth.getCurrentUser();
        user.setUserID(mAuthUser.getUid());
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
    public void close(){
        removeAuthListener();
        mAuth.signOut();
    }

/*    public void setFriendsListener() {
        UserModel.get().refreshFriends();
        mDatabase.child("following").child(mAuthUser.getUid()).addChildEventListener(mFriendsListener);
    }
    public void removeFriendsListener(){
        mDatabase.child("following").child(mAuthUser.getUid()).removeEventListener(mFriendsListener);
    }*/
    public void setFriendsListener(ChildEventListener listener) {
        UserModel.get().refreshFriends();
        mDatabase.child("following").child(mAuthUser.getUid()).addChildEventListener(listener);
    }
    public void removeFriendsListener(ChildEventListener listener){
        mDatabase.child("following").child(mAuthUser.getUid()).removeEventListener(listener);
    }
    public void setRegisteredEventListener() {
        UserModel.get().refreshAllRegisteredEvents();
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).addChildEventListener(mRegisteredEventListener);
    }
    public void removeRegisteredEventListener(ChildEventListener mRegisteredEventListener){
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).removeEventListener(mRegisteredEventListener);
    }
    public void setAllEventListener(ChildEventListener mAllEventListener) {
        UserModel.get().refreshAllEvents();
        mDatabase.child("events").addChildEventListener(mAllEventListener);
    }
    public void removeAllEventListener(ChildEventListener mAllEventListener){
        mDatabase.child("events").removeEventListener(mAllEventListener);
    }
/*    public void setEventAttendeesCountListener(String eventKey){
        mDatabase.child("rsvp").child("event_users").child(eventKey).addChildEventListener(mEventAttendeesCountListener);
    }
    public void removeEventAttendeesCountListener(String eventKey){
        mDatabase.child("rsvp").child("event_users").child(eventKey).removeEventListener(mEventAttendeesCountListener);
    }*/
    public void setEventAttendeesCountListener(ChildEventListener mEventAttendeesCountListener){
        if(UserModel.get().getCurrentDetailedEvent() != null) {
            String car =""+UserModel.get().getCurrentDetailedEvent().getEventID();
            mDatabase.child("rsvp").child("event_users").child(UserModel.get().getCurrentDetailedEvent().getEventID()).addChildEventListener(mEventAttendeesCountListener);
        }
    }
    public void removeEventAttendeesCountListener(ChildEventListener mEventAttendeesCountListener){
        mDatabase.child("rsvp").child("event_users").child(UserModel.get().getCurrentDetailedEvent().getEventID()).removeEventListener(mEventAttendeesCountListener);
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
    public void getAmtGoingForEvent(String key){
        mDatabase.child("rsvp").child("event_users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel.get().setEventsPersonCount(dataSnapshot.getKey(),(int)dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void findEventByID(String key){
       // mPostReference = FirebaseDatabase.getInstance().getReference()
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
    public void userRSVP(String key){
        mDatabase.child("rsvp").child("event_users").child(key).child(mAuthUser.getUid()).setValue(true);
        mDatabase.child("rsvp").child("user_events").child(mAuthUser.getUid()).child(key).setValue(true);
    }
    public void addFriend(String key){
        mDatabase.child("following").child(mAuthUser.getUid()).child(key).setValue(true);
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
    public void findUserForDetailedUser(User user){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        UserModel.get().getCurrentDetailedUser().setUser(user);
        mDatabase.child("profiles").child(user.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Found Profile For "+dataSnapshot.getKey());
                Profile profile = dataSnapshot.getValue(Profile.class);
                UserModel.get().getCurrentDetailedUser().setProfile(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
  /*  public void findUserByID_RemoveAttendee(String key){
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
    public void findUserByID_AddAttendee(String key){
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
    }*/

    private void findAndAddFriend(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"findFriends("+key+")");
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
/*        public void loadEventIMG(ImageView eventImg,String id,Context context) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.getP
            Glide.with(context)
                    //.using(new FirebaseImageLoader())
                    .load(mStorageRef.child("event_images").child(id))
                    .into(eventImg);
        }*/
    public boolean uploadEventIMG(ImageView eventImg,String id) {
        final boolean[] uploaded = {false};
        eventImg.setDrawingCacheEnabled(true);
        eventImg.buildDrawingCache();
        Bitmap bitmap = eventImg.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorageRef.child("event_images").child(id).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
               // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                uploaded[0] = true;
            }
        });
        return uploaded[0];
    }
}
