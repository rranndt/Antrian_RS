package com.rizkirian.rsantrian;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pasien.Pasien;
import com.rizkirian.rsantrian.pasien.PasienAdapter;
import com.rizkirian.rsantrian.pasien.PasienJSON;
import com.rizkirian.rsantrian.pdModel.pdModel;

import java.util.List;

public class PilihVerifikasi extends AppCompatActivity implements ListView.OnItemClickListener {
    List<Pasien> pList;
    ListView lv;
    TextView tvemail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listip);

        tvemail=(TextView)findViewById(R.id.tvemailuser);

        Intent intent=getIntent();
        tvemail.setText(intent.getStringExtra("email_user"));
        lv = (ListView) findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        requestData( Consts.SERVERAPP+"listkeluarga.php?id_user="+tvemail.getText().toString());
        pdModel.pdData(PilihVerifikasi.this);

    }
    public void requestData(String uri) {

        StringRequest request = new StringRequest(uri,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pList = PasienJSON.parseData(response);
                        PasienAdapter adapter = new PasienAdapter(PilihVerifikasi.this, pList);
                        lv.setAdapter(adapter);
                        pdModel.hideProgressDialog();

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //    Toast.makeText(PengumumanActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        pdModel.hideProgressDialog();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         Intent intent = new Intent(this, VerifikasiPasien.class);
        Pasien p = pList.get(i);
        intent.putExtra("id_pasien", p.getId_pasien());
        //intent.putExtra(Config.TAG_KELAS, p.getDetail());
        //intent.putExtra(Config.TAG_SUB_KELAS, p.getTgl());
        //intent.putExtra(Config.TAG_TGL,p.getImage());

        startActivity(intent);


    }
}
