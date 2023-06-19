package ac.id.poligon.greenrockadventure.register.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.login.activity.Login;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;

public class Register extends AppCompatActivity {

    private EditText namaLengkap,Eemail,noHp,alamat,password,nik;

    String nama,email,Nohp,Alamat,Password,Nik;

    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppCompatButton regis = findViewById(R.id.btnRegister);
        namaLengkap = findViewById(R.id.nama);
        Eemail = findViewById(R.id.email);
        noHp = findViewById(R.id.telp);
        alamat = findViewById(R.id.alamat);
        password = findViewById(R.id.pass);
        nik = findViewById(R.id.nik);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = namaLengkap.getText().toString().trim();
                email = Eemail.getText().toString().trim();
                Nohp = noHp.getText().toString().trim();
                Alamat = alamat.getText().toString().trim();
                Password = password.getText().toString().trim();
                Nik = nik.getText().toString().trim();

                if (nama.equals("")){
                    namaLengkap.setError("nama harus di isi");
                } else if (email.equals("")) {
                    Eemail.setError("email harus di isi");
                }else if (Nohp.equals("")) {
                    noHp.setError("nomor hp harus di isi");
                }else if (Alamat.equals("")) {
                    alamat.setError("alamat harus di isi");
                }else if (Password.equals("")) {
                    password.setError("password harus di isi");
                }else if (Nik.equals("")) {
                    nik.setError("nik harus di isi");
                } else {

                    try {
                        Register();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        });
    }


    @SuppressLint("NotConstructor")
    private void Register() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nama",nama);
        jsonObject.put("email",email);
        jsonObject.put("noHp",Nohp);
        jsonObject.put("alamat",Alamat);
        jsonObject.put("password",Password);
        jsonObject.put("nik",Nik);

        System.out.println(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_SERVER.url_regis, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code")!=0){
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());requestQueue.add(jsonObjectRequest);
    }
}