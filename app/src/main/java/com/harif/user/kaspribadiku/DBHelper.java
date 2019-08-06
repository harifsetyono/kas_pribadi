package com.harif.user.kaspribadiku;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="kasku3.db";
    private static final int SCHEMA_VERSION=1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_kas (_id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, tanggal DATE, kategori TEXT, jenis TEXT, nominal INTEGER); ");
        db.execSQL("CREATE TABLE tbl_kategori (_id INTEGER PRIMARY KEY AUTOINCREMENT, nama_kategori TEXT, jenis TEXT); ");
//        awal();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_kas");
        db.execSQL("DROP TABLE IF EXISTS tbl_kategori ");
        onCreate(db);
    }

    //======================== Kategori Masuk =========================================
    public Cursor getKategoriMasuk() {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, nama_kategori FROM tbl_kategori WHERE jenis='masuk' ORDER BY _id", null));
    }

    public void insertKategoriMasuk(String nama_kategori) {
        getWritableDatabase().execSQL("insert into tbl_kategori (nama_kategori, jenis) values ('"+nama_kategori+"','masuk');");
    }

    public void updateKategoriMasuk(String no,String nama_kategori) {
        getWritableDatabase().execSQL("update tbl_kategori set nama_kategori='"+nama_kategori+"' "+"where _id="+no);
    }

    public void deleteKategoriMasuk(String no) {
        getWritableDatabase().execSQL("delete from tbl_kategori where _id="+no);
    }

    //======================== Kategori Keluar =========================================

    public Cursor getKategoriKeluar() {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, nama_kategori FROM tbl_kategori WHERE jenis='keluar' ORDER BY _id", null));
    }

    public void insertKategoriKeluar(String nama_kategori) {
        getWritableDatabase().execSQL("insert into tbl_kategori (nama_kategori, jenis) values ('"+nama_kategori+"','keluar');");
    }

    public void updateKategoriKeluar(String no,String nama_kategori) {
        getWritableDatabase().execSQL("update tbl_kategori set nama_kategori='"+nama_kategori+"' "+"where _id="+no);
    }

    public void deleteKategoriKeluar(String no) {
        getWritableDatabase().execSQL("delete from tbl_kategori where _id="+no);
    }

//======================== Pemasukan =========================================
    public ArrayList<String> getSpinnerPrmasukan() {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT _id, nama_kategori FROM tbl_kategori WHERE jenis = 'masuk' ORDER BY _id", null);
        cursor.requery();
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex("nama_kategori")));
            cursor.moveToNext();
        }
        cursor.close();
        return names;
    }

    public Cursor getMasuk() {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, nama, tanggal, kategori, nominal FROM tbl_kas WHERE jenis='masuk' ORDER BY _id DESC", null));
    }

    public void insertMasuk(String nama, String tanggal, String kategori, String nominal) {
        getWritableDatabase().execSQL("insert into tbl_kas (nama, tanggal, kategori, jenis,nominal) " +
                "values ('"+nama+"','"+tanggal+"','"+kategori+"','masuk','"+nominal+"');");
    }

    public void updateMasuk(String no,String nama, String tanggal, String kategori, String nominal) {
        getWritableDatabase().execSQL("update tbl_kas set nama='"+nama+"', tanggal='"+tanggal+"', kategori='"+kategori+"'," +
                "nominal='"+nominal+"' where _id="+no);
    }

    public void deleteMasuk(String no) {
        getWritableDatabase().execSQL("delete from tbl_kas where _id="+no);
    }

//======================== Pengeluaran =========================================
    public ArrayList<String> getSpinnerPengeluaran() {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT _id, nama_kategori FROM tbl_kategori WHERE jenis = 'keluar' ORDER BY _id", null);
        cursor.requery();
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex("nama_kategori")));
            cursor.moveToNext();
        }
        cursor.close();
        return names;
    }

    public Cursor getKeluar() {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, nama, tanggal, kategori, nominal FROM tbl_kas WHERE jenis='keluar' ORDER BY _id DESC", null));
    }

    public void insertKeluar(String nama, String tanggal, String kategori, String nominal) {
        getWritableDatabase().execSQL("insert into tbl_kas (nama, tanggal, kategori, jenis,nominal) " +
                "values ('"+nama+"','"+tanggal+"','"+kategori+"','keluar','"+nominal+"');");
    }

    public void updateKeluar(String no,String nama, String tanggal, String kategori, String nominal) {
        getWritableDatabase().execSQL("update tbl_kas set nama='"+nama+"', tanggal='"+tanggal+"', kategori='"+kategori+"'," +
                "nominal='"+nominal+"' where _id="+no);
    }

    public void deleteKeluar(String no) {
        getWritableDatabase().execSQL("delete from tbl_kas where _id="+no);
    }

