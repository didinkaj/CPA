package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.johnson.priceadvvisorbeta.Constants.SIXTH_COLUMNF;
import static com.example.johnson.priceadvvisorbeta.Constants.FIRTH_COLUMNF;
import static com.example.johnson.priceadvvisorbeta.Constants.SEVENTH_COLUMNF;
import static com.example.johnson.priceadvvisorbeta.Constants.EIGTH_COLUMNF;
import static com.example.johnson.priceadvvisorbeta.Constants.NINETH_COLUMNF;
import static com.example.johnson.priceadvvisorbeta.Constants.TENTH_COLUMNF;

/**
 * Created by johnson on 24-May-16.
 */


/**
 * Created by johnson on 13-May-16.
 */
public class FavoriteListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirstf;
    TextView txtSecondf;
    TextView txtThirdf;
    TextView txtFouthf;
    TextView txtFirfthf;
    TextView txtSixthf;
    //TextView txtFourth;
    public FavoriteListAdapter(Activity activity,ArrayList<HashMap<String, String>> list){
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

            convertView=inflater.inflate(R.layout.activity_favorite_list , null);

            txtFirstf=(TextView) convertView.findViewById(R.id.idf);
            txtSecondf=(TextView) convertView.findViewById(R.id.unamef);
            txtThirdf=(TextView) convertView.findViewById(R.id.productidf);
            txtFouthf=(TextView) convertView.findViewById(R.id.productnamef);
            txtFirfthf=(TextView) convertView.findViewById(R.id.minpricef);
            txtSixthf=(TextView) convertView.findViewById(R.id.maxpricef);


        }

        HashMap<String, String> map=list.get(position);
        txtFirstf.setText(map.get(FIRTH_COLUMNF));
        txtSecondf.setText(map.get(SIXTH_COLUMNF));
        txtThirdf.setText(map.get(SEVENTH_COLUMNF));
        txtFouthf.setText(map.get(EIGTH_COLUMNF));
        txtFirfthf.setText(map.get(NINETH_COLUMNF));
        txtSixthf.setText(map.get(TENTH_COLUMNF));

        return convertView;
    }

}
