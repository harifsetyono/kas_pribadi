package com.harif.user.kaspribadiku;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PengeluaranActivity extends AppCompatActivity {

    private Button btnBack, btnHome, addPengeluaran, updatePengeluaran, deletePengeluaran;
    private EditText namaPengeluaran, edDate, nominalPengeluaran;
    private Spinner kategoriPengeluaran;
    ListView listPengeluaran;
    String kategoriPengeluaranTerpilih;
    TextView idPengeluaran;

    DBHelper helper;
    Cursor model=null;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    //jalal=========
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    //=============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        edDate = (EditText) findViewById(R.id.edDate);
        addPengeluaran= (Button) findViewById(R.id.addPengeluaran);
        updatePengeluaran = (Button) findViewById(R.id.updatePengeluaran);
        deletePengeluaran = (Button) findViewById(R.id.deletePengeluaran);
        namaPengeluaran = (EditText) findViewById(R.id.namaPengeluaran);
        edDate = (EditText) findViewById(R.id.edDate);
        kategoriPengeluaran = (Spinner) findViewById(R.id.kategoriPengeluaran);
        nominalPengeluaran = (EditText) findViewById(R.id.nominalPengeluaran);
        listPengeluaran = (ListView) findViewById(R.id.listPengeluaran);
        idPengeluaran = (TextView) findViewById(R.id.idPengeluaran);

        //Ambil data dari database:====================================================================
        helper = new DBHelper(this);
        model=helper.getKeluar();

        ListAdapter adapter=new SimpleCursorAdapter(this,
                R.layout.activity_row,
                model,
                new String[]{"nama","tanggal","kategori","nominal"},
                new int[]{R.id.rowNama,R.id.rowTanggal,R.id.rowKategori,R.id.rowNominal});
        listPengeluaran.setAdapter(adapter);

        ArrayList<String> arrayList=helper.getSpinnerPengeluaran();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        kategoriPengeluaran.setAdapter(dataAdapter);
        kategoriPengeluaran.setOnItemSelectedListener(itemSelect);

        //Panggil method manipulasi:======================================================================
        listPengeluaran.setOnItemClickListener(onListClick);
        addPengeluaran.setOnClickListener(onSave);
        updatePengeluaran.setOnClickListener(onUpd);
        deletePengeluaran.setOnClickListener(onDel);


        tombol_false();
        home();
        kembali();
        setCurrentDateOnView();
        addListenerOnButton();
    }

//untuk navigasi=================================================================================================

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengeluaranActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengeluaranActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//untuk tanggal=================================================================================================
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

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
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

//untuk manipulasi=================================================================================================
    AdapterView.OnItemSelectedListener itemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            kategoriPengeluaranTerpilih = kategoriPengeluaran.getItemAtPosition(position).toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private View.OnClickListener onSave=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaPengeluaran.getText().toString().equals("")||edDate.getText().toString().equals("")||nominalPengeluaran.getText().toString().equals("")){
                showDialog();
            }else{
                helper.insertKeluar(namaPengeluaran.getText().toString(),edDate.getText().toString(),
                        kategoriPengeluaran.getSelectedItem().toString(),nominalPengeluaran.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(PengeluaranActivity.this,"Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
            tombol_false();
        }
    };

    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            model.moveToPosition(position);
            idPengeluaran.setText(helper.getNo(model));
            namaPengeluaran.setText(helper.getNama(model));
            edDate.setText(helper.getTanggal(model));
            kategoriPengeluaran.setSelection(helper.getKategori(model));
            nominalPengeluaran.setText(helper.getNominal(model));
            tombol_true();
            addPengeluaran.setEnabled(false);
        }
    };

    private View.OnClickListener onUpd=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaPengeluaran.getText().toString().equals("")||edDate.getText().toString().equals("")||nominalPengeluaran.getText().toString().equals("")){
                showDialog();
            }else {
                helper.updateKeluar(idPengeluaran.getText().toString(), namaPengeluaran.getText().toString(), edDate.getText().toString(),
                        kategoriPengeluaran.getSelectedItem().toString(), nominalPengeluaran.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(PengeluaranActivity.this,"Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();

            }
            tombol_false();
            addPengeluaran.setEnabled(true);
        }
    };

    private View.OnClickListener onDel=new View.OnClickListener() {
        public void onClick(View v) {
            helper.deleteKeluar(idPengeluaran.getText().toString());
            model.requery();
            clearText();
            tombol_false();
            addPengeluaran.setEnabled(true);
        }
    };


    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title dialog
        alertDialogBuilder.setTitle("Field Kosong");
        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Silahkan lengkapi !")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }


    private void clearText(){
        namaPengeluaran.setText("");
        setCurrentDateOnView();
        kategoriPengeluaran.setSelection(0);
        nominalPengeluaran.setText("");
    }

    private void tombol_false(){
        updatePengeluaran.setEnabled(false);
        deletePengeluaran.setEnabled(false);
    }
    private void tombol_true(){
        updatePengeluaran.setEnabled(true);
        deletePengeluaran.setEnabled(true);
    }


}
