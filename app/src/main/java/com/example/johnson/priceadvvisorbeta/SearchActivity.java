package com.example.johnson.priceadvvisorbeta;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    TextView sr;
    Button btsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sr = (TextView) findViewById(R.id.editTextsearch);
        btsr = (Button)findViewById(R.id.btnsavemsrch);

        btsr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String ds = sr.getText().toString();
                // convert bitmap to byte


                boolean error = false;
                View focusView = null;
                sr.setError(null);


                // password validation
                if (TextUtils.isEmpty(ds)) {
                    sr.setError(getString(R.string.error_field_required));
                    focusView = sr;
                    error = true;
                }
                //posting to dabb
                if (error) {
                    focusView.requestFocus();
                } else {
                    TextView srs = (TextView) findViewById(R.id.sres);
                    srs.setVisibility(View.VISIBLE);
                    srs.setText("No record was found matching your search "+ds+"");


                    //Toast msg = Toast.makeText(SearchActivity.this, ds, Toast.LENGTH_SHORT);
                   // msg.show();
                }
            }
        });


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
