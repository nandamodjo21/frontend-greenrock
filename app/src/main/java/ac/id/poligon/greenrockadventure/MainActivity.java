package ac.id.poligon.greenrockadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import ac.id.poligon.greenrockadventure.beranda.activity.home;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

        @Override
        public void run(){
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        }
        }, 5000L);
    }
}