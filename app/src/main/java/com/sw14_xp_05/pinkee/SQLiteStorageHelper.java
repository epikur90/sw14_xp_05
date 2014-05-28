package com.sw14_xp_05.pinkee;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by your mama on 21.05.14.
 */
public class SQLiteStorageHelper extends SQLiteOpenHelper {

    private static final String DB_DEFAULT_NAME = "pinkeeDB";
    private static final String MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + Message.DB_TABLE +  "("
            + Message.DB_COL_ID + " integer primary key autoincrement, "
            + Message.DB_COL_MESSAGETEXT + " text, "
            + Message.DB_COL_CONTACT + " text, "
            + Message.DB_COL_DATE + " integer)";

    private static final String CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + Contact.DB_TABLE + "("
            + Contact.DB_COL_EMAIL + " text primary key,"
            + Contact.DB_COL_FORENAME + " text,"
            + Contact.DB_COL_NAME + " text,"
            + Contact.DB_COL_PICTURE + " text)";

    public SQLiteStorageHelper(Context context, String name, CursorFactory factory,
                                int version){
        super(context, name, factory, version);
    }

    public SQLiteStorageHelper(Context context){
        this(context, DB_DEFAULT_NAME, null, 1);
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
        // some magic happens with your mama
    }

    public boolean saveMessage(Message message){
        Log.d("messages", "saving message..." + message.getMessageText());
        final String table = "message";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Message.DB_COL_MESSAGETEXT, message.getMessageText());
        values.put(Message.DB_COL_CONTACT, message.getContactID());
        values.put(Message.DB_COL_DATE, message.getDate().getTime());

        db.insert(table, null, values);
        db.close();

        return true;
    }

    public ArrayList<Message> getMessages(Contact contact){
        Log.d("messages", "get messages, c not null?????");

        if(contact == null) return new ArrayList<Message>();
           Log.d("contact", "get messages, c not null");
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = Message.DB_COL_CONTACT + " = ?";
        String[] selectionArgs = new String[] {contact.getEmail()};
        Cursor cursor = db.query(Message.DB_TABLE, null, selection, selectionArgs, null, null, Message.DB_COL_DATE, null);
        //Cursor cursor = db.query(Message.DB_TABLE, null, null, null, null, null, null, null);

        ArrayList<Message> result = new ArrayList<Message>();

        while (cursor.moveToNext()){
            Log.d("messages", "message found");
            Message message = new Message();
            message.setMessageText(cursor.getString(cursor.getColumnIndexOrThrow(Message.DB_COL_MESSAGETEXT)));
            message.setContact( contact );
            Date date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Message.DB_COL_DATE)));
            message.setDate(date);
            result.add(message);
        }
        cursor.close();
        db.close();

        return result;
    }
}
