package com.rizkirian.rsantrian.onboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.rizkirian.rsantrian.ui.login.LoginActivity;
import com.rizkirian.rsantrian.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class OnboardActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private OnboardViewPagerAdapter onboardViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btnNext;
    private Button btnGetStarted;
    private Animation btnAnim;
    private TextView tvSkip;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // When activity is about to be launch we need to check if its opened or not
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }

        setContentView(R.layout.activity_onboard);

        // init views
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Tidak Perlu Repot","Dengan aplikasi ini calon pasien dapat dengan mudah mengambil antrian dari rumah tanpa perlu repot datang ke Rumah Sakit",R.drawable.onboard1));
        mList.add(new ScreenItem("Tidak Perlu Menunggu","Calon pasien dapat menunggu giliran dari rumah masing-masing tanpa perlu lelah dan repot menunggu di Rumah Sakit",R.drawable.onboard2));
        mList.add(new ScreenItem("Akses Dimanapun","Dapat diakses dimanapun dan kapanpun, tidak perlu harus mengambil antrian langsung dari Rumah Sakit",R.drawable.onboard3));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        onboardViewPagerAdapter = new OnboardViewPagerAdapter(this,mList);
        screenPager.setAdapter(onboardViewPagerAdapter);

        // setup tab lyout with view pager
        tabIndicator.setupWithViewPager(screenPager);

        // next button click listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // when we reach to the last screen
                    loadLastScreen();
                }
            }
        });

        // tablayout and change listener
        tabIndicator.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() -1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // get started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open main activity
                Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mainActivity);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                savePrefsData();
                finish();

            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        return pref.getBoolean("isIntroOpen",false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpen",true);
        editor.apply();
    }

    // show the getstarted button and hide the indicator and the next button
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }
}