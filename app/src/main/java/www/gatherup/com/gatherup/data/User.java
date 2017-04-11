package www.gatherup.com.gatherup.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by edwinsventura on 3/20/17.
 */

public class User {//implements Parcelable {
    //private int userId;
    private String mUsername;
    //public String mUserID;
    private String mFullName;
    private String mEmail;
    //private String passwordHash;
    //private ArrayList<User> mFriendList;
    //private double latitude;
    //private double longitude;
    //private ArrayList<Event> mAttendingEventsList;
    //private ArrayList<Event> mCreatedEventsList;


    public User(){}

    public User(String username, String fullName, String email) {
        this.mUsername = username;
        this.mFullName = fullName;
        this.mEmail = email;
        //userId = 0;
        //passwordHash = "passwordHash";
        //latitude = 0.0;
        //longitude = 0.0;
        //mFriendList = new ArrayList<>();
        //mAttendingEventsList = new ArrayList<>();
        //mCreatedEventsList = new ArrayList<>();
    }
    public String getUsername() {
        return mUsername;
    }
    public String getFullName() {
        return mFullName;
    }
    public String getEmail() {
        return mEmail;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
    //public String getUserID(){ return mUserID; }
    //public void setUserID(String userID){this.mUserID = userID;}
/*
    public void setFriendList(ArrayList<User> friendList) {
        this.mFriendList = friendList;
    }
    public ArrayList<User> getFriendList() {
        return mFriendList;
    }


    public ArrayList<Event> getAttendingEventsList() {
        return mAttendingEventsList;
    }
    public void setAttendingEventsList(ArrayList<Event> attendingEventsList) {
        this.mAttendingEventsList = attendingEventsList;
    }

    public void addAttendingEvent(Event e){
        mAttendingEventsList.add(e);
    }


    public ArrayList<Event> getCreatedEventsList() {
        return mCreatedEventsList;
    }
    public void setCreatedEventsList(ArrayList<Event> createdEventsList) {
        this.mCreatedEventsList = createdEventsList;
    }

    public void addCreatedEvent(Event e){
        mCreatedEventsList.add(e);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(userId);
        dest.writeString(mUsername);
        dest.writeString(mFullName);
        dest.writeString(mEmail);
        //dest.writeString(passwordHash);
        if (mFriendList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mFriendList);
        }
        //dest.writeDouble(latitude);
        //dest.writeDouble(longitude);
        if (mAttendingEventsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mAttendingEventsList);
        }
        if (mCreatedEventsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mCreatedEventsList);
        }
    }

    protected User(Parcel in) {
        //userId = in.readInt();
        mUsername = in.readString();
        mFullName = in.readString();
        mEmail = in.readString();
        //passwordHash = in.readString();
        if (in.readByte() == 0x01) {
            mFriendList = new ArrayList<User>();
            in.readList(mFriendList, User.class.getClassLoader());
        } else {
            mFriendList = null;
        }
        //latitude = in.readDouble();
        //longitude = in.readDouble();
        if (in.readByte() == 0x01) {
            mAttendingEventsList = new ArrayList<Event>();
            in.readList(mAttendingEventsList, Event.class.getClassLoader());
        } else {
            mAttendingEventsList = null;
        }
        if (in.readByte() == 0x01) {
            mCreatedEventsList = new ArrayList<Event>();
            in.readList(mCreatedEventsList, Event.class.getClassLoader());
        } else {
            mCreatedEventsList = null;
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };




   // public int getUserId() {
        //return userId;
   // }
  //  public void setUserId(int userId) {
  //      this.userId = userId;
  //  }


    //public void setUsername(String username) {
    //    this.username = username;
    //}


    //public void setFullName(String fullName) {
    //    this.fullName = fullName;
    //}

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
*/

/*
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
*/

    //public void setEmail(String email) {
    //    this.email = email;
    //}







}
