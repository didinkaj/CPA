package com.example.johnson.priceadvvisorbeta;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class PriceSection extends Fragment {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;


    public PriceSection() {
        // Required empty public constructor
    }
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_price_section, container, false);


        mydb = new DBHelper(this.getContext());
        ArrayList array_list = mydb.getAllCotacts();
        //PriceListAdapter arrayAdapter = new PriceListAdapter((Activity) getContext(), array_list);
       // ArrayList array_list = databaseHelper.getUserDetails();

        ListView listView = (ListView)view.findViewById(R.id.listView1);
        PriceListAdapter adapter=new PriceListAdapter((Activity) getContext(), array_list);
        listView.setAdapter(adapter);
        //counting number of registered users


       obj = (ListView) view.findViewById(R.id.listView1);
        //obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                HashMap<String, Object> v;
                v = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                String in = (String) v.get("id");
                int c = Integer.parseInt(in);

                Log.d("Result prizeeeeee:-------- ", String.valueOf(arg0.getItemAtPosition(arg2)));


                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", c);

                Intent intent = new Intent(getContext(), DisplayProducts.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }

        });

        return view;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getContext(),DisplayProducts.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
