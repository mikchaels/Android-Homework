package by.soloviev.mybrowser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HistoryDBHelper extends SQLiteOpenHelper {

    public static final int VERSION_BASE=1;
    public static final String NAME_BASE="HistoryBrowserDB";
    public static final String NAME_TABLE="HistoryBrowserTable";

    public static final String COLUMN_PAGE="PAGE";
    public static final String COLUMN_TIME="TIME";

    private  static final String CREATE_QERY_TABLE="CREATE TABLE " + NAME_TABLE + " (" + " _id"
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PAGE + " TEXT," + COLUMN_TIME + " TEXT" + ")";

    public HistoryDBHelper(Context context) {
        super(context, NAME_BASE, null, VERSION_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QERY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /*TODO*/
    }
}
