package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class ListSewa extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Button back;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<JSONObject> listData;

    private TextView info;
    private String idPenyewa;

   List<String>lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sewa);

        back = findViewById(R.id.backback);
        info = findViewById(R.id.textinfoo);

//        lihatlist = new ArrayList<HashMap<String,String>>();
        listView = findViewById(R.id.listdata);
        lihat = new ArrayList<>();
        listData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lihat);

        listView.setAdapter(adapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String id = SharedPrefManager.getInstance(this).getKeyNik();
        String api = API_SERVER.url_list + id;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, api, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("code")==1){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject js = jsonArray.getJSONObject(i);

                        String namaBarang = js.getString("nama_barang");


                        listData.add(js);
                        lihat.add(namaBarang);
                        Log.d("data", lihat.toString());
                    }
                    adapter.notifyDataSetChanged();
                } else {

                        info.setText("TIDAK ADA PESANAN");

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                JSONObject selected = listData.get(position);
                String selectedBarang = selected.toString();
                Intent intent = new Intent(ListSewa.this, RincianList.class);
                intent.putExtra("selected_barang", selectedBarang);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


    }
}