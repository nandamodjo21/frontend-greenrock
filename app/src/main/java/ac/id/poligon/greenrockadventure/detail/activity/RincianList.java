package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;

public class RincianList extends AppCompatActivity {

    private TextView barang,stok,lama,tgl,total,status;
    private AppCompatButton home;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_list);

        home = findViewById(R.id.tohome);
        barang = findViewById(R.id.textBarang);
        stok = findViewById(R.id.textStok);
        lama = findViewById(R.id.textLama);
        tgl = findViewById(R.id.textTgl);
        total = findViewById(R.id.textTotal);
        status = findViewById(R.id.sts);



        String selectedBarang = getIntent().getStringExtra("selected_barang");

        try {

            JSONObject jsonObject = new JSONObject(selectedBarang);
            Log.d("data",jsonObject.toString());

            String namaBarang = jsonObject.getString("nama_barang");
            int lamaSewa = jsonObject.getInt("lama_sewa");
            int stok1 = jsonObject.getInt("stok");
            String tglSewa = jsonObject.getString("tgl_sewa");
            int totalbayar = jsonObject.getInt("total_bayar");
            int status1 = jsonObject.getInt("status");


            barang.setText(namaBarang);
            stok.setText(String.valueOf(stok1));
            lama.setText(String.valueOf(lamaSewa));
            tgl.setText(tglSewa);
            total.setText(String.valueOf(totalbayar));
            status.setText(String.valueOf(status1));

            if (status1 == 0) {
                status.setText("Pesanan sedang diproses");
            } else {
                status.setText("Status lainnya");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListSewa.class));
            }
        });


    }
}