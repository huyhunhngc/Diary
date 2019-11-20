package com.huyproduct.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private ArrayList<postModule> list = new ArrayList<>();


    public recyclerCustomAdapter(ArrayList<postModule> list,RecyclerView recyclerView)
    {
        this.list = list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
            return new ItemViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position)
    {

            postModule post = list.get(position);

            final String dateStr = post.getDate();
            final String timestr = post.getTime();
            final String titlestr = post.getTitle();
            final String contentstr = post.getContent();

            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.dayTxt.setText(dateStr);
            viewHolder.timeTxt.setText(timestr);
            viewHolder.titleTxt.setText(titlestr);
            viewHolder.contentTxt.setText(contentstr);

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
    @Override
    public int getItemViewType(int position)
    {
        if (list.get(position) == null)
        {
            return 1;
        }

        return 0;
    }


    public class ItemViewHolder extends  RecyclerView.ViewHolder
    {
        TextView timeTxt, titleTxt, contentTxt, dayTxt;
        public ItemViewHolder(View itemView)
        {
            super(itemView);
            timeTxt = itemView.findViewById(R.id.post_time_label);
            titleTxt = itemView.findViewById(R.id.title_post);
            contentTxt = itemView.findViewById(R.id.content_post);
            dayTxt = itemView.findViewById(R.id.day_posts);
        }

    }



}

