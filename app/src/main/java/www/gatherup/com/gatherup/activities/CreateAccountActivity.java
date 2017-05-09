package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.seniorproject.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.gatherup.com.gatherup.LoginActivity;
import www.gatherup.com.gatherup.R;

import www.gatherup.com.gatherup.data.User;
import www.gatherup.com.gatherup.models.Firebase_Model;

/**
 * Created by edwinsventura on 3/23/17.
 */

public class CreateAccountActivity extends AppCompatActivity {
    private final String TAG = "CreateAccountActivity";
    private static final String REQUIRED = "Required";
    //private boolean accountCreated = false;
    private Button mCreate_BTN;
    private EditText mUsername_ET;
    private EditText mFullName_ET;
    private EditText mEmail_ET;
    private EditText mPassword_ET;
    private EditText mPasswordConfirm_ET;
    private String mMessage;
    private String mEmail;
    private String mUserName;
    private String mFullName;
    private final String mEmailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private final Pattern mPattern = Pattern.compile(mEmailPattern);
    private Matcher mMatcher;
    //User mNewUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation_activity);

        mCreate_BTN = (Button)findViewById(R.id.create_acc_create_btn);
        mUsername_ET = (EditText)findViewById(R.id.create_acc_username_edit);
        mFullName_ET = (EditText)findViewById(R.id.create_acc_name_edit);
        mEmail_ET = (EditText)findViewById(R.id.create_acc_email_edit);
        mPassword_ET = (EditText)findViewById(R.id.create_acc_pass_edit);
        mPasswordConfirm_ET = (EditText)findViewById(R.id.create_acc_pass_confirm_edit);


        mCreate_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditingEnabled(false);
                validateAndCreateUser();
            }
        });
    }
    private void setEditingEnabled(boolean enabled) {
        if (enabled) {
            mCreate_BTN.setVisibility(View.VISIBLE);
        } else {
            mCreate_BTN.setVisibility(View.GONE);
        }
    }
    private void createUserAccount(String email, final String password) {
        // TODO: Create the user account
        Firebase_Model.get().getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //accountCreated = true;
                                    Firebase_Model.get().addUserToDataBase(new User(mUserName,mFullName,mEmail,false,false),password);
                                    Toast.makeText(CreateAccountActivity.this,"User succesfully created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(CreateAccountActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.toString());
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(CreateAccountActivity.this,"This email address is already in use.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
    private void validateAndCreateUser(){
        boolean isUserValid = true;
        final String username = mUsername_ET.getText().toString();
        final String fullName = mFullName_ET.getText().toString();
        final String email = mEmail_ET.getText().toString();
        final String password = mPassword_ET.getText().toString();
        final String passwordConfirm = mPasswordConfirm_ET.getText().toString();
        mMatcher = mPattern.matcher(email);

        // Validate user creation
        if(TextUtils.isEmpty(username)){
            isUserValid = false;
            mUsername_ET.setError(REQUIRED);
        }
        if(TextUtils.isEmpty(email)){
            isUserValid = false;
            mEmail_ET.setError(REQUIRED);
        }
        if(TextUtils.isEmpty(fullName)){
            isUserValid = false;
            mFullName_ET.setError(REQUIRED);
        }
        if (TextUtils.isEmpty(password)){
            isUserValid = false;
            mPassword_ET.setError(REQUIRED);
        }
        if(TextUtils.isEmpty(passwordConfirm)){
            mPasswordConfirm_ET.setError(REQUIRED);
        }
        mMessage = "Account Creation Failed";
        if(!mMatcher.matches()){
            mMessage =  "The email address "+ email +" is invalid";
            isUserValid = false;
        }

        if(!password.equals(passwordConfirm)){
            mMessage = "Passwords don't match";
            isUserValid = false;
        }

        if(isUserValid){
            mUserName = username;
            mEmail = email;
            mFullName = fullName;
            createUserAccount(email,password);
        }else{
            Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_LONG).show();
            setEditingEnabled(true);
        }
    }
}
