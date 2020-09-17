package es.unex.geoapp.retrofit;


import es.unex.geoapp.firebase.FirebaseResponse;
import es.unex.geoapp.firebase.FirebaseSender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAOwspVRg:APA91bHKbnWAW_PXCXF02Q6hksxF1oXFWXW2dhSh5VxZ5TtIhzgAqVzpc8KvjD9dRyA3M_rCawKoqNdLsP8z3h1JKpphZ2hFf4TusMLWSXs9wzE6Ic6pom2hMY-u9M9G2rJ1MVQELRet"
    })
    @POST( "fcm/send")
    Call<FirebaseResponse> sendNotification(@Body FirebaseSender body);

}
