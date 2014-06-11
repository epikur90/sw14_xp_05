package com.sw14_xp_05.pinkee;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            + Message.DB_COL_DATE + " integer, "
            + Message.DB_COL_INCOMING + " integer)";

    private static final String CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " + Contact.DB_TABLE + "("
            + Contact.DB_COL_EMAIL + " text primary key,"
            + Contact.DB_COL_FORENAME + " text,"
            + Contact.DB_COL_NAME + " text,"
            + Contact.DB_COL_PICTURE + " text)";
    private static SQLiteStorageHelper instance;
    private MessageList observer;

    private SQLiteStorageHelper(Context context, String name, CursorFactory factory,
                                int version){
        super(context, name, factory, version);
    }

    private SQLiteStorageHelper(Context context){
        this(context, DB_DEFAULT_NAME, null, 3);
    }

    public static SQLiteStorageHelper getInstance(Context context){
        if(instance == null){
            instance = new SQLiteStorageHelper(context);
        }

        return instance;
    }

    public void registerObserver(MessageList observer){
        this.observer = observer;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{

            if(db.isOpen()){

                 db.execSQL(MESSAGE_TABLE);
                 db.execSQL(CONTACT_TABLE);
                Log.d("dbupdate", "db created");
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
        db.execSQL("DROP TABLE IF EXISTS " + Message.DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Contact.DB_TABLE);
        onCreate(db);
        Log.d("dbupdate", "db updated");
    }

    public boolean saveMessage(Message message){
        Log.d("messages", "saving message..." + message.getMessageText());
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            Log.d("error", "saveMessage error fetching db");
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(Message.DB_COL_MESSAGETEXT, message.getMessageText());
        values.put(Message.DB_COL_CONTACT, message.getContactID());
        values.put(Message.DB_COL_DATE, message.getDate().getTime());
        values.put(Message.DB_COL_INCOMING, message.isIncoming() ? 1 : 0);

        long result = db.insert(Message.DB_TABLE, null, values);
        db.close();

        if (result == -1){
            Log.d("error", "saveMessage insertion failed");
            return false;
        } else {
            notifyObservers(message);
            return true;
        }
    }

    private void notifyObservers(Message message){
        if (observer != null) {
            observer.updateMessages(message);
        }
    }

    public ArrayList<Message> getMessages(Contact contact){
        Log.d("messages", "get messages, c not null?????");

        if(contact == null) return new ArrayList<Message>();
           Log.d("contact", "get messages, c not null");

        Log.d("contact", contact.getEmail());
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = Message.DB_COL_CONTACT + " = ?";
        String[] selectionArgs = new String[] {contact.getEmail()};
        Cursor cursor = db.query(Message.DB_TABLE, null, selection, selectionArgs, null, null, Message.DB_COL_DATE, null);

        ArrayList<Message> result = new ArrayList<Message>();

        while (cursor.moveToNext()){
            Message message = new Message();
            Log.d("messages", "message found");
            message.setMessageText(cursor.getString(cursor.getColumnIndexOrThrow(Message.DB_COL_MESSAGETEXT)));
            message.setContact( contact );
            Date date = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Message.DB_COL_DATE)));
            message.setDate(date);
            message.setIncoming(cursor.getInt(cursor.getColumnIndex(Message.DB_COL_INCOMING)) == 1 ? true : false);
            result.add(message);
        }
        cursor.close();
        db.close();

        return result;
    }

    public boolean saveContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            Log.d("error", "saveContact error fetching db");
            return false;
        }

        Cursor cursor = db.rawQuery("SELECT * from " + Contact.DB_TABLE + " where " +
                        Contact.DB_COL_EMAIL + " = '" + contact.getEmail() + "'", null);

        long result = 0;
        ContentValues values = new ContentValues();

        if (cursor.getCount() > 0){
            // update
            String strFilter = Contact.DB_COL_EMAIL + "= '" + contact.getEmail() +"'";
            values.put(Contact.DB_COL_FORENAME, contact.getForename());
            values.put(Contact.DB_COL_NAME, contact.getName());
            values.put(Contact.DB_COL_PICTURE, contact.getPicture_link());

            result = db.update(Contact.DB_TABLE, values, strFilter, null);

        } else {
            // insert
            //ContentValues values = new ContentValues();
            values.put(Contact.DB_COL_FORENAME, contact.getForename());
            values.put(Contact.DB_COL_NAME, contact.getName());
            values.put(Contact.DB_COL_EMAIL, contact.getEmail());
            values.put(Contact.DB_COL_PICTURE, contact.getPicture_link());

            result = db.insert(Contact.DB_TABLE, null, values);
        }

        cursor.close();
        db.close();

        if (result == -1){
          //db.update(Contact.DB_TABLE, null, values);
            Log.d("error", "saveContact insertion failed");
            return false;
      } else
          return true;
    }

    public ArrayList<Contact> getContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + Contact.DB_TABLE, null);

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        while (cursor.moveToNext()){
            Contact contact = new Contact();
            contact.setForename( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_FORENAME )));
            contact.setName( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_NAME )));
            contact.setEmail( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_EMAIL )));
            contact.setPicture_link( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_PICTURE )));
            contacts.add(contact);
        }
        cursor.close();
        db.close();
        return contacts;
    }

    public Contact getContact(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + Contact.DB_TABLE +
                         " where " + Contact.DB_COL_EMAIL +"='" + email +"'", null);

        Contact contact = null;

        if (cursor.moveToFirst()) {
            contact = new Contact();
            contact.setForename(cursor.getString(cursor.getColumnIndex(Contact.DB_COL_FORENAME)));
            contact.setName( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_NAME )));
            contact.setEmail( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_EMAIL )));
            contact.setPicture_link( cursor.getString( cursor.getColumnIndex(Contact.DB_COL_PICTURE )));
        }

        cursor.close();
        db.close();
        return contact;
    }
}
