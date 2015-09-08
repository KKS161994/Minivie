package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rajat on 9/9/2015.
 */
public class LoginOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="Users.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_USERS="users";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_PASSWORD="password";
    private static final String TABLE_CREATE=
            "CREATE TABLE "+TABLE_USERS+"("
                    +COLUMN_EMAIL+" TEXT PRIMARY KEY, "
                    +COLUMN_PASSWORD+" TEXT"
                    +")";

    public LoginOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE I EXISTS "+TABLE_USERS);
        onCreate(db);

    }
    public boolean validateUser(String email, String password){
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE "
                        + COLUMN_EMAIL + "='" + email +"'AND "+COLUMN_PASSWORD+"='"+password+"'" ,  null);
        if (c.getCount()>0)
            return true;
        return false;
    }
}
