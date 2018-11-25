package com.example.johnson.priceadvvisorbeta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by johnson on 19-May-16.
 */





public class ForgotPassword extends AppCompatActivity {
    public  TextView email;
    DatabaseHelper db;
    Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         email = (TextView) findViewById(R.id.emailpc);

        email.setError(null);
         reset = (Button) findViewById(R.id.passret);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                if(v==reset){
                   // reset.setBackgroundColor(Color.parseColor("#F7CA18"));
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError(getString(R.string.error_field_required));
                    focusView = email;
                    cancel = true;
                } else {
                    if (!isEmailValid(email.getText().toString())) {
                        email.setError(getString(R.string.error_invalid_email));
                        focusView = email;
                        cancel = true;
                    }
                }
                if(cancel){
                    focusView.requestFocus();

                }else {
                    String emailtx = email.getText().toString();
                    db = new DatabaseHelper(getApplicationContext());
                   String emaildb = db.getPassword(emailtx);

                    if(emaildb==null){
                        Toast passt = Toast.makeText(ForgotPassword.this, "Account doesnot exist", Toast.LENGTH_SHORT);
                        TextView pass = (TextView) findViewById(R.id.pres);
                        pass.setVisibility(View.VISIBLE);
                        pass.setText("Email not Registered " );
                        passt.show();

                    }else {
                        TextView pass = (TextView) findViewById(R.id.pres);
                        pass.setVisibility(View.VISIBLE);
                        pass.setText("Your password is " + emaildb);
                        //Toast passt = Toast.makeText(ForgotPassword.this, "Login sucessful", Toast.LENGTH_SHORT);
                        // passt.show();
                    }
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

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

}

