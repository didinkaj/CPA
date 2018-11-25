package com.example.johnson.priceadvvisorbeta;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchDogSection extends Fragment {
    View view;
    Button addImage;
    ArrayList<Contact> imageArry = new ArrayList<Contact>();
    ContactImageAdapter imageAdapter;
    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    ListView dataList;
    byte[] imageName;
    int imageId;
    String txtPost;
    Bitmap theImage;
    DataBaseHandlerImage db;
    ImageView iv;
    TextView tv;

    public WatchDogSection() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_watch_dog_section, container, false);
        dataList = (ListView) view.findViewById(R.id.list);


        db = new DataBaseHandlerImage(getContext());

        //dummy data
        tv = (TextView) view.findViewById(R.id.editTextWatchdog);
        iv=(ImageView) view.findViewById(R.id.imageupload);

       // String ds = tv.getText().toString();
        // convert bitmap to byte

       /*/ Bitmap image = ((BitmapDrawable)iv.getDrawable()).getBitmap();;
        Bitmap image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_add_shopping_cart);
        //int image = R.drawable.ic_add_shopping_cart;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);

        db.addContact(new Contact(imageId, "hellow testing", imageInByte));




        db = new DataBaseHandlerImage(getContext());*/
        /**
         * Reading and getting all records from database
         */
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "ID:" + cn.getID() + " Name: " + cn.getNameImage()
                    + " ,Image: " + cn.getImage();

            // Writing Contacts to log
            Log.d("Result: ", log);
            // add contacts data in arrayList
            imageArry.add(cn);
        }

            /**
             * Set Data base Item into listview
             */
            imageAdapter = new ContactImageAdapter(getContext(), R.layout.post_details,
                    imageArry);
            dataList.setAdapter(imageAdapter);
            /**
             * go to next activity for detail image
             */
            dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        final int position, long id) {
                    imageName = imageArry.get(position).getImage();
                    imageId = imageArry.get(position).getID();
                    txtPost = imageArry.get(position).getNameImage();

                    Log.d("Before Send:****", imageName + " "+txtPost+" " + imageId);
                    // convert byte to bitmap
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(
                            imageName);
                    theImage = BitmapFactory.decodeStream(imageStream);
                    Intent intent = new Intent(getContext(),
                            DisplayImageActivity.class);
                    intent.putExtra("imagename", theImage);
                    intent.putExtra("imageid", imageId);
                    intent.putExtra("name",txtPost);
                    startActivity(intent);
                }
            });


        return view;
    }





        }



