package www.gatherup.com.gatherup.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by edwinsventura on 3/20/17.
 */

public class DetailedEvent implements Parcelable {
    private int eventID;
    private String title;
    private double latitude;
    private double longitude;
    private User owner;
    private String address;
    private ArrayList<User> atendeesList;
    private Calendar startDate;
    private Calendar endDate;
    private String Description;
    private String category;
    private double rating;
    private String mStartTime;
    public DetailedEvent(){
        this(null, "title", 0.0, 0.0, Calendar.getInstance(), Calendar.getInstance(), "description", "category");
    }

    public DetailedEvent(Context context){
        this(context, "title", 0.0, 0.0, Calendar.getInstance(), Calendar.getInstance(), "description", "category");
    }

    public DetailedEvent(Context context, String title, double latitude, double longitude, Calendar startDate, Calendar endDate, String description, String category) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        address = context!=null? AddressGenerator.getAddressLine(context, latitude, longitude): "No valid address";
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        Description = description;
        eventID = 0;
        owner = new User();
        atendeesList = new ArrayList<>();
        rating = 0.0;
    }
    public DetailedEvent(Event e){
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
        this.eventID = 0;
        this.owner = new User();
        this.atendeesList = new ArrayList<>();
        this.rating = 0;

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

    public int getEventID() {
        return eventID;
    }
    public void setEventID(int eventID) {
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

    public ArrayList<User> getAtendeesList() {
        return atendeesList;
    }
    public void setAtendeesList(ArrayList<User> atendeesList) {
        this.atendeesList = atendeesList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventID);
        dest.writeString(title);
        dest.writeString(mStartTime);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
//        dest.writeValue(owner);
        dest.writeString(address);
        if (atendeesList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(atendeesList);
        }
        dest.writeValue(startDate);
        dest.writeValue(endDate);
        dest.writeString(Description);
        dest.writeString(category);
        dest.writeDouble(rating);
    }

    protected DetailedEvent(Parcel in) {
        eventID = in.readInt();
        title = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        mStartTime = in.readString();
      //  owner = (User) in.readValue(User.class.getClassLoader());
        address = in.readString();
        if (in.readByte() == 0x01) {
            atendeesList = new ArrayList<User>();
            in.readList(atendeesList, User.class.getClassLoader());
        } else {
            atendeesList = null;
        }
        startDate = (Calendar) in.readValue(Calendar.class.getClassLoader());
        //startDate = (GregorianCalendar) in.readSerializable();
        endDate = (Calendar) in.readValue(Calendar.class.getClassLoader());
        //endDate = (GregorianCalendar) in.readSerializable();
        //
        Description = in.readString();
        category = in.readString();
        rating = in.readDouble();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DetailedEvent> CREATOR = new Parcelable.Creator<DetailedEvent>() {
        @Override
        public DetailedEvent createFromParcel(Parcel in) {
            return new DetailedEvent(in);
        }

        @Override
        public DetailedEvent[] newArray(int size) {
            return new DetailedEvent[size];
        }
    };













}
