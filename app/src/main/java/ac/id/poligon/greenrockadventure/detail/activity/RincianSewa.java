package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    private EditText nm_barang,stok,lm_sewa,tg_sewa,tg_back,sts;
    private TextView status,status2;

    List<String>list;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinciandetail);

        //insialisasi/deklarasi
        nm_barang = findViewById(R.id.nm_barang);
        stok = findViewById(R.id.stok);
        lm_sewa = findViewById(R.id.lm_sewa);
        tg_sewa = findViewById(R.id.tgl_sewa);
        tg_back = findViewById(R.id.tgl_kembali);
        sts = findViewById(R.id.status);
        status = findViewById(R.id.status1);



        //disabled edittext
        nm_barang.setEnabled(false);
        stok.setEnabled(false);
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
                        stok.setText(js.getString("stok"));
                        lm_sewa.setText(js.getString("lama_sewa"));
                        tg_sewa.setText(js.getString("tgl_sewa"));
                        tg_back.setText(js.getString("tgl_kembali"));
                        String statusValue = js.getString("status");
                        sts.setText(statusValue);
                        if (statusValue.equals("0")){
                            status.setText("pesanan sedang diproses");
                        } else {
                            status.setText("pesanan selesai");
                        }
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