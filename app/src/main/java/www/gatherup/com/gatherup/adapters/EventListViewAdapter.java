package www.gatherup.com.gatherup.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.example.seniorproject.test.R;
import www.gatherup.com.gatherup.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import www.gatherup.com.gatherup.data.DetailedEvent;
import www.gatherup.com.gatherup.data.Event;

/**
 * Created by edwinsventura on 3/25/17.
 */

public class EventListViewAdapter extends ArrayAdapter<Event> {

    public EventListViewAdapter(Context context, ArrayList<Event> someEvents){
        super(context, 0, someEvents);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event someEvent = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        TextView shortDay = (TextView)convertView.findViewById(R.id.item_event_day_ofw);
        TextView dayNumber = (TextView)convertView.findViewById(R.id.item_event_day_number);
        TextView title = (TextView)convertView.findViewById(R.id.item_event_title);
        TextView location = (TextView)convertView.findViewById(R.id.item_event_address);
        TextView dayAndTime = (TextView)convertView.findViewById(R.id.item_event_date);
        TextView category = (TextView)convertView.findViewById(R.id.item_event_category);
        TextView numberOfPeople = (TextView)convertView.findViewById(R.id.item_event_people);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(someEvent.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        shortDay.setText(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) );
        dayNumber.setText(String.valueOf(someEvent.getDate().substring(someEvent.getDate().indexOf("/")+1,someEvent.getDate().lastIndexOf("/"))) );
        title.setText(someEvent.getTitle());
        //location.setText(AddressGenerator.getAddressLine(getContext(), detailedEvent.getLatitude(), detailedEvent.getLongitude()));
        location.setText(someEvent.getAddress()+ " "+ someEvent.getCity() + " "+ someEvent.getState()+ " "+ someEvent.getZipcode());
        dayAndTime.setText(someEvent.getDate()+ " "+ someEvent.getStartTime());
        category.setText(someEvent.getCategory());
        //numberOfPeople.setText("RSVP: " + String.valueOf(someEvent.getAmountOfPeople()));
        Log.d("BUG", "yes_rsvp_adapter = "+ someEvent.getAmountOfPeople());


        return convertView;
    }
}
