package ac.id.poligon.greenrockadventure.servis;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context mCtx;

    private  static final String SHARED_PREF_NAME = "KelasA";
    private static final  String KEY_ID = "id_user";
    private static final  String KEY_EMAIL = "email";
    private static final  String KEY_LEVEL = "level";



    String channelnotif = "channelA" ;
    String channelid = "default" ;


    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public  boolean session(String id_user, String email, String role){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, id_user);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_LEVEL, role);


        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID, "0") != "0"){
            return true;
        }
        return false;
    }

    public  boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null);
    }
    public String getKeyUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);
    }
    public String getKeyLevel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LEVEL, null);
    }

}
