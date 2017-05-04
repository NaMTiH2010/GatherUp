package www.gatherup.com.gatherup.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.MapsActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;

public class EventInfoActivity extends AppCompatActivity {
    DetailedEvent mDetailedEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        //mDetailedEvent = (DetailedEvent)getIntent().getExtras().get("mDetailedEvent");

        //TODO this is using GlobalAppState
        mDetailedEvent = new DetailedEvent(((GlobalAppState)getApplicationContext()).getCurrentEvent());

        TextView titleTv = (TextView)findViewById(R.id.event_info_title_tv);
        TextView dayTv = (TextView)findViewById(R.id.event_info_day);
        TextView timetv = (TextView)findViewById(R.id.event_info_time);
        TextView addressTv = (TextView)findViewById(R.id.event_info_address);
        TextView hostedByTv = (TextView)findViewById(R.id.event_info_owner);
        final TextView rsvpTv = (TextView)findViewById(R.id.event_info_atendees);
        TextView description = (TextView)findViewById(R.id.event_info_desc);
        final Button rsvpBtn = (Button)findViewById(R.id.event_info_rsvp);
        Button mapBtn = (Button)findViewById(R.id.event_info_open_map_btn);
        Button editBtn = (Button)findViewById(R.id.event_info_edit_btn);
        final Button reportButton = (Button)findViewById(R.id.event_info_report);


        editBtn.setVisibility(View.GONE);
        if (mDetailedEvent.getOwner().getEmail().startsWith("http")){
            rsvpBtn.setText("Go to Event Page");
        }

        titleTv.setText(mDetailedEvent.getTitle());
        dayTv.setText(mDetailedEvent.getStartDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        timetv.setText(mDetailedEvent.getStartTime());
                /*mDetailedEvent.getStartDate().get(Calendar.HOUR) + ":" + mDetailedEvent.getStartDate().get(Calendar.MINUTE) +
                " " + mDetailedEvent.getStartDate().getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()) +
                " to " + mDetailedEvent.getEndDate().get(Calendar.HOUR) + ":" + mDetailedEvent.getEndDate().get(Calendar.MINUTE) +
                " " + mDetailedEvent.getEndDate().getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()));*/
        addressTv.setText(mDetailedEvent.getAddress());

        hostedByTv.setText((mDetailedEvent.getOwner().getEmail().startsWith("http")) ? "Meetup.com": mDetailedEvent.getOwner().getFullName());

        rsvpTv.setText(mDetailedEvent.getAtendeesList().size() + " people are going");
        description.setText(mDetailedEvent.getDescription());

        rsvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDetailedEvent.getOwner().getEmail().startsWith("http")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mDetailedEvent.getOwner().getEmail()));
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"You succesfully registered", Toast.LENGTH_SHORT).show();
                    rsvpBtn.setEnabled(false);

                    //TODO actually report mDetailedEvent
                    rsvpTv.setText(mDetailedEvent.getAtendeesList().size()+1 + " people are going");
                }

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

                //TODO implement report to the database
                reportButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "DetailedEvent successfully reported", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
