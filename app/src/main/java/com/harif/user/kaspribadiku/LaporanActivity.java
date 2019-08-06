package com.harif.user.kaspribadiku;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class LaporanActivity extends AppCompatActivity {

    private EditText edDate, edDate2;

    private Button btnBack, btnHome, btnTampil, btnDetail;

    TextView totLaporanPemasukan,totLaporanPengeluaran, totLaporanSaldo;



    DBHelper helper;
    Cursor model;

    Locale localeID;
    NumberFormat formatRupiah;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID2 = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        edDate = (EditText) findViewById(R.id.edDate);
        edDate2 = (EditText) findViewById(R.id.edDate2);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnTampil = (Button) findViewById(R.id.btnTampil);
        totLaporanPemasukan = (TextView) findViewById(R.id.totLaporanPemasukan);
        totLaporanPengeluaran = (TextView) findViewById(R.id.totLaporanPengeluaran);
        totLaporanSaldo = (TextView) findViewById(R.id.totLaporanSaldo);
        btnDetail = (Button) findViewById(R.id.btnDetail);

        helper = new DBHelper(this);
        localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        totLaporanPemasukan.setText("Rp0");
        totLaporanPengeluaran.setText("Rp0");
        totLaporanSaldo.setText("Rp0");

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masuk = Integer.parseInt(helper.getLaporanPemasukan(model,edDate.getText().toString(),edDate2.getText().toString()));
                totLaporanPemasukan.setText(""+formatRupiah.format((double) masuk));

                int keluar = Integer.parseInt(helper.getLaporanPengeluaran(model,edDate.getText().toString(),edDate2.getText().toString()));
                totLaporanPengeluaran.setText(""+formatRupiah.format((double) keluar));

                int saldo = masuk-keluar;
                totLaporanSaldo.setText(""+formatRupiah.format((double) saldo));

                Toast.makeText(LaporanActivity.this, "Data Ditampilkan", Toast.LENGTH_SHORT).show();
                btnDetail.setEnabled(true);
            }
        });

//==============================================detail===================================
        btnDetail.setEnabled(false);

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal1, tanggal2;
                tanggal1 = edDate.getText().toString();
                tanggal2 = edDate2.getText().toString();

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("tanggal1",tanggal1);
                intent.putExtra("tanggal2",tanggal2);

                intent.putExtra("totMasuk",totLaporanPemasukan.getText().toString());
                intent.putExtra("totKeluar",totLaporanPengeluaran.getText().toString());
                intent.putExtra("Saldo",totLaporanSaldo.getText().toString());

                startActivity(intent);
            }
        });


        home();
        kembali();
        setCurrentDateOnView();
        addListenerOnButton();
       // detail();
    }

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaporanActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);

        String tanggal = null;
        if (day<10){
            tanggal = "0"+day;
        }else {
            tanggal =day+"";
        }

        String bulan = null;
        if (month<10){
            bulan = "0"+month;
        }else{
            bulan = ""+month;
        }

        // set current date into textview
        edDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year ).append("-").append(bulan).append("-")
                .append(tanggal).append(" "));

        edDate2.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year ).append("-").append(bulan).append("-")
                .append(tanggal).append(" "));

        // set current date into datepicker
//        dpResult.init(year, month, day, null);

    }

    public void addListenerOnButton() {


        edDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

        edDate2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID2);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
            case DATE_DIALOG_ID2:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener2,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;

            String tanggal = null;
            if (day<10){
                tanggal = "0"+day;
            }else {
                tanggal =day+"";
            }

            String bulan = null;
            if (month<10){
                bulan = "0"+month;
            }else{
                bulan = ""+month;
            }

            // set selected date into textview
            edDate.setText(new StringBuilder(). append(year ).append("-").append(bulan).append("-")
                    .append(tanggal).append(" "));



            // set selected date into datepicker also
//            dpResult.init(year, month, day, null);

        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener2
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;

            String tanggal = null;
            if (day<10){
                tanggal = "0"+day;
            }else {
                tanggal =day+"";
            }

            String bulan = null;
            if (month<10){
                bulan = "0"+month;
            }else{
                bulan = ""+month;
            }

            // set selected date into textview


            edDate2.setText(new StringBuilder(). append(year ).append("-").append(bulan).append("-")
                    .append(tanggal).append(" "));

            // set selected date into datepicker also
//            dpResult.init(year, month, day, null);

        }
    };

//    public void detail(){
//        btnDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LaporanActivity.this);
//                View mView = getLayoutInflater().inflate(R.layout.detail_layout, null);
//                final ListView listDetail = (ListView) mView.findViewById(R.id.listDetail);
//
//                model=helper.getDetail(edDate.getText().toString(),edDate2.getText().toString());
//                ListAdapter adapter=new SimpleCursorAdapter(this,
//                        R.layout.activity_row_detail,
//                        model,
//                        new String[]{"nama","tanggal","jenis","nominal"},
//                        new int[]{R.id.detailNama,R.id.detailTanggal,R.id.detailKategori,R.id.detailNominal});
//                listDetail.setAdapter(adapter);
//
//
//                mBuilder.setView(mView);
//                AlertDialog dialog = mBuilder.create();
//                dialog.show();
//            }
//        });
//    }
}
