package com.example.johnson.priceadvvisorbeta;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContract;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by johnson on 13-Apr-16.
 */
public class ViewAdapterList extends RecyclerView.Adapter<ViewAdapterList.MyViewHolder>{
    private  LayoutInflater inflater;
   // private ClickListener clickListener;
    List<NavigationDrawerMenu> data = Collections.emptyList();

    public ViewAdapterList(Context context,  List<NavigationDrawerMenu>data){
        inflater =  LayoutInflater.from(context);
        this.data = data;
    }
    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.custom_view, parent, false);
       // Log.d("Home", "onCreateViewHolder called");
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavigationDrawerMenu current = data.get(position);
        //Log.d("Home", "onBindViewHolder called" + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);


    }/*
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }*/

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView){
            super(itemView);
            //itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
           // icon.setOnClickListener(this);
        }

         /*   public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item Clicked at"+getAdapterPosition(), Toast.LENGTH_SHORT).show();
              //  delete(getAdapterPosition());

                if(clickListener!=null){
                    clickListener.itemClicked(v,getAdapterPosition());
                }
            }



*/
    }
/*
    public interface ClickListener{
        public void itemClicked(View view, int position);


    }*/
}
