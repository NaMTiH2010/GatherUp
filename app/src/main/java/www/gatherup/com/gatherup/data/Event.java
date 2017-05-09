package www.gatherup.com.gatherup.data;

import android.databinding.ObservableList;

import java.util.HashMap;

/**
 * Created by Matthew Luce on 4/12/2017.
 */

public class Event {
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
    private String mId;
    private boolean hasPicture;
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
        //mRegisteredUsers = new HashMap<>();
    }
    /*public void addRegisteredUser(String userID){
        mRegisteredUsers.put(userID,true);
    }*/

    public boolean isHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(boolean hasPicture) {
        this.hasPicture = hasPicture;
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
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
