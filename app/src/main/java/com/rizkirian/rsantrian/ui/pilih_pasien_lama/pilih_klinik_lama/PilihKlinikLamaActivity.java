package com.rizkirian.rsantrian.ui.pilih_pasien_lama.pilih_klinik_lama;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.klinik.Klinik;
import com.rizkirian.rsantrian.klinik.KlinikAdapter;
import com.rizkirian.rsantrian.klinik.KlinikJSON;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class PilihKlinikLamaActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private List<Klinik> pList;
    private ListView lv;
    private TextView tviduser, tvdilayani, tvidpasien, tvnomor, tvidklinik, tvnomortelp;
    private ImageButton ibBackButtonPilihKlinik;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_NO = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private String id_klinik;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_klinik_lama);

        tvdilayani = findViewById(R.id.tvdilayani);
        tviduser = findViewById(R.id.tvIduser);
        tvnomortelp = findViewById(R.id.tvnomortelp);
        tvidklinik = findViewById(R.id.tvidklinik);
        Intent intent = getIntent();
        tvnomor = findViewById(R.id.tvnomor);
        tvidpasien = findViewById(R.id.tvidpasien);
        ibBackButtonPilihKlinik = findViewById(R.id.ibBackButtonPilihKlinik);
        ibBackButtonPilihKlinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        tvidpasien.setText(intent.getStringExtra("id_p"));

        lv = findViewById(R.id.myList);
        lv.setOnItemClickListener(this);
        requestData(Consts.SERVERAPP + "listklinik.php");
        new AlertDialog.Builder(PilihKlinikLamaActivity.this)
                .setMessage("Pilih Klinik Sesuai Yang Di Butuhkan")
                .setTitle("Pesan")
                .setCancelable(false)
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
//                        lanjutan();
                        dialog.cancel();
                    }
                })
                .show();
    }

//    public void lanjutan() {
//        new AlertDialog.Builder(PilihKlinikLamaActivity.this)
//                .setMessage("Jika Klinik Tutup Antrian Tidak Dapat Di Proses")
//                .setTitle("Jangan Pilih Jika Klinik Tutup")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @SuppressLint("NewApi")
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                })
//                .show();
//    }

    public void requestData(String uri) {
        StringRequest request = new StringRequest(uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pList = KlinikJSON.parseData(response);
                KlinikAdapter adapter = new KlinikAdapter(PilihKlinikLamaActivity.this, pList);
                lv.setAdapter(adapter);
                pdModel.hideProgressDialog();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PilihKlinikLamaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_SHORT).show();
                        pdModel.hideProgressDialog();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Klinik p = pList.get(i);
        tvidklinik.setText(p.getId_klinik());
        id_klinik = p.getId_klinik();

        //startActivity(intent);
        if (tvidklinik.getText().equals("")) {
            Toast.makeText(PilihKlinikLamaActivity.this, "Id Belum di Pilih", Toast.LENGTH_LONG).show();
        } else {
            pdModel.pdLogin(PilihKlinikLamaActivity.this);
            String url = Consts.SERVERAPP + "getHari.php?id_klinik=";
            StringRequest stringRequest2 = new StringRequest(url + p.getId_klinik(), new Response.Listener<String>() {
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
                    new AlertDialog.Builder(PilihKlinikLamaActivity.this)
//                            .setMessage("Senin  : " + senin + "\n"
//                                    + "Selasa : " + selasa + "\n"
//                                    + "Rabu   : " + rabu + "\n"
//                                    + "Kamis  : " + kamis + "\n"
//                                    + "Jumat  : " + jumat + "\n"
//                                    + "Saptu  : " + saptu + "\n"
//                                    + "Minggu : " + minggu + "\n")
                            .setMessage("Pastikan anda sudah mengetahui jadwal praktek, anda dapat cek pada menu Jadwal Klinik")
                            .setTitle(nama)
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                public void onClick(DialogInterface dialog, int id) {
                                    inputantrian();
                                }
                            })

                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @SuppressLint("NewApi")
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PilihKlinikLamaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                            pdModel.hideProgressDialog();
                        }
                    });

            RequestQueue requestQueue2 = Volley.newRequestQueue(PilihKlinikLamaActivity.this);
            requestQueue2.add(stringRequest2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        no_telp = settings.getString(PREF_NO, DefaultUnameValue);
        tviduser.setText(UnameValue);
        tvnomortelp.setText(no_telp);
        getNomor();
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    public void getNomor() {
        String url = Consts.SERVERAPP + "getNomorantrian.php?id_klinik=" + id_klinik;
        StringRequest stringRequest2 = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nomor_antrian");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnomor.setText(nama);
                getDilayani();
                pdModel.hideProgressDialog();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PilihKlinikLamaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue2 = Volley.newRequestQueue(PilihKlinikLamaActivity.this);
        requestQueue2.add(stringRequest2);
    }

    public void inputantrian() {
        String url = Consts.SERVERAPP + "inputantrian.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(PilihKlinikLamaActivity.this, getString(R.string.register_patient_success), Toast.LENGTH_LONG).show();
                        updateNomor();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PilihKlinikLamaActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id_pasien = tvidpasien.getText().toString().trim();
                String nomor_antrian = tvnomor.getText().toString().trim();
                String id_klinik = tvidklinik.getText().toString();
                String no_telp = tvnomortelp.getText().toString();
                String di_layani = tvdilayani.getText().toString();
                Map<String, String> params = new Hashtable<String, String>();

                params.put("id_pasien", id_pasien);
                params.put("nomor_antrian", nomor_antrian);
                params.put("id_klinik", id_klinik);
                params.put("di_layani", di_layani);
                params.put("no_telp", no_telp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PilihKlinikLamaActivity.this);
        requestQueue.add(stringRequest);
    }

    public void updateNomor() {
        String url = Consts.SERVERAPP + "updatenomor.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
//                        Toast.makeText(PilihKlinikLamaActivity.this, "Succes ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PilihKlinikLamaActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PilihKlinikLamaActivity.this, "Kode Salah", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String nomor_antrian = tvnomor.getText().toString().trim();
                Map<String, String> params = new Hashtable<String, String>();
                params.put("nomor_antrian", nomor_antrian);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PilihKlinikLamaActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getDilayani() {
        String url = Consts.SERVERAPP + "getDilayani.php?nomor_antrian=";
        StringRequest stringRequest2 = new StringRequest(url + tvnomor.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("di_layani");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvdilayani.setText(nama);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PilihKlinikLamaActivity.this, "Not Connections", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(PilihKlinikLamaActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
