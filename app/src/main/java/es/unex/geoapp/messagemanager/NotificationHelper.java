package es.unex.geoapp.messagemanager;

import android.location.Location;

import android.util.Log;
import android.widget.ArrayAdapter;


import java.util.Date;
import java.util.List;


import es.unex.geoapp.MainActivity;
import es.unex.geoapp.firebase.FirebaseSender;
import es.unex.geoapp.model.LocationFrequency;
import es.unex.geoapp.retrofit.APIService;
import es.unex.geoapp.retrofit.Common;
import es.unex.geoapp.firebase.FirebaseResponse;
import es.unex.geoapp.firebase.FirebaseData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * This class contains methods to help MainActivity
 */
public class NotificationHelper {

    /**
     * MainActivity to show elements on the UI.
     */
    MainActivity mMainActivity;

    /**
     * The Array for notifications
     */
    public ArrayAdapter<String> mNotificationArrayAdapter;

    /**
     * Parameter for send the Firebase message in a topic.
     */
    private static String topicURL = "/topics/heatmap";

    /**
     * Class to make the POST request.
     */
    private static APIService apiService;

    /**
     * Default constructor
     *
     * @param act The Mainactivity instance.
     */
    public NotificationHelper(MainActivity act) {
        this.mMainActivity = act;
    }


    /**
     * This method send the message with the given radius and dates.
     *
     * @param radius    the radius to send the message.
     * @param location  the location of the user.
     * @param beginDate the begin date for the HeatMap
     * @param endDate   the end date for the HeatMap
     */
    public void sendRequestLocationMessage(String token, int radius, Location location, Date beginDate, Date endDate) {
        if (location == null) {
            return;
        }

        /**Firebase**/
        //Send subscribed devices in the 'heatmap' topic.
        apiService = Common.getFCMClient();
        apiService.sendNotification(new FirebaseSender(topicURL, new FirebaseData(new RequestLocationMessage(token, beginDate, endDate, location.getLatitude(), location.getLongitude(), radius)))).enqueue(new Callback<FirebaseResponse>() {
            @Override
            public void onResponse(Call<FirebaseResponse> call, Response<FirebaseResponse> response) {
                Log.d("HEATMAP:", "Request Send");
            }

            @Override
            public void onFailure(Call<FirebaseResponse> call, Throwable throwable) {
                Log.e("ERROR", throwable.getMessage());
            }
        });


    }

    /**
     * This method send the message with the user's locations for the given dates.
     */
    public static void sendLocationsMessage(List<LocationFrequency> locations, String token) {


        /**Firebase**/

        //Reply request
        apiService = Common.getFCMClient();
        apiService.sendNotification(new FirebaseSender(token, new FirebaseData(new SendLocationsMessage(locations)))).enqueue(new Callback<FirebaseResponse>() {
            @Override
            public void onResponse(Call<FirebaseResponse> call, Response<FirebaseResponse> response) {

                Log.e("Success", " Reply send.");

            }

            @Override
            public void onFailure(Call<FirebaseResponse> call, Throwable throwable) {
                Log.e("ERROR", throwable.getMessage());
            }
        });

    }


}
