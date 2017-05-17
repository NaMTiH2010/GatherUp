package www.gatherup.com.gatherup.data;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by edwinsventura on 3/25/17.
 */

public class AddressGenerator {
    private static Geocoder sGeocoder;
    private static List<Address> sAddresses;
    private static Location loc;
    public static String getAddressLine(Context context, double latitude, double longitude){
        String s = "";

        //Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        sGeocoder = new Geocoder(context);
        sAddresses = null;
        try {
            sAddresses = sGeocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sAddresses.get(0).getAddressLine(0) != null? sAddresses.get(0).getAddressLine(0): "");
            stringBuilder.append(sAddresses.get(0).getLocality() != null? ", " + sAddresses.get(0).getLocality() : "");
            stringBuilder.append(sAddresses.get(0).getAdminArea() != null? ", " + sAddresses.get(0).getAdminArea(): "");
            stringBuilder.append(sAddresses.get(0).getPostalCode() != null? " " + sAddresses.get(0).getPostalCode(): "");

            //TODO Possibly use featured name?
            String knownName = sAddresses.get(0).getFeatureName();

            s = stringBuilder.toString();
        } catch (IOException e) {
            // TODO Properly handle this exception
            e.printStackTrace();
        }

        return s;
    }
    public static String getZipcode(Context context,double latitude,double longitude){
        sGeocoder = new Geocoder(context);
        sAddresses = null;
        try {
            sAddresses = sGeocoder.getFromLocation(latitude, longitude, 1);
            return sAddresses.get(0).getPostalCode();
        } catch (IOException e) {
            // TODO Properly handle this exception
            e.printStackTrace();
        }
        return null;
    }
    public static Location getCoords(Context context, String address){
        sGeocoder = new Geocoder(context);
        sAddresses = null;
        loc = null;
        try{
            sAddresses = sGeocoder.getFromLocationName(address, 1);
            if(sAddresses == null || sAddresses.size()<1){return null;}
            loc = new Location("");
            loc.setLatitude(sAddresses.get(0).getLatitude());
            loc.setLongitude(sAddresses.get(0).getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return loc;
    }
}
