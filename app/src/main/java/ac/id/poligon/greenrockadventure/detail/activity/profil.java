package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.login.activity.Login;
import ac.id.poligon.greenrockadventure.servis.SharedPrefManager;

public class profil extends AppCompatActivity {

    private AppCompatButton logg;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        user = findViewById(R.id.vuser);

        logg = findViewById(R.id.logoout);

        user.setText(SharedPrefManager.getInstance(this).getKeyName());

        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                Toast.makeText(getApplicationContext(),"Anda berhasil Logout",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

            }
        });

    }
}