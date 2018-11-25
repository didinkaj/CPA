package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by johnson on 20-May-16.
 */


/**
 * Created by johnson on 03-May-16.
 */
public class ProfileUser extends Fragment implements View.OnClickListener {
    private TextView txtEmail;
    private TextView total;
    View view;
    private String text;
    private SharedPreference sharedPreference;
    DatabaseHelper databaseHelper;
    private ListView objdbf;
    String txtPostaa;
    DataBaseFavorite nodb;
    public ArrayList<HashMap<String, String>> lista;
    //Activity activity;
    private ArrayList<HashMap<String, String>> list;


    public ProfileUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.userprofile, container, false);
        //shared preference retrival
        sharedPreference = new SharedPreference();
        text = sharedPreference.getValue(this.getContext());
        txtEmail = (TextView) view.findViewById(R.id.txt_texts);
        txtEmail.setText(text);
        total = (TextView) view.findViewById(R.id.titlem);


        //database values
        databaseHelper = new DatabaseHelper(getContext());
        String[] textRes = databaseHelper.getAppCategoryDetail(text); //this is the method to query
        TextView log1 = (TextView) view.findViewById(R.id.txt_text_names);
        log1.setText(textRes[0]);

        //phone number
        TextView log2 = (TextView) view.findViewById(R.id.phoneviews);
        log2.setText(textRes[1]);
        ///set number of favorite goods
        nodb = new DataBaseFavorite(getContext());
        Integer favprod = nodb.numberOfRowsProducts(text);
        Integer sumMin = nodb.getAllFavoriteSum(text);
        Integer sumMax = nodb.getAllFavoriteSumMax(text);
        TextView no = (TextView) view.findViewById(R.id.titleUs);
        no.setText("Favorite Goods" +" ("+String.valueOf(favprod)+")");
        if(favprod<=0){
            total.setVisibility(View.INVISIBLE);
        }else{

            total.setVisibility(View.VISIBLE);
            total.setText("Total Cost\t\t\t\t Min \t"+ sumMin.toString()+"\t\t\t Max \t"+sumMax.toString());
        }

        //Toast.makeText(getContext(), Integer.toString(users) + " Clicked", Toast.LENGTH_SHORT).show();


        final ListView listView = (ListView)view.findViewById(R.id.listView3);

        final ArrayList array_list = nodb.getAllFavorite(text);


        FavoriteListAdapter adapter=new FavoriteListAdapter((Activity) getContext(), array_list);
        listView.setAdapter(adapter);

        //ArrayList<HashMap<String, String>> contacts = nodb.getUserDetails();


        objdbf = (ListView) view.findViewById(R.id.listView3);
        //obj.setAdapter(arrayAdapter);
        objdbf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                HashMap<String, Object> v;
                v = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                String in = (String) v.get("productid");
                String inf = (String) v.get("id");
                int c = Integer.parseInt(in);
                int f = Integer.parseInt(inf);

                Log.d("Result prizeeeeee:-------- ", String.valueOf(arg0.getItemAtPosition(arg2)));


                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", c);
                dataBundle.putInt("idf", f);

                Intent intent = new Intent(getContext(), DisplayProducts.class);

                intent.putExtras(dataBundle);
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
