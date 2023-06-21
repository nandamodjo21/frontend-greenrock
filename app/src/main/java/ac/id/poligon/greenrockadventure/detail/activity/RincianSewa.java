package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.home;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;

public class RincianSewa extends AppCompatActivity {

    private EditText nm_barang,lm_sewa,tg_sewa,tg_back;

    List<String>list;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinciandetail);

        //insialisasi/deklarasi
        nm_barang = findViewById(R.id.nm_barang);
        lm_sewa = findViewById(R.id.lm_sewa);
        tg_sewa = findViewById(R.id.tgl_sewa);
        tg_back = findViewById(R.id.tgl_kembali);

        //disabled edittext
        nm_barang.setEnabled(false);
        lm_sewa.setEnabled(false);
        tg_sewa.setEnabled(false);
        tg_back.setEnabled(false);

        getData();
    }
    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_SERVER.url_lihat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        nm_barang.setText(js.getString("nama_barang"));
                        lm_sewa.setText(js.getString("lama_sewa"));
                        tg_sewa.setText(js.getString("tgl_sewa"));
                        tg_back.setText(js.getString("tgl_kembali"));
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
        requestQueue.add(stringRequest);
    }
    public void kmbli(View view){
        Intent kmbli = new Intent(RincianSewa.this, home.class);
        startActivity(kmbli);
    }


}