package www.gatherup.com.gatherup.fragments;

import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.activities.UserProfileActivity;
import www.gatherup.com.gatherup.data.User;
import www.gatherup.com.gatherup.data.WeakOnListChangedCallback;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class UserListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private ObservableArrayList<User> users;
    private UserAdapter mAdapter;
    private RecyclerView mUserRecyclerView;
    private ChildEventListener mEventAttendeesCountListener;
    private final String TAG = "USER_RECYCLER_ADAPTER";    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static UserListFragment newInstance(int columnCount) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUserRecyclerView = (RecyclerView) view
                .findViewById(R.id.user_recycler_view);
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        users = new ObservableArrayList<>();

        mEventAttendeesCountListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Log.d(TAG, "mEventAttendeesCountListener(" + key + ")");
                findUserByID_AddAttendee(key);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                findUserByID_RemoveAttendee(key);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        updateUI();
        return view;
    }
    public void findUserByID_RemoveAttendee(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"findUserByID_RemoveAttendee("+key+")");
        Firebase_Model.get().getRef().child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //UserModel.get().getCurrentDetailedEvent().removeAttendee(user);
                mAdapter.removeUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void findUserByID_AddAttendee(String key){
        //mPostReference = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"findUserByID_AddAttendee("+key+")");
        Firebase_Model.get().getRef().child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mAdapter.addUser(user);
                //UserModel.get().getCurrentDetailedEvent().addAttendee(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        Firebase_Model.get().setEventAttendeesCountListener(mEventAttendeesCountListener);


    }
    @Override
    public void onPause(){
        super.onPause();
        Firebase_Model.get().removeEventAttendeesCountListener(mEventAttendeesCountListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void updateUI() {
        if (mAdapter == null) {
            if (getActivity() instanceof HomeScreenActivity) {
                mAdapter = new UserListFragment.UserAdapter();
            }
            else{mAdapter = new UserListFragment.UserAdapter();}

            mUserRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            if (getActivity() instanceof HomeScreenActivity) {

            }
            else {}

            mAdapter.clearList();
            mAdapter.notifyDataSetChanged();
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private User mUser;

        private TextView rating_TV;
        private TextView content;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_user, parent, false));
            itemView.setOnClickListener(this);
            rating_TV = (TextView)itemView.findViewById(R.id.rating_TV);
            content = (TextView)itemView.findViewById(R.id.content);
        }

        public void bind(User temp_user) {
            mUser = temp_user;
            rating_TV.setText("Rating: 4/5");
            content.setText(mUser.getUsername());
        }

        @Override
        public void onClick(View view) {
            Firebase_Model.get().findUserForDetailedUser(mUser);
            UserModel.get().setCurrentDetailedUser(UserModel.get().getCurrentDetailedUser());
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            startActivity(intent);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserListFragment.UserHolder> {

        private ObservableArrayList<User> mUserList;
        public void clearList(){
            mUserList.clear();
        }
        public UserAdapter(){//ObservableArrayList<User> users) {
            mUserList = new ObservableArrayList<>();
            ObservableList.OnListChangedCallback<ObservableList<User>> onListChangedCallback
                    = new ObservableList.OnListChangedCallback<ObservableList<User>>() {
                @Override
                public void onChanged(ObservableList<User> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<User> sender, int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<User> sender, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<User> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeRemoved(ObservableList<User> sender, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            };
            mUserList.addOnListChangedCallback(new WeakOnListChangedCallback<>(onListChangedCallback));
        }

        @Override
        public UserListFragment.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new UserListFragment.UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserListFragment.UserHolder holder, int position) {
            User user = mUserList.get(position);
            holder.bind(user);
        }
        public void addUser(User e){if(mUserList != null){mUserList.add(e);}}
        public void removeUser(User e){
            if(mUserList != null){
                mUserList.remove(e);
            }
        }
        @Override
        public int getItemCount() {
            return mUserList.size();
        }

        public void setUsers(ObservableArrayList<User> users) {
            mUserList = users;
        }
    }
}
