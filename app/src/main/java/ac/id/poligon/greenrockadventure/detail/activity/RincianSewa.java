package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;

public class RincianSewa extends AppCompatActivity {


    private TextView nm_barang,stok,lm_sewa, tg_sw, status;
    private String penyewa;
    private AppCompatButton lst;

    List<String>list;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinciandetail);

        //insialisasi/deklarasi
        nm_barang = findViewById(R.id.nmBarang);
        stok = findViewById(R.id.stk);
       lm_sewa = findViewById(R.id.lmSewa);
        tg_sw = findViewById(R.id.tglSw);
        status = findViewById(R.id.status1);

//        Intent intent = getIntent();
//
//        if (intent != null){
//
//            String namaBarang = intent.getStringExtra("nama_barang");
//            String lamaSewa = intent.getStringExtra("lama_sewa");
//            String stokBarang = intent.getStringExtra("stok");
//
//            nm_barang.setText(namaBarang);
//            stok.setText(stokBarang);
//            lm_sewa.setText(lamaSewa);
//        }


        lst = findViewById(R.id.listdetail);

        lst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Total(penyewa);
            }
        });

      getData();
    }

    private void showPopup(RincianSewa activity, String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

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
                        tg_sw.setText(js.getString("tgl_sewa"));
                        String statusValue = js.getString("status");
                        penyewa = js.getString("id_user");

                        status.setText(statusValue);
                        if (statusValue.equals("0")){
                            status.setText("pesanan sedang diproses");
                        } else {
                            status.setText("Barang sukses disewa");
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
        Intent kmbli = new Intent(RincianSewa.this, Home.class);
        startActivity(kmbli);
    }

    private void Total(String penyewa){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_SERVER.url_total, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if (jsonArray.length() > 0){
                        JSONObject data = jsonArray.getJSONObject(0);
                        String n = data.getString("total_bayar");
                        String rp = "rupiah";
                        showPopup(RincianSewa.this, "TOTAL",n);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this); requestQueue.add(jsonObjectRequest);
    }


}