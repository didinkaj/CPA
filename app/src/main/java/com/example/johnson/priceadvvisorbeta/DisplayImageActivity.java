package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 07-May-16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;

public class DisplayImageActivity extends AppCompatActivity {
    Button btnDelete;
    ImageView imageDetail;
    int imageId;
    Bitmap theImage;
    TextView textView;
    String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**
         * getting intent data from search and previous screen
         */
        btnDelete = (Button) findViewById(R.id.btnDelete);
        imageDetail = (ImageView) findViewById(R.id.imageView1);
        textView = (TextView) findViewById(R.id.editTextWatchdognews);
        //user previlafes
        SharedPreference sharedPreference = new SharedPreference();
        String text = sharedPreference.getValue(DisplayImageActivity.this);

        //setting visibility to various menu items to admin
        DatabaseHelper c =new DatabaseHelper(DisplayImageActivity.this);
        String k = c.getprevilage(text);
        if(text==null){
            btnDelete.setVisibility(View.INVISIBLE);

        }else {
            if (Integer.parseInt(k) == 1) {
                btnDelete.setVisibility(View.VISIBLE);

            } else {
                btnDelete.setVisibility(View.INVISIBLE);

            }
        }


        Intent intnt = getIntent();
        theImage = intnt.getParcelableExtra("imagename");
        imageId = intnt.getIntExtra("imageid", 20);
        title = intnt.getStringExtra("name");
        Log.d("Image ID:****", String.valueOf(imageId));
        Log.d("Image text:****", String.valueOf(title));
        imageDetail.setImageBitmap(theImage);
        textView.setText(title);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayImageActivity.this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            DataBaseHandlerImage dba = new DataBaseHandlerImage(DisplayImageActivity.this);
                            public void onClick(DialogInterface dialog, int id) {
                                dba.deleteContact(new Contact(imageId));
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle tab = new Bundle();
                                tab.putInt("tab", 2);
                                intent.putExtras(tab);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                /**
                 * create DatabaseHandler object
                 */
                DataBaseHandlerImage db = new DataBaseHandlerImage(DisplayImageActivity.this);
                /**
                 * Deleting records from database
                 */
                Log.d("Delete Image: ", "Deleting.....");
                db.deleteContact(new Contact(imageId));
                // /after deleting data go to main page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle tab = new Bundle();
                tab.putInt("tab", 2);
                intent.putExtras(tab);
                startActivity(intent);
                finish();
            }
        });

    }

}
