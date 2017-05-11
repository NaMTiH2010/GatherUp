package www.gatherup.com.gatherup.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.activities.CreateEventActivity;


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link Default_Images_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Default_Images_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Default_Images_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static String event_type ="2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    /*private String mParam1;
    private String mParam2;*/
    private ImageButton imageButton_1;
    private ImageButton imageButton_2;
    private ImageButton imageButton_3;
    private ImageButton imageButton_4;
    private ImageButton imageButton_5;
    private Spinner category_Spin;


    /*private OnFragmentInteractionListener mListener;*/

    public Default_Images_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Default_Images_Fragment newInstance() {
        Default_Images_Fragment fragment = new Default_Images_Fragment();
        event_type = "1";
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    public void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_default__images_, container, false);

        //Find the +1 button

        imageButton_1 = (ImageButton) view.findViewById(R.id.imageButton1);
        imageButton_2 = (ImageButton) view.findViewById(R.id.imageButton2);
        imageButton_3 = (ImageButton) view.findViewById(R.id.imageButton3);
        imageButton_4 = (ImageButton) view.findViewById(R.id.imageButton4);
        imageButton_5 = (ImageButton) view.findViewById(R.id.imageButton5);
        category_Spin = (Spinner) getActivity().findViewById(R.id.category_Spin);

        imageButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img =  (ImageView)getActivity().findViewById(R.id.imageView2);
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/small_"+event_type+""+1, null, c.getPackageName());
                img.setImageResource(id);
                CreateEventActivity.setEventsSelectedIMG(Integer.parseInt(event_type),1);
                closeFragment();
            }
        });
        imageButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img =  (ImageView)getActivity().findViewById(R.id.imageView2);
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/small_"+event_type+""+2, null, c.getPackageName());
                img.setImageResource(id);
                CreateEventActivity.setEventsSelectedIMG(Integer.parseInt(event_type),2);
                closeFragment();
            }
        });
        imageButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img =  (ImageView)getActivity().findViewById(R.id.imageView2);
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/small_"+event_type+""+3, null, c.getPackageName());
                img.setImageResource(id);
                CreateEventActivity.setEventsSelectedIMG(Integer.parseInt(event_type),3);
                closeFragment();
            }
        });
        imageButton_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img =  (ImageView)getActivity().findViewById(R.id.imageView2);
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/small_"+event_type+""+4, null, c.getPackageName());
                img.setImageResource(id);
                CreateEventActivity.setEventsSelectedIMG(Integer.parseInt(event_type),4);
                closeFragment();
            }
        });
        imageButton_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img =  (ImageView)getActivity().findViewById(R.id.imageView2);
                Context c = getContext();
                int id = c.getResources().getIdentifier("drawable/small_"+event_type+""+5, null, c.getPackageName());
                CreateEventActivity.setEventsSelectedIMG(Integer.parseInt(event_type),5);
                img.setImageResource(id);
                closeFragment();
            }
        });
        Context c = getContext();
        event_type = category_Spin.getSelectedItem().toString();
        switch(event_type){
            case "Any":
                event_type = "7";
                break;
            case "Food":
                event_type = "6";
                break;
            case "Sports":
                event_type = "1";
                break;
            case "Gathering":
                event_type = "2";
                break;
            case "Music":
                event_type = "3";
                break;
            case "Learning":
                event_type = "4";
                break;
            case "Games":
                event_type = "5";
                break;
        }
        int id = c.getResources().getIdentifier("drawable/very_small_"+event_type+""+1, null, c.getPackageName());
        imageButton_1.setImageResource(id);

        id = c.getResources().getIdentifier("drawable/very_small_"+event_type+""+2, null, c.getPackageName());
        imageButton_2.setImageResource(id);

        id = c.getResources().getIdentifier("drawable/very_small_"+event_type+""+3, null, c.getPackageName());
        imageButton_3.setImageResource(id);

        id = c.getResources().getIdentifier("drawable/very_small_"+event_type+""+4, null, c.getPackageName());
        imageButton_4.setImageResource(id);

        id = c.getResources().getIdentifier("drawable/very_small_"+event_type+""+5, null, c.getPackageName());
        imageButton_5.setImageResource(id);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {

            mListener.onFragmentInteraction(uri);        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
/*
        mListener = null;
*/
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
