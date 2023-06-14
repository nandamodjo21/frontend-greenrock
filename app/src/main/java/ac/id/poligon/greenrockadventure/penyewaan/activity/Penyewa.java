package ac.id.poligon.greenrockadventure.penyewaan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class Penyewa extends AppCompatActivity {

    private EditText nama_barang,lama_sewa,tgl_kembali;
    private AppCompatButton pesan;
    private String nm_barang,lm_sewa,tg_back;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);
        nama_barang = findViewById(R.id.n_barang);
        lama_sewa = findViewById(R.id.l_sewa);
        tgl_kembali = findViewById(R.id.t_kembali);

        pesan = findViewById(R.id.psn);
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nm_barang = nama_barang.getText().toString().trim();
                lm_sewa = lama_sewa.getText().toString().trim();
                tg_back = tgl_kembali.getText().toString().trim();

                if (nm_barang.equals("")){
                    nama_barang.setError("nama barang harus di isi");
                } else if (lm_sewa.equals("")){
                    lama_sewa.setError("data harus di isi");
                } else if (tg_back.equals("")){
                    tgl_kembali.setError("tanggal harus di isi");
                } else {
                    try {
                        aksiPesan();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void aksiPesan() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", SharedPrefManager.getInstance(this).getKeyId());
        jsonObject.put("nama_barang",nm_barang);
        jsonObject.put("lama_sewa",lm_sewa);
        jsonObject.put("tgl_kembali",tg_back);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_SERVER.url_pesan, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")){

                        startActivity(new Intent(getApplicationContext(), rinciandetail.class));
                        finish();
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());requestQueue.add(jsonObjectRequest);
    }

}