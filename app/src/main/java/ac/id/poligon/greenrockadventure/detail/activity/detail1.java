package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.login.activity.Login;

public class detail1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);
    }
    public void pesan(View view){
        Intent pesan = new Intent(detail1.this, Login.class);
        startActivity(pesan);
    }
}