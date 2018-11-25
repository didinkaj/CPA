package com.example.johnson.priceadvvisorbeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johnson on 17-May-16.
 */

/**
 * Created by johnson on 29-Mar-16.
 */
public class ComplainsSection extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Complain.db";
    public static final String TABLE_NAME = "complains";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MSGBODY = "Body";
    public static final String COLUMN_MSGFROM = "UserEmail";
    public SharedPreference sharedPreference;
    private String text;

    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table complains (id integer primary key not null," +
            "Body text not null,UserEmail text not null)";

    public ComplainsSection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
        // insertUsersDefault(1, "Johnson", "0712328250", "didinkaj@gmail.com", "didinkaj@gmail.com");
        inseremsg(1, "Consumers be warnerned unstandarized", "judy@gmail.com");
        inseremsg(2, "Consumers be warnerned unstandarized", "didinkaj@gmail.com");
        inseremsg(3, "Consumers be warnerned unstandarized", "didinkaj@gmail.com");
        inseremsg(4, "Consumers be warnerned unstandarized", "didinkaj@gmail.com");
        inseremsg(5, "Consumers be warnerned unstandarized", "didinkaj@gmail.com");
        inseremsg(6, "Consumers be warnerned unstandarized", "didinkaj@gmail.com");
    }

    public Cursor getDataUsers(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from complains where id="+id+"", null );
        return res;
    }


    public boolean insertUserDetails(String msgbody,String userfrom) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from complains";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount()+1;

        values.put(COLUMN_ID, count);
        values.put(COLUMN_MSGBODY, msgbody);
        values.put(COLUMN_MSGFROM, userfrom);
        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    //get user data
    public String[] getAppCategoryDetail(String eml) {

        // final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  * FROM "+TABLE_NAME+" WHERE email='"+ eml +"'";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String data[]      = null;

        if (cursor.moveToFirst()) {
            do {
                data = new String[]{cursor.getString(1),cursor.getString(2)};
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> getComplains() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM complains order by id desc";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //Contact contact = new Contact();
                //contact.setIDu(Integer.parseInt(cursor.getString(0)));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("msgbody", cursor.getString(1));
                map.put("useremail", cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }


        return wordList;
    }
    //get specif complain
    public ArrayList<HashMap<String, String>> getComplainsSpecific(String email) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM complains WHERE UserEmail='"+email+"' order by id desc";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String a;
        if (cursor.moveToFirst()) {

            do {
                a = cursor.getString(2);
                if (a.equals(email)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", cursor.getString(0));
                    map.put("msgbody", cursor.getString(1));
                    map.put("useremail", cursor.getString(2));
                    wordList.add(map);
                }

            } while (cursor.moveToNext());
        }


        return wordList;
    }





    public int numberOfRowsUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from complains";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }


    public void inseremsg(Integer countD, String msgD, String UserEmail){
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, countD);
        values.put(COLUMN_MSGBODY, msgD);
        values.put(COLUMN_MSGFROM, UserEmail);
        db.insert("complains", null, values);
    }

    public Integer deleteMessage (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("complains",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


}
