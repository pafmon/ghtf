package es.unex.geoapp.firebase;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import es.unex.geoapp.messagemanager.RequestLocationMessage;
import es.unex.geoapp.messagemanager.SendLocationsMessage;

/*
 *In this class, define the content of the Firebase Cloud Messaging body request.
 */

public class FirebaseData {


    @SerializedName("requestLocation")
    @Expose
    public RequestLocationMessage request;


    @SerializedName("sendLocation")
    @Expose
    public SendLocationsMessage reply;

    public FirebaseData(RequestLocationMessage request) {
        this.request = request;
    }

    public FirebaseData(SendLocationsMessage reply) {
        this.reply = reply;
    }

    public RequestLocationMessage getRequest() {
        return request;
    }

    public SendLocationsMessage getReply() {
        return reply;
    }


}


