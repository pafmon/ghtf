package es.unex.geoapp.retrofit;

public class Common {

    private static String baseUrl="https://fcm.googleapis.com";

    public static APIService getFCMClient(){
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
