package by.soloviev.mybrowser;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class HistoryRepository {

    private static HistoryRepository sHistoryRepository;

    private List<History> mHistory = new ArrayList<>();
    private HistoryDBHelper mHistoryDBHelper;
    private SQLiteDatabase db;
    private Context mContext;

    public static HistoryRepository getInstance(Context context) {
        if (sHistoryRepository==null){
            sHistoryRepository = new HistoryRepository(context);
        }
        return sHistoryRepository;

    }

    private void readLastInformation() {
        openDB();
        Cursor cursor = db.query(HistoryDBHelper.NAME_TABLE,
                new String[]{HistoryDBHelper.COLUMN_PAGE, HistoryDBHelper.COLUMN_TIME}
                , null, null, null, null, null);
        int idColumnPage = cursor.getColumnIndex(HistoryDBHelper.COLUMN_PAGE);
        int idColumnTime = cursor.getColumnIndex(HistoryDBHelper.COLUMN_TIME);
        while (cursor.moveToNext()) {
            mHistory.add(new History(cursor.getString(idColumnPage), cursor.getString(idColumnTime)));
        }
        closeDB();
    }

    private HistoryRepository(Context context) {
        mContext=context;
        readLastInformation();
    }

    public List<History> getHistory() {
        return mHistory;
    }

    public void writeHistory(History historyInstance) {
        mHistory.add(historyInstance);
        writeInformation(historyInstance);
    }

    private void writeInformation(History historyInstance) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HistoryDBHelper.COLUMN_PAGE, historyInstance.getAddress());
        contentValues.put(HistoryDBHelper.COLUMN_TIME, historyInstance.getTime());
        db.insert(HistoryDBHelper.NAME_TABLE, HistoryDBHelper.COLUMN_PAGE, contentValues);
        closeDB();
    }

    public void openDB() {
        mHistoryDBHelper = new HistoryDBHelper(mContext);
        db = mHistoryDBHelper.getWritableDatabase();
    }

    public void closeDB() {
        mHistoryDBHelper.close();
        db.close();
    }
}
