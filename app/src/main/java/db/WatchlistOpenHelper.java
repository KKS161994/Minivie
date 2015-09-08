package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rajat on 9/7/2015.
 */
public class WatchlistOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="OMDB.db";
    private static final int DATABASE_VERSION=3;
    private static final String TABLE_WATCHLIST="watchlist";
    private static final String COLUMN_IMAGE="image";
    private static final String COLUMN_TITLE="title";
    private static final String COLUMN_YEAR="year";
    private static final String TABLE_CREATE=
            "CREATE TABLE "+TABLE_WATCHLIST+"("
             +COLUMN_TITLE+" TEXT, "
             +COLUMN_IMAGE+" TEXT PRIMARY KEY, "
             +COLUMN_YEAR+" TEXT"
             +")";

    public WatchlistOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS watchlist");
           onCreate(db);
    }
}
