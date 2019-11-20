package com.huyproduct.diary;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    private ArrayList<postModule> list;
    private ArrayList<String> listKey;
    private DatabaseReference mReference;
    private recyclerCustomAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        list = new ArrayList<postModule>();
        listKey = new ArrayList<String>();

        recyclerView = view.findViewById(R.id.list_post);
        adapter = new recyclerCustomAdapter(list,recyclerView);
        progressBar = view.findViewById(R.id.loading_posts);

        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot item: dataSnapshot.child("post").getChildren())
                {
                    postModule p = item.getValue(postModule.class);

                    if(!listKey.contains(item.getKey()))
                    {
                        list.add(0,p);

                    }
                    adapter.notifyDataSetChanged();
                    listKey.add(item.getKey());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;


    }




}
