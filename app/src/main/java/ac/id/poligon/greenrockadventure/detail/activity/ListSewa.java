package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import ac.id.poligon.greenrockadventure.servis.API_SERVER;

public class ListSewa extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private ArrayAdapter<String> adapter;

   List<String>lihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sewa);

//        lihatlist = new ArrayList<HashMap<String,String>>();
        listView = findViewById(R.id.listdata);
        lihat = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lihat);

        listView.setAdapter(adapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_SERVER.url_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Response",response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i <jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);

                        String namaBarang = js.getString("nama_barang");
                        String lamaSewa = js.getString("lama_sewa");
                        lihat.add(namaBarang + " : " + lamaSewa);
                        Log.d("data",lihat.toString());
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
        requestQueue.add(stringRequest);
    }
}