package ac.id.poligon.greenrockadventure.servis;

import com.google.gson.Gson;

import ac.id.poligon.greenrockadventure.login.interfaces.I_login;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    private static Retrofit retro;

    public static Retrofit getRetrofit() {

        if (retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}

