package com.rizkirian.rsantrian.ui.splash;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.onboard.OnboardActivity;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class SplahScreen extends AppCompatActivity {

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSplash();
    }

    private void setSplash() {
        new Handler().postDelayed(() -> {

            if (!isOnline()) {
                // If No Internet ACtive
                checkInternetStatus();
            } else {
                // When Internet Active
                startActivity(new Intent(SplahScreen.this, OnboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, 3000);
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void checkInternetStatus() {
            // Initalize dialog
            dialog = new Dialog(this);
            // Set content view
            dialog.setContentView(R.layout.alert_dialog);
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
            Button btnTryAgain = dialog.findViewById(R.id.btnTryAgain);
            btnTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call recreate method
                    recreate();
                }
            });
            // Show dialog
            dialog.show();
    }
}
