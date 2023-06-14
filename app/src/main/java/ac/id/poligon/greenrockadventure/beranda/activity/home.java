package ac.id.poligon.greenrockadventure.beranda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.barang.activity.carril;
import ac.id.poligon.greenrockadventure.barang.activity.coking;
import ac.id.poligon.greenrockadventure.barang.activity.tenda;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
