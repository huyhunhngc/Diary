package com.huyproduct.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends Activity {

    private EditText email,pass;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button login, singup;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();


        login = findViewById(R.id.btn_login);
        singup = findViewById(R.id.btn_register);
        email = findViewById(R.id.edt_username);
        pass = findViewById(R.id.edt_password);
        progressBar = findViewById(R.id.process);

        singup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(login.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailtxt = email.getText().toString();
                final String passtxt = pass.getText().toString();

                if(TextUtils.isEmpty(emailtxt))
                {
                    Toast.makeText(getApplicationContext(),"Enter your email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(passtxt))
                {
                    Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(emailtxt, passtxt).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(),"Email or Password is incorrect",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent intent = new Intent(login.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}
