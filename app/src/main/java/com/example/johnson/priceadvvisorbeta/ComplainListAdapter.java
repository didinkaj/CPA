package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.johnson.priceadvvisorbeta.Constants.FIRTH_COLUMNm;
import static com.example.johnson.priceadvvisorbeta.Constants.SIXTH_COLUMNm;
import static com.example.johnson.priceadvvisorbeta.Constants.SEVENTH_COLUMNm;

/**
 * Created by johnson on 17-May-16.
 */



/**
 * Created by johnson on 13-May-16.
 */
public class ComplainListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    public ComplainListAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
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

            convertView=inflater.inflate(R.layout.activity_complain_list , null);


            txt6=(TextView) convertView.findViewById(R.id.msqid);
            txt7=(TextView) convertView.findViewById(R.id.msgbody);
            txt8=(TextView) convertView.findViewById(R.id.userfrom);


        }

        HashMap<String, String> map=list.get(position);
        txt6.setText(map.get(FIRTH_COLUMNm));
        txt7.setText(map.get(SIXTH_COLUMNm));
        txt8.setText(map.get(SEVENTH_COLUMNm));


        return convertView;
    }

}