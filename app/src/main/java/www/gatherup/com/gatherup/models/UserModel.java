package www.gatherup.com.gatherup.models;

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

    private ArrayList<Event> mRegisteredEvents = new ArrayList<>();
    private ArrayList<Event> mEvents = new ArrayList<>();
    private ArrayList<User> mFriends = new ArrayList<>();
    private DetailedEvent mCurrentDetailedEvent;
    private DetailedUser mCurrentDetailedUser;
    private String mAccountName;
    //private String mAlias;
    private String mEmail;
    private String mFullname;
    //private String mPhoneNum;
    private static final UserModel sUserModel = new UserModel();

    private UserModel(){
    }
    public static UserModel get(){

        return sUserModel;
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
    public ArrayList<Event> getRegisteredDetailedEvents() {
        return mRegisteredEvents;
    }

    public void addRegisteredEvent(Event aEvent) {
        mRegisteredEvents.add(aEvent);
    }

    public ArrayList<Event> getEvents() {
        return mEvents;
    }

    public void addEvent(Event aEvent) {
        mEvents.add(aEvent);
    }

    public ArrayList<User> getFriends() {
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
}
