package ac.id.poligon.greenrockadventure.barang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.home;
import ac.id.poligon.greenrockadventure.detail.activity.detail1;

public class tenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenda);
    }
    public void tnda1(View view){
        Intent tnda1 = new Intent(tenda .this, detail1.class);
        startActivity(tnda1);
    }
}