package ac.id.poligon.greenrockadventure.register.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ac.id.poligon.greenrockadventure.R;
import ac.id.poligon.greenrockadventure.login.activity.Login;
import ac.id.poligon.greenrockadventure.register.interfaces.ApiService;
import ac.id.poligon.greenrockadventure.register.responses.ApiResponse;
import ac.id.poligon.greenrockadventure.servis.API_SERVER;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
    private ImageView ktp;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private AppCompatButton openCameraButton;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private File imageFile;

    private EditText namaLengkap,Eemail,noHp,alamat,password,nik;

    String nama,email,Nohp,Alamat,Password,Nik;

    RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openCameraButton = findViewById(R.id.btnktp);
        ktp = findViewById(R.id.imgktp);

        AppCompatButton regis = findViewById(R.id.btnRegister);
        namaLengkap = findViewById(R.id.nama);
        Eemail = findViewById(R.id.email);
        noHp = findViewById(R.id.telp);
        alamat = findViewById(R.id.alamat);
        password = findViewById(R.id.pass);
        nik = findViewById(R.id.nik);

        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(getApplicationContext(), ALLOW_KEY)) {
                        showSettingsAlert();
                    } else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CAMERA)

                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(),
                                Manifest.permission.CAMERA)) {
                            showAlert();
                        } else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                } else {
                    openCamera();
                }
            }
        });

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
                        Register();
                }

            }
        });
    }

    @SuppressLint("NotConstructor")
    private void Register() {
        // Inisialisasi Retrofit
        String api = API_SERVER.root_url;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api) // Ganti dengan base URL Anda
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        // Membuat bagian request body untuk setiap parameter
        RequestBody namaBody = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody noHpBody = RequestBody.create(MediaType.parse("text/plain"), Nohp);
        RequestBody alamatBody = RequestBody.create(MediaType.parse("text/plain"), Alamat);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), Password);
        RequestBody nikBody = RequestBody.create(MediaType.parse("text/plain"), Nik);

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"),imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file",imageFile.getName(), imageBody);
        Call<ApiResponse> call = apiService.uploadImage(namaBody,emailBody,noHpBody,alamatBody,passwordBody,nikBody,imagePart);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200){
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                        Toast.makeText(getApplicationContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(), apiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"error bro",Toast.LENGTH_SHORT).show();

            }
        });
    }
        // Membuat bagian request body untuk gambar


    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(Register.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(Register.this);
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(Register.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ktp.setImageBitmap(imageBitmap);

            saveImageToFile(imageBitmap);
        }
    }

    private void saveImageToFile(Bitmap imageBitmap) {
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, ".jpg");

            FileOutputStream fos = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            this.imageFile = imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}