package www.gatherup.com.gatherup.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.MapsActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event_Type;
import www.gatherup.com.gatherup.data.User;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class EventInfoActivity extends AppCompatActivity {
    DetailedEvent mDetailedEvent;
    private final String TAG = "EventInfoActivity";
    public TextView rsvpTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() called");
        setContentView(R.layout.activity_event_info);

        //mDetailedEvent = (DetailedEvent)getIntent().getExtras().get("mDetailedEvent");

        //TODO this is using GlobalAppState
        mDetailedEvent = new DetailedEvent(((GlobalAppState)getApplicationContext()).getCurrentEvent(),this);
        ImageView img = (ImageView)findViewById(R.id.imageView5);
        TextView titleTv = (TextView)findViewById(R.id.event_info_title_tv);
        TextView dayTv = (TextView)findViewById(R.id.event_info_day);
        TextView timetv = (TextView)findViewById(R.id.event_info_time);
        TextView addressTv = (TextView)findViewById(R.id.event_info_address);
        TextView hostedByTv = (TextView)findViewById(R.id.event_info_owner);
        rsvpTv = (TextView)findViewById(R.id.event_info_attendees);
        TextView description = (TextView)findViewById(R.id.event_info_desc);
        final Button rsvpBtn = (Button)findViewById(R.id.event_info_rsvp);
        Button mapBtn = (Button)findViewById(R.id.event_info_open_map_btn);
        Button editBtn = (Button)findViewById(R.id.event_info_edit_btn);
        final Button reportButton = (Button)findViewById(R.id.event_info_report);

        if(mDetailedEvent.getInnerEvent().getId().equals("http")){
            img.setImageResource(R.drawable.meet_up_large);
        }
        else if(mDetailedEvent.getInnerEvent().getEvent_type() == Event_Type.CUSTOM.getTypeNumber()){
            img.setImageResource(R.drawable.default_event_detailed);
/*
                Firebase_Model.get().loadEventIMG(mEventImg,mEvent.getId(),getContext());
*/

        }
        else if(mDetailedEvent.getInnerEvent().getEvent_type() == Event_Type.DEFAULT.getTypeNumber()){
            img.setImageResource(R.drawable.default_event_detailed);
        }
        // A STORED PICTURE
        else{
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/small_"+mDetailedEvent.getInnerEvent().getEvent_type()+""+mDetailedEvent.getInnerEvent().getPicNumber(), null, c.getPackageName());
            img.setImageResource(id);
        }
        editBtn.setVisibility(View.GONE);
        if (mDetailedEvent.getOwner().getEmail().startsWith("http")){
            rsvpBtn.setText("Go to Event Page");
        }

        titleTv.setText(mDetailedEvent.getTitle());
        dayTv.setText(mDetailedEvent.getStartDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        timetv.setText(mDetailedEvent.getStartTime());
                /*mDetailedEvent.getStartDate().get(Calendar.HOUR) + ":" + mDetailedEvent.getStartDate().get(Calendar.MINUTE) +
                " " + mDetailedEvent.getStartDate().getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()) +
                " to " + mDetailedEvent.getEndDate().get(Calendar.HOUR) + ":" + mDetailedEvent.geFtEndDate().get(Calendar.MINUTE) +
                " " + mDetailedEvent.getEndDate().getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()));*/
        addressTv.setText(mDetailedEvent.getAddress());

        hostedByTv.setText((mDetailedEvent.getOwner().getEmail().startsWith("http")) ? "Meetup.com": mDetailedEvent.getOwner().getFullName());
        rsvpTv.setText(mDetailedEvent.getAttendeesList().size() + " people are going");

        description.setText(mDetailedEvent.getDescription());

        rsvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailedEvent.getOwner().getEmail().startsWith("http")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDetailedEvent.getOwner().getEmail()));
                    startActivity(intent);
                }/*else{
                    Toast.makeText(getApplicationContext(),"You succesfully registered", Toast.LENGTH_SHORT).show();
                    rsvpBtn.setEnabled(false);

                    //TODO actually report mDetailedEvent
                    rsvpTv.setText(mDetailedEvent.getAtendeesList().size()+1 + " people are going");
                }*/
                //TODO actually report mDetailedEvent
                rsvpTv.setText(mDetailedEvent.getAttendeesList().size()+1 + " people are going");
                Firebase_Model.get().userRSVP(mDetailedEvent.getEventID());
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventInfoActivity.this, MapsActivity.class);
                //intent.putExtra("mDetailedEvent", mDetailedEvent);
                startActivity(intent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase_Model.get().sendReport(mDetailedEvent.getEventID());
                //TODO implement report to the database
                reportButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "DetailedEvent successfully reported", Toast.LENGTH_SHORT).show();
            }
        });
        UserModel.get().setCurrentDetailedEvent(mDetailedEvent);
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
        if(mDetailedEvent.getEventID() != null && !mDetailedEvent.getEventID().isEmpty()) {
            Firebase_Model.get().removeEventAttendeesCountListener(mDetailedEvent.getEventID());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
        if(mDetailedEvent.getEventID() != null && !mDetailedEvent.getEventID().isEmpty()) {
            Firebase_Model.get().setEventAttendeesCountListener(mDetailedEvent.getEventID());
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
        mDetailedEvent = null;
        finish();
    }


}
