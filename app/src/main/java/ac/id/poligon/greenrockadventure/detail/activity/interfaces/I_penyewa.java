package ac.id.poligon.greenrockadventure.detail.activity.interfaces;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface I_penyewa {
    @Multipart

    @POST("api/bukti/{id}")
    Call<R_penyewa> updateData(
            @Path("id") String id,
            @Part MultipartBody.Part image,
            @Part("image") RequestBody imageName
    );
}

