package com.huyproduct.diary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class detailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView content, title;
    private CircleImageView avatar;
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        content = findViewById(R.id.content_detail);
        title = findViewById(R.id.content_title);
        avatar = findViewById(R.id.image_author_detail);
        button = findViewById(R.id.edit_post);
        button.hide();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String authorStr = bundle.getString("author");
        final String imgUrl = bundle.getString("uid");
        final String key = bundle.getString("key");
        final String titleStr = bundle.getString("title");
        final String contentStr = bundle.getString("content");
        Log.d("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(imgUrl)) button.show();

        getSupportActionBar().setTitle("Nhật ký " + authorStr);
        content.setText(contentStr);
        title.setText(titleStr);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.child("profile").child(imgUrl).getValue(String.class);
                Glide.with(detailActivity.this).load(imageUrl).into(avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(detailActivity.this, editActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("cnt", contentStr);
                bundle1.putString("tit", titleStr);
                bundle1.putString("uid",imgUrl);
                intent.putExtras(bundle1);
                startActivityForResult(intent,111);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==111)
        {
            if(resultCode==RESULT_OK)
            {
                content.setText(data.getStringExtra("content"));
                title.setText(data.getStringExtra("title"));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }
}
