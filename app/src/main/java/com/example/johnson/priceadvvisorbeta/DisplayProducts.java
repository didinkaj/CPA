package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 01-May-16.
 */

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayProducts extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;
    private String text;

    TextView quantity;
    Button imageView;
    TextView unit;
    TextView name;
    TextView minPrice;
    TextView maxPrice;
    TextView currency;
    Button buttonn;
    int id_To_Update = 0;
    DataBaseFavorite dataBaseFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataBaseFavorite = new DataBaseFavorite(getApplicationContext());

        quantity = (TextView) findViewById(R.id.editTextQuantity);
        unit = (TextView) findViewById(R.id.editTextUnit);
        name = (TextView) findViewById(R.id.editTextName);
        minPrice = (TextView) findViewById(R.id.editTextMinPrice);
        maxPrice = (TextView) findViewById(R.id.editTextMaxPrice);
        currency = (TextView) findViewById(R.id.editTextCurrency);
        imageView = (Button) findViewById(R.id.imageButtonlk);
        buttonn = (Button) findViewById(R.id.rmf);



        final Bundle extras = getIntent().getExtras();
        final int Valuea = extras.getInt("id");

        SharedPreference sharedPreference = new SharedPreference();
        text = sharedPreference.getValue(DisplayProducts.this);

        //setting visibility to various menu items to admin
        DatabaseHelper c =new DatabaseHelper(DisplayProducts.this);
        String k = c.getprevilage(text);
        if(text==null){
            imageView.setVisibility(View.INVISIBLE);
            buttonn.setVisibility(View.INVISIBLE);

        }else {
            if(Integer.parseInt(k)==1) {
                imageView.setVisibility(View.INVISIBLE);
                buttonn.setVisibility(View.INVISIBLE);

            }else {

                //checks wether the user has alreadey added a product to favorite
                String favariteflag = dataBaseFavorite.dbCheckFavorite(Valuea, text);
                if(favariteflag== "not found"){
                    imageView.setVisibility(View.VISIBLE);
                    buttonn.setVisibility(View.INVISIBLE);
                }else {
                    if (Integer.parseInt(favariteflag) == Valuea) {
                        // Toast.makeText(getApplicationContext(), "favorite exist", Toast.LENGTH_SHORT).show();
                        imageView.setVisibility(View.INVISIBLE);
                        buttonn.setVisibility(View.VISIBLE);

                    } else {
                        // Toast.makeText(getApplicationContext(), "favorite not exist" + Valuea + favariteflag, Toast.LENGTH_SHORT).show();
                        imageView.setVisibility(View.VISIBLE);
                        buttonn.setVisibility(View.INVISIBLE);
                    }
                }

            }
        }



        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Valuef = extras.getInt("idf");
                dataBaseFavorite.deleteFavorite(Valuef);
                int Value = extras.getInt("id");
               dataBaseFavorite.deleteFavorite(Value);
                Toast.makeText(getApplicationContext(), "Removed from favorite", Toast.LENGTH_SHORT).show();

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", Valuea);
                Intent intent = new Intent(getApplicationContext(), DisplayProducts.class);
                intent.putExtras(dataBundle);
                startActivity(intent);


            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast msg = Toast.makeText(DisplayProducts.this, "Added to favorites", Toast.LENGTH_SHORT);
                msg.show();
                SharedPreference sharedPreference = new SharedPreference();
                text = sharedPreference.getValue(DisplayProducts.this);
                Bundle extras = getIntent().getExtras();
                int Value = extras.getInt("id");
                dataBaseFavorite.insertFavorite(text, Value, Integer.parseInt(quantity.getText().toString()),unit.getText().toString(), name.getText().toString(), Integer.parseInt(minPrice.getText().toString()), Integer.parseInt(maxPrice.getText().toString()),currency.getText().toString());

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", Value);
                Intent intent = new Intent(getApplicationContext(), DisplayProducts.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        mydb = new DBHelper(this);

       // Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String quantitydb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_QUANTITY));
                String unitdb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_UNIT));
                String namedb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String minPricedb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_MINPRICE));
                String maxPricedb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_MAXPRICE));
                String currencydb = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CURRRENCY));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                quantity.setText(quantitydb);
                quantity.setFocusable(false);
                quantity.setClickable(false);
                quantity.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                unit.setText(unitdb);
                unit.setFocusable(false);
                unit.setClickable(false);
                unit.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                name.setText(namedb);
                name.setFocusable(false);
                name.setClickable(false);
                name.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                minPrice.setText(minPricedb);
                minPrice.setFocusable(false);
                minPrice.setClickable(false);
                minPrice.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                maxPrice.setText(maxPricedb);
                maxPrice.setFocusable(false);
                maxPrice.setClickable(false);
                maxPrice.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                currency.setText(currencydb);
                currency.setFocusable(false);
                currency.setClickable(false);
                currency.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                DatabaseHelper c =new DatabaseHelper(DisplayProducts.this);
                if(text!=null) {
                    String k = c.getprevilage(text);
                    if (Integer.parseInt(k) == 1) {
                        getMenuInflater().inflate(R.menu.display_product, menu);
                    }
                }
            } else {
                //getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button) findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                quantity.setEnabled(true);
                quantity.setFocusableInTouchMode(true);
                quantity.setClickable(true);
                //quantity.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
                quantity.getBackground().clearColorFilter();


                unit.setEnabled(true);
                unit.setFocusableInTouchMode(true);
                unit.setClickable(true);
                unit.getBackground().clearColorFilter();

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);
                name.getBackground().clearColorFilter();

                minPrice.setEnabled(true);
                minPrice.setFocusableInTouchMode(true);
                minPrice.setClickable(true);
                minPrice.getBackground().clearColorFilter();

                maxPrice.setEnabled(true);
                maxPrice.setFocusableInTouchMode(true);
                maxPrice.setClickable(true);
                maxPrice.getBackground().clearColorFilter();

                currency.setEnabled(true);
                currency.setFocusableInTouchMode(true);
                currency.setClickable(true);
                currency.getBackground().clearColorFilter();

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                dataBaseFavorite.deleteFavoriteAdmin(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Delete Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void run(View view) {


        Bundle extras = getIntent().getExtras();
        if (extras != null) {


            quantity = (TextView) findViewById(R.id.editTextQuantity);
            unit = (TextView) findViewById(R.id.editTextUnit);
            name = (TextView) findViewById(R.id.editTextName);
            minPrice = (TextView) findViewById(R.id.editTextMinPrice);
            maxPrice = (TextView) findViewById(R.id.editTextMaxPrice);
            currency = (TextView) findViewById(R.id.editTextCurrency);


            boolean cancel = false;
            View focusView = null;
            quantity.setError(null);
            unit.setError(null);
            name.setError(null);
            minPrice.setError(null);
            maxPrice.setError(null);
            currency.setError(null);



            //validation
            if(currency.length()<=0){
                currency.setError("Field required");
                focusView = currency;
                cancel = true;
            }
            if(maxPrice.length()<=0){
                maxPrice.setError("Field required");
                focusView = maxPrice;
                cancel = true;
            }

            if(minPrice.length()<=0){
                minPrice.setError("Field required");
                focusView = minPrice;
                cancel = true;
            }

            if(name.length()<=0){
                name.setError("Field required");
                focusView = name;
                cancel = true;
            }

            if(unit.length()<=0){
                unit.setError("Field required");
                focusView = unit;
                cancel = true;
            }
            if(quantity.length()<=0){
                quantity.setError(getString(R.string.error_invalid_quantity));
                focusView = quantity;
                cancel = true;
            }


            int Value = extras.getInt("id");
            if (Value > 0) {
                if (cancel) {
                    focusView.requestFocus();

                }else{

                if (mydb.updateContact(id_To_Update, Integer.parseInt(quantity.getText().toString()),
                        unit.getText().toString(), name.getText().toString(), Integer.parseInt(minPrice.getText().toString()),
                        Integer.parseInt(maxPrice.getText().toString()), currency.getText().toString())) {
                    //update users favorite
                   if(dataBaseFavorite.updateProducts(id_To_Update, Integer.parseInt(quantity.getText().toString()),
                            unit.getText().toString(), name.getText().toString(), Integer.parseInt(minPrice.getText().toString()),
                            Integer.parseInt(maxPrice.getText().toString()), currency.getText().toString())){
                       //Toast.makeText(getApplicationContext(), "Updated all favorites", Toast.LENGTH_SHORT).show();
                   }
                    // update msq
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //notification

                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder mBuilder =
                            (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("Price Update")
                                    .setContentText(quantity.getText() + " " + unit.getText()+" "+name.getText()+" " +
                                            "@ "+currency.getText()+" "+minPrice.getText()+" - "+maxPrice.getText())
                                    .setSound(soundUri);
                    // Creates an explicit intent for an Activity in your app
                    Intent resultIntent = new Intent(this, DisplayProducts.class);

                    // The stack builder object will contain an artificial back stack for the
                    // started Activity.
                    // This ensures that navigating backward from the Activity leads out of
                    //  application to the Home screen.
                    TaskStackBuilder stackBuilder = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        stackBuilder = TaskStackBuilder.create(this);
                    }
                    // Adds the back stack for the Intent (but not the Intent itself)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        stackBuilder.addParentStack(DisplayProducts.class);
                    }
                    // Adds the Intent that starts the Activity to the top of the stack
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        stackBuilder.addNextIntent(resultIntent);
                    }
                    PendingIntent resultPendingIntent =
                            null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        resultPendingIntent = stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                    }
                    mBuilder.setContentIntent(resultPendingIntent);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(id_To_Update, mBuilder.build());
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            } else {
                if (cancel) {
                    focusView.requestFocus();

                }else{

                if (mydb.insertContact(Integer.parseInt(quantity.getText().toString()), unit.getText().toString(),
                        name.getText().toString(), Integer.parseInt(minPrice.getText().toString()), Integer.parseInt(maxPrice.getText().toString()), currency.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }

            }
            }
        }
    }


}


