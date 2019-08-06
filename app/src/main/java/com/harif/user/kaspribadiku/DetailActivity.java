package com.harif.user.kaspribadiku;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private Button btnBack, btnHome;
    ListView listDetail;
    TextView tvDetail;

    DBHelper helper;
    Cursor model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        listDetail = (ListView) findViewById(R.id.listDetail);
        tvDetail = (TextView) findViewById(R.id.tvDetail);

        Intent intent = getIntent();
        String tanggal1 = intent.getStringExtra("tanggal1");
        String tanggal2 = intent.getStringExtra("tanggal2");
        String totMasuk = intent.getStringExtra("totMasuk");
        String totKeluar = intent.getStringExtra("totKeluar");
        String Saldo = intent.getStringExtra("Saldo");


        helper = new DBHelper(this);
        model=helper.getDetail(tanggal1,tanggal2);

        ListAdapter adapter=new SimpleCursorAdapter(this,
                R.layout.activity_row_detail,
                model,
                new String[]{"nama","tanggal","jenis","nominal"},
                new int[]{R.id.detailNama,R.id.detailTanggal,R.id.detailKategori,R.id.detailNominal});
        listDetail.setAdapter(adapter);

        if (totMasuk.equals("Rp0")&&totKeluar.equals("Rp0")&&Saldo.equals("Rp0")){
            tvDetail.setText("No Record");
        }else {
            tvDetail.setText("");
        }
        Toast.makeText(DetailActivity.this, "Data Ditampilkan", Toast.LENGTH_SHORT).show();


        home();
        kembali();
    }

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, LaporanActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
