package zm.hashcode.android.mshengu.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;



/**
 * Created by hashcode on 2014/06/30.
 */
public class LocationUtil {

    Context ctx;
    Location updatedLocation;
    LocationListener gpsLocationListener, networkLocationListener;
    LocationManager locationManager;

    public LocationUtil(Context context) {
        ctx = context;
        // Define a listener that responds to location updates
        gpsLocationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {
                if (location.getAccuracy() < 100) {
                    updatedLocation = location;
                    String msg = "Location updated by GPS. Latitude: " + Double.toString(location.getLatitude()) + "," + " Longitude: " +
                            Double.toString(location.getLongitude());
                    Toast.makeText(ctx, "GPS Accuracy: " + String.valueOf(location.getAccuracy()), Toast.LENGTH_SHORT).show();

                }

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(ctx, "GPS is now turned on.", Toast.LENGTH_LONG).show();

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(ctx, "GPS is now turned off. Mshengu application will not work properly.", Toast.LENGTH_LONG).show();

            }
        };


        // Define a listener that responds to location updates
        networkLocationListener = new android.location.LocationListener() {
            public void onLocationChanged(Location location) {

                if (location.getAccuracy() <= 100) {

                    updatedLocation = location;
                    String msg = "Location updated by Network. Latitude: " + Double.toString(location.getLatitude()) + "," + " Longitude: " +
                            Double.toString(location.getLongitude());
                    Toast.makeText(ctx, "Network Accuracy: " + String.valueOf(location.getAccuracy()), Toast.LENGTH_SHORT).show();
                }


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {

                Toast.makeText(ctx, "Network is now turned on.", Toast.LENGTH_LONG).show();

            }

            public void onProviderDisabled(String provider) {

                Toast.makeText(ctx, "Network is now turned off. Mshengu application will not work properly.", Toast.LENGTH_LONG).show();

            }
        };


        locationManager = (LocationManager) ctx.getSystemService(ctx.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, gpsLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, networkLocationListener);

    }


    public Location getUpdatedLocation() {
        return updatedLocation;
    }

    public void stopUpdates() {
        locationManager.removeUpdates(gpsLocationListener);
        locationManager.removeUpdates(networkLocationListener);
    }
}
