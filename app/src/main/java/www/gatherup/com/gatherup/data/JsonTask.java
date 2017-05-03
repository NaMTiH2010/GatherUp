package www.gatherup.com.gatherup.data;

import android.os.AsyncTask;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import www.gatherup.com.gatherup.GlobalAppState;
import www.gatherup.com.gatherup.models.UserModel;

/**
 * Created by edwinsventura on 4/27/17.
 */

public class JsonTask extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line ="";

            while ((line=reader.readLine())!=null){
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("results");
            return finalJson + "\n";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection!=null){
                connection.disconnect();
            }

            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        //events = getEventsFromJSON(result);

        // Put the API events on the Usermodel events
        UserModel.get().getEvents().addAll(getEventsFromJSON(result));

    }

    public ArrayList<Event> getEventsFromJSON(String jsonResponse){
        ArrayList<Event> events = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        try {
            JSONObject parentObject = new JSONObject(jsonResponse);
            JSONArray parentArray = parentObject.getJSONArray("results");

            for (int i = 0; i < parentArray.length(); i++){
                Event currentEvent = new Event();
                JSONObject jsonEvent = parentArray.getJSONObject(i);

                // Mandatory
                if (jsonEvent.has("name")){
                    currentEvent.setTitle(jsonEvent.getString("name"));
                }else continue;

                // Optional
                if (jsonEvent.has("time")){
                    Date tempDate = new Date(jsonEvent.getLong("time"));
                    currentEvent.setDate(format.format(tempDate));
                }

                // Optional
                if(jsonEvent.has("rsvp_limit")){
                    currentEvent.setMaxCapacity(jsonEvent.getInt("rsvp_limit"));
                }

                currentEvent.setCategory("Any");

                // Mandatory
                if(jsonEvent.has("venue")){
                    JSONObject venue = jsonEvent.getJSONObject("venue");
                    currentEvent.setCity((venue.has("city"))? venue.getString("city"):"");
                    currentEvent.setAddress((venue.has("address_1"))? venue.getString("address_1"):"");
                    currentEvent.setZipcode((venue.has("zip"))? venue.getString("zip"):"");
                    currentEvent.setState((venue.has("state"))? venue.getString("state"):"");
                    currentEvent.setLatitude((venue.has("lat")? venue.getDouble("lat"):0));
                    currentEvent.setLongitude((venue.has("lon")? venue.getDouble("lon"):0));
                }else continue;

                // Optional
                if(jsonEvent.has("description")){
                    currentEvent.setDescription(android.text.Html.fromHtml( jsonEvent.getString("description"), Html.FROM_HTML_MODE_COMPACT).toString());
                }

                // Optional in case it's needed
                if(jsonEvent.has("yes_rsvp_count")){
                    int numberAtendees = jsonEvent.getInt("yes_rsvp_count");
                }

                // Optional in case it's needed
                if(jsonEvent.has("event_url")){
                    String url = jsonEvent.getString("event_url");
                }

                events.add(currentEvent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return events;
    }



}