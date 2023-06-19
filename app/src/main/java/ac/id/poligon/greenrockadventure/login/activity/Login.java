package ac.id.poligon.greenrockadventure.login.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.detail.activity.rinciandetail;
import ac.id.poligon.greenrockadventure.login.interfaces.I_login;
import ac.id.poligon.greenrockadventure.login.responses.R_login;
import ac.id.poligon.greenrockadventure.penyewaan.activity.Penyewa;
import ac.id.poligon.greenrockadventure.register.activity.Register;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import ac.id.poligon.greenrockadventure.servis.NetworkUtils;
import ac.id.poligon.greenrockadventure.servis.Server;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {
    private EditText e_email, e_password;
    private AppCompatButton btnlogin;
    private TextView regis;
    String email, password;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e_email = findViewById(R.id.l_username);
        e_password = findViewById(R.id.l_password);
        btnlogin = findViewById(R.id.btnlogin);
        regis = findViewById(R.id.btnRegis);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = e_email.getText().toString().trim();
                password = e_password.getText().toString().trim();

                if (email.equals("")) {
                    e_email.setError("Masukan Username Anda");
                } else if (password.equals("")) {
                    e_password.setError("Masukan Password Anda");
                } else {
                    try {
                        acesslogin();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }


        });

    }
    private void acesslogin() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
     JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_SERVER.url_login, jsonObject, new Response.Listener<JSONObject>() {
         @Override
         public void onResponse(JSONObject response) {

             try {
              JSONObject js = response.getJSONObject("data");
              if (response.getInt("code")!=0){
                  SharedPrefManager.getInstance(getApplicationContext()).session(js.getString("id_user"),js.getString("email"),js.getString("nama_lengkap"),js.getString("role"));
                  Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(getApplicationContext(),Penyewa.class));
              } else {
                  Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();

              }
             } catch (JSONException e) {
                 throw new RuntimeException(e);
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {

             Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
         }
     });
     requestQueue = Volley.newRequestQueue(getApplicationContext()); requestQueue.add(jsonObjectRequest);

    }


//    private void acesslogin() {
//
//
//        I_login ilogin = Server.getRetrofit().create(I_login.class);
//        Call<R_login> callreslogin = ilogin.login(email, password);
//        System.out.println(callreslogin);
//        callreslogin.enqueue(new Callback<R_login>() {
//            @Override
//            public void onResponse(Call<R_login> call, Response<R_login> response) {
//
//                if (response.body().getStatus().equals("true")){
//                    startActivity(new Intent(getApplicationContext(),Penyewa.class));
//                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<R_login> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
