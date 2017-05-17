package www.gatherup.com.gatherup.data;

/**
 * Created by Matthew Luce on 4/12/2017.
 */

public class DetailedUser {
    private Profile mProfile;
    private User mUser;

    public DetailedUser(Profile p,User u){
        this.mProfile = p;
        this.mUser = u;
    }
    public DetailedUser(){
    }

    public Profile getProfile() {
        return mProfile;
    }

    public void setProfile(Profile profile) {
        mProfile = profile;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
