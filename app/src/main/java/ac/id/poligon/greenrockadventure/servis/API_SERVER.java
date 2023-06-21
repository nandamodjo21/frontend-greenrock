package ac.id.poligon.greenrockadventure.servis;

import android.annotation.SuppressLint;
import android.content.Context;

public class API_SERVER {
    @SuppressLint("StaticFieldLeak")
    public static Context mct;
    public static final String id_user = SharedPrefManager.getInstance(mct).getKeyId();
    public static final String root_url = "http://abc6-36-85-221-107.ngrok-free.app";
    public static final String url_login = root_url + "/login";
    public static final String url_regis = root_url + "/register";
    public static final String url_pesan = root_url + "/sewa/add";
    public static final String url_lihat = root_url + "/sewa/lihat/" + id_user;

}
