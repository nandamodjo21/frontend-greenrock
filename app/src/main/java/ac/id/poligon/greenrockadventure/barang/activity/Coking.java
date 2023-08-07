package ac.id.poligon.greenrockadventure.barang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;
import ac.id.poligon.greenrockadventure.penyewaan.activity.Penyewa;

public class Coking extends AppCompatActivity {

    private ImageView bck,gas,kompor1,kompor2,alat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coking);

        bck = findViewById(R.id.bekbek);
        gas = findViewById(R.id.gas);
        kompor1 = findViewById(R.id.komporkotak);
        kompor2 = findViewById(R.id.komporwind);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
        kompor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
        kompor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
    }
}