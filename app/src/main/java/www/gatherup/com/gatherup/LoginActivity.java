package www.gatherup.com.gatherup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import www.gatherup.com.gatherup.activities.CreateAccountActivity;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "FB_SIGNIN";
    private EditText etPass;
    private EditText etEmail;
    private TextView status_TV;

    /**
     * Standard Activity lifecycle methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase_Model.get();
        UserModel.get();

        setContentView(R.layout.activity_login);

        // Get events from API 3 miles from zip: 11735
        /*new JsonTask().execute("https://api.meetup.com/2/open_events?zip=11735&radius=3&key=2d374e6c29622464852186f769345e");
        Firebase_Model.get().setAllEventListener();*/
        status_TV = (TextView)findViewById(R.id.tvSignInStatus);

        // Set up click handlers and view item references
        findViewById(R.id.login_signup_btn).setOnClickListener(this);
        findViewById(R.id.login_signin_btn).setOnClickListener(this);
        findViewById(R.id.login_fb_btn).setOnClickListener(this);

        etEmail = (EditText)findViewById(R.id.etEmailAddr);
        etPass = (EditText)findViewById(R.id.etPassword);

        // TODO for testing purposes only
        etPass.setText("password");
        etEmail.setText("test12@test.com");

    }

    /**
     * When the Activity starts and stops, the app needs to connect and
     * disconnect the AuthListener
     */
    @Override
    public void onStart() {
        super.onStart();

        // TODO: add the AuthListener
        Firebase_Model.get().addAuthListener();
        if(Firebase_Model.get().isUserConnected()) {
            //Firebase_Model.get().
            Firebase_Model.get().setMainUser();
            Firebase_Model.get().setRegisteredEventListener();
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                finish();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO: Remove the AuthListener
        //Firebase_Model.get().removeAuthListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_signin_btn:
                signUserIn();
                break;

            case R.id.login_signup_btn:
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                break;

            case R.id.login_fb_btn:
                Toast.makeText(getApplicationContext(), "Login with Facebook", Toast.LENGTH_SHORT);
                break;
        }
    }

    private boolean checkFormFields() {
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email Required");
            return false;
        }
        if (password.isEmpty()){
            etPass.setError("Password Required");
            return false;
        }

        return true;
    }

    private void updateStatus() {
        if (Firebase_Model.get().isUserConnected()) {
            status_TV.setText("Signed in: " + Firebase_Model.get().getEmail());
        }
        else {
            status_TV.setText("Signed Out");
        }
    }

    private void updateStatus(String stat) {
        status_TV.setText(stat);
    }

    private void signUserIn() {
        if (!checkFormFields())
            return;
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();

        // TODO: sign the user in with email and password credentials
        Firebase_Model.get().getAuth().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Nothing Done");
                            //Firebase_Model.get().setMainUser();
                            //Firebase_Model.get().getRegFake();
                            Firebase_Model.get().setRegisteredEventListener();
                            Firebase_Model.get().setMainUser();
                            Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                        updateStatus();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            updateStatus("Invalid password.");
                        }
                        else if (e instanceof FirebaseAuthInvalidUserException) {
                            updateStatus("No account with this email.");
                        }
                        else {
                            updateStatus(e.getLocalizedMessage());
                        }
                    }
                });
    }

    /*private void signUserOut() {
        // TODO: sign the user out
        Firebase_Model.get().getAuth().signOut();
        updateStatus();
    }*/
}

