package com.rizkirian.rsantrian.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.databinding.ActivityLoginBinding;
import com.rizkirian.rsantrian.helper.ValidEmail;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    private String email_user, password_user;
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_EMAIL = "email_user";
    private static final String PREF_NO = "Password";
    private static final String PREF_PASS = "Password2";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;
    private String pass;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initCLickListener();
    }

    public void setUserLogin() {
        pdModel.pdLogin(LoginActivity.this);
        String url = Consts.SERVERAPP + "loginuser.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + binding.edtEmail.getText().toString().trim() + "&password=" + binding.edtPassword.getText().toString().trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String active = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("content_access");
                    JSONObject collegeData = result.getJSONObject(0);
                    active = collegeData.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (active.equals("active")) {
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("email_user", binding.edtEmail.getText().toString());
                    finish();
                    startActivity(intent);
                    pdModel.hideProgressDialog();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.connection_on), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
//                    loadingDialog.dismissDialog();
                }
            }
        },
                error -> {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(LoginActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        loadPreferences();
        super.onResume();
    }

    private void savePreferences() {
        preference = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();

        // Edit and commit
        UnameValue = binding.edtEmail.getText().toString();
        pass = binding.edtPassword.getText().toString();
        System.out.println("onPause Saving Name: " + UnameValue);
        editor.putString(PREF_EMAIL, UnameValue);
        editor.putString(PREF_PASS, pass);
        editor.apply();
    }

    private void loadPreferences() {
        preference = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get value
        UnameValue = preference.getString(PREF_EMAIL, DefaultUnameValue);
        pass = preference.getString(PREF_PASS, DefaultUnameValue);
        binding.edtEmail.setText(UnameValue);
        binding.edtPassword.setText(pass);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume Load password: " + PasswordValue);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panduan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_panduan) {
//            Intent intent = new Intent(LoginActivity.this, PanduanActivity.class);
//            startActivity(intent);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void initCLickListener() {
        binding.btnLogin.setOnClickListener(v -> formLoginValidation());

        binding.tvForgotPass.setOnClickListener(v -> Toast.makeText(LoginActivity.this, getString(R.string.in_development), Toast.LENGTH_SHORT).show());

        binding.ivLoginGoogle.setOnClickListener(v -> Toast.makeText(LoginActivity.this, getString(R.string.in_development), Toast.LENGTH_SHORT).show());

        binding.ivLoginFacebook.setOnClickListener(v -> Toast.makeText(LoginActivity.this, getString(R.string.in_development), Toast.LENGTH_SHORT).show());

        binding.tvRegisterNow.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void formLoginValidation() {
        String inputEmail = binding.edtEmail.getText().toString().trim();
        String inputPassword = binding.edtPassword.getText().toString().trim();

        boolean isEmptyField = false;

        // Input Email
        if (TextUtils.isEmpty(inputEmail)) {
            isEmptyField = true;
            binding.tipEmail.setError(getString(R.string.toast_not_empty));
        } else if (!ValidEmail.isValidEmail(binding.edtEmail.getText().toString().trim())) {
            binding.tipEmail.setError(getString(R.string.email_not_valid));
            return;
        } else {
            binding.tipEmail.setError(null);
        }

        // Input Password
        if (TextUtils.isEmpty(inputPassword)) {
            isEmptyField = true;
            binding.tipPassword.setError(getString(R.string.toast_not_empty));
        } else {
            binding.tipPassword.setError(null);
        }

        if (!isEmptyField) {
            setUserLogin();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
