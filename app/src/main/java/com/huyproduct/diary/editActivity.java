package com.huyproduct.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editActivity extends AppCompatActivity
{
    private EditText contentEdt, titleEdt;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contentEdt = findViewById(R.id.content_edt);
        titleEdt = findViewById(R.id.title_edt);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        contentEdt.setText(bundle.getString("cnt"));
        titleEdt.setText(bundle.getString("tit"));
        key = bundle.getString("key");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.postbutton, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.post_btn:
                changeData();
                return true;
            default:


        }
        return super.onOptionsItemSelected(item);
    }
    public void changeData()
    {
        String tit = titleEdt.getText().toString();
        String cnt = contentEdt.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            ref.child("postPublic").child(key).child("content").setValue(cnt);
            ref.child("postPublic").child(key).child("title").setValue(tit);
        }catch(Exception e)
        {
            Toast.makeText(editActivity.this,"Có vấn đề đã xảy ra",Toast.LENGTH_LONG).show();
        }


        Intent rsIntent = new Intent();
        rsIntent.putExtra("title",tit);
        rsIntent.putExtra("content", cnt);
        setResult(Activity.RESULT_OK, rsIntent);
        super.finish();
    }
}
