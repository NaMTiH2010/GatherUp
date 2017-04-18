package www.gatherup.com.gatherup;

import android.app.Dialog;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final int ERROR_DIALOG_REQUEST = 90;

    private static double latitude = 0,longitude = 0;

    public static void setLatLong(double lat,double lon){
        latitude = lat;
        longitude = lon;
    }

    private static final int POLYGON_POINTS = 5;

    GoogleMap mMap;
    private GoogleApiClient mLocationClient;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
         //       .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        GlobalAppState appState = (GlobalAppState)getApplicationContext();

        latitude = appState.getCurrentEvent().getLatitude();
        longitude = appState.getCurrentEvent().getLongitude();

        //Bundle bundle = getIntent().getExtras();
        //DetailedEvent event = (DetailedEvent)bundle.get("event");

        //latitude = 0;
        //longitude = 0;

        if (servicesOK()) {
            setContentView(R.layout.activity_maps);

            if (initMap()) {
                gotoLocation(latitude, longitude, 20);

            } else {

                //Toast.makeText(this, "Map not connected!", Toast.LENGTH_SHORT).show();
            }

        } else {
            setContentView(R.layout.activity_maps);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng eventLoc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(eventLoc).title("DetailedEvent Marker"));
        CameraUpdate center = CameraUpdateFactory.newLatLng(eventLoc);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);



    }



    public boolean servicesOK() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        return (mMap != null);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        CameraUpdate update = CameraUpdateFactory.zoomTo( 15);
        mMap.moveCamera(update);
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

  //  public void geoLocate(View v) throws IOException {

   //     hideSoftKeyboard(v);

    //    TextView tv = (TextView) findViewById(R.id.editText1);
    //    String searchString = tv.getText().toString();
//
    //    Geocoder gc = new Geocoder(this);
    //    List<Address> list = gc.getFromLocationName(searchString, 1);
//
      //  if (list.size() > 0) {
     //       Address add = list.get(0);
     //       double lat = add.getLatitude();
     //       double lng = add.getLongitude();
     //       gotoLocation(lat, lng, 15);
      //      addMarker(add, lat, lng);
      //  }

    //}

    private void addMarker(Address ad, double lat, double lng) {

        if (marker != null) {
            removeEverything();
        }

        MarkerOptions options = new MarkerOptions()
                //.title(add.getLocality())
                .position(new LatLng(lat, lng))
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker());
//              .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker));
       /* String country = add.getCountryName();
        if (country.length() > 0) {
            options.snippet(country);
        }*/

        marker = mMap.addMarker(options);

        gotoLocation(lat, lng, 5);

    }

    private void removeEverything() {
        marker.remove();
        marker = null;
    }


}
