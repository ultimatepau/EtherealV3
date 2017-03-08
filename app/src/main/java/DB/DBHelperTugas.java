package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import DAO.Tugas;

/**
 * Created by SacredGeeks on 3/6/2017.
 */

public class DBHelperTugas extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Tugas.db";

    public DBHelperTugas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_TUGAS = "CREATE TABLE "+ Tugas.TABLE+" (" +
                Tugas.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Tugas.KEY_TUGAS +" varchar(255) NOT NULL," +
                Tugas.KEY_KETERANGAN +" varchar(255) NOT NULL," +
                Tugas.KEY_TANGGAL +" varchar(255) NOT NULL," +
                Tugas.KEY_JAM +" varchar(255) NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_TUGAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Tugas.TABLE);
        onCreate(sqLiteDatabase);
    }
}
