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

public class SettingPemasukan extends AppCompatActivity {

    private Button btnBack, btnHome, addKategoriPemasukan,updateKategoriPemasukan, deleteKategoriPemasukan;
    EditText namaKategoriPemasukan;
    ListView listKategoriPemasukan;
    TextView idKategoriPemasukan;

    DBHelper helper;
    Cursor model=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pemasukan);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnHome = (Button) findViewById(R.id.btnHome);
        addKategoriPemasukan = (Button) findViewById(R.id.addKategoriPemasukan);
        updateKategoriPemasukan = (Button) findViewById(R.id.updateKategoriPemasukan);
        deleteKategoriPemasukan = (Button) findViewById(R.id.deleteKategoriPemasukan);
        namaKategoriPemasukan = (EditText) findViewById(R.id.namaKategoriPemasukan);
        listKategoriPemasukan = (ListView) findViewById(R.id.listKategoriPemasukan);
        idKategoriPemasukan = (TextView) findViewById(R.id.idKategoriPemasukan);

        //Ambil data dari database:====================================================================
        helper = new DBHelper(this);
        model=helper.getKategoriMasuk();

        ListAdapter adapter=new SimpleCursorAdapter(this,
                R.layout.activity_row_setting,
                model,
                new String[]{"nama_kategori"},
                new int[]{R.id.rowKategoriSetting});
        listKategoriPemasukan.setAdapter(adapter);

        //Panggil method manipulasi:======================================================================
        listKategoriPemasukan.setOnItemClickListener(onListClick);
        addKategoriPemasukan.setOnClickListener(onSave);
        updateKategoriPemasukan.setOnClickListener(onUpd);
        deleteKategoriPemasukan.setOnClickListener(onDel);

        tombol_false();
        kembali();
        home();
    }

    private void kembali(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPemasukan.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void home(){
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingPemasukan.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private View.OnClickListener onSave=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaKategoriPemasukan.getText().toString().equals("")){
                showDialog();
            }else{
                helper.insertKategoriMasuk(namaKategoriPemasukan.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(SettingPemasukan.this,namaKategoriPemasukan.getText().toString()+" Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

            }
            tombol_false();
        }
    };

    private View.OnClickListener onUpd=new View.OnClickListener() {
        public void onClick(View v) {
            if (namaKategoriPemasukan.getText().toString().equals("")){
                showDialog();
            }else {
                helper.updateKategoriMasuk(idKategoriPemasukan.getText().toString(), namaKategoriPemasukan.getText().toString());
                model.requery();
                clearText();
                Toast.makeText(SettingPemasukan.this,"Data Berhasil Dirubah", Toast.LENGTH_SHORT).show();

            }
            tombol_false();
            addKategoriPemasukan.setEnabled(true);
        }
    };
    private View.OnClickListener onDel=new View.OnClickListener() {
        public void onClick(View v) {
            helper.deleteKategoriMasuk(idKategoriPemasukan.getText().toString());
            model.requery();
            clearText();
            tombol_false();
            addKategoriPemasukan.setEnabled(true);
        }
    };

    private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            model.moveToPosition(position);
            idKategoriPemasukan.setText(helper.getNo(model));
            namaKategoriPemasukan.setText(helper.getNama(model));
            tombol_true();
            addKategoriPemasukan.setEnabled(false);
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
        namaKategoriPemasukan.setText("");
    }

    private void tombol_false(){
        updateKategoriPemasukan.setEnabled(false);
        deleteKategoriPemasukan.setEnabled(false);
    }
    private void tombol_true(){
        updateKategoriPemasukan.setEnabled(true);
        deleteKategoriPemasukan.setEnabled(true);
    }
}
