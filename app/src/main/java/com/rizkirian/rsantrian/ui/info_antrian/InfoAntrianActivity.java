package com.rizkirian.rsantrian.ui.info_antrian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.rizkirian.rsantrian.ui.detail_antrian.DetailAntrianActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.antrian.Antrian;
import com.rizkirian.rsantrian.antrian.AntrianJSON;
import com.rizkirian.rsantrian.antrian.AntrianAdapter;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class InfoAntrianActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private List<Antrian> pList;
    private ListView lv;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private ImageButton ibBackButtonInfoAntrian;
    private ShimmerFrameLayout shimmer;
    private View view, view0, view2, view3;
    private TextView tv1;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SharedPreferences settings;

    private TextView tviduser, tvnomor_saatini, tvNomorSaatIniA, tvNomorSaatIniB, tvNomorSaatIniD;


    private RecyclerView rv;
    private List<Antrian> list = new ArrayList<>();
    private AntrianAdapter recycler;
    private ConstraintLayout animContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_info);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        recycler = new AntrianAdapter(list, this);
        rv.setAdapter(recycler);
        rv.setNestedScrollingEnabled(false);
        rv.smoothScrollToPosition(0);
        recycler.notifyDataSetChanged();

        animContainer = findViewById(R.id.animContainer);
        tviduser = findViewById(R.id.tvIduser);
        tvnomor_saatini = findViewById(R.id.tvnomorsaatini);
        tvNomorSaatIniA = findViewById(R.id.tvnomorsaatiniA);
        tvNomorSaatIniB = findViewById(R.id.tvnomorsaatiniB);
        tvNomorSaatIniD = findViewById(R.id.tvnomorsaatiniD);

        ibBackButtonInfoAntrian = findViewById(R.id.ibBackButtonInfoAntrian);
//        lv = findViewById(R.id.myList);
        shimmer = findViewById(R.id.shimmer);
        view = findViewById(R.id.view);
        view0 = findViewById(R.id.view0);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        tv1 = findViewById(R.id.tv1);

        ibBackButtonInfoAntrian.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
//        lv.setOnItemClickListener(this);
//        getNomor();
//        getNomorA();
//        getNomorB();
//        getNomorD();

        final Handler handler2 = new Handler();
        Runnable refresh2 = new Runnable() {
            @Override
            public void run() {
                int second = 1;
                shimmer.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                view0.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);
                getNomor();
                getNomorA();
                getNomorB();
                getNomorD();
                handler2.postDelayed(this, second * 2000);
            }
        };
        handler2.postDelayed(refresh2, 2 * 1000);
    }

    public void requestData(String uri) {
        StringRequest request = new StringRequest(uri, response -> {
            try {
                list = AntrianJSON.parseData(response);
                recycler = new AntrianAdapter(list, InfoAntrianActivity.this);
                rv.setAdapter(recycler);
                animContainer.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },
                error -> Toast.makeText(InfoAntrianActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_SHORT).show());
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @SuppressLint("NewApi")
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailAntrianActivity.class);
        Antrian p = pList.get(i);
        intent.putExtra("no_antrian", p.getNomor_antrian());
        intent.putExtra("di_layani", p.getDi_layani());
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        tviduser.setText(UnameValue);
        requestData(Consts.SERVERAPP + "listantrian.php?id_user=" + tviduser.getText().toString());
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    // Nomor C
    public void getNomor() {
        String url = Consts.SERVERAPP + "getnomor.php?id_user=";
        StringRequest stringRequest2 = new StringRequest(url, response2 -> {
            String nama = "";
            try {
                JSONObject jsonObject = new JSONObject(response2);
                JSONArray result = jsonObject.getJSONArray("profile");
                JSONObject collegeData = result.getJSONObject(0);
                nama = collegeData.getString("nomor_antrian");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvnomor_saatini.setText(nama);
            Log.e(TAG, "getNomor: ");
//            Toast.makeText(InfoAntrianActivity.this, "nama : " + nama, Toast.LENGTH_SHORT).show();
        },
                error -> Toast.makeText(InfoAntrianActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue2 = Volley.newRequestQueue(InfoAntrianActivity.this);
        requestQueue2.add(stringRequest2);
    }

    // Nomor A
    public void getNomorA() {
        String url = Consts.SERVERAPP + "getnomorA.php?id_user=";
        StringRequest stringRequest2 = new StringRequest(url, response2 -> {
            String nama = "";
            try {
                JSONObject jsonObject = new JSONObject(response2);
                JSONArray result = jsonObject.getJSONArray("profile");
                JSONObject collegeData = result.getJSONObject(0);
                nama = collegeData.getString("nomor_antrian");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvNomorSaatIniA.setText(nama);
//            Toast.makeText(InfoAntrianActivity.this, "nama : " + nama, Toast.LENGTH_SHORT).show();
        },
                error -> Toast.makeText(InfoAntrianActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue2 = Volley.newRequestQueue(InfoAntrianActivity.this);
        requestQueue2.add(stringRequest2);
    }

    // Nomor B
    public void getNomorB() {
        String url = Consts.SERVERAPP + "getnomorB.php?id_user=";
        StringRequest stringRequest2 = new StringRequest(url, response2 -> {
            String nama = "";
            try {
                JSONObject jsonObject = new JSONObject(response2);
                JSONArray result = jsonObject.getJSONArray("profile");
                JSONObject collegeData = result.getJSONObject(0);
                nama = collegeData.getString("nomor_antrian");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvNomorSaatIniB.setText(nama);
//            Toast.makeText(InfoAntrianActivity.this, "nama : " + nama, Toast.LENGTH_SHORT).show();
        },
                error -> Toast.makeText(InfoAntrianActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue2 = Volley.newRequestQueue(InfoAntrianActivity.this);
        requestQueue2.add(stringRequest2);
    }

    // Nomor D
    public void getNomorD() {
        String url = Consts.SERVERAPP + "getnomorD.php?id_user=";
        StringRequest stringRequest2 = new StringRequest(url, response2 -> {
            String nama = "";
            try {
                JSONObject jsonObject = new JSONObject(response2);
                JSONArray result = jsonObject.getJSONArray("profile");
                JSONObject collegeData = result.getJSONObject(0);
                nama = collegeData.getString("nomor_antrian");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvNomorSaatIniD.setText(nama);
//            Toast.makeText(InfoAntrianActivity.this, "nama : " + nama, Toast.LENGTH_SHORT).show();
        },
                error -> Toast.makeText(InfoAntrianActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue2 = Volley.newRequestQueue(InfoAntrianActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InfoAntrianActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(InfoAntrianActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
        finishAffinity();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return super.onKeyDown(keyCode, event);
    }
}
