package com.rizkirian.rsantrian.ui.detail_antrian;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

public class DetailAntrianActivity extends AppCompatActivity {


    private TextView tvnoantrian, tvdilayani;
    private ImageButton ibBackButtonDetailAntrian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvnoantrian = findViewById(R.id.tvnomor);
        tvdilayani = findViewById(R.id.tvperkiraan);
        ibBackButtonDetailAntrian = findViewById(R.id.ibBackButtonDetailAntrian);

        ibBackButtonDetailAntrian.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        Intent intent = getIntent();
        tvnoantrian.setText(intent.getStringExtra("nomor_antrian"));
        tvdilayani.setText(intent.getStringExtra("di_layani"));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailAntrianActivity.this, DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        finishAffinity();
        super.onBackPressed();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(DetailAntrianActivity.this, DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        finishAffinity();
        return super.onKeyDown(keyCode, event);
    }
}
