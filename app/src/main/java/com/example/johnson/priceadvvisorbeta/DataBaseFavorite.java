package com.example.johnson.priceadvvisorbeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johnson on 24-May-16.
 */


public class DataBaseFavorite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite.db";
    public static final String CONTACTS_TABLE_NAME = "favorite";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_UNAME = "uname";
    public static final String CONTACTS_COLUMN_PRODUCTID = "productid";
    public static final String CONTACTS_COLUMN_PRODUCT_QUANTITY = "quantity";
    public static final String CONTACTS_COLUMN_PRODUCT_UNIT = "unit";
    public static final String CONTACTS_COLUMN_PRODUCT_NAME = "productname";
    public static final String CONTACTS_COLUMN_PRODUCT_MINPRICE = "minprice";
    public static final String CONTACTS_COLUMN_PRODUCT_MAXPRICE = "maxprice";
    public static final String CONTACTS_COLUMN_PRODUCT_CURRENCY = "currency";
    private HashMap hp;
    SQLiteDatabase db;

    public DataBaseFavorite(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table favorite " +
                        "(id integer primary key not null,  uname text, productid integer,quantity integer,unit text, productname text, minprice integer, maxprice integer, currency text)"
        );
        this.db = db;
        //dummy data
        insertProductDefault(1, "judy@gmail.com", 1, 2, "kg", "sugar", 90, 100, "Ksh");
        insertProductDefault(1, "judy@gmail.com", 1, 2, "kg", "sugar", 90, 100, "Ksh");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS'" + CONTACTS_TABLE_NAME + "'");
        onCreate(db);
        //dummy data


    }

    // TODO auto generated method stub
    public boolean insertFavorite(String uname, Integer productid, Integer quantity, String unit, String productname, Integer minprice, Integer maxprice, String currency) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.query(CONTACTS_TABLE_NAME, new String[]{"MAX(id) AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0) + 1;
        contentValues.put(CONTACTS_COLUMN_ID, count);
        contentValues.put(CONTACTS_COLUMN_UNAME, uname);
        contentValues.put(CONTACTS_COLUMN_PRODUCTID, productid);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_UNIT, unit);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_NAME, productname);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MINPRICE, minprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MAXPRICE, maxprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_CURRENCY, currency);
        db.insert("favorite", null, contentValues);
        return true;
    }

    public int numberOfRowsProducts(String elm) {
        db = this.getReadableDatabase();
        String query = "select * from favorite  WHERE uname='" + elm + "'";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }

    public Cursor getData(int id) {
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from favorite where id=" + id + "", null);
        return res;
    }

    public Integer deleteFavoriteAdmin(Integer id) {
        db = this.getWritableDatabase();
        return db.delete("favorite",
                "productid = ? ",
                new String[]{Integer.toString(id)});
    }


    public Integer deleteFavorite(Integer id) {
        db = this.getWritableDatabase();
        return db.delete("favorite",
                "productid = ? ",
                new String[]{Integer.toString(id)});
    }


    //get products detailsInteger id, Integer quantity, String unit, String Name, Integer MinPrice,Integer MaxPrice, String Currency
    public ArrayList<HashMap<String, String>> getAllFavorite(String elm) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM favorite WHERE uname='" + elm + "'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put(CONTACTS_COLUMN_ID, cursor.getString(0));
                map.put(CONTACTS_COLUMN_UNAME, cursor.getString(1));
                map.put(CONTACTS_COLUMN_PRODUCTID, cursor.getString(2));
                map.put(CONTACTS_COLUMN_PRODUCT_QUANTITY, cursor.getString(3));
                map.put(CONTACTS_COLUMN_PRODUCT_UNIT, cursor.getString(4));
                map.put(CONTACTS_COLUMN_PRODUCT_NAME, cursor.getString(5) + " " + "@ ");
                map.put(CONTACTS_COLUMN_PRODUCT_MINPRICE, cursor.getString(6) + " " + " - ");
                map.put(CONTACTS_COLUMN_PRODUCT_MAXPRICE, cursor.getString(7));
                map.put(CONTACTS_COLUMN_PRODUCT_CURRENCY, cursor.getString(8));
                wordList.add(map);
            } while (cursor.moveToNext());
        }


        return wordList;
    }

    //sum of favorite min
    public Integer getAllFavoriteSum(String elm) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT sum(minprice) FROM favorite WHERE uname='" + elm + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        // int count = cursor.getCount();
        //Cursor cursor = db.query(CONTACTS_TABLE_NAME,new String[]{"SUM(minprice)"},null,null,null,null,null);
        cursor.moveToFirst();

        int sum = cursor.getInt(0);
        return sum;

    }

    //sum of favorite max
    public Integer getAllFavoriteSumMax(String elm) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT sum(maxprice) FROM favorite WHERE uname='" + elm + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        // int count = cursor.getCount();
        //Cursor cursor = db.query(CONTACTS_TABLE_NAME,new String[]{"SUM(minprice)"},null,null,null,null,null);
        cursor.moveToFirst();

        int sum = cursor.getInt(0);
        return sum;

    }


    public void insertProductDefault(Integer id, String uname, Integer productid, Integer quantity, String unit, String productname, Integer minprice, Integer maxprice, String currency) {
//         db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_ID, id);
        contentValues.put(CONTACTS_COLUMN_UNAME, uname);
        contentValues.put(CONTACTS_COLUMN_PRODUCTID, productid);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_UNIT, unit);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_NAME, productname);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MINPRICE, minprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MAXPRICE, maxprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_CURRENCY, currency);
        db.insert("favorite", null, contentValues);
    }

    //updating user details
    public boolean updateProducts(Integer productid, Integer quantity, String unit, String productname, Integer minprice, Integer maxprice, String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_PRODUCTID, productid);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_UNIT, unit);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_NAME, productname);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MINPRICE, minprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_MAXPRICE, maxprice);
        contentValues.put(CONTACTS_COLUMN_PRODUCT_CURRENCY, currency);
        db.update("favorite", contentValues, "productid = ? ", new String[]{Integer.toString(productid)});
        return true;
    }

    //checks if already added to favorites
    public String dbCheckFavorite(Integer pid, String uname) {
        db = this.getReadableDatabase();
        String query = "select productid,uname from '" + CONTACTS_TABLE_NAME + "' WHERE productid ='" + pid + "'";
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(1);
                if (a.equals(uname)) {
                    b = cursor.getString(0);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return b;

    }

}
