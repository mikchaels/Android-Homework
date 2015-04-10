package by.soloviev.mybrowser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 19.03.2015.
 */
public class HistoryDBHelper extends SQLiteOpenHelper {
    /*
    * public static final int VERSION = 1;
    public static final String CONTACTS_DATA_BASE = "contacts_data_base";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String COLUMN_NAME_ID = "ID";
    public static final String COLUMN_NAME_NAME = "NAME";

    public static final String COLUMN_NAME_PHONE = "PHONE";
    public static final String COLUMN_NAME_EMAIL = "EMAIL";
    public static final String COLUMN_NAME_ADDRESS = "ADDRESS";
    public static final String COLUMN_NAME_BIRTH_DATE = "BIRTH_DATE";
    public static final String COLUMN_NAME_OCCUPATION = "OCCUPATION";
    private static final String DELETE_QUERY_TABLE = "DROP TABLE IF EXISTS "+CONTACTS_TABLE_NAME;
    private static final String CREATE_QUERY_TABLE = "CREATE TABLE " + CONTACTS_TABLE_NAME + " (" + " _id" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_ID + " INTEGER," + COLUMN_NAME_NAME + " TEXT," + CONTACTS_TABLE_NAME + " TEXT," + COLUMN_NAME_PHONE + " TEXT,"
            + COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_ADDRESS + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT,"
            + COLUMN_NAME_OCCUPATION + " TEXT" + ")";


    public ContactsDataBaseHelper(Context context) {
        super(context, CONTACTS_DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_QUERY_TABLE);
        onCreate(db);

    }
}
    * */
public static final int VERSION_BASE=1;
    public static final String NAME_BASE="HistoryBrowserDB";

     public HistoryDBHelper(Context context) {
        super(context, NAME_BASE, null, VERSION_BASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /*TODO*/
    }
}
