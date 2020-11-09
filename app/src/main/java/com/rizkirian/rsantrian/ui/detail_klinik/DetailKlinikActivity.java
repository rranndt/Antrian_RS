package com.rizkirian.rsantrian.ui.detail_klinik;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.list_klinik.ListKlinikActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class DetailKlinikActivity extends AppCompatActivity {

    private TextView tvsenin, tvselasa, tvrabu, tvkamis, tvjumat, tvsaptu, tvminggu;
    private TextView tvdokter, tvidklinik, tvnamaklinik;
    private ImageButton ibBackButtonDetailKlinik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailklinik);

        tvidklinik = findViewById(R.id.tvidklinik);
        tvnamaklinik = findViewById(R.id.tvnamaklinik);
        tvsenin = findViewById(R.id.tvsenin);
        tvselasa = findViewById(R.id.tvselasa);
        tvrabu = findViewById(R.id.tvrabu);
        tvkamis = findViewById(R.id.tvkamis);
        tvjumat = findViewById(R.id.tvjumat);
        tvsaptu = findViewById(R.id.tvsaptu);
        tvminggu = findViewById(R.id.tvminggu);
        tvdokter = findViewById(R.id.tvdokter);
        ibBackButtonDetailKlinik = findViewById(R.id.ibBackButtonDetailKlinik);
        ibBackButtonDetailKlinik.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ListKlinikActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        Intent intent = getIntent();
        tvidklinik.setText(intent.getStringExtra("id_klinik2"));
        getHari();
    }

    public void getHari() {
        pdModel.pdLogin(DetailKlinikActivity.this);
        String url = Consts.SERVERAPP + "getHari.php?id_klinik=";
        StringRequest stringRequest2 = new StringRequest(url + tvidklinik.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String senin = "";
                String selasa = "";
                String jumat = "";
                String rabu = "";
                String kamis = "";
                String saptu = "";
                String minggu = "";

                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_klinik");
                    senin = collegeData.getString("senin");
                    selasa = collegeData.getString("selasa");
                    rabu = collegeData.getString("rabu");
                    kamis = collegeData.getString("kamis");
                    jumat = collegeData.getString("jumat");
                    saptu = collegeData.getString("saptu");
                    minggu = collegeData.getString("minggu");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tvnamaklinik.setText(nama);
                tvsenin.setText(senin);
                tvselasa.setText(selasa);
                tvrabu.setText(rabu);
                tvkamis.setText(kamis);
                tvjumat.setText(jumat);
                tvsaptu.setText(saptu);
                tvminggu.setText(minggu);
                pdModel.hideProgressDialog();
                getDokter();
            }
        },
                error -> {
                    Toast.makeText(DetailKlinikActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(DetailKlinikActivity.this);
        requestQueue2.add(stringRequest2);
    }

    public void getDokter() {
        pdModel.pdLogin(DetailKlinikActivity.this);
        String url = Consts.SERVERAPP + "getdokter.php?id_klinik=";
        StringRequest stringRequest2 = new StringRequest(url + tvidklinik.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvdokter.append(nama);
                pdModel.hideProgressDialog();
            }
        },
                error -> {
                    Toast.makeText(DetailKlinikActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(DetailKlinikActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
