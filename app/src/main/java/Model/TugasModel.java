package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import DAO.Tugas;
import DB.DBHelperTugas;

/**
 * Created by SacredGeeks on 3/6/2017.
 */

public class TugasModel {
    private DBHelperTugas dbHelper;

    public TugasModel (Context context){
        dbHelper = new DBHelperTugas(context);
    }

    public int Insert(Tugas tugas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Tugas.KEY_ID,tugas.id);
        values.put(Tugas.KEY_TUGAS,tugas.tugas);
        values.put(Tugas.KEY_KETERANGAN,tugas.keterangan);
        values.put(Tugas.KEY_TANGGAL,tugas.tanggal);
        values.put(Tugas.KEY_JAM,tugas.jam);

        long sukses = db.insert(Tugas.TABLE,null, values);
        db.close();
        return (int) sukses;
    }

    public void Delete (int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Tugas.TABLE,Tugas.KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public void Update (Tugas tugas) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Tugas.KEY_TUGAS,tugas.tugas);
        values.put(Tugas.KEY_KETERANGAN,tugas.keterangan);
        values.put(Tugas.KEY_TANGGAL,tugas.tanggal);
        values.put(Tugas.KEY_JAM,tugas.jam);

        db.update(Tugas.TABLE, values , Tugas.KEY_ID + "=?",
                new String[] {String.valueOf(tugas.id)});
        db.close();
    }

    public String getRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT count(*) as baris from "+ Tugas.TABLE;
        String row = "";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            row = cursor.getString(cursor.getColumnIndex("baris"));
        }
        cursor.close();
        db.close();
        return row;
    }

    public ArrayList<HashMap<String,String>> getTugasList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+Tugas.TABLE;
        ArrayList<HashMap<String, String>> TugasList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(query, null);
        int no = 0;
        if(cursor.moveToFirst()){
            do {
                no += 1;
                HashMap<String, String> dataTugas = new HashMap<String, String>();
                dataTugas.put(Tugas.KEY_ID,cursor.getString(cursor.getColumnIndex(Tugas.KEY_ID)));
                dataTugas.put(Tugas.KEY_NO, String.valueOf(no));
                dataTugas.put(Tugas.KEY_TUGAS,cursor.getString(cursor.getColumnIndex(Tugas.KEY_TUGAS)));
                dataTugas.put(Tugas.KEY_TANGGAL,cursor.getString(cursor.getColumnIndex(Tugas.KEY_TANGGAL)));
                dataTugas.put(Tugas.KEY_JAM,cursor.getString(cursor.getColumnIndex(Tugas.KEY_JAM)));
                TugasList.add(dataTugas);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return TugasList;
    }

    public Tugas getTugasById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM "+Tugas.TABLE+" WHERE "+Tugas.KEY_ID + "=?";
        Tugas tugas = new Tugas();

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)} );

        if(cursor.moveToFirst()){
            do {
                tugas.id = cursor.getString(cursor.getColumnIndex(Tugas.KEY_ID));
                tugas.tugas = cursor.getString(cursor.getColumnIndex(Tugas.KEY_TUGAS));
                tugas.keterangan = cursor.getString(cursor.getColumnIndex(Tugas.KEY_KETERANGAN));
                tugas.tanggal= cursor.getString(cursor.getColumnIndex(Tugas.KEY_TANGGAL));
                tugas.jam = cursor.getString(cursor.getColumnIndex(Tugas.KEY_JAM));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tugas;
    }
}
