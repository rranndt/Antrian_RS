package com.rizkirian.rsantrian.ui.pilih_pasien_lama;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.ui.pilih_pasien_lama.pilih_klinik_lama.PilihKlinikLamaActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pasien.Pasien;
import com.rizkirian.rsantrian.pasien.PasienAdapter;
import com.rizkirian.rsantrian.pasien.PasienJSON;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class PilihPasienLamaActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private List<Pasien> pList;
    private ListView lv;
    private TextView tvemail;
    private ImageButton ibBackButtonPasienLama;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_pasien_lama);

        tvemail = findViewById(R.id.tvemailuser);
        ibBackButtonPasienLama = findViewById(R.id.ibBackButtonPasienLama);
        ibBackButtonPasienLama.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        Intent intent = getIntent();
        tvemail.setText(intent.getStringExtra("id_user2"));
        lv = findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        requestData(Consts.SERVERAPP + "listkeluarga.php?id_user=" + tvemail.getText().toString());
        pdModel.pdData(PilihPasienLamaActivity.this);
    }

    public void requestData(String uri) {
        StringRequest request = new StringRequest(uri, response -> {
            pList = PasienJSON.parseData(response);
            PasienAdapter adapter = new PasienAdapter(PilihPasienLamaActivity.this, pList);
            lv.setAdapter(adapter);
            pdModel.hideProgressDialog();

        },
                error -> {
                    Toast.makeText(PilihPasienLamaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_SHORT).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, PilihKlinikLamaActivity.class);
        Pasien p = pList.get(i);
        intent.putExtra("id_p", p.getId_pasien());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
