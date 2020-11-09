package com.rizkirian.rsantrian.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.databinding.ActivityRegisterBinding;
import com.rizkirian.rsantrian.helper.ValidEmail;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initButtonListener();
    }

    public void setRegisterUser() {
        pdModel.pdData(RegisterActivity.this);
        String url = Consts.SERVERAPP + "registrasi_user.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                s -> {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                },
                volleyError -> {
                    Toast.makeText(RegisterActivity.this, getString(R.string.connection_on), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String email_user = binding.edtEmailRegister.getText().toString().trim();
                String ho_hp = binding.edtPhoneRegister.getText().toString().trim();
                String nama_user = binding.edtNameRegister.getText().toString();
                String password = binding.edtPasswordRegister.getText().toString();
                String alamat = binding.edtAddressRegister.getText().toString();
                String nip = binding.edtNikRegister.getText().toString();

                Map<String, String> params = new Hashtable<String, String>();
                params.put("nama_user", nama_user);
                params.put("nip", nip);
                params.put("alamat", alamat);
                params.put("no_telp", ho_hp);
                params.put("email_user", email_user);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getUser(String email_user) {
        pdModel.pdLogin(RegisterActivity.this);
        String url = Consts.SERVERAPP + "cek.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + email_user, new Response.Listener<String>() {

            @Override
            public void onResponse(String response2) {
                String nama = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (nama.equals("tersedia")) {
                    setRegisterUser();
                } else {
                    Toast.makeText(RegisterActivity.this, getString(R.string.email_duplicated), Toast.LENGTH_LONG).show();
                }
                pdModel.hideProgressDialog();
            }
        },
                error -> {
                    Toast.makeText(RegisterActivity.this, getString(R.string.connection_on), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue2.add(stringRequest2);
    }

    private void formRegisterValidation() {
        String inputName = binding.edtNameRegister.getText().toString().trim();
        String inputNik = binding.edtNikRegister.getText().toString().trim();
        String inputAddress = binding.edtAddressRegister.getText().toString().trim();
        String inputPhone = binding.edtPhoneRegister.getText().toString().trim();
        String inputEmail = binding.edtEmailRegister.getText().toString().trim();
        String inputPass = binding.edtPasswordRegister.getText().toString().trim();

        boolean isEmptyField = false;

        // Input Name
        if (TextUtils.isEmpty(inputName)) {
            isEmptyField = true;
            binding.tipNameRegister.setError(getString(R.string.toast_not_empty));
        } else {
            binding.tipNameRegister.setError(null);
        }

        // Input Nik
        if (TextUtils.isEmpty(inputNik)) {
            isEmptyField = true;
            binding.tipNikRegister.setError(getString(R.string.toast_not_empty));
        } else if (binding.edtNikRegister.length() < 16) {
            binding.tipNikRegister.setError(getString(R.string.digit_nik));
            return;
        } else {
            binding.tipNikRegister.setError(null);
        }

        // Input Address
        if (TextUtils.isEmpty(inputAddress)) {
            isEmptyField = true;
            binding.tipAddressRegister.setError(getString(R.string.toast_not_empty));
        } else {
            binding.tipAddressRegister.setError(null);
        }

        // Input Phone Number
        if (TextUtils.isEmpty(inputPhone)) {
            isEmptyField = true;
            binding.tipPhoneRegister.setError(getString(R.string.toast_not_empty));
        } else if (binding.edtPhoneRegister.length() < 11) {
            binding.tipPhoneRegister.setError(getString(R.string.digit_phone));
            return;
        } else {
            binding.tipPhoneRegister.setError(null);
        }

        // Input Email
        if (TextUtils.isEmpty(inputEmail)) {
            isEmptyField = true;
            binding.tipEmailRegister.setError(getString(R.string.toast_not_empty));
        } else if (!ValidEmail.isValidEmail(binding.edtEmailRegister.getText().toString().trim())) {
            binding.tipEmailRegister.setError(getString(R.string.email_not_valid));
            return;
        } else {
            binding.tipEmailRegister.setError(null);
        }

        // Input Password
        if (TextUtils.isEmpty(inputPass)) {
            isEmptyField = true;
            binding.tipPasswordRegister.setError(getString(R.string.toast_not_empty));
        } else if (binding.edtPasswordRegister.length() < 6) {
            binding.tipPasswordRegister.setError(getString(R.string.digit_password));
            return;
        } else {
            binding.tipPasswordRegister.setError(null);
        }

        if (!binding.edtPasswordRegister.getText().toString().equals(binding.edtPasswordConfirmation.getText().toString())) {
            binding.tipPasswordConfirmationRegister.setError(getString(R.string.password_same));
            return;
        } else {
            binding.tipPasswordConfirmationRegister.setError(null);
        }

        if (!isEmptyField) {
            getUser(binding.edtEmailRegister.getText().toString());
        }

    }

    private void initButtonListener() {
        binding.btnRegister.setOnClickListener(v -> formRegisterValidation());

        binding.tvRegisterAccount.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
