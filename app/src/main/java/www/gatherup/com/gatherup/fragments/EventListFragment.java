package www.gatherup.com.gatherup.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.R;

import java.util.ArrayList;

import www.gatherup.com.gatherup.activities.EventInfoActivity;
import www.gatherup.com.gatherup.adapters.EventListViewAdapter;
import www.gatherup.com.gatherup.data.DetailedEvent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_EVENTLIST = "mDetailedEventArrayList";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private ArrayList<DetailedEvent> mDetailedEventArrayList;

    private OnFragmentInteractionListener mListener;

    public EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListFragment newInstance(ArrayList<DetailedEvent> detailedEventArrayList) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM_EVENTLIST, detailedEventArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDetailedEventArrayList = getArguments().getParcelableArrayList(ARG_PARAM_EVENTLIST);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView eventListView = (ListView)view.findViewById(R.id.eventlist_fragment_listview);
        // TODO This is using GlobalAppState

        mDetailedEventArrayList = ((GlobalAppState)getContext().getApplicationContext()).getFilteredDetailedEvents();
        EventListViewAdapter adapter = new EventListViewAdapter(this.getContext(), mDetailedEventArrayList);


        eventListView.setAdapter(adapter);


        //TODO
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //TextView textView = (TextView)view.findViewById(R.id.test_text_view);
        //textView.setText(mParam1 + " " + mParam2);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DetailedEvent detailedEvent = (DetailedEvent)eventListView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), EventInfoActivity.class);
               // intent.putExtra("detailedEvent", detailedEvent);
                //TODO this is using GLobalAppState
                ((GlobalAppState)getContext().getApplicationContext()).setCurrentDetailedEvent(detailedEvent);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
