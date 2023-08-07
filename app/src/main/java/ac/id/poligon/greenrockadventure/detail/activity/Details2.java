package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.login.activity.Login;
import ac.id.poligon.greenrockadventure.penyewaan.activity.Penyewa;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class Details2 extends AppCompatActivity {

    private AppCompatButton pesan1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        pesan1 = findViewById(R.id.btnpesan);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), Penyewa.class));
            finish();
        }

        pesan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                Toast.makeText(getApplicationContext(),"anda harus login dulu",Toast.LENGTH_SHORT).show();
            }
        });
    }
}