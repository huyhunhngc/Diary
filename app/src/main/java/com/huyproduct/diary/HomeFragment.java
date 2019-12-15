package com.huyproduct.diary;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private ArrayList<constraint> list;
    private ArrayList<String> listKey;
    private DatabaseReference mReference;
    private recyclerCustomAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        list = new ArrayList<constraint>();
        listKey = new ArrayList<String>();
        swipe = view.findViewById(R.id.swipe_container);
        recyclerView = view.findViewById(R.id.list_post);
        adapter = new recyclerCustomAdapter(list,recyclerView);
        progressBar = view.findViewById(R.id.loading_posts);

        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        swipe.setColorSchemeResources(R.color.colorAccent);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        swipe.setRefreshing(false);
                    }
                },3000);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot item: dataSnapshot.child("postPublic").getChildren())
                {
                    postModule p1 = item.getValue(postModule.class);

                    if(!listKey.contains(item.getKey()))
                    {
                            constraint p = new constraint(p1.getAuthorName(),p1.getUrlImage(),p1.getTime(),p1.getDate(),p1.getTitle(),p1.getContent(),item.getKey());
                            list.add(0,p);

                    }
                    adapter.notifyDataSetChanged();
                    listKey.add(item.getKey());

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        return view;


    }




}
