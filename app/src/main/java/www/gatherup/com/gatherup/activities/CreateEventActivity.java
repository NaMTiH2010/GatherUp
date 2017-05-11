package www.gatherup.com.gatherup.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.AddressGenerator;
import www.gatherup.com.gatherup.data.Event;
import www.gatherup.com.gatherup.fragments.Default_Images_Fragment;
import www.gatherup.com.gatherup.models.Firebase_Model;
import www.gatherup.com.gatherup.models.UserModel;

public class CreateEventActivity extends AppCompatActivity {
    private TextView title_ET;
    private TextView description_ET;
    private Spinner category_Spin;
    private EditText date_ET;
    private EditText startTime_ET;
    private EditText maxCap_ET;
    private EditText city_ET;
    private EditText state_ET;
    private EditText zipcode_ET;
    private EditText address_ET;
    //private EditText endTime_ET;
    private Calendar mCalendar;
    //private DatePicker mDatePicker;
    private int mYear, mMonth,mDay,mHour,mMin;
    private String eventCategory;
    private boolean addedPicture;
    private Button createButton;
    private Button setIMG_BTN;
    private Button uploadIMG_BTN;
    private ImageView event_Img;
    private static int eventImgType = 0;
    private static int eventImgNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
/*
        uploadIMG_BTN = (Button)findViewById(R.id.upload_BTN);
*/
        setIMG_BTN = (Button)findViewById(R.id.setIMG_BTN);
        createButton = (Button)findViewById(R.id.createEvent_BTN);
        event_Img = (ImageView)findViewById(R.id.imageView2);
        title_ET = (TextView)findViewById(R.id.title_ET);
        description_ET = (TextView)findViewById(R.id.description_ET);
        category_Spin = (Spinner)findViewById(R.id.category_Spin);
        date_ET = (EditText) findViewById(R.id.date_ET);
        address_ET = (EditText) findViewById(R.id.address_ET);

        city_ET = (EditText) findViewById(R.id.city_ET);
        state_ET = (EditText) findViewById(R.id.state_ET);
        zipcode_ET = (EditText) findViewById(R.id.zipcode_ET);

        maxCap_ET = (EditText) findViewById(R.id.maxCap_ET);
        startTime_ET = (EditText) findViewById(R.id.startTime_ET);

        category_Spin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ((GlobalAppState)getApplicationContext()).getCategories()));


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send mDetailedEvent to database
               Location loc =  AddressGenerator.getCoords(getApplicationContext(),address_ET.getText().toString()+" "+
                       city_ET.getText().toString()+" "+ state_ET.getText().toString()+" "+ zipcode_ET.getText().toString());
                if(loc == null){
                    Toast.makeText(getApplicationContext(),"Address Not Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Event tempEvent = new Event(title_ET.getText().toString(), date_ET.getText().toString(),
                    startTime_ET.getText().toString(), Integer.parseInt(maxCap_ET.getText().toString()),
                    category_Spin.getSelectedItem().toString(), address_ET.getText().toString(),
                    city_ET.getText().toString(), state_ET.getText().toString(), zipcode_ET.getText().toString(),
                    description_ET.getText().toString(), loc.getLatitude(), loc.getLongitude(), 0, 0);
                    if(eventImgType>0) {
                        tempEvent.setEvent_type(eventImgType);
                        tempEvent.setPicNumber(eventImgNumber);
                    }
                    Firebase_Model.get().addEvent(tempEvent);

                    Intent intent = new Intent(CreateEventActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                }
            }
        });
        setIMG_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImages();
                /*fm.beginTransaction().replace(R.id.images_fragment_container, new Default_Images_Fragment()).commit();*/
            }
        });
        /*uploadIMG_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.images_fragment_container, new Default_Images_Fragment()).commit();*//*
            }
        });*/
        date_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    setDate(v);

                }
            }
        });

        startTime_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    // warnings in the log

                    setTime(v);
                }
            }
        });

        category_Spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eventCategory = (String)adapterView.getItemAtPosition(i);
                //Toast.makeText(getApplicationContext(), categoryFilter + " selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //title_ET.setText(UserModel.get().getAccountName());
        //fullname_TV.setText(UserModel.get().getFullname());


        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMin = mCalendar.get(Calendar.MINUTE);
        showDate(mYear, mMonth+1, mDay);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    public void setTime(View view){
        showDialog(333);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, mYear, mMonth, mDay);
        }
        if(id == 333){
            return new TimePickerDialog(this,myTimeListener,mHour,mMin,false);
        }
        return null;
    }
    public void showImages(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.images_fragment_container);
        if(fragment == null){
            fragment = new Default_Images_Fragment();
            fm.beginTransaction().add(R.id.images_fragment_container,fragment).commit();
        }
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private TimePickerDialog.OnTimeSetListener myTimeListener = new
            TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Time tme = new Time(selectedHour,selectedMinute,0);//seconds by default set to zero
                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a");
                    startTime_ET.setText(formatter.format(tme));
                }
            };

    private void showDate(int year, int month, int day) {
        date_ET.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
    public static void setEventsSelectedIMG(int type,int number){
        eventImgNumber = number;
        eventImgType = type;
    }
}
