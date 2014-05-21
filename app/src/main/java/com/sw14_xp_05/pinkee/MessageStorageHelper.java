package com.sw14_xp_05.pinkee;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

/**
 * Created by your mama on 21.05.14.
 */
public class MessageStorageHelper extends SQLiteOpenHelper {

    private static final String MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS message("
            + Message.DB_COL_ID + " integer primary key autoincrement, "
            + Message.DB_COL_MESSAGETEXT + " text, "
            + Message.DB_COL_CONTACT + " text, "
            + Message.DB_COL_DATE + " integer)";

    private static final String CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS contact(email primary key, forename, lastname)";

    public MessageStorageHelper(Context context, String name, CursorFactory factory,
                                int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{

            if(db.isOpen()){

                 db.execSQL(MESSAGE_TABLE);
                 db.execSQL(CONTACT_TABLE);
            }

        }
        catch(Exception e){
            Log.d("onCreateDB", e.getMessage());
            return;
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public boolean saveMessage(Message message){
        final String table = "message";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Message.DB_COL_MESSAGETEXT, message.getMessageText());
        values.put(Message.DB_COL_CONTACT, message.getContactID());
        values.put(Message.DB_COL_DATE, message.getDateSent().getTime());

        db.insert(table, null, values);

        return true;
    }
}
