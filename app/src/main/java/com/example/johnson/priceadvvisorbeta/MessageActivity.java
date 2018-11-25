package com.example.johnson.priceadvvisorbeta;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    TextView msg;
    public SharedPreference sharedPreference;
    private String text;
    Activity context = this;
    Button bt;
    ListView lv;
    ComplainsSection mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

//restricting access to send message secti
        sharedPreference = new SharedPreference();
        text = sharedPreference.getValue(context);
        TextView tlt;


        if(text==null){
            bt = (Button)findViewById(R.id.btnsavemsg1);
            bt.setVisibility(View.INVISIBLE);
            bt.setHeight(0);
            msg = (TextView) findViewById(R.id.editTextWatchdog);
            msg.setVisibility(View.INVISIBLE);
            msg.setHeight(0);
            tlt = (TextView) findViewById(R.id.titleBody);
            tlt.setText("Login to Send a Message");


        }else {
            bt = (Button)findViewById(R.id.btnsavemsg1);
            bt.setVisibility(View.VISIBLE);
            msg = (TextView) findViewById(R.id.editTextWatchdog);
            msg.setVisibility(View.VISIBLE);
            tlt = (TextView) findViewById(R.id.titleBody);
            tlt.setText("Message Contents");


        }




        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msg = (TextView) findViewById(R.id.editTextWatchdog);
        bt = (Button)findViewById(R.id.btnsavemsg1);

        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String ds = msg.getText().toString();
                // convert bitmap to byte



                boolean error = false;
                View focusView = null;
                msg.setError(null);


                // password validation
                if (TextUtils.isEmpty(ds)) {
                    msg.setError(getString(R.string.error_field_required));
                    focusView = msg;
                    error = true;
                }
                //posting to dabb
                if (error) {
                    focusView.requestFocus();
                } else {
                    mydb = new ComplainsSection(getApplicationContext());

                   if(mydb.insertUserDetails(ds, text)){
                        Toast msg = Toast.makeText(MessageActivity.this, "Message sent", Toast.LENGTH_SHORT);
                        startActivity(new Intent(MessageActivity.this, MessageActivity.class));
                        msg.show();
                    }
                }
            }
        });


        //list view
        DatabaseHelper c =new DatabaseHelper(MessageActivity.this);
        String k = c.getprevilage(text);
        if(text!=null) {

            if (Integer.parseInt(k) == 1) {
                mydb = new ComplainsSection(getApplication());
                ArrayList array_list = mydb.getComplains();
                ListView listView = (ListView) findViewById(R.id.listViewmsg);
                ComplainListAdapter adapter = new ComplainListAdapter(this, array_list);
                listView.setAdapter(adapter);
            } else {
                mydb = new ComplainsSection(getApplication());
                ArrayList array_list = mydb.getComplainsSpecific(text);
                ListView listView = (ListView) findViewById(R.id.listViewmsg);
                ComplainListAdapter adapter = new ComplainListAdapter(this, array_list);
                listView.setAdapter(adapter);

            }
        }else{

        }
        //PriceListAdapter arrayAdapter = new PriceListAdapter((Activity) getContext(), array_list);
        // ArrayList array_list = databaseHelper.getUserDetails();



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }


        return super.onOptionsItemSelected(item);
    }

}
