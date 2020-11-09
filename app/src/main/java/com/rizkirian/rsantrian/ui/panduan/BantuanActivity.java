package com.rizkirian.rsantrian.ui.panduan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.databinding.ActivityBantuanBinding;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class BantuanActivity extends AppCompatActivity {

    private ActivityBantuanBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBantuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ibBackButtonPanduan.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
