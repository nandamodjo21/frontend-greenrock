package ac.id.poligon.greenrockadventure.servis;

import android.annotation.SuppressLint;
import android.content.Context;

public class API_SERVER {
    @SuppressLint("StaticFieldLeak")
    public static Context mct;
    public static final String id_user = SharedPrefManager.getInstance(mct).getKeyId();
    public static final String nik = SharedPrefManager.getInstance(mct).getKeyNik();

    public static final String root_url = "http://0ea5-110-139-238-140.ngrok-free.app";
    public static final String url_login = root_url + "/login";
    public static final String url_regis = root_url + "/register";
    public static final String url_pesan = root_url + "/sewa/add";
    public static final String url_lihat = root_url + "/sewa/lihat/" + id_user;
    public static final String url_list = root_url + "/lihatsemua/lihat/" + nik;
    public static final String url_spin = root_url + "/sewa/barang";

}
