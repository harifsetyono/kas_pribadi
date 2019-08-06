package com.harif.user.kaspribadiku;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SettingPengeluaran extends AppCompatActivity {

    private Button btnBack, btnHome, addKategoriPengeluaran,updateKategoriPengeluaran, deleteKategoriPengeluaran;
    EditText namaKategoriPengeluaran;
    ListView listKategoriPengeluaran;
    TextView idKategoriPengeluaran;

    DBHelper helper;
    Cursor model=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pengeluaran);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        addKategoriPengeluaran = (Button) findViewById(R.id.addKategoriPengeluaran);
        updateKategoriPengeluaran = (Button) findViewById(R.id.updateKategoriPengeluaran);
        deleteKategoriPengeluaran = (Button) findViewById(R.id.deleteKategoriPengeluaran);
        namaKategoriPengeluaran = (EditText) findViewById(R.id.namaKategoriPengeluaran);
        listKategoriPengeluaran = (ListView) findViewById(R.id.listKategoriPengeluaran);
        idKategoriPengeluaran = (TextView) findViewById(R.id.idKategoriPengeluaran);

        helper = new DBHelper(this);
        model=helper.getKategoriKeluar();

        ListAdapter adapter=new SimpleCursorAdapter(this,
                R.layout.activity_row_setting,
                model,
                new String[]{"nama_kategori"},
                new int[]{R.id.rowKategoriSetting});
        listKategoriPengeluaran.setAdapter(adapter);

        //Panggil method manipulasi:======================================================================
        listKategoriPengeluaran.setOnItemClickListener(onListClick);
        addKategoriPengeluaran.setOnClickListener(onSave);
        updateKategoriPengeluaran.setOnClickListener(onUpd);
        deleteKategoriPengeluaran.setOnClickListener(onDel);

        tombol_false();
        kembali();
        home();
    }

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPengeluaran.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPengeluaran.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private View.OnClickListener onSave=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaKategoriPengeluaran.getText().toString().equals("")){
                showDialog();
            }else{
                helper.insertKategoriKeluar(namaKategoriPengeluaran.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(SettingPengeluaran.this,namaKategoriPengeluaran.getText().toString()+" Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

            }
            tombol_false();

        }
    };

    private View.OnClickListener onUpd=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaKategoriPengeluaran.getText().toString().equals("")){
                showDialog();
            }else{
                helper.updateKategoriKeluar(idKategoriPengeluaran.getText().toString(),namaKategoriPengeluaran.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(SettingPengeluaran.this,"Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();

            }
            tombol_false();
            addKategoriPengeluaran.setEnabled(true);

        }
    };
    private View.OnClickListener onDel=new View.OnClickListener() {
        public void onClick(View v) {
            helper.deleteKategoriKeluar(idKategoriPengeluaran.getText().toString());
            model.requery();
            clearText();
            tombol_false();
            addKategoriPengeluaran.setEnabled(true);
        }
    };

    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            model.moveToPosition(position);
            idKategoriPengeluaran.setText(helper.getNo(model));
            namaKategoriPengeluaran.setText(helper.getNama(model));
            tombol_true();
            addKategoriPengeluaran.setEnabled(false);
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
        namaKategoriPengeluaran.setText("");
    }

    private void tombol_false(){
        updateKategoriPengeluaran.setEnabled(false);
        deleteKategoriPengeluaran.setEnabled(false);
    }
    private void tombol_true(){
        updateKategoriPengeluaran.setEnabled(true);
        deleteKategoriPengeluaran.setEnabled(true);
    }
}
