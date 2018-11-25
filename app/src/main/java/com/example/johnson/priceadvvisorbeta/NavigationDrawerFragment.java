package com.example.johnson.priceadvvisorbeta;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class jNavigationDrawerFragment extends Fragment  {
    private ActionBarDrawerToggle mDrawerToogle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private ViewAdapterList adapter;

    //navigation drawer varriables
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    public static final String PREF_FILE_NAME = "textpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private View containerView;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView)layout.findViewById(R.id.drawerlist);
        adapter = new ViewAdapterList(getActivity(),getData());
      //  adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            //launching activities
            public void onClick(View view, int position) {

                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), ContactUs.class));
                        break;

                    case 2:
                        startActivity(new Intent(getActivity(), About.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), SettingsActivity.class));
                        break;
                    default:
                        startActivity(new Intent(getActivity(), MainActivity.class));

                }
               // Toast.makeText(getActivity(), "onclick" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
               //a Toast.makeText(getActivity(), "onLongclick" + position, Toast.LENGTH_SHORT).show();

            }
        }));
        return layout;
    }

    public static List<NavigationDrawerMenu> getData(){
        List<NavigationDrawerMenu> data = new ArrayList<>();
        //int[] icons = {R.drawable.ic_home, R.drawable.ic_list,R.drawable.ic_account_box,R.drawable.ic_report_problem
        //,R.drawable.ic_settings,R.drawable.ic_fiber_new,R.drawable.ic_info};
        //String[] titles = {"Home","Products","Account","Report","Settings","News","About"};
        int[] icons = {R.drawable.ic_home
                ,R.drawable.ic_phone,R.drawable.ic_info, R.drawable.ic_settings};
        String[] titles = {"Home","Contact","About","Settings"};

        //i<titles.length && i<icons.length
        for (int i=0;i<titles.length && i<icons.length;i++){
            NavigationDrawerMenu current = new NavigationDrawerMenu();
            current.iconId =icons[i%icons.length];
            current.title = titles[i%titles.length];
            data.add(current);
        }
        return data;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToogle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Log.d("VIVZ","offset"+slideOffset);
                if(slideOffset<-0.1){
                    toolbar.setAlpha(1-slideOffset);
                }

            }
        };
        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToogle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToogle.syncState();

            }
        });

    }
    public static void saveToPreferences(Context context,String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();

    }
    public static String readFromPreferences(Context context, String preferenceName,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }
/*
    @Override
    public void itemClicked(View view, int position) {
       // startActivity(new Intent(getActivity(), SearchActivity.class));

    }*/

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector =new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                  //  Log.d("Home","onSingleTapUp"+e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                   // super.onLongPress(e);
                   View child =  recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child!=null && clickListener!=null){
                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));

                    }
                   // Log.d("Home", "onLongPress" + e);
                }
            });
           // Log.d("Home","constructor invoked");
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child =  rv.findChildViewUnder(e.getX(), e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child,rv.getChildAdapterPosition(child));

            }
            //Log.d("Home","onInterceptTouchEvent"+e);

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
           // Log.d("Home","onTouchEvent"+gestureDetector.onTouchEvent(e)+""+e);


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
