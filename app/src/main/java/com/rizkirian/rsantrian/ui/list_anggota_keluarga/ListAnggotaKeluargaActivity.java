package com.rizkirian.rsantrian.ui.list_anggota_keluarga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pasien.Pasien;
import com.rizkirian.rsantrian.pasien.PasienAdapter;
import com.rizkirian.rsantrian.pasien.PasienJSON;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import java.util.List;


public class ListAnggotaKeluargaActivity extends AppCompatActivity {

    private List<Pasien> pList;
    private ListView lv;
    private TextView tvemail;

    private ImageButton ibBackButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        tvemail = findViewById(R.id.tvemailuser);
        lv = findViewById(R.id.myList);
        ibBackButton = findViewById(R.id.ibBackButton);
        ibBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Intent intent = getIntent();
        tvemail.setText(intent.getStringExtra("id_user2"));
        getDataKeluarga(Consts.SERVERAPP + "listkeluarga.php?id_user=" + tvemail.getText().toString());
        pdModel.pdData(ListAnggotaKeluargaActivity.this);

    }

    public void getDataKeluarga(String uri) {
        StringRequest request = new StringRequest(uri,
                response -> {
                    pList = PasienJSON.parseData(response);
                    PasienAdapter adapter = new PasienAdapter(ListAnggotaKeluargaActivity.this, pList);
                    lv.setAdapter(adapter);
                    pdModel.hideProgressDialog();
                },
                error -> {
                    Toast.makeText(ListAnggotaKeluargaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_SHORT).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
