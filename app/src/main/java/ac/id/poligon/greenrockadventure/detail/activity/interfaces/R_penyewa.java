package ac.id.poligon.greenrockadventure.detail.activity.interfaces;

import com.google.gson.annotations.SerializedName;

public class R_penyewa {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
