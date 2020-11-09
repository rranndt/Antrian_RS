package com.rizkirian.rsantrian;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PanduanActivity extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    TextView tvpanduan;
    ImageView imgpanduan;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.btn6);
        btn7=(Button)findViewById(R.id.btn7);
        btn8=(Button)findViewById(R.id.btn8);
        tvpanduan=(TextView)findViewById(R.id.tvtutorial);
        imgpanduan=(ImageView)findViewById(R.id.imgtutorial);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            imgpanduan.setImageResource(R.drawable.tutorial1);
            tvpanduan.setText(getString(R.string.panduan1));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial2);
                tvpanduan.setText(getString(R.string.panduan2));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial3);
                tvpanduan.setText(getString(R.string.panduan3));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutor);
                tvpanduan.setText(getString(R.string.panduan4));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial5);
                tvpanduan.setText(getString(R.string.panduan5));
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial6);
                tvpanduan.setText(getString(R.string.panduan6));
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial7);
                tvpanduan.setText(getString(R.string.panduan7));
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgpanduan.setImageResource(R.drawable.tutorial8);
                tvpanduan.setText(getString(R.string.panduan8));
            }
        });

    }
}
