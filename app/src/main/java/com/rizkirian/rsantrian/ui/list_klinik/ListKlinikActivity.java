package com.rizkirian.rsantrian.ui.list_klinik;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.ui.detail_klinik.DetailKlinikActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.klinik.Klinik;
import com.rizkirian.rsantrian.klinik.KlinikJSON;

import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class ListKlinikActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private List<Klinik> pList;
    private ListView lv;
    private ImageButton ibBackButtonJadwalKlinik;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_klinik);

        lv = findViewById(R.id.myListKlinik);
        ibBackButtonJadwalKlinik = findViewById(R.id.ibBackButtonJadwalKlinik);
        ibBackButtonJadwalKlinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        lv.setOnItemClickListener(this);

        requestData(Consts.SERVERAPP + "listklinik.php");
        pdModel.pdData(ListKlinikActivity.this);
    }

    public void requestData(String uri) {
        StringRequest request = new StringRequest(uri, response -> {
            pList = KlinikJSON.parseData(response);
            ListKlinikAdapter adapter = new ListKlinikAdapter(ListKlinikActivity.this, pList);
            lv.setAdapter(adapter);
            pdModel.hideProgressDialog();
        },
                error -> {
                    Toast.makeText(ListKlinikActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_SHORT).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailKlinikActivity.class);
        Klinik p = pList.get(i);
        intent.putExtra("id_klinik2", p.getId_klinik());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
