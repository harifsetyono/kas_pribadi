package com.harif.user.kaspribadiku;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    CardView btnpemasukan, btnpengeluaran, btnlaporan, btnpengaturan;
    TextView totPemasukan, totPengeluaran, totSaldo;
    Locale localeID;

    DBHelper helper;
    Cursor model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnpemasukan = (CardView) findViewById(R.id.btnpemasukan);
        btnpengeluaran = (CardView) findViewById(R.id.btnpengeluaran);
        btnlaporan = (CardView) findViewById(R.id.btnlaporan);
        btnpengaturan = (CardView) findViewById(R.id.btnpengaturan);
        totPemasukan = (TextView) findViewById(R.id.totPemasukan);
        totPengeluaran = (TextView) findViewById(R.id.totPengeluaran);
        totSaldo = (TextView) findViewById(R.id.totSaldo);


        helper = new DBHelper(this);
        localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);


        int masuk = Integer.parseInt(helper.getTotPemasukan(model));
        totPemasukan.setText(""+formatRupiah.format((double) masuk));
        int keluar = Integer.parseInt(helper.getTotPengeluaran(model));
        totPengeluaran.setText(""+formatRupiah.format((double) keluar));
        int saldo = masuk-keluar;
        totSaldo.setText(""+formatRupiah.format((double) saldo));



        btnpemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PemasukanActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnpengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PengeluaranActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnlaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LaporanActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        btnpengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
//                finish();
            }
        });


    }
}
