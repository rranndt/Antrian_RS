package com.rizkirian.rsantrian;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import java.util.Hashtable;
import java.util.Map;

public class VerifikasiPasien extends AppCompatActivity {


    TextView tvidpasien;
    EditText edtkode;
    Button btnverifikasi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi);
        tvidpasien = (TextView) findViewById(R.id.tvidpasien);
        edtkode = (EditText) findViewById(R.id.input_kode);
        btnverifikasi = (Button) findViewById(R.id.btn_verifikasi);

        Intent intent = getIntent();
        tvidpasien.setText(intent.getStringExtra("id_pasien"));
        btnverifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

    }

    public void updateProfile() {
        String url = Consts.SERVERAPP + "verifikasi.php";
        pdModel.pdData(VerifikasiPasien.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(VerifikasiPasien.this, "Succes verifikasi pasien", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(VerifikasiPasien.this, DashboardActivity.class);

                        startActivity(intent);
                        pdModel.hideProgressDialog();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(VerifikasiPasien.this, "Kode Salah", Toast.LENGTH_LONG).show();


                        pdModel.hideProgressDialog();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id_pasien = tvidpasien.getText().toString().trim();
                String kode = edtkode.getText().toString().trim();


                Map<String, String> params = new Hashtable<String, String>();

                params.put("id_pasien", id_pasien);
                params.put("kode", kode);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VerifikasiPasien.this);

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(VerifikasiPasien.this)
                .setMessage("Tetap Keluar ?")
                .setTitle("Jika Anda tidak memverifikasi Pasien maka pendaftaran tidak akan di proses")
                .setCancelable(false)
                .setPositiveButton("Lanjutkan Verifikasi", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //lm.clearTestProviderLocation(provider);
                    }
                })

                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })

                .show();
        super.onBackPressed();
    }
}
