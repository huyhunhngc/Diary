package com.huyproduct.diary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends Activity
{

    private Button register;
    CircleImageView avatar;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private EditText firstname, lastname, addEmail, addPassword, addUsername;
    private Uri selectedImage;
    private ProgressBar progressBar;
    private String pathPhoto;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);

        register = findViewById(R.id.btn_registerConfirm);
        avatar = findViewById(R.id.avatar_register);

        firstname = findViewById(R.id.add_fristname);
        lastname = findViewById(R.id.add_lastname);
        addEmail = findViewById(R.id.add_email);
        addPassword = findViewById(R.id.add_password);
        addUsername = findViewById(R.id.add_username);


        avatar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pickPhoto();
            }
        });
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String emailTxt = addEmail.getText().toString();
                String passwordTxt = addPassword.getText().toString();
                String displayNameTxt = firstname.getText().toString()+" "+lastname.getText().toString();
                createAccount(emailTxt,passwordTxt,displayNameTxt,pathPhoto);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            selectedImage = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                avatar.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void createAccount(final String email, final String password, final String displayName, final String path)
    {
        if (!validateForm())
        {
            return;
        }
        progressDialog.setTitle("Đang đăng kí...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                progressDialog.dismiss();

                if (task.isSuccessful())
                {
                    FirebaseUser user = auth.getCurrentUser();
                    String url = user.getUid();

                    upLoadFile(displayName);

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("UpdateUI", "User profile updated.");
                                    }
                                }
                            });
                    //loginIn(email,password);
                    Intent intent = new Intent(RegisterActivity.this, startActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private boolean validateForm()
    {
        boolean valid = true;

        String email = addEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            addEmail.setError("Required.");
            valid = false;
        } else {
            addEmail.setError(null);
        }

        String password = addPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            addPassword.setError("Required.");
            valid = false;
        } else {
            addPassword.setError(null);
        }
        return valid;
    }
    private void loginIn(String email, String password)
    {
        progressDialog.show();
        progressDialog.setTitle("Đang đăng nhập...");
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                progressDialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, startActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    private void upLoadFile(final String displayName)
    {

        if(selectedImage != null)
        {
            StorageReference refStorage = storage.getReference().child("images/"+UUID.randomUUID().toString());
            final StorageReference ref = refStorage.child("profile");


            final String userFolder ;

            ref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            FirebaseUser user = auth.getCurrentUser();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("profile").child(user.getUid());
                            databaseReference.setValue(String.valueOf(uri));
                        }
                    });
                }
            });

        }
        else
        {
            Toast.makeText(RegisterActivity.this, "Lỗi", Toast.LENGTH_LONG).show();
        }

    }


    public void pickPhoto()
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,100);
    }
}
