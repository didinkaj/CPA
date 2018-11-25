package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.johnson.priceadvvisorbeta.Constants.FIRST_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.FOURTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.SECOND_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.THIRD_COLUMN;
/**
 * Created by johnson on 03-May-16.
 */
public class Profile extends Fragment implements View.OnClickListener {
    private TextView txtEmail;
    View view;
    private String text;
    private SharedPreference sharedPreference;
    DatabaseHelper databaseHelper;
    private ListView objdb;
    String txtPostaa;
    DatabaseHelper nodb;
    public ArrayList<HashMap<String, String>> lista;
    //Activity activity;
    private ArrayList<HashMap<String, String>> list;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my, container, false);
        //shared preference retrival
        sharedPreference = new SharedPreference();
        text = sharedPreference.getValue(this.getContext());
        txtEmail = (TextView) view.findViewById(R.id.txt_text);
        txtEmail.setText(text);


        //database values
        databaseHelper = new DatabaseHelper(getContext());
        String[] textRes = databaseHelper.getAppCategoryDetail(text); //this is the method to query
        TextView log1 = (TextView) view.findViewById(R.id.txt_text_name);
        log1.setText(textRes[0]);

        //phone number
        TextView log2 = (TextView) view.findViewById(R.id.phoneview);
        log2.setText(textRes[1]);
        //set number of users
        nodb = new DatabaseHelper(getContext());
       Integer users = nodb.numberOfRowsUsers();
        TextView no = (TextView) view.findViewById(R.id.titleU);
        no.setText("Registered Users" +" ("+String.valueOf(users)+")");
        //Toast.makeText(getContext(), Integer.toString(users) + " Clicked", Toast.LENGTH_SHORT).show();


        final ListView listView = (ListView)view.findViewById(R.id.listView2);

        final ArrayList array_list = databaseHelper.getUserDetails();


        ListViewAdapters adapter=new ListViewAdapters((Activity) getContext(), array_list);
        listView.setAdapter(adapter);

        //ArrayList<HashMap<String, String>> contacts = nodb.getUserDetails();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                   HashMap<String, Object> v;
               v = (HashMap<String, Object>) parent.getItemAtPosition(position);

                String in = (String) v.get("id");
                int c = Integer.parseInt(in);
                Log.d("mmmmm:-------- ", String.valueOf(in));

                int pos = position + 1;
                listView.getChildAt(0);
                Log.d("Result:-------- ", String.valueOf(parent.getItemAtPosition(position)));


                    Bundle dataBundle2 = new Bundle();
                    dataBundle2.putInt("id", c);

                    Intent intent = new Intent(getContext(), DisplayUsersDetails.class);

                    intent.putExtras(dataBundle2);
                    startActivity(intent);

            }

        });


        databaseHelper.close();
        return view;
    }

   // @Override
   public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.Logout:
                //
                sharedPreference.clearSharedPreference(this.getContext());
                Toast loginErr = Toast.makeText(getContext(), "Logout sucessful", Toast.LENGTH_SHORT);
                loginErr.show();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.default_activity_button:*/


        }
    }
}