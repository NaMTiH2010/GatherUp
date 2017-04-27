package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.User;
import android.widget.TextView;

import www.gatherup.com.gatherup.models.UserModel;

public class UserProfileActivity extends AppCompatActivity {

    private TextView aboutMe_TV;
    private TextView age_TV;
    private TextView birthday_TV;
    private TextView gender_TV;
    private TextView job_TV;
    private TextView location_TV;

    private TextView username_TV;
    private TextView fullname_TV;

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
        location_TV = (TextView)findViewById(R.id.location_TV);

        username_TV = (TextView)findViewById(R.id.username_TV);
        fullname_TV = (TextView)findViewById(R.id.fullname_TV);

        username_TV.setText(UserModel.get().getAccountName());
        fullname_TV.setText(UserModel.get().getFullname());

        aboutMe_TV.setText("Likes long walks on the beach");
        age_TV.setText("25");
        birthday_TV.setText("01/30/1992");
        gender_TV.setText("Male");
        job_TV.setText("Hand Model");
        location_TV.setText("Jersey Shore");


    }
}
