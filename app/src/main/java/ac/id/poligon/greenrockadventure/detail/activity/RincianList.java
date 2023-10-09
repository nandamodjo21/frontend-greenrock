package ac.id.poligon.greenrockadventure.detail.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//import ac.id.poligon.greenrockadventure.Manifest;
import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.beranda.activity.Home;
import ac.id.poligon.greenrockadventure.detail.activity.interfaces.I_penyewa;
import ac.id.poligon.greenrockadventure.detail.activity.interfaces.R_penyewa;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RincianList extends AppCompatActivity {

    private TextView barang,stok,lama,tgl,total,status;
    private AppCompatButton home,upload;
    private ImageView foto,btnStruk;
    private String id;


    private static final int FILE_REQUEST_CODE = 1002;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private File fileFoto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_list);

        home = findViewById(R.id.tohome);
        barang = findViewById(R.id.textBarang);
        stok = findViewById(R.id.textStok);
        lama = findViewById(R.id.textLama);
        tgl = findViewById(R.id.textTgl);
        total = findViewById(R.id.textTotal);
        status = findViewById(R.id.sts);
        upload = findViewById(R.id.btnupload);
        foto = findViewById(R.id.imgstruk);
        btnStruk = findViewById(R.id.idUpload);


        String selectedBarang = getIntent().getStringExtra("selected_barang");

        try {

            JSONObject jsonObject = new JSONObject(selectedBarang);
            Log.d("data",jsonObject.toString());


            String namaBarang = jsonObject.getString("nama_barang");
            String lamaSewa = jsonObject.getString("lama_sewa");
            int stok1 = jsonObject.getInt("stok");
            String tglSewa = jsonObject.getString("tgl_sewa");
            String totalbayar = jsonObject.getString("total_bayar");
            int status1 = jsonObject.getInt("status");
            id = jsonObject.getString("id_penyewa");


            barang.setText(namaBarang);
            stok.setText(String.valueOf(stok1));
            lama.setText(String.valueOf(lamaSewa));
            tgl.setText(tglSewa);
            total.setText(String.valueOf(totalbayar));
            status.setText(String.valueOf(status1));

            if (status1 == 0) {
                status.setText("Pesanan sedang diproses");
            } else if (status1 == 1){
                status.setText("Pesanan anda bisa dijemput");
            } else {
                status.setText("Pesanan anda Gagal");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        btnStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkCameraPermission()) {
                    // Izin kamera sudah diberikan, jalankan perintah kamera di sini
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                } else {
                    // Izin kamera belum diberikan, minta izin kamera
                    requestCameraPermission();
                }

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

            }


        });



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String api = API_SERVER.root_url;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(api) // Ganti BASE_URL dengan URL server Anda.
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                I_penyewa i_penyewa = retrofit.create(I_penyewa.class);



                Log.d("dataid",id);

//                File file = new File(String.valueOf(foto));
                Log.d("data", String.valueOf(fileFoto));
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileFoto);
                Log.d("foto", String.valueOf(requestFile));
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", fileFoto.getName(), requestFile);

                RequestBody imageName = RequestBody.create(MediaType.parse("text/plain"), ".jpg");

                Call<R_penyewa> call = i_penyewa.updateData(id, body, imageName);

                call.enqueue(new Callback<R_penyewa>() {
                    @Override
                    public void onResponse(Call<R_penyewa> call, Response<R_penyewa> response) {

                        if (response.isSuccessful()) {
                            R_penyewa r_penyewa = response.body();

                            if (r_penyewa != null && r_penyewa.getCode() == 200) {
                                startActivity(new Intent(getApplicationContext(), ListSewa.class));
                                Toast.makeText(getApplicationContext(), r_penyewa.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplicationContext(), r_penyewa.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "upload gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<R_penyewa> call, Throwable t) {

                        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListSewa.class));
            }
        });


    }
    private boolean checkCameraPermission() {
        int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Atur gambar sebagai sumber ImageView.

            foto.setImageBitmap(imageBitmap);
            saveImageToFile(imageBitmap);
        } else  {
            Toast.makeText(this, "Gagal menangkap gambar.", Toast.LENGTH_SHORT).show();

        }
    }


    private void saveImageToFile(Bitmap imageBitmap) {

        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, ".jpg");

            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            this.fileFoto = imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}