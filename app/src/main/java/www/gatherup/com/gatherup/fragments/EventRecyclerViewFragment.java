package www.gatherup.com.gatherup.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ObservableList.OnListChangedCallback;
import java.lang.ref.WeakReference;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.activities.EventInfoActivity;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.data.WeakOnListChangedCallback;
import www.gatherup.com.gatherup.models.UserModel;

/**
 * Created by Matthew Luce on 5/7/2017.
 */

public class EventRecyclerViewFragment  extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

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

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
        UserModel userModel = UserModel.get(getActivity());
        ObservableArrayList<Event> events = userModel.getFilteredEvents();

        if (mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mEventRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setEvents(events);
            mAdapter.notifyDataSetChanged();
        }

        /*updateSubtitle();*/
    }

    private class EventHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Event mEvent;

        private TextView shortDay;
        private TextView dayNumber;
        private TextView title;
        private TextView location;
        private TextView dayAndTime;
        private TextView category;
        private TextView numberOfPeople;

/*        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;*/

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_event, parent, false));
            itemView.setOnClickListener(this);

            shortDay = (TextView)itemView.findViewById(R.id.item_event_day_ofw);
            dayNumber = (TextView)itemView.findViewById(R.id.item_event_day_number);
            title = (TextView)itemView.findViewById(R.id.item_event_title);
            location = (TextView)itemView.findViewById(R.id.item_event_address);
            dayAndTime = (TextView)itemView.findViewById(R.id.item_event_date);
            category = (TextView)itemView.findViewById(R.id.item_event_category);
            numberOfPeople = (TextView)itemView.findViewById(R.id.item_event_people);

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
            dayNumber.setText(String.valueOf(mEvent.getDate().substring(mEvent.getDate().indexOf("/")+1,mEvent.getDate().lastIndexOf("/"))) );
            title.setText(mEvent.getTitle());
            //location.setText(AddressGenerator.getAddressLine(getContext(), detailedEvent.getLatitude(), detailedEvent.getLongitude()));
            location.setText(mEvent.getAddress()+ " "+ mEvent.getCity() + " "+ mEvent.getState()+ " "+ mEvent.getZipcode());
            dayAndTime.setText(mEvent.getDate()+ " "+ mEvent.getStartTime());
            category.setText(mEvent.getCategory());
            numberOfPeople.setText("RSVP: " + String.valueOf(0));
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), EventInfoActivity.class);
            // intent.putExtra("Event", Event);
            //TODO this is using GLobalAppState
            ((GlobalAppState)getContext().getApplicationContext()).setCurrentEvent(mEvent);
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

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public void setEvents(ObservableArrayList<Event> events) {
            mEvents = events;
        }
    }
}
