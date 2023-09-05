package ac.id.poligon.greenrockadventure.register.responses;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
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
