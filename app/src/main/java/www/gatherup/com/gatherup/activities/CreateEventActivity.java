package www.gatherup.com.gatherup.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import www.gatherup.com.gatherup.HomeScreenActivity;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.models.UserModel;

public class CreateEventActivity extends AppCompatActivity {
    private TextView title_ET;
    private TextView description_ET;
    private Spinner category_Spin;
    private EditText date_ET;
    private EditText startTime_ET;
    private EditText endTime_ET;
    private Calendar mCalendar;
    private DatePicker mDatePicker;
    private int mYear, mMonth,mDay,mHour,mMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Button createButton = (Button)findViewById(R.id.createEvent_BTN);
        title_ET = (TextView)findViewById(R.id.title_ET);
        description_ET = (TextView)findViewById(R.id.description_ET);
        category_Spin = (Spinner)findViewById(R.id.category_Spin);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateEventActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        date_ET = (EditText) findViewById(R.id.date_ET);
        date_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Always use a TextKeyListener when clearing a TextView to prevent android
                    setDate(v);

                }
            }
        });
        startTime_ET = (EditText) findViewById(R.id.startTime_ET);
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
}
