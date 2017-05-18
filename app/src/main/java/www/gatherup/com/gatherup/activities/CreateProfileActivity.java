package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.LoginActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.Profile;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class CreateProfileActivity extends AppCompatActivity {
    private Spinner gender_Spin;
    private EditText create_prof_age_edit;
    private EditText create_prof_job_edit;
    private EditText create_prof_birth_edit;
    private EditText create_prof_about_edit;
    private Button create_prof_create_btn;

    private static final String REQUIRED = "Required";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] genderChoices = {"Male","Female","Other"};
        setContentView(R.layout.activity_create_profile);
        create_prof_age_edit = (EditText)findViewById(R.id.create_prof_age_edit);
        create_prof_job_edit = (EditText)findViewById(R.id.create_prof_job_edit);
        create_prof_birth_edit = (EditText)findViewById(R.id.create_prof_birth_edit);
        create_prof_about_edit = (EditText)findViewById(R.id.create_prof_about_edit);
        gender_Spin = (Spinner) findViewById(R.id.gender_Spin);
        create_prof_create_btn = (Button) findViewById(R.id.create_prof_create_btn);

        gender_Spin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,genderChoices));
        gender_Spin.setSelection(1);
        if(UserModel.get().getCurrentDetailedUser() != null && UserModel.get().getCurrentDetailedUser().getProfile() != null ){
            create_prof_age_edit.setText(""+UserModel.get().getCurrentDetailedUser().getProfile().getAge());
            create_prof_job_edit.setText(UserModel.get().getCurrentDetailedUser().getProfile().getJob());
            create_prof_birth_edit.setText(UserModel.get().getCurrentDetailedUser().getProfile().getBirthday());
            create_prof_about_edit.setText(UserModel.get().getCurrentDetailedUser().getProfile().getAboutMe());
            if(UserModel.get().getCurrentDetailedUser().getProfile().getGender().equalsIgnoreCase("Male")){
                gender_Spin.setSelection(0);
            }
            else if(UserModel.get().getCurrentDetailedUser().getProfile().getGender().equalsIgnoreCase("Female")){
                gender_Spin.setSelection(1);
            }
            else{gender_Spin.setSelection(2);}
        }

        create_prof_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyInfo()){
                    if(Firebase_Model.get().isUserConnected()) {
                        int age = Integer.parseInt(create_prof_age_edit.getText().toString());
                        Firebase_Model.get().setProfile(new Profile(age, gender_Spin.getSelectedItem().toString(), create_prof_job_edit.getText().toString(), create_prof_birth_edit.getText().toString(), create_prof_about_edit.getText().toString(), 0));

                        /**/
                    }

                    Firebase_Model.get().setHasProfileToTrue();
                    Toast.makeText(CreateProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    finish();
                }

            }
        });

    }

    private boolean verifyInfo(){
        boolean isProfileValid = true;
        int innerAge = 0;
        try {
            innerAge = Integer.parseInt(create_prof_age_edit.getText().toString());
        }catch (ClassCastException ex){create_prof_age_edit.setError("Age must be a number"); return false; }
        final String job = create_prof_job_edit.getText().toString();
        final String birthdate = create_prof_birth_edit.getText().toString();
        final String about = create_prof_about_edit.getText().toString();
        if(TextUtils.isEmpty(job)){
            isProfileValid = false;
            create_prof_job_edit.setError(REQUIRED);
        }
        if(TextUtils.isEmpty(birthdate)){
            isProfileValid = false;
            create_prof_birth_edit.setError(REQUIRED);
        }
        if (TextUtils.isEmpty(about)){
            isProfileValid = false;
            create_prof_about_edit.setError(REQUIRED);
        }
        if(innerAge<15){
            isProfileValid = false;
            create_prof_age_edit.setError("Must be 15 or older");
        }
       return isProfileValid;
    }
}
