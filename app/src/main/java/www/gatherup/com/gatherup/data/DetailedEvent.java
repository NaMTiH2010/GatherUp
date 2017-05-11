package www.gatherup.com.gatherup.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import www.gatherup.com.gatherup.activities.EventInfoActivity;

/**
 * Created by edwinsventura on 3/20/17.
 */

public class DetailedEvent{
    private Event mEvent;
    private String eventID;
    private String title;
    private double latitude;
    private double longitude;
    private User owner;
    private String address;
    private ArrayList<User> attendeesList;
    private Calendar startDate;
    private Calendar endDate;
    private String Description;
    private String category;
    private double rating;
    private String mStartTime;
    private EventInfoActivity activity;
    private int mAmountOfPeopleAttending;

    public DetailedEvent(Event e, EventInfoActivity activity){
        mEvent = e;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(e.getDate()));
            this.startDate = cal ;
            this.endDate = cal;
        } catch (ParseException e1) {
            this.startDate = Calendar.getInstance();
            this.endDate = Calendar.getInstance();
        }
        this.mStartTime = e.getStartTime();
        this.title = e.getTitle();
        this.latitude = e.getLatitude();
        this.longitude = e.getLongitude();
        this.address = e.getAddress() + " "+ e.getCity() + " "+ e.getState()+ " "+ e.getZipcode();
        this.category = e.getCategory();
        this.Description = e.getDescription();
        this.eventID = e.getId();
        if(e.getCreator().startsWith("http")){
            String h = e.getCreator();
            this.owner = new User(h,h,h,false,false);
            this.eventID = "API";
        }else{
            this.owner = new User();
        }

        this.attendeesList = new ArrayList<>();
        this.rating = 0;
        this.activity = activity;
    }


    public String getStartTime() {
        if (mStartTime == null )
            mStartTime = "12:00PM";
        if(mStartTime.length()>1){return mStartTime;}
        else{return "12:00PM";}

    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEventID() {
        return eventID;
    }
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ArrayList<User> getAttendeesList() {
        return attendeesList;
    }
    /*public void setAtendeesList(ArrayList<User> atendeesList) {
        this.atendeesList = atendeesList;
    }*/
    public void addAttendee(User user){
        attendeesList.add(user);
        activity.rsvpTv.setText(""+attendeesList.size());
    }
    public void removeAttendee(User user){
        attendeesList.remove(user);
        activity.rsvpTv.setText(""+attendeesList.size());
    }

    public Calendar getStartDate() {
        return startDate;
    }
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Event getInnerEvent(){
        return this.mEvent;
    }














}
