package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import DAO.Jadwal;
import DB.DBHelperJadwal;

/**
 * Created by SacredGeeks on 3/6/2017.
 */

public class JadwalModel {
    private DBHelperJadwal dbHelper;

    public JadwalModel(Context context){
        dbHelper = new DBHelperJadwal(context);
    }

    public int Insert(Jadwal jadwal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Jadwal.KEY_ID,jadwal.id);
        values.put(Jadwal.KEY_JADWAL,jadwal.jadwal);
        values.put(Jadwal.KEY_KETERANGAN,jadwal.keterangan);
        values.put(Jadwal.KEY_HARI,jadwal.hari);
        values.put(Jadwal.KEY_JAM,jadwal.jam);

        long sukses = db.insert(Jadwal.TABLE,null, values);
        db.close();
        return (int) sukses;
    }

    public void Delete (int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Jadwal.TABLE,Jadwal.KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public void Update (Jadwal jadwal) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Jadwal.KEY_JADWAL,jadwal.jadwal);
        values.put(Jadwal.KEY_KETERANGAN,jadwal.keterangan);
        values.put(Jadwal.KEY_HARI,jadwal.hari);
        values.put(Jadwal.KEY_JAM,jadwal.jam);

        db.update(Jadwal.TABLE, values , Jadwal.KEY_ID + "=?",
                new String[] {String.valueOf(jadwal.id)});
        db.close();
    }
    public String getRow() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT count(*) as baris from "+Jadwal.TABLE;
        String row = "";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            row = cursor.getString(cursor.getColumnIndex("baris"));
        }
        cursor.close();
        db.close();
        return row;
    }

    public ArrayList<HashMap<String,String>> getJadwalList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " +
                Jadwal.KEY_ID +"," +
                Jadwal.KEY_JADWAL +"," +
                Jadwal.KEY_KETERANGAN +"," +
                Jadwal.KEY_HARI + "," +
                Jadwal.KEY_JAM +
                " FROM "+Jadwal.TABLE;
        ArrayList<HashMap<String, String>> JadwalList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            int no = 0;
            do {
                no += 1;
                HashMap<String, String> dataJadwal = new HashMap<String, String>();
                dataJadwal.put(Jadwal.KEY_NO, String.valueOf(no));
                dataJadwal.put(Jadwal.KEY_ID,cursor.getString(cursor.getColumnIndex(Jadwal.KEY_ID)));
                dataJadwal.put(Jadwal.KEY_JADWAL,cursor.getString(cursor.getColumnIndex(Jadwal.KEY_JADWAL)));
                dataJadwal.put(Jadwal.KEY_HARI,cursor.getString(cursor.getColumnIndex(Jadwal.KEY_HARI)));
                dataJadwal.put(Jadwal.KEY_JAM,cursor.getString(cursor.getColumnIndex(Jadwal.KEY_JAM)));
                JadwalList.add(dataJadwal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return JadwalList;
    }

    public Jadwal getJadwalById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM "+Jadwal.TABLE+" WHERE "+Jadwal.KEY_ID+"=?";
        Jadwal jadwal = new Jadwal();

        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)} );

        if(cursor.moveToFirst()){
            do {
                jadwal.jadwal = cursor.getString(cursor.getColumnIndex(Jadwal.KEY_JADWAL));
                jadwal.keterangan = cursor.getString(cursor.getColumnIndex(Jadwal.KEY_KETERANGAN));
                jadwal.hari = cursor.getString(cursor.getColumnIndex(Jadwal.KEY_HARI));
                jadwal.jam = cursor.getString(cursor.getColumnIndex(Jadwal.KEY_JAM));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return jadwal;
    }
}
