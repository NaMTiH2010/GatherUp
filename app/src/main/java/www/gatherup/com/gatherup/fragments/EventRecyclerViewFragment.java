package www.gatherup.com.gatherup.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.activities.EventInfoActivity;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.Event_Type;
import www.gatherup.com.gatherup.data.JsonTask;
import www.gatherup.com.gatherup.data.WeakOnListChangedCallback;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

/**
 * Created by Matthew Luce on 5/7/2017.
 */

public class EventRecyclerViewFragment  extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    private ObservableArrayList<Event> events;
    private ChildEventListener mAllEventListener;
    private final String TAG = "EVENT_RECYCLER_ADAPTER";
    private ChildEventListener mRegisteredEventListener;
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;
    private boolean mSubtitleVisible;
    //private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onEventSelected(Event event);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        mEventRecyclerView = (RecyclerView) view
                .findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        mAllEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                Event ev = dataSnapshot.getValue(Event.class);
                UserModel.get().addEvent(ev);
                updateUI();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
            //Firebase_Model.get().setRegisteredEventListener(mRegisteredEventListener);
            Firebase_Model.get().setAllEventListener(mAllEventListener);
            new JsonTask().execute("https://api.meetup.com/2/open_events?zip=11735&radius=3&key=2d374e6c29622464852186f769345e");



        updateUI();

    }
    @Override
    public void onPause(){
        super.onPause();
            Firebase_Model.get().removeAllEventListener(mAllEventListener);
            //Firebase_Model.get().removeRegisteredEventListener(mRegisteredEventListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mCallbacks = null;
    }
    public void updateUI() {
        //UserModel userModel = UserModel.get(getActivity());
        //= userModel.getFilteredEvents();

        if (mAdapter == null) {

            //events = new ObservableArrayList<Event>();
            if (getActivity() instanceof HomeScreenActivity) {
                mAdapter = new EventAdapter(UserModel.get().getEvents());
            }
            else{mAdapter = new EventAdapter(UserModel.get().getRegisteredDetailedEvents());}
            mEventRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            if (getActivity() instanceof HomeScreenActivity) {
                mAdapter.setEvents(UserModel.get().getEvents());
            }
            else { mAdapter.setEvents(UserModel.get().getRegisteredDetailedEvents());}
                    mAdapter.notifyDataSetChanged();
        }

        /*updateSubtitle();*/
    }

    private class EventHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Event mEvent;

        private TextView shortDay;
        private TextView month;
        private TextView dayNumber;
        private TextView title;
        //private TextView location;
        private ProgressBar mProgressBar;
        private TextView dayAndTime;
        private TextView category;
        private TextView numberOfPeople;
        private ImageView mEventImg;

/*        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;*/

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_event, parent, false));
            itemView.setOnClickListener(this);

            shortDay = (TextView)itemView.findViewById(R.id.item_event_day_ofw);
            month = (TextView)itemView.findViewById(R.id.event_month);
            dayNumber = (TextView)itemView.findViewById(R.id.item_event_day_number);
            title = (TextView)itemView.findViewById(R.id.item_event_title);
/*
            location = (TextView)itemView.findViewById(R.id.item_event_address);
*/
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            dayAndTime = (TextView)itemView.findViewById(R.id.item_event_date);
            category = (TextView)itemView.findViewById(R.id.item_event_category);
            numberOfPeople = (TextView)itemView.findViewById(R.id.item_event_people);
            mEventImg = (ImageView)itemView.findViewById(R.id.item_event_image);
            /*mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.event_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.event_solved);*/
        }

        public void bind(Event event) {
            mEvent = event;

            /*mTitleTextView.setText(mEvent.getTitle());
            mDateTextView.setText(mEvent.getDate().toString());
            mSolvedImageView.setVisibility(event.isSolved() ? View.VISIBLE : View.GONE);*/

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(mEvent.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            shortDay.setText(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) );
            month.setText(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) );
            dayNumber.setText(String.valueOf(mEvent.getDate().substring(mEvent.getDate().indexOf("/")+1,mEvent.getDate().lastIndexOf("/"))) );
            title.setText(mEvent.getTitle());

            if(mEvent.getId().equals("http")){
                mEventImg.setImageResource(R.drawable.meet_up_icon);
            }
            else if(mEvent.getEvent_type() == Event_Type.CUSTOM.getTypeNumber()){
                mEventImg.setImageResource(R.drawable.default_event);
/*
                Firebase_Model.get().loadEventIMG(mEventImg,mEvent.getId(),getContext());
*/

            }
            else if(mEvent.getEvent_type() == Event_Type.DEFAULT.getTypeNumber()){
                mEventImg.setImageResource(R.drawable.default_event);
            }
            // A STORED PICTURE
            else{
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/very_small_"+mEvent.getEvent_type()+""+mEvent.getPicNumber(), null, c.getPackageName());
                mEventImg.setImageResource(id);
            }

            //location.setText(AddressGenerator.getAddressLine(getContext(), detailedEvent.getLatitude(), detailedEvent.getLongitude()));
/*
            location.setText(mEvent.getAddress()+ " "+ mEvent.getCity() + " "+ mEvent.getState()+ " "+ mEvent.getZipcode());
*/
            if(mEvent.getMaxCapacity() > 0){
                if(mEvent.getAmountOfPeople() == 0){
                    mProgressBar.setProgress(0);
                }

                else{
                    double dProgress = (((double)mEvent.getAmountOfPeople() / mEvent.getMaxCapacity())*100);
                    mProgressBar.setProgress((int)dProgress);
                    if(dProgress < 26){
                        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                    }
                    else if(dProgress < 51){
                        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                    }
                    else{
                        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    }
                }
            }
            else {
                mProgressBar.setProgress(100);
                mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));;
            }



            // .getIndeterminateDrawable().setColorFilter(0xFFcc0000, PorterDuff.Mode.SRC_IN);
            dayAndTime.setText(mEvent.getDate()+ "\t"+ mEvent.getStartTime());
            category.setText(mEvent.getCategory());
            numberOfPeople.setText("RSVP: " + String.valueOf(mEvent.getAmountOfPeople()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), EventInfoActivity.class);
            // intent.putExtra("Event", Event);
            //TODO this is using GLobalAppState
            //((GlobalAppState)getContext().getApplicationContext()).setCurrentEvent(mEvent);
            UserModel.get().setCurrentEvent(mEvent);
            startActivity(intent);
            //mCallbacks.onEventSelected(mEvent);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private ObservableArrayList<Event> mEvents;

        public EventAdapter(ObservableArrayList<Event> events) {
            mEvents = events;
            ObservableList.OnListChangedCallback<ObservableList<Event>> onListChangedCallback
                    = new ObservableList.OnListChangedCallback<ObservableList<Event>>() {
                @Override
                public void onChanged(ObservableList<Event> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<Event> sender, int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<Event> sender, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<Event> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<Event> sender, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            };
            mEvents.addOnListChangedCallback(new WeakOnListChangedCallback<>(onListChangedCallback));
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            Event event = mEvents.get(position);
            holder.bind(event);
        }
        public void addEvent(Event e){if(mEvents != null){mEvents.add(e);}}
        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public void setEvents(ObservableArrayList<Event> events) {
            mEvents = events;
        }
    }
}
