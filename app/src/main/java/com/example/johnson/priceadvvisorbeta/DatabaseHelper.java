package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 01-May-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by johnson on 29-Mar-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "consumers.db";
    public static final String TABLE_NAME = "consumers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PREVILAGE = "previlage";
    public SharedPreference sharedPreference;
    private String text;

    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table consumers (id integer primary key not null," +
            "name text not null,phone text not null,email text not null,password text not null,previlage text not null)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
        // insertUsersDefault(1, "Johnson", "0712328250", "didinkaj@gmail.com", "didinkaj@gmail.com");
        insertUsersDefault(1, "Johnson", "0712328250", "didinkaj@gmail.com", "didinkaj@gmail.com", "1");
        insertUsersDefault(2, "Jackline", "0712328251", "jacklyne@gmail.com", "jacklyne@gmail.com", "0");
        insertUsersDefault(3, "Judy", "0712328281", "judy@gmail.com", "judy@gmail.com", "0");
        insertUsersDefault(4, "Peter", "0712328451", "peter@gmail.com", "peter@gmail.com", "0");
        insertUsersDefault(5, "Eunice", "0712321251", "eunic@gmail.com", "eunic@gmail.com", "0");
        insertUsersDefault(6, "Mary", "0712328451", "mary@gmail.com", "mary@gmail.com", "0");
        insertUsersDefault(7, "Paul", "0712321251", "paul@gmail.com", "paul@gmail.com", "0");
    }

    public Cursor getDataUsers(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from consumers where id=" + id + "", null);
        return res;
    }


    public int getDataUsersm() {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //String query = "select max(id) from consumers";
        Cursor cursor = db.query(TABLE_NAME, new String[]{"MAX(id) AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public boolean insertUserDetails(String name, String phone, String email, String pass) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // String query = "select max(id) from consumers";
        Cursor cursor = db.query(TABLE_NAME, new String[]{"MAX(id) AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0) + 1;

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pass);
        values.put(COLUMN_PREVILAGE, "0");
        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }


    //register new user
    public void insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"MAX(id) AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0) + 1;

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_PHONE, c.getPhone());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_PREVILAGE, "0");
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //verify if the entered password and email exist in the database
    public String searchPass(String emailDb) {
        db = this.getReadableDatabase();
        String query = "select email,password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "Not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                // b = cursor.g(1);
                if (a.equals(emailDb)) {
                    b = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return b;

    }

    //checks if email already exist in the database
    public String dbCheck(String str) {
        db = this.getReadableDatabase();
        String query = "select email from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(str)) {
                    b = cursor.getString(0);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return b;

    }

    //checks if email already exist in the database
    public String dbCheckPhone(String str) {
        db = this.getReadableDatabase();
        String query = "select phone from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(str)) {
                    b = cursor.getString(0);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return b;

    }


    //get user data
    public String[] getAppCategoryDetail(String eml) {

        // final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE email='" + eml + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data[] = null;

        if (cursor.moveToFirst()) {
            do {
                data = new String[]{cursor.getString(1), cursor.getString(2)};
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    //password reset
    // final String TABLE_NAME = "name of table";

    public String getPassword(String emls) {

        // final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  password FROM " + TABLE_NAME + " WHERE email='" + emls + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = null;
        String a;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(emls)) {
                    data = cursor.getString(0);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return data;
    }
    //
    // get user previlage

    public String getprevilage(String emls) {

        // final String TABLE_NAME = "name of table";

        String selectQuery = "SELECT  email,previlage FROM " + TABLE_NAME + " WHERE email='" + emls + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String data = null;
        String a;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(emls)) {
                    data = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return data;
    }

    public ArrayList<HashMap<String, String>> getUserDetails() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM consumers";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                //Contact contact = new Contact();
                //contact.setIDu(Integer.parseInt(cursor.getString(0)));
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                // map.put("phone", cursor.getString(2));
                map.put("email", cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }


        return wordList;
    }

    public int numberOfRowsUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from consumers";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);/*
        insertUsersDefault(0, "Johnson", "0712328250", "didinkaj@gmail.com","didinkaj@gmail.com" );
        insertUsersDefault(1, "Jackline", "0712328251", "jacklyne@gmail.com","jacklyne@gmail.com" );
        insertUsersDefault(2, "Judy", "0712328281", "judy@gmail.com","judy@gmail.com" );
        insertUsersDefault(3, "Peter", "0712328451", "peter@gmail.com", "peter@gmail.com");
        insertUsersDefault(4, "Eunice", "0712321251", "eunic@gmail.com", "eunic@gmail.com");*/
    }


    public void insertUsersDefault(Integer countD, String nameD, String phoneD, String emailD, String passwordD, String privilage) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, countD);
        values.put(COLUMN_NAME, nameD);
        values.put(COLUMN_PHONE, phoneD);
        values.put(COLUMN_EMAIL, emailD);
        values.put(COLUMN_PASSWORD, passwordD);
        values.put(COLUMN_PREVILAGE, privilage);
        db.insert("consumers", null, values);
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("consumers",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean updateUsers(Integer id, String name, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("previlage", "0");
        db.update("consumers", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }
}
