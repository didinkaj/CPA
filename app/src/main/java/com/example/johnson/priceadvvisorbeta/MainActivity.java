package com.example.johnson.priceadvvisorbeta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.johnson.priceadvvisorbeta.DataBaseHandlerImage.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    public MyPageAdapter mAdapter;
    public static ViewPager mPager;
    public TabLayout mTabLayout;
    NavigationView navigationView = null;
    public SharedPreference sharedPreference;
    private String text;
   Activity context = this;
    private DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

       // mydb = new DBHelper(this);
       // mydb.insertContact("sugar", "sugar", "sugar", "sugar", "sugar");

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mAdapter = new MyPageAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setAdapter(mAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //navigation drawer COMMUNICATION SECTION
        navigationView = (NavigationView) findViewById(R.id.nav_views);
        navigationView.setNavigationItemSelectedListener(this);

        //setting a tab


        Bundle extrasTab = getIntent().getExtras();


        if(extrasTab==null){
            mPager.setCurrentItem(1);
        }else {
            int Value = extrasTab.getInt("tab");

            mPager.setCurrentItem(Value);
        }
        //DatabaseHelper db = new DatabaseHelper(this);
        //db.insertUsersDefault(1, "Johnson", "0712328250", "didinkaj@gmail.com", "didinkaj@gmail.com");
        //enableling and disabling logout button




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        sharedPreference = new SharedPreference();
        text = sharedPreference.getValue(context);
        //setting visibility to various menu items to admin
        DatabaseHelper c =new DatabaseHelper(MainActivity.this);
        String k = c.getprevilage(text);
        //Log.d("immmmmmmmmmmmmmmmmmmmmm****",k);


        if(text==null){
            MenuItem logb = menu.findItem(R.id.logut);
            logb.setVisible(false);
            MenuItem item1 = menu.findItem(R.id.item1);
            item1.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.watchup);
            item2.setVisible(false);
        }else {
            if(Integer.parseInt(k)==1) {
                MenuItem logb = menu.findItem(R.id.logut);
                logb.setVisible(true);
                MenuItem item1 = menu.findItem(R.id.item1);
                item1.setVisible(true);
                MenuItem item2 = menu.findItem(R.id.watchup);
                item2.setVisible(true);

            }else{
                MenuItem logb = menu.findItem(R.id.logut);
                logb.setVisible(true);
                MenuItem item1 = menu.findItem(R.id.item1);
                item1.setVisible(false);
                MenuItem item2 = menu.findItem(R.id.watchup);
                item2.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            return true;
        }
        if (id == R.id.contact) {
            startActivity(new Intent(MainActivity.this, ContactUs.class));
            return true;
        }
        if (id == R.id.logut) {
            sharedPreference.clearSharedPreference(this);
            Toast loginErr = Toast.makeText(this, "Logout sucessful", Toast.LENGTH_SHORT);
            loginErr.show();
            finish();
            Intent i = new Intent(this, MainActivity.class);
            Bundle tab = new Bundle();
            tab.putInt("tab", 0);
            i.putExtras(tab);
            startActivity(i);

            return true;
        }
        if (id == R.id.watchup) {
            startActivity(new Intent(MainActivity.this, WatchDogUpdates.class));
            return true;
        }

        switch(item.getItemId())
        {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayProducts.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:

        }

        return super.onOptionsItemSelected(item);
    }
    //drawer menu items
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {
           // startActivity(new Intent(MainActivity.this, SearchActivity.class));
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri screenshotUri = Uri.parse("android.resource://com/example/johnson/priceadvvisorbeta/*");

            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            startActivity(Intent.createChooser(sharingIntent, "Share image using"));
            return true;

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, MessageActivity.class));
            return true;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    //page adapter
    class MyPageAdapter extends FragmentStatePagerAdapter {


        public MyPageAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public Fragment getItem(int position) {
            /*MainActivity.MyFragment myFragment = new MainActivity.MyFragment().newInstance(position);
            return myFragment;*/
            switch (position) {
                case 0:
                    sharedPreference = new SharedPreference();
                    text = sharedPreference.getValue(context);

                    if(text==null){
                        TextView log1 = (TextView) findViewById(R.id.fab);
                        log1.setText("Not logged in");
                        AccountSection tab2 = new AccountSection();
                        return tab2;


                    }else {
                        //checking the user previlage
                        DatabaseHelper c =new DatabaseHelper(MainActivity.this);
                        String k = c.getprevilage(text);
                        //Log.d("immmmmmmmmmmmmmmmmmmmmm****",k);
                        if(Integer.parseInt(k)==1){
                        TextView log1 = (TextView) findViewById(R.id.fab);
                        log1.setText(text);
                        Profile tab1 = new Profile();
                        return tab1;
                        }else {
                            TextView log1 = (TextView) findViewById(R.id.fab);
                            log1.setText(text);
                            ProfileUser tab1 = new ProfileUser();
                            return tab1;
                        }

                    }

                case 1:
                    PriceSection tab2 = new PriceSection();
                    return tab2;
                case 2:
                    WatchDogSection tab3 = new WatchDogSection();
                    return tab3;
                default:
                    PriceSection tab0 = new PriceSection();
                    return tab0;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return "ACCOUNT";
                case 1:

                    return "PRICE";
                case 2:

                    return "WATCHDOG";

                default:
                    return null;
            }

        }
    }


}
