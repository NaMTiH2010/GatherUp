package www.gatherup.com.gatherup.data;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Matthew Luce on 4/12/2017.
 */

public class Event implements Comparable<Event>{
    private Event_Type mEvent_type = Event_Type.DEFAULT;
    private int picNumber;
    private String mTitle;
    private String mDate;
    private String mStartTime;
    private int mMaxCapacity;
    private String mCategory;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipcode;
    private String mDescription;
    private String mId = "";
    private int mAmountOfPeople;
   // private HashMap<String,Boolean> mRegisteredUsers;

    private String mCreator;
    private double mLatitude;
    private double mLongitude;
    private int mRating;

    public Event(){}
    public Event(String title,String date,String startTime,int maxCapacity, String category,
                 String address,String city,String state, String zipcode,String description,
                 double latitude,double longitude,int rating){
        this.mTitle = title;
        this.mDate = date;
        this.mStartTime = startTime;
        this.mMaxCapacity = maxCapacity;
        this.mCategory = category;
        this.mAddress = address;
        this.mCity = city;
        this.mState = state;
        this.mZipcode = zipcode;
        this.mDescription = description;
        this.mCreator = "";
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRating = rating;
        this.mAmountOfPeople = 0;
        //mRegisteredUsers = new HashMap<>();
        mEvent_type = Event_Type.DEFAULT;
        int picNumber = 0;
    }
    public Event(String title,String date,String startTime,int maxCapacity, String category,
                 String address,String city,String state, String zipcode,String description,
                 double latitude,double longitude,int rating, int amountOfPeople){
        this.mTitle = title;
        this.mDate = date;
        this.mStartTime = startTime;
        this.mMaxCapacity = maxCapacity;
        this.mCategory = category;
        this.mAddress = address;
        this.mCity = city;
        this.mState = state;
        this.mZipcode = zipcode;
        this.mDescription = description;
        this.mCreator = "";
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mRating = rating;
        this.mAmountOfPeople = amountOfPeople;
        //mRegisteredUsers = new HashMap<>();
        mEvent_type = Event_Type.DEFAULT;
        int picNumber = 0;
    }
    /*public void addRegisteredUser(String userID){
        mRegisteredUsers.put(userID,true);
    }*/

    public int getAmountOfPeople() {
        return mAmountOfPeople;
    }
    public void setAmountOfPeople(int mAmountOfPeople) {
        this.mAmountOfPeople = mAmountOfPeople;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public int getMaxCapacity() {
        return mMaxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        mMaxCapacity = maxCapacity;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public void setZipcode(String zipcode) {
        mZipcode = zipcode;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getCreator() {
        return mCreator;
    }

    public void setCreator(String creator) {
        mCreator = creator;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

/*    public HashMap<String, Boolean> getRegisteredUsers() {
        return mRegisteredUsers;
    }

    public void setRegisteredUsers(HashMap<String, Boolean> registeredUsers) {
        mRegisteredUsers = registeredUsers;
    }*/

    public String getId() {
        if(mId!=null) {
            return mId;
        }
        return "";
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public int compareTo(@NonNull Event o) {
        return Integer.compare(o.mAmountOfPeople, mAmountOfPeople);
    }

    public int getEvent_type() {
        return mEvent_type.getTypeNumber();
    }

    public void setEvent_type(int event_type) {
        switch (event_type){
            case 0:
                mEvent_type = Event_Type.DEFAULT;
                break;
            case 1:
                mEvent_type = Event_Type.SPORT;
                break;
            case 2:
                mEvent_type = Event_Type.GATHERING;
                break;
            case 3:
                mEvent_type = Event_Type.MUSIC;
                break;
            case 4:
                mEvent_type = Event_Type.LEARNING;
                break;
            case 5:
                mEvent_type = Event_Type.GAMES;
                break;
            case 6:
                mEvent_type = Event_Type.FOOD;
                break;
            case 7:
                mEvent_type = Event_Type.ANY;
                break;
            case 8:
                mEvent_type = Event_Type.CUSTOM;
                break;
        }
    }

    public int getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(int picNumber) {
        this.picNumber = picNumber;
    }
/* public boolean equals(Event e){
        if(){
            return true;
        }
        else{
            return false;
        }
    }*/

}
