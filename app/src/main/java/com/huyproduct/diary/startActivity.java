package com.huyproduct.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class startActivity extends Activity
{
    FirebaseUser user;
    private ProgressBar progressBar;
    Handler mHandler;
    Runnable mNextActivityCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        user = FirebaseAuth.getInstance().getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(user == null)
                {
                    Intent intent = new Intent(startActivity.this,login.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(startActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);

    }

}
