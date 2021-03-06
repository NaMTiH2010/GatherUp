package www.gatherup.com.gatherup.models;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.DetailedUser;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.User;

/**
 * Created by Matthew Luce on 4/9/2017.
 */

public class UserModel {
    private boolean displayFriends = true;
    //private ArrayList<Event> mRegisteredEvents = new ArrayList<>();
    /*private HashSet<String>*/
    private HashMap<String,String> noDuplicatesHash = new HashMap<>();
    private ObservableArrayList<Event> mEvents = new ObservableArrayList<>();
    private ObservableArrayList<Event> mRegisteredEvents = new ObservableArrayList<>();
    private ObservableArrayList<User> mFriends = new ObservableArrayList<>();
    private ObservableArrayList<Event> mFilteredEvents = new ObservableArrayList<>();
    //private ArrayList<User> mFriends = new ArrayList<>();

    private DetailedEvent mCurrentDetailedEvent;
    private DetailedUser mCurrentDetailedUser = new DetailedUser();
    private String mAccountName;
    private Context mContext;
    //private String mAlias;
    private String mEmail;
    private String mFullname;
    //private String mPhoneNum;
    private static UserModel sUserModel;
    private boolean mLoading;
    private User mMainUser;
    private Event mCurrentEvent;

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
        noDuplicatesHash.clear();
    }
    public User getMainUser(){
        return mMainUser;
    }
    public void refreshAllEvents(){
        for(Event e : mEvents){
            if(noDuplicatesHash.containsKey(e.getId())) {
                noDuplicatesHash.remove(e.getId());
            }
        }
        mEvents.clear();
    }
    public void refreshAllRegisteredEvents(){
        for(Event e : mRegisteredEvents){
            if(noDuplicatesHash.containsKey(e.getId())) {
                noDuplicatesHash.remove(e.getId());
            }
        }
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
        if(!noDuplicatesHash.containsKey(aEvent.getId())&& aEvent.getAmountOfPeople() < aEvent.getMaxCapacity()) {
            if(!aEvent.getCreator().equalsIgnoreCase("http")) {
                mRegisteredEvents.add(aEvent);
                noDuplicatesHash.put(aEvent.getId(), "R");
            }
       }
        else {
            if(!noDuplicatesHash.get(aEvent.getId()).equals("R")&& aEvent.getAmountOfPeople() < aEvent.getMaxCapacity()) {
                mRegisteredEvents.add(aEvent);
                if(!aEvent.getCreator().equalsIgnoreCase("http")) {
                    noDuplicatesHash.put(aEvent.getId(), "R");
                }
                removeAllEvent(aEvent.getId());
            }
        }
        Firebase_Model.get().getAmtGoingForEvent(aEvent.getId());
    }

    public ObservableArrayList<Event>  getEvents() {
        return mEvents;
    }

    public void addEvent(Event aEvent) {
        if(!noDuplicatesHash.containsKey(aEvent.getId()) && aEvent.getAmountOfPeople() < aEvent.getMaxCapacity()) {
            mEvents.add(aEvent);
            Firebase_Model.get().getAmtGoingForEvent(aEvent.getId());
            noDuplicatesHash.put(aEvent.getId(),"A");
        }
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
        this.mMainUser = user;
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
    private void removeAllEvent(String id){
        for(Event e : mEvents){
            if(e.getId().equals(id)){
                mEvents.remove(e);
                break;
            }
        }
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }
    public boolean getLoading(){
        return mLoading;
    }

    public void setEventsPersonCount(String key, int childrenCount) {
        if(noDuplicatesHash.containsKey(key)){
            if(noDuplicatesHash.get(key).equals("R")){
                for(Event e : mRegisteredEvents){
                    if(e.getId().equals(key)){
                        e.setAmountOfPeople(childrenCount);
                    }
                }
            }
            else{
                for(Event e : mEvents){
                    if(e.getId().equals(key)){
                        e.setAmountOfPeople(childrenCount);
                    }
                }
            }
        }
    }
    public void setCurrentEvent(Event e){
        mCurrentEvent = e;
    }
    public Event getCurrentEvent() {
        return mCurrentEvent;
    }

    public boolean isDisplayFriends() {
        return displayFriends;
    }

    public void setDisplayFriends(boolean displayFriends) {
        this.displayFriends = displayFriends;
    }
}
