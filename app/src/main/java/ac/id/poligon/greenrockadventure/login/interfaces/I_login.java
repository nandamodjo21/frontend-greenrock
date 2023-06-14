package ac.id.poligon.greenrockadventure.login.interfaces;

import ac.id.poligon.greenrockadventure.login.responses.R_login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface I_login {
    @FormUrlEncoded
    @POST("authentication/login")
    Call<R_login>login(@Field("email")String email,
                       @Field("password")String password);

}
