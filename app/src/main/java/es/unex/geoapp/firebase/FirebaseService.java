package es.unex.geoapp.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import es.unex.geoapp.locationmanager.LocationManager;
import es.unex.geoapp.messagemanager.NotificationHelper;
import es.unex.geoapp.model.LocationFrequency;

public class FirebaseService extends FirebaseMessagingService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    public FirebaseService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("Firebase Token", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String TAG = "FirebaseService: ";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Map<String, String> data = remoteMessage.getData();

            FirebaseData notification = gson.fromJson(String.valueOf(data), FirebaseData.class);

            //Check the type of message
            if (notification.getRequest() != null) {
                processRequest(notification);
                Log.e(TAG, "Locations received from sender.");

            } else {
                if (notification.getReply() != null) {
                    Log.e(TAG, "Reply received.");
                    processReply(notification);

                } else {
                    Log.e(TAG, "Another kind of message: ");
                }

            }

        }

    }

    private void processReply(FirebaseData notification) {
        LocationManager.storeLocations(notification.getReply().getLocationList());
    }

    private void processRequest(FirebaseData notification) {
        List<LocationFrequency> locations = LocationManager.getLocationHistory(notification.getRequest().getBeginDate(), notification.getRequest().getEndDate(), notification.getRequest().getLatitude(), notification.getRequest().getLongitude(), notification.getRequest().getRadius());
        List<List<LocationFrequency>> locationLists = partition(locations, 25);

        for (List<LocationFrequency> list : locationLists) {
            NotificationHelper.sendLocationsMessage(list, notification.getRequest().getSenderId());
        }
    }

    private static <T> List<List<T>> partition(List<T> input, int size) {
        List<List<T>> lists = new ArrayList<List<T>>();
        for (int i = 0; i < input.size(); i += size) {
            lists.add(input.subList(i, Math.min(input.size(), i + size)));
        }
        return lists;
    }

}
