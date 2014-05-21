package com.sw14_xp_05.pinkee;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by your mama on 21.05.14.
 */
public class MessageStorageHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE = "CREATE TABLE messages (id INT, message TEXT, date DATE, received BOOL";
    public MessageStorageHelper(Context context, String name, CursorFactory factory,
                                int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
