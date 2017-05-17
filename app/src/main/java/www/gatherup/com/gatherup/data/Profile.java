package www.gatherup.com.gatherup.data;

/**
 * Created by Matthew Luce on 4/12/2017.
 */

public class Profile {
    private int mAge;
    private String mGender;
    //private String mLocation;
    private String mJob;
    private String mBirthday;
    private String mAboutMe;
    private int mRating;

    public Profile(){}
    public Profile(int age,String gender,String job,String birthday,String aboutMe,int rating){
        this.mAge = age;
        this.mGender = gender;
        //this.mLocation = location;
        this.mJob = job;
        this.mBirthday = birthday;
        this.mAboutMe = aboutMe;
        this.mRating = rating;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    /*public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
*/
    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getAboutMe() {
        return mAboutMe;
    }

    public void setAboutMe(String aboutMe) {
        mAboutMe = aboutMe;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

}
