package ac.id.poligon.greenrockadventure.beranda.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.barang.activity.carril;
import ac.id.poligon.greenrockadventure.barang.activity.coking;
import ac.id.poligon.greenrockadventure.barang.activity.tenda;
import ac.id.poligon.greenrockadventure.detail.activity.ListSewa;
import ac.id.poligon.greenrockadventure.login.activity.Login;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class Home extends AppCompatActivity {

    private AppCompatButton log;

    private ImageButton lihat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lihat = findViewById(R.id.tampilan);
        log = findViewById(R.id.logout);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Toast.makeText(getApplicationContext(),"Anda berhasil Logout",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListSewa.class));
                finish();
            }
        });
    }
    public void tenda(View view){
        Intent tenda = new Intent(Home.this, tenda.class);
        startActivity(tenda);
    }
    public void cooking(View view){
        Intent cooking = new Intent(Home.this, coking.class);
        startActivity(cooking);
    }
    public void alat(View view){
        Intent alat = new Intent(Home.this, carril.class);
        startActivity(alat);
    }
}
