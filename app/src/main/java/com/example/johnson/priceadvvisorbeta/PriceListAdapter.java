package com.example.johnson.priceadvvisorbeta;

/**
 * Created by johnson on 14-May-16.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.johnson.priceadvvisorbeta.Constants.FIRTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.SIXTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.SEVENTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.EIGTH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.NINETH_COLUMN;
import static com.example.johnson.priceadvvisorbeta.Constants.TENTH_COLUMN;




/**
 * Created by johnson on 13-May-16.
 */
public class PriceListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirth;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    TextView txt9;
    TextView txt10;
    public PriceListAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
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

            convertView=inflater.inflate(R.layout.activity_price_list , null);

            txtFirth=(TextView) convertView.findViewById(R.id.quantityP);
            txt6=(TextView) convertView.findViewById(R.id.unitP);
            txt7=(TextView) convertView.findViewById(R.id.nameP);
            txt8=(TextView) convertView.findViewById(R.id.minpriceP);
            txt9=(TextView) convertView.findViewById(R.id.maxpriceP);
            txt10=(TextView) convertView.findViewById(R.id.currencyP);

        }

        HashMap<String, String> map=list.get(position);
        txtFirth.setText(map.get(FIRTH_COLUMN));
        txt6.setText(map.get(SIXTH_COLUMN));
        txt7.setText(map.get(SEVENTH_COLUMN));
        txt8.setText(map.get(EIGTH_COLUMN));
        txt9.setText(map.get(NINETH_COLUMN));
        txt10.setText(map.get(TENTH_COLUMN));

        return convertView;
    }

}