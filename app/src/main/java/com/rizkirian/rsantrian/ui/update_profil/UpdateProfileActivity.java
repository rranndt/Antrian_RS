package com.rizkirian.rsantrian.ui.update_profil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.helper.ValidEmail;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNama, edtNik, edtAlamat, edtPhone, edtEmail, edtPassword, edtPasswordConfirm;
    private TextInputLayout tipName, tipNik, tipAlamat, tipPhone, tipEmail, tipPassword, tipPasswordConfirm;
    private Button btnUpdate;
    private String id_user = "";
    private ImageButton btnUpdatePhoto, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        edtNama = findViewById(R.id.edtNameUpdate);
        edtNik = findViewById(R.id.edtNikUpdate);
        edtAlamat = findViewById(R.id.edtAddressUpdate);
        edtPhone = findViewById(R.id.edtPhoneUpdate);
        edtEmail = findViewById(R.id.edtEmailUpdate);
        edtPassword = findViewById(R.id.edtPasswordUpdate);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirmationUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.ibBackButtonUpdate);
        btnUpdatePhoto = findViewById(R.id.ibButtonAddPhoto);

        tipName = findViewById(R.id.tipNameUpdate);
        tipNik = findViewById(R.id.tipNikUpdate);
        tipAlamat = findViewById(R.id.tipAddressUpdate);
        tipPhone = findViewById(R.id.tipPhoneUpdate);
        tipEmail = findViewById(R.id.tipEmailUpdate);
        tipPassword = findViewById(R.id.tipPasswordUpdate);
        tipPasswordConfirm = findViewById(R.id.tipPasswordConfirmationUpdate);

        btnUpdate.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnUpdatePhoto.setOnClickListener(this);

        // Get id user
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_akun");
//        Toast.makeText(UpdateProfileActivity.this, id_user, Toast.LENGTH_LONG).show();
        getAkun();
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateuser();
//            }
//        });

    }

    public void updateuser() {
        pdModel.pdData(UpdateProfileActivity.this);
        String url = Consts.SERVERAPP + "updateuser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                s -> {
                    Toast.makeText(UpdateProfileActivity.this, getString(R.string.update_profil), Toast.LENGTH_LONG).show();
                    finish();
                    pdModel.hideProgressDialog();
                },
                volleyError -> {
                    Toast.makeText(UpdateProfileActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String email_user = edtEmail.getText().toString().trim();
                String ho_hp = edtPhone.getText().toString().trim();
                String nama_user = edtNama.getText().toString();
                String password = edtPassword.getText().toString();
                String alamat = edtAlamat.getText().toString();
                String nip = edtNik.getText().toString();

                Map<String, String> params = new Hashtable<String, String>();
                params.put("nama_user", nama_user);
                params.put("id_user", id_user);
                params.put("nip", nip);
                params.put("alamat", alamat);
                params.put("no_telp", ho_hp);
                params.put("email_user", email_user);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateProfileActivity.this);
        requestQueue.add(stringRequest);
    }

    public void getAkun() {
        pdModel.pdLogin(UpdateProfileActivity.this);
        String url = Consts.SERVERAPP + "getAkun.php";
        StringRequest stringRequest2 = new StringRequest(url + "?id_user=" + id_user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String no_telp = "";
                String nip = "";
                String alamat = "";
                String email_user = "";
                String password = "";

                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                    nip = collegeData.getString("nip");
                    alamat = collegeData.getString("alamat");
                    no_telp = collegeData.getString("no_telp");
                    email_user = collegeData.getString("email_user");
                    password = collegeData.getString("password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                edtAlamat.setText(alamat);
                edtEmail.setText(email_user);
                edtNama.setText(nama);
                edtPassword.setText(password);
                edtNik.setText(nip);
                edtPhone.setText(no_telp);
                pdModel.hideProgressDialog();
            }
        },
                error -> {
                    Toast.makeText(UpdateProfileActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(UpdateProfileActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                formRegisterValidation();
                break;
            case R.id.ibBackButtonUpdate:
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.ibButtonAddPhoto:
                Toast.makeText(this, getString(R.string.in_development), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void formRegisterValidation() {
        String inputName = edtNama.getText().toString().trim();
        String inputNik = edtNik.getText().toString().trim();
        String inputAddress = edtAlamat.getText().toString().trim();
        String inputPhone = edtPhone.getText().toString().trim();
        String inputEmail = edtEmail.getText().toString().trim();
        String inputPass = edtPassword.getText().toString().trim();

        boolean isEmptyField = false;

        // Input Name
        if (TextUtils.isEmpty(inputName)) {
            isEmptyField = true;
            tipName.setError(getString(R.string.toast_not_empty));
        } else {
            tipName.setError(null);
        }

        // Input Nik
        if (TextUtils.isEmpty(inputNik)) {
            isEmptyField = true;
            tipNik.setError(getString(R.string.toast_not_empty));
        } else if (edtNik.length() < 16) {
            tipNik.setError(getString(R.string.digit_nik));
            return;
        } else {
            tipNik.setError(null);
        }

        // Input Address
        if (TextUtils.isEmpty(inputAddress)) {
            isEmptyField = true;
            tipAlamat.setError(getString(R.string.toast_not_empty));
        } else {
            tipAlamat.setError(null);
        }

        // Input Phone Number
        if (TextUtils.isEmpty(inputPhone)) {
            isEmptyField = true;
            tipPhone.setError(getString(R.string.toast_not_empty));
        } else if (edtPhone.length() < 11) {
            tipPhone.setError(getString(R.string.digit_phone));
            return;
        } else {
            tipPhone.setError(null);
        }

        // Input Email
        if (TextUtils.isEmpty(inputEmail)) {
            isEmptyField = true;
            tipEmail.setError(getString(R.string.toast_not_empty));
        } else if (!ValidEmail.isValidEmail(edtEmail.getText().toString().trim())) {
            tipEmail.setError(getString(R.string.email_not_valid));
            return;
        } else {
            tipEmail.setError(null);
        }

        // Input Password
        if (TextUtils.isEmpty(inputPass)) {
            isEmptyField = true;
            tipPassword.setError(getString(R.string.toast_not_empty));
        } else if (edtPassword.length() < 6) {
            tipPassword.setError(getString(R.string.digit_password));
            return;
        } else {
            tipPassword.setError(null);
        }

        if (!edtPassword.getText().toString().equals(edtPasswordConfirm.getText().toString())) {
            tipPasswordConfirm.setError(getString(R.string.password_same));
            return;
        } else {
            tipPasswordConfirm.setError(null);
        }

        if (!isEmptyField) {
            updateuser();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
