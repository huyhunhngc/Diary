package com.huyproduct.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private ArrayList<constraint> list = new ArrayList<>();


    public recyclerCustomAdapter(ArrayList<constraint> list,RecyclerView recyclerView)
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

            constraint post = list.get(position);

            final String authorStr = post.getAuthorName();
            final String uid = post.getUrlImage();
            final String dateStr = post.getDate();
            final String timestr = post.getTime();
            final String titlestr = post.getTitle();
            final String contentstr = post.getContent();
            final String key = post.getKey();

            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.authorTxt.setText(authorStr);
            viewHolder.dayTxt.setText(dateStr);
            viewHolder.timeTxt.setText(timestr);
            viewHolder.titleTxt.setText(titlestr);
            viewHolder.contentTxt.setText(contentstr);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),detailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("author", authorStr);
                    bundle.putString("uid",uid);
                    bundle.putString("time",timestr);
                    bundle.putString("title", titlestr);
                    bundle.putString("content", contentstr);
                    bundle.putString("key",key);
                    intent.putExtras(bundle);


                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity)v.getContext(), ((ItemViewHolder) holder).authorAvatar, "profile_photo");
                    v.getContext().startActivity(intent,options.toBundle());
                }
            });

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String imageUrl = dataSnapshot.child("profile").child(uid).getValue(String.class);
               Glide.with(viewHolder.authorAvatar.getContext()).load(imageUrl).into(viewHolder.authorAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


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
        TextView timeTxt, titleTxt, contentTxt, dayTxt, authorTxt;
        CircleImageView authorAvatar;
        public ItemViewHolder(View itemView)
        {
            super(itemView);
            authorAvatar = itemView.findViewById(R.id.image_author);
            authorTxt = itemView.findViewById(R.id.author_name);
            timeTxt = itemView.findViewById(R.id.post_time_label);
            titleTxt = itemView.findViewById(R.id.title_post);
            contentTxt = itemView.findViewById(R.id.content_post);
            dayTxt = itemView.findViewById(R.id.day_posts);
        }

    }



}

