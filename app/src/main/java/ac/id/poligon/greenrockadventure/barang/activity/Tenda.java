package ac.id.poligon.greenrockadventure.barang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;
import ac.id.poligon.greenrockadventure.detail.activity.Details;
import ac.id.poligon.greenrockadventure.penyewaan.activity.Penyewa;

public class Tenda extends AppCompatActivity {

    private ImageView tenda2,tenda3,tenda4,back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenda);

        back = findViewById(R.id.balik);
        tenda2 = findViewById(R.id.tnda2);
        tenda3 = findViewById(R.id.tnda3);
        tenda4 = findViewById(R.id.tnda4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        tenda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
        tenda3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
        tenda4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Penyewa.class));
            }
        });
    }
    public void tnda1(View view){
        Intent tnda1 = new Intent(Tenda.this, Details.class);
        startActivity(tnda1);
    }
}