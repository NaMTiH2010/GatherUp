package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.User;
import android.widget.TextView;

import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class UserProfileActivity extends AppCompatActivity {

    private TextView aboutMe_TV;
    private TextView age_TV;
    private TextView birthday_TV;
    private TextView gender_TV;
    private TextView job_TV;
    //private TextView location_TV;

    private TextView username_TV;
    private TextView fullname_TV;
    private Button edit_BTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button viewEventsButton = (Button)findViewById(R.id.profile_view_events_btn);

        viewEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MyEventsActivity.class);
                startActivity(intent);
            }
        });

        aboutMe_TV = (TextView)findViewById(R.id.aboutME_TV);
        age_TV = (TextView)findViewById(R.id.age_TV);
        birthday_TV = (TextView)findViewById(R.id.birthday_TV);
        gender_TV = (TextView)findViewById(R.id.gender_TV);
        job_TV = (TextView)findViewById(R.id.job_TV);
        //location_TV = (TextView)findViewById(R.id.location_TV);
        edit_BTN = (Button)findViewById(R.id.edit_BTN);

        username_TV = (TextView)findViewById(R.id.username_TV);
        fullname_TV = (TextView)findViewById(R.id.fullname_TV);

        username_TV.setText(UserModel.get().getAccountName());
        fullname_TV.setText(UserModel.get().getFullname());

        if(UserModel.get().getCurrentDetailedUser() != null){
            if(UserModel.get().getMainUser().getUserID() != UserModel.get().getCurrentDetailedUser().getUser().getUserID()){
                edit_BTN.setVisibility(View.INVISIBLE);
            }
            if(UserModel.get().getCurrentDetailedUser().getProfile() != null){
                aboutMe_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getAboutMe());
                age_TV.setText(""+UserModel.get().getCurrentDetailedUser().getProfile().getAge());
                birthday_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getBirthday());
                gender_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getGender());
                job_TV.setText(UserModel.get().getCurrentDetailedUser().getProfile().getJob());
                //location_TV.setText("Jersey Shore");
            }
        }
        else {
            aboutMe_TV.setText("Not Set");
            age_TV.setText("Not Set");
            birthday_TV.setText("Not Set");
            gender_TV.setText("Not Set");
            job_TV.setText("Not Set");
            //location_TV.setText("Not Set");
        }

        edit_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, CreateProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
