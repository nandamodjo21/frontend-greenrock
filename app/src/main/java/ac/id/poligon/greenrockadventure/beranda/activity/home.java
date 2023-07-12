package ac.id.poligon.greenrockadventure.beranda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.barang.activity.carril;
import ac.id.poligon.greenrockadventure.barang.activity.coking;
import ac.id.poligon.greenrockadventure.barang.activity.tenda;
import ac.id.poligon.greenrockadventure.detail.activity.ListSewa;

public class home extends AppCompatActivity {

    private ImageButton lihat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lihat = findViewById(R.id.tampilan);

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListSewa.class));
                finish();
            }
        });
    }
    public void tenda(View view){
        Intent tenda = new Intent(home.this, tenda.class);
        startActivity(tenda);
    }
    public void cooking(View view){
        Intent cooking = new Intent(home.this, coking.class);
        startActivity(cooking);
    }
    public void alat(View view){
        Intent alat = new Intent(home.this, carril.class);
        startActivity(alat);
    }
}
