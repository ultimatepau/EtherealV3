package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import DAO.Jadwal;

/**
 * Created by SacredGeeks on 3/6/2017.
 */

public class DBHelperJadwal extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Jadwal.db";

    public DBHelperJadwal(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_JADWAL = "CREATE TABLE "+ Jadwal.TABLE+" (" +
                Jadwal.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Jadwal.KEY_JADWAL +" varchar(255)," +
                Jadwal.KEY_KETERANGAN +" varchar(255) ," +
                Jadwal.KEY_HARI +" varchar(255)," +
                Jadwal.KEY_JAM +" varchar(255))";
        sqLiteDatabase.execSQL(CREATE_TABLE_JADWAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Jadwal.TABLE);
        onCreate(sqLiteDatabase);
    }
}
