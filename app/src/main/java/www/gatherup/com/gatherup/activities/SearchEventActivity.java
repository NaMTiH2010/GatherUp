package www.gatherup.com.gatherup.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.R;
import www.gatherup.com.gatherup.data.DetailedEvent;

public class SearchEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Calendar dateFilter;
    String categoryFilter;
    String keywordFilter;
    double radiusFilter = 0;

    TextView dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        dateFilter = Calendar.getInstance();

        Button mapButton = (Button)findViewById(R.id.search_event_map_btn);
        Button listButton = (Button)findViewById(R.id.search_event_list_btn);
        final EditText keywordTv = (EditText) findViewById(R.id.search_event_keyword);
        final EditText radiusEdit = (EditText) findViewById(R.id.search_event_radius);

        dateTv = (TextView)findViewById(R.id.search_event_date);
        Spinner categorySpin = (Spinner)findViewById(R.id.search_event_category);


        dateTv.setFocusable(false);
        categorySpin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, ((GlobalAppState)getApplicationContext()).getCategories()));


        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordFilter = keywordTv.getText().toString();
                if (radiusEdit.getText().length() != 0)
                    radiusFilter = Double.valueOf(radiusEdit.getText().toString());
                filter();

                // TODO move to map activity/fragment
                Intent intent = new Intent(SearchEventActivity.this, SearchMapActivity.class);
                startActivity(intent);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                keywordFilter = keywordTv.getText().toString();
                if (radiusEdit.getText().length() != 0)
                    radiusFilter = Double.valueOf(radiusEdit.getText().toString());
                filter();


                // TODO move to list activity/fragment
                Intent intent = new Intent(SearchEventActivity.this, SearchListActivity.class);
                startActivity(intent);
            }
        });

        dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "date touched", Toast.LENGTH_SHORT).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(SearchEventActivity.this,SearchEventActivity.this, dateFilter.get(Calendar.YEAR), dateFilter.get(Calendar.MONTH), dateFilter.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        categorySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryFilter = (String)adapterView.getItemAtPosition(i);
                //Toast.makeText(getApplicationContext(), categoryFilter + " selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void filter(){
        GlobalAppState appState = (GlobalAppState)getApplicationContext();
        ArrayList<DetailedEvent> detailedEvents;
        if (radiusFilter > 0 ){
            detailedEvents = (ArrayList<DetailedEvent>) appState.getNearByEvents(40.7525, -73.4287, radiusFilter).clone();
        }
        else {
            detailedEvents = (ArrayList<DetailedEvent>) appState.getDetailedEventList().clone();
        }

        int dateTvLength = dateTv.getText().length();

        for (Iterator<DetailedEvent> it = detailedEvents.iterator(); it.hasNext();){
            DetailedEvent detailedEvent = it.next();
            if (dateTvLength != 0){
                if (detailedEvent.getStartDate().get(Calendar.YEAR )!= dateFilter.get(Calendar.YEAR) ||
                        detailedEvent.getStartDate().get(Calendar.MONTH )!= dateFilter.get(Calendar.MONTH) ||
                        detailedEvent.getStartDate().get(Calendar.DAY_OF_MONTH )!= dateFilter.get(Calendar.DAY_OF_MONTH)){
                    it.remove();
                    continue;
                }
            }
            if (keywordFilter.length() != 0){
                if (!detailedEvent.getTitle().contains(keywordFilter)){
                    it.remove();
                    continue;
                }
            }

            if (categoryFilter != "Any"){
                if (!detailedEvent.getCategory().equals(categoryFilter)){
                    it.remove();
                    continue;
                }
            }
        }

        appState.setFilteredDetailedEvents(detailedEvents);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        dateFilter.set(i, i1, i2);
        dateTv.setText(i1 + "/" + i2 + "/" + i);
    }
}
