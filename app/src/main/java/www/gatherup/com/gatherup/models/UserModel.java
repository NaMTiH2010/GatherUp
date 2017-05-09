package www.gatherup.com.gatherup.models;

import android.content.Context;
import android.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.DetailedUser;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.User;

/**
 * Created by Matthew Luce on 4/9/2017.
 */

public class UserModel {

    //private ArrayList<Event> mRegisteredEvents = new ArrayList<>();
    private ObservableArrayList<Event> mEvents = new ObservableArrayList<>();
    private ObservableArrayList<Event> mRegisteredEvents = new ObservableArrayList<>();
    private ObservableArrayList<User> mFriends = new ObservableArrayList<>();
    private ObservableArrayList<Event> mFilteredEvents = new ObservableArrayList<>();
    //private ArrayList<User> mFriends = new ArrayList<>();
    private DetailedEvent mCurrentDetailedEvent;
    private DetailedUser mCurrentDetailedUser;
    private String mAccountName;
    private Context mContext;
    //private String mAlias;
    private String mEmail;
    private String mFullname;
    //private String mPhoneNum;
    private static UserModel sUserModel;

    private UserModel(Context context){
        this.mContext = context;
    }
    private UserModel(){}
    public static UserModel get(Context context){
        if(sUserModel == null){
            sUserModel = new UserModel(context);
        }
        return sUserModel;
    }

    public static UserModel get(){
        if(sUserModel == null){
            sUserModel = new UserModel();
        }
        return sUserModel;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    /* public void removeYourEventsFromAllEvents(){
            for(Event e : mEvents){
                for (Event re : mRegisteredEvents){
                    if(e.getId() == re.getId()){
                        mEvents.remove(e);
                    }
                }
            }
        }*/
    public ObservableArrayList<Event> getRegisteredDetailedEvents() {
        return mRegisteredEvents;
    }
    public void refresh(){
        mEvents.clear();
        mFriends.clear();
        mRegisteredEvents.clear();
    }
    public void refreshAllEvents(){
        mEvents.clear();
    }
    public void refreshAllRegisteredEvents(){
        mRegisteredEvents.clear();
    }

    public void refreshFriends(){
        mFriends.clear();
    }
    public ObservableArrayList<Event> getFilteredEvents() {
        return mFilteredEvents;
    }

    public void setFilteredEvents(ObservableArrayList<Event> filteredEvents) {
        mFilteredEvents = filteredEvents;
    }

    public void addRegisteredEvent(Event aEvent) {
        mRegisteredEvents.add(aEvent);
    }

    public ObservableArrayList<Event>  getEvents() {
        return mEvents;
    }

    public void addEvent(Event aEvent) {
        mEvents.add(aEvent);
    }

    public ObservableArrayList<User> getFriends() {
        return mFriends;
    }

    public void addFriends(User friend) {
        mFriends.add(friend);
    }

    public String getAccountName() {
        return mAccountName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFullname() {
        return mFullname;
    }

    public void setMainUser(User user){
        this.mAccountName = user.getUsername();
        this.mEmail = user.getEmail();
        this.mFullname = user.getFullName();
       /* for (String key: user.getEvents().keySet()) {
            Firebase_Model.get().findEventByID(key);
        }
        for (String key: user.getFollowing().keySet()) {
            Firebase_Model.get().findUserByID(key);
        }*/
    }

    public DetailedEvent getCurrentDetailedEvent() {
        return mCurrentDetailedEvent;
    }

    public void setCurrentDetailedEvent(DetailedEvent currentDetailedEvent) {
        mCurrentDetailedEvent = currentDetailedEvent;
    }

    public DetailedUser getCurrentDetailedUser() {
        return mCurrentDetailedUser;
    }

    public void setCurrentDetailedUser(DetailedUser currentDetailedUser) {
        mCurrentDetailedUser = currentDetailedUser;
    }
    public ObservableArrayList<Event> getNearByEvents(double latitude, double longitude, double mileRadius){
        ObservableArrayList<Event> nearbyEvents = new ObservableArrayList<>();

        for(Event aEvent : mEvents){
            if (distFrom(latitude, longitude, aEvent.getLatitude(), aEvent.getLongitude()) < mileRadius){
                nearbyEvents.add(aEvent);
            }
        }

        return nearbyEvents;
    }

    public double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }
}
