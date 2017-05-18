package www.gatherup.com.gatherup.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.fragments.UserListFragment;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        UserListFragment usersListFragment = new UserListFragment();
        FragmentManager manager= getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.my_users_content, usersListFragment).commit();    }
}
