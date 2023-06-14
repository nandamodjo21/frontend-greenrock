package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.home;

public class rinciandetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinciandetail);
    }
    public void kmbli(View view){
        Intent kmbli = new Intent(rinciandetail.this, home.class);
        startActivity(kmbli);
    }
}