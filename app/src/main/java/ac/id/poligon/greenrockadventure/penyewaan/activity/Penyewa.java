package ac.id.poligon.greenrockadventure.penyewaan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.detail.activity.RincianSewa;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class Penyewa extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Spinner nama_barang;
    private EditText stok,lama_sewa,tgl_kembali;
    private AppCompatButton pesan;
    private ArrayAdapter<String> adapter;
    private List<String> data;
    private Button btnTgl;
    private String nm_barang,stokbarang,lm_sewa,tg_back;
    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);
        nama_barang = findViewById(R.id.n_barang);
        stok = findViewById(R.id.jml);
        lama_sewa = findViewById(R.id.l_sewa);
        tgl_kembali = findViewById(R.id.t_kembali);
        btnTgl = findViewById(R.id.btntgl);
        pesan = findViewById(R.id.psn);
        tgl_kembali.setEnabled(false);

        data = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nama_barang.setAdapter(adapter);


        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_SERVER.url_spin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);

                        String namaBarang = js.getString("nama_barang");
                        data.add(namaBarang);
                    }
                    adapter.notifyDataSetChanged();
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(this); requestQueue1.add(stringRequest);

        btnTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDate();
            }
        });
        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stokbarang = stok.getText().toString().trim();
                lm_sewa = lama_sewa.getText().toString().trim();
                tg_back = tgl_kembali.getText().toString().trim();

              if (stokbarang.equals("")){
                    stok.setError("data harus di isi");
                } else if (lm_sewa.equals("")){
                    lama_sewa.setError("tanggal harus di isi");
                } else if (tg_back.equals("")){
                 tgl_kembali.setError("tanggal wajib diisi");
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

    private void showDialogDate(){
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar date = Calendar.getInstance();
                date.set(year, month, dayOfMonth);
                tgl_kembali.setText(dateFormatter.format(date.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void aksiPesan() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", SharedPrefManager.getInstance(this).getKeyId());
        jsonObject.put("barang",nama_barang);
        jsonObject.put("stok",stokbarang);
        jsonObject.put("lamaSewa",lm_sewa);
        jsonObject.put("tglKembali",tg_back);

        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_SERVER.url_pesan, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code")!=0){

                        startActivity(new Intent(getApplicationContext(), RincianSewa.class));
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