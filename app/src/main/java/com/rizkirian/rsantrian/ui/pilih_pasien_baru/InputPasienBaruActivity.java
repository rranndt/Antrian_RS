package com.rizkirian.rsantrian.ui.pilih_pasien_baru;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.rizkirian.rsantrian.ui.pilih_pasien_baru.pilih_klinik_baru.PilihKlinikBaruActivity;
import com.rizkirian.rsantrian.R;
import com.rizkirian.rsantrian.network.Consts;
import com.rizkirian.rsantrian.pdModel.pdModel;
import com.rizkirian.rsantrian.ui.dashboard.DashboardActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */

public class InputPasienBaruActivity extends AppCompatActivity {

    private EditText edtnama, edtnip, edtalamat, edtnotelp, edttgllahir;
    private Spinner spJK;
    private TextView tviduser;
    private Button btnDaftar, btntgllahir;
    private ImageButton ibBackButtonDaftarPasienBaru;
    private TextInputLayout tipNameDaftarPasien, tipNikDaftarPasien, tiptipInputTglLahir, tipAddressDaftarPasien, tipPhoneDaftarPasien;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    int hour, minute, mYear, mMonth, mDay;
    int hour2, minute2, mYear2, mMonth2, mDay2;
    private String[] arrMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pasien_baru);

        edtnama = findViewById(R.id.edtNameRegister);
        edtnip = findViewById(R.id.edtNikRegister);
        edtalamat = findViewById(R.id.edtAddressRegister);
        edtnotelp = findViewById(R.id.edtPhoneRegister);
        edttgllahir = findViewById(R.id.input_tgl_lahir);
        spJK = findViewById(R.id.sp_jeniskelamin);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btnDaftar = findViewById(R.id.btnRegister);
        ibBackButtonDaftarPasienBaru = findViewById(R.id.ibBackButtonDaftarPasienBaru);
        ibBackButtonDaftarPasienBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // init Text Input Layout
        tipNameDaftarPasien = findViewById(R.id.tipNameDaftarPasien);
        tipNikDaftarPasien = findViewById(R.id.tipNikDaftarPasien);
        tiptipInputTglLahir = findViewById(R.id.tiptipInputTglLahir);
        tipAddressDaftarPasien = findViewById(R.id.tipAddressDaftarPasien);
        tipPhoneDaftarPasien = findViewById(R.id.tipPhoneDaftarPasien);

        btntgllahir = (Button) findViewById(R.id.btntgl_lahir);
        btntgllahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        tviduser = findViewById(R.id.tvIduser);
        spjenis();

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                inputPasien();
                formRegisterValidation();
            }
        });
    }

    private void formRegisterValidation() {
        String inputName = edtnama.getText().toString().trim();
        String inputNik = edtnip.getText().toString().trim();
        String inputTglLahir = edttgllahir.getText().toString().trim();
        String inputAlamat = edtalamat.getText().toString().trim();
        String inputPhone = edtnotelp.getText().toString().trim();

        boolean isEmptyField = false;

        // Input Name
        if (TextUtils.isEmpty(inputName)) {
            isEmptyField = true;
            tipNameDaftarPasien.setError(getString(R.string.toast_not_empty));
        } else {
            tipNameDaftarPasien.setError(null);
        }

        // Input Nik
        if (TextUtils.isEmpty(inputNik)) {
            isEmptyField = true;
            tipNikDaftarPasien.setError(getString(R.string.toast_not_empty));
        } else if (edtnip.length() < 16) {
            tipNikDaftarPasien.setError(getString(R.string.digit_nik));
            return;
        } else {
            tipNikDaftarPasien.setError(null);
        }

        // Input Tanggal Lahir
        if (TextUtils.isEmpty(inputTglLahir)) {
            isEmptyField = true;
            tiptipInputTglLahir.setError(getString(R.string.toast_not_empty));
        } else {
            tiptipInputTglLahir.setError(null);
        }

        // Input Alamat
        if (TextUtils.isEmpty(inputAlamat)) {
            isEmptyField = true;
            tipAddressDaftarPasien.setError(getString(R.string.toast_not_empty));
        } else {
            tipAddressDaftarPasien.setError(null);
        }

        // Input Phone
        if (TextUtils.isEmpty(inputPhone)) {
            isEmptyField = true;
            tipPhoneDaftarPasien.setError(getString(R.string.toast_not_empty));
        } else if (edtnotelp.length() < 11) {
            tipPhoneDaftarPasien.setError(getString(R.string.digit_phone));
            return;
        } else {
            tipPhoneDaftarPasien.setError(null);
        }

        if (!isEmptyField) {
            inputPasien();
        }
    }

    private void spjenis() {
        List<String> list = new ArrayList<String>();
        list.add("Laki-laki");
        list.add("Perempuan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spJK.setAdapter(dataAdapter);
    }

    public void inputPasien() {
        pdModel.pdData(InputPasienBaruActivity.this);
        String url = Consts.SERVERAPP + "inputpasien.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(InputPasienBaruActivity.this, getString(R.string.register_patient_success), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InputPasienBaruActivity.this, PilihKlinikBaruActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(InputPasienBaruActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                        pdModel.hideProgressDialog();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String tgl_lahir = edttgllahir.getText().toString();
                String ho_hp = edtnotelp.getText().toString();
                String nama_user = edtnama.getText().toString();
                String jenis_kelamin = spJK.getSelectedItem().toString();
                String alamat = edtalamat.getText().toString();
                String nip = edtnip.getText().toString();
                String id_user = tviduser.getText().toString();
                Map<String, String> params = new Hashtable<String, String>();

                params.put("nama_pasien", nama_user);
                params.put("nip", nip);
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("no_telp", ho_hp);
                params.put("tgl_lahir", tgl_lahir);
                params.put("alamat", alamat);
                params.put("id_user", id_user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(InputPasienBaruActivity.this);
        requestQueue.add(stringRequest);
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
        tviduser.setText(UnameValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(
                        this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    String sdate = arrMonth[mMonth] + " " + LPad(mDay + "", "0", 2) + ", " + mYear;
                    edttgllahir.setText(sdate);
                }
            };

    private static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
