package com.rizkirian.rsantrian.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.rizkirian.rsantrian.ui.update_profil.UpdateProfileActivity;
import com.rizkirian.rsantrian.ui.panduan.BantuanActivity;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.ui.info_antrian.InfoAntrianActivity;
import com.rizkirian.rsantrian.ui.pilih_pasien_baru.InputPasienBaruActivity;
import com.rizkirian.rsantrian.ui.list_anggota_keluarga.ListAnggotaKeluargaActivity;
import com.rizkirian.rsantrian.ui.list_klinik.ListKlinikActivity;
import com.rizkirian.rsantrian.ui.pilih_pasien_lama.PilihPasienLamaActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.slider.BannerSlider;
import com.rizkirian.rsantrian.slider.FragmentSlider;
import com.rizkirian.rsantrian.slider.SliderIndicator;
import com.rizkirian.rsantrian.slider.SliderPagerAdapter;
import com.rizkirian.rsantrian.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tviduser, tvnamauser, tvemailuser, tvnomortelp;
    CardView cvAnggotaKeluarga, cvPendaftaranOnline, cvJadwalKlinik, cvInfoAntrian;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_NO = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;
    private String email_user;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private BannerSlider bannerSlider;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }

        cvAnggotaKeluarga = findViewById(R.id.view_anggotakeluarga);
        cvPendaftaranOnline = findViewById(R.id.view_pendaftaran);
        cvJadwalKlinik = findViewById(R.id.view_klinik);
        cvInfoAntrian = findViewById(R.id.view_antrian);
        bannerSlider = findViewById(R.id.sliderView);
        mLinearLayout = findViewById(R.id.pagesContainer);
        setupSlider();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set Navigation Header
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        tviduser = hView.findViewById(R.id.tvIduser);
        tvnomortelp = hView.findViewById(R.id.tvnomortelp);
        tvnamauser = hView.findViewById(R.id.tvNamauser);
        tvemailuser = hView.findViewById(R.id.tvemailuser);

//        Intent intent = getIntent();
//        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        // tvemailuser.setText(intent.getStringExtra("email_user"));

        // Button Pasien Terdaftar
        cvAnggotaKeluarga.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, ListAnggotaKeluargaActivity.class);
            intent1.putExtra("id_user2", tviduser.getText().toString());
            startActivity(intent1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        // Button Pendaftaran Online
        cvPendaftaranOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DashboardActivity.this)
                        .setTitle("Pendaftaran Pasien")
                        .setMessage("Pilih Pasien Baru jika anda belum pernah mendaftar... Pilih Pasien Lama jika anda sudah pernah mendaftar")
                        .setCancelable(false)
                        .setPositiveButton("Pasien Lama", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent1 = new Intent(DashboardActivity.this, PilihPasienLamaActivity.class);
                                intent1.putExtra("id_user2", tviduser.getText().toString());
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        })
                        .setNegativeButton("Pasien Baru", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(DashboardActivity.this, InputPasienBaruActivity.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        })
                        .show();
            }
        });

        // Button Jadwal Klinik
        cvJadwalKlinik.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, ListKlinikActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        });

        // Button Info Antrian
        cvInfoAntrian.setOnClickListener(view -> {
            Intent intent1 = new Intent(DashboardActivity.this, InfoAntrianActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void setupSlider() {
        bannerSlider.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();

        //link image
        fragments.add(FragmentSlider.newInstance("https://user-images.githubusercontent.com/65359346/98191741-6eb0fb00-1f4c-11eb-929c-258c0672e933.png"));
        fragments.add(FragmentSlider.newInstance("https://user-images.githubusercontent.com/65359346/98191749-71135500-1f4c-11eb-81a5-35b921c984e5.png"));
        fragments.add(FragmentSlider.newInstance("https://user-images.githubusercontent.com/65359346/98191745-6fe22800-1f4c-11eb-8cd4-7b863425b053.png"));
        fragments.add(FragmentSlider.newInstance("https://user-images.githubusercontent.com/65359346/98191748-707abe80-1f4c-11eb-96c3-5835d8d8fd07.png"));

        mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
        bannerSlider.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(this, mLinearLayout, bannerSlider, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(DashboardActivity.this, getString(R.string.in_development), Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent intent = new Intent(DashboardActivity.this, UpdateProfileActivity.class);
            intent.putExtra("id_akun", tviduser.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_bantuan) {
            Intent intent = new Intent(DashboardActivity.this, BantuanActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.nav_akun) {
            Intent intent = new Intent(DashboardActivity.this, UpdateProfileActivity.class);
            intent.putExtra("id_akun", tviduser.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.nav_logout) {
            logoutAlertDialog();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutAlertDialog() {
        // Initalize dialog
        Dialog dialog = new Dialog(this);
        // Set content view
        dialog.setContentView(R.layout.logout_dialog);
        // Set outside touch
        dialog.setCanceledOnTouchOutside(false);
        // Set dialog width and height
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // Set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Set animation
        dialog.getWindow().getAttributes().windowAnimations =
                android.R.style.Animation_Dialog;
        // Initiative dialog variable
        Button btnOk = dialog.findViewById(R.id.buttonOk);
        Button btnCancel = dialog.findViewById(R.id.buttonCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Show dialog
        dialog.show();
    }

    public void getProfile() {
        pdModel.pdLogin(DashboardActivity.this);
        String url = Consts.SERVERAPP + "getprofile.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + tvemailuser.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String no_telp = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                    id_user = collegeData.getString("id_user");
                    no_telp = collegeData.getString("no_telp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnamauser.setText(nama);
                tviduser.setText(id_user);
                tvnomortelp.setText(no_telp);
                pdModel.hideProgressDialog();
            }
        },
                error -> {
                    Toast.makeText(DashboardActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(DashboardActivity.this);
        requestQueue2.add(stringRequest2);
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = tviduser.getText().toString();
        no_telp = tvnomortelp.getText().toString();

        System.out.println("onPause save name: " + UnameValue);

        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_NO, no_telp);
        editor.apply();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        email_user = settings.getString("email_user", DefaultUnameValue);
        no_telp = settings.getString(PREF_NO, DefaultUnameValue);

        tviduser.setText(UnameValue);
        tvnomortelp.setText(no_telp);
        tvemailuser.setText(email_user);
//        Toast.makeText(DashboardActivity.this, tvemailuser.getText().toString(), Toast.LENGTH_LONG).show();
        getProfile();

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
}