//======================== Untuk Home =========================================
    public String getTotPemasukan(Cursor c) {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT SUM(nominal) as total_masuk FROM tbl_kas WHERE jenis='masuk'", null);
        cursor.requery();
        String names;

        cursor.moveToFirst();
   //     masukkan data cursor ke objek name
        names = cursor.getString(cursor.getColumnIndex("total_masuk"));
        if (names==null){
            return "0";
        }else {
            //tutup sambungan
            cursor.close();
            //return names
            return names;
        }
    }

    public String getTotPengeluaran(Cursor c) {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT SUM(nominal) as total_keluar FROM tbl_kas WHERE jenis='keluar'", null);
        cursor.requery();
        String names;

        cursor.moveToFirst();
        //masukkan data cursor ke objek name
        names = cursor.getString(cursor.getColumnIndex("total_keluar"));
        if (names==null){
            return "0";
        }else {
            //tutup sambungan
            cursor.close();
            //return names
            return names;
        }
    }

//======================== Untuk Laporan =========================================
    public String getLaporanPemasukan(Cursor c, String tanggal1, String tanggal2) {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT SUM(nominal) as total_masuk FROM tbl_kas WHERE jenis='masuk' AND tanggal " +
                "BETWEEN '"+tanggal1+"' AND '"+tanggal2+"'" , null);
        cursor.requery();
        String names;

        cursor.moveToFirst();
        //     masukkan data cursor ke objek name
        names = cursor.getString(cursor.getColumnIndex("total_masuk"));
        if (names==null){
            return "0";
        }else {
            //tutup sambungan
            cursor.close();
            //return names
            return names;
        }
    }

    public String getLaporanPengeluaran(Cursor c, String tanggal1, String tanggal2) {
        Cursor cursor=getReadableDatabase().rawQuery("SELECT SUM(nominal) as total_masuk FROM tbl_kas WHERE jenis='keluar' AND tanggal " +
                "BETWEEN '"+tanggal1+"' AND '"+tanggal2+"'" , null);
        cursor.requery();
        String names;

        cursor.moveToFirst();
        //     masukkan data cursor ke objek name
        names = cursor.getString(cursor.getColumnIndex("total_masuk"));
        if (names==null){
            return "0";
        }else {
            //tutup sambungan
            cursor.close();
            //return names
            return names;
        }
    }
 //================================================= Tampil Detail======================

    public Cursor getDetail(String date1, String date2) {
        return(getReadableDatabase().rawQuery(
                "SELECT _id, nama, tanggal, jenis, nominal FROM tbl_kas WHERE tanggal BETWEEN '"+date1+"' AND '"+date2+"'  ORDER BY _id DESC", null));
    }

//======================== On Click =========================================
    public String getNo(Cursor c) {
        return(c.getString(0));
    }

    public String getNama(Cursor c) {
        return(c.getString(1));
    }

    public String getTanggal(Cursor c) {
        return(c.getString(2));
    }

    public int getKategori(Cursor c){
        return(c.getInt(3));
    }

    public String getNominal(Cursor c){
        return(c.getString(4));
    }

//    public void awal(){
//        getWritableDatabase().execSQL("insert into tbl_kategori (nama_kategori, jenis) values ('Gaji','masuk');");
//        getWritableDatabase().execSQL("insert into tbl_kategori (nama_kategori, jenis) values ('Uang Makan','keluar');");
//        getWritableDatabase().execSQL("insert into tbl_kas (nama, tanggal, kategori, jenis,nominal) " +
//                "values ('Contoh nama','0000-00-00','Gaji','masuk','0');");
//        getWritableDatabase().execSQL("insert into tbl_kas (nama, tanggal, kategori, jenis,nominal) " +
//                "values ('Contoh nama','0000-00-00','Uang Makan','keluar','0');");
//    }


}
