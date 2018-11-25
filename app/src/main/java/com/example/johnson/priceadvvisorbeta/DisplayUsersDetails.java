package com.example.johnson.priceadvvisorbeta;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
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
import android.widget.TextView;
import android.widget.Toast;

import static com.example.johnson.priceadvvisorbeta.R.*;

/**
 * Created by johnson on 14-May-16.
 */



public class DisplayUsersDetails extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DatabaseHelper mydb;

    TextView idE;
    TextView nameE;
    TextView phoneE;
    TextView emailE;
    TextView passwordE;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.user_details);

        Toolbar toolbar = (Toolbar) findViewById(id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idE = (TextView) findViewById(id.uidE);
        nameE = (TextView) findViewById(id.unameE);
        phoneE = (TextView) findViewById(id.uphoneE);
        emailE = (TextView) findViewById(id.uemailE);
        passwordE = (TextView) findViewById(id.upasswordE);


        mydb = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getDataUsers(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String idDb = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String nameDb = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                String pnoneDb = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_PHONE));
                String emailDb = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                String passwordDb = rs.getString(rs.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(id.button8);
                b.setVisibility(View.INVISIBLE);

                idE.setText(idDb);
                idE.setFocusable(false);
                idE.setClickable(false);
               // idE.setBackgroundResource(color.white);
                //idE.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                nameE.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                nameE.setText(nameDb);
                nameE.setFocusable(false);
                nameE.setClickable(false);
                nameE.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                phoneE.setText(pnoneDb);
                phoneE.setFocusable(false);
                phoneE.setClickable(false);
                phoneE.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                emailE.setText(emailDb);
                emailE.setFocusable(false);
                emailE.setClickable(false);
                emailE.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

                passwordE.setText(passwordDb);
                passwordE.setFocusable(false);
                passwordE.setClickable(false);
                passwordE.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);


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
                getMenuInflater().inflate(R.menu.display_product, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case id.Edit_Contact:
                Button b = (Button) findViewById(id.button8);
                b.setVisibility(View.VISIBLE);
                idE.setEnabled(true);
                idE.setFocusableInTouchMode(true);
                idE.setClickable(true);
                idE.getBackground().clearColorFilter();


                nameE.setEnabled(true);
                nameE.setFocusableInTouchMode(true);
                nameE.setClickable(true);
                nameE.getBackground().clearColorFilter();

                phoneE.setEnabled(true);
                phoneE.setFocusableInTouchMode(true);
                phoneE.setClickable(true);
                phoneE.getBackground().clearColorFilter();

                emailE.setEnabled(true);
                emailE.setFocusableInTouchMode(true);
                emailE.setClickable(true);
                emailE.getBackground().clearColorFilter();

                passwordE.setEnabled(true);
                passwordE.setFocusableInTouchMode(true);
                passwordE.setClickable(true);
                passwordE.getBackground().clearColorFilter();


                return true;
            case id.Delete_Contact:


                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(string.deleteContact)
                        .setPositiveButton(string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {


                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Bundle tab = new Bundle();
                                tab.putInt("tab", 0);
                                intent.putExtras(tab);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton(string.no, new DialogInterface.OnClickListener() {
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


            idE = (TextView) findViewById(id.uidE);
            nameE = (TextView) findViewById(id.unameE);
            phoneE = (TextView) findViewById(id.uphoneE);
            emailE = (TextView) findViewById(id.uemailE);
            passwordE = (TextView) findViewById(id.upasswordE);


            boolean cancel = false;
            View focusView = null;
            idE.setError(null);
            nameE.setError(null);
            phoneE.setError(null);
            emailE.setError(null);
            passwordE.setError(null);



            //validation
            if(passwordE.length()<=0){
                passwordE.setError("Field required");
                focusView = passwordE;
                cancel = true;
            }
            if(emailE.length()<=0){
                emailE.setError("Field required");
                focusView = emailE;
                cancel = true;
            }

            if(phoneE.length()<=0){
                phoneE.setError("Field required");
                focusView = phoneE;
                cancel = true;
            }

            if(nameE.length()<=0){
                nameE.setError("Field required");
                focusView = nameE;
                cancel = true;
            }

            if(idE.length()<=0){
                idE.setError("Field required");
                focusView = idE;
                cancel = true;
            }



            int Value = extras.getInt("id");
            if (Value > 0) {
                if (cancel) {
                    focusView.requestFocus();

                }else{

                    if (mydb.updateUsers(Integer.parseInt(idE.getText().toString()),
                            nameE.getText().toString(), phoneE.getText().toString(), emailE.getText().toString(),
                            passwordE.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                        //notification

                        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder mBuilder =
                                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                                        .setSmallIcon(mipmap.ic_launcher)
                                        .setContentTitle("User Details Update")
                                        .setContentText(idE.getText() + " " + nameE.getText() + " " + phoneE.getText() + " " +
                                                "@ " + emailE.getText() + " " + passwordE.getText())
                                        .setSound(soundUri);
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(this, DisplayUsersDetails.class);

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
                        mNotificationManager.notify(Integer.parseInt(idE.getText().toString()), mBuilder.build());
                        Bundle tab = new Bundle();
                        tab.putInt("tab", 0);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtras(tab);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (cancel) {
                    focusView.requestFocus();

                }else{

                    if (mydb.insertUserDetails(nameE.getText().toString(), phoneE.getText().toString(), emailE.getText().toString(),
                            passwordE.getText().toString())) {
                        Bundle tab = new Bundle();
                        tab.putInt("tab", 0);
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtras(tab);
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



