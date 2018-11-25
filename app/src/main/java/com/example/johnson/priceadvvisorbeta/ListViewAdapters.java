package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.example.johnson.priceadvvisorbeta.Constants.FIRST_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.FOURTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.SECOND_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.THIRD_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johnson on 13-May-16.
 */
public class ListViewAdapters extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;
    //TextView txtFourth;
    public ListViewAdapters(Activity activity,ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.activity_users_list , null);

            txtFirst=(TextView) convertView.findViewById(R.id.idU);
            txtSecond=(TextView) convertView.findViewById(R.id.nameU);
            txtThird=(TextView) convertView.findViewById(R.id.emailU);
            //txtFourth=(TextView) convertView.findViewById(R.id.phoneU);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        //txtFourth.setText(map.get(FOURTH_COLUMN));

        return convertView;
    }

}