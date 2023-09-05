package ac.id.poligon.greenrockadventure.register.interfaces;

import ac.id.poligon.greenrockadventure.register.responses.ApiResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @Multipart
    @POST("/api/register") // Ganti dengan URL endpoint upload Anda
    Call<ApiResponse> uploadImage(
            @Part("nama") RequestBody nama,
            @Part("email") RequestBody email,
            @Part("noHp") RequestBody noHp,
            @Part("alamat") RequestBody alamat,
            @Part("password") RequestBody password,
            @Part("nik") RequestBody nik,
            @Part MultipartBody.Part image
    );
}

