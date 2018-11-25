package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 01-May-16.
 */import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "products";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_QUANTITY = "quantity";
    public static final String CONTACTS_COLUMN_UNIT = "unit";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_MINPRICE = "minPrice";
    public static final String CONTACTS_COLUMN_MAXPRICE = "maxPrice";
    public static final String CONTACTS_COLUMN_CURRRENCY = "currency";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table products " +
                        "(id integer primary key not null, quantity integer,unit text,name text, minPrice integer,maxPrice integer,currency text)"
        );
        //dummy data
        insertContactDefault(1,"Kg", "Sugar", 90, 110, "Ksh", db);
        insertContactDefault(1,"Kg", "Rice", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Maize Floor", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Salt", 40, 50, "Ksh", db);
        insertContactDefault(2,"Kg", "Wheat Floor", 110, 130, "Ksh", db);
        insertContactDefault(1,"Roll", "Toilet Papar", 20, 30, "Ksh", db);
        insertContactDefault(2,"Kg", "Millet Floor", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Cooking Oil", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Tooth Paste", 90, 110, "Ksh", db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
       // insertContact("sugar", "sugar", "sugar", "sugar", "sugar");
        insertContactDefault(1, "Kg", "Sugar", 90, 110, "Ksh", db);
        insertContactDefault(1, "Kg", "Rice", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Maize Floor", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Salt", 40, 50, "Ksh", db);
        insertContactDefault(2,"Kg", "Wheat Floor", 110, 130, "Ksh", db);
        insertContactDefault(1, "Roll", "Toilet Papar", 20, 30, "Ksh", db);
        insertContactDefault(2, "Kg", "Millet Floor", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Cooking Oil", 90, 110, "Ksh", db);
        insertContactDefault(2,"Kg", "Tooth Paste", 90, 110, "Ksh", db);

    }

    public boolean insertContact  (Integer quantity, String unit, String Name, Integer MinPrice,Integer MaxPrice, String Currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", quantity);
        contentValues.put("unit", unit);
        contentValues.put("name", Name);
        contentValues.put("minPrice", MinPrice);
        contentValues.put("maxPrice", MaxPrice);
        contentValues.put("currency", Currency);
        db.insert("products", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from products where id="+id+"", null );
        return res;
    }



    public boolean updateContact (Integer id, Integer quantity, String unit, String Name, Integer MinPrice,Integer MaxPrice, String Currency )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", quantity);
        contentValues.put("unit", unit);
        contentValues.put("name", Name);
        contentValues.put("minPrice", MinPrice);
        contentValues.put("maxPrice", MaxPrice);
        contentValues.put("currency", Currency);
        db.update("products", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("products",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
/*
    public ArrayList<String> getAllCotacts() 
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from products ", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY))+" "+res.getString(res.getColumnIndex(CONTACTS_COLUMN_UNIT))+" "+res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))+" @ "+res.getString(res.getColumnIndex(CONTACTS_COLUMN_CURRRENCY))+" " +
                    " "+res.getString(res.getColumnIndex(CONTACTS_COLUMN_MINPRICE))+" - "+res.getString(res.getColumnIndex(CONTACTS_COLUMN_MAXPRICE))+"");
            //array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_CURRRENCY)));
            res.moveToNext();
        }

        return array_list;
    }*/

    //get products detailsInteger id, Integer quantity, String unit, String Name, Integer MinPrice,Integer MaxPrice, String Currency
    public ArrayList<HashMap<String, String>> getAllCotacts() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM products order by name asc";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("quantity", cursor.getString(1));
                map.put("unit", cursor.getString(2));
                map.put("name", cursor.getString(3)+" "+" "+ " @");
                map.put("minPrice", cursor.getString(4)+" "+" - ");
                map.put("maxPrice", cursor.getString(5));
                map.put("currency", cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }


        return wordList;
    }



    public class MyClass {
        private String d1;
        private String d2;
        private String d3;
    }
   public void insertContactDefault(Integer quantity, String unit, String name, Integer minPrice, Integer maxPrice,String currency,SQLiteDatabase db){

       ContentValues contentValues = new ContentValues();
       contentValues.put("quantity", quantity);
       contentValues.put("unit", unit);
       contentValues.put("name", name);
       contentValues.put("minPrice", minPrice);
       contentValues.put("maxPrice", maxPrice);
       contentValues.put("currency", currency);
       db.insert("products", null, contentValues);
    }
}
