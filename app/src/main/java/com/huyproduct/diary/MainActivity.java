package com.huyproduct.diary;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;
    private TextView displayName, displayEmail;
    private CircleImageView circleImageView, toolbarImg;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DIARY");

        collapsingToolbarLayout = findViewById(R.id.collapse_toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true, true);
        collapsingToolbarLayout.setTitle("Diary");

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        NavigationView navigationView = findViewById(R.id.nav_menu);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        user = auth.getCurrentUser();

        displayName = header.findViewById(R.id.name_display);
        displayName.setText(user.getDisplayName());
        displayEmail = header.findViewById(R.id.email_display);
        displayEmail.setText(user.getEmail());

        circleImageView = header.findViewById(R.id.header_image);
        toolbarImg = findViewById(R.id.toolbar_img);
        toolbarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, toolbarImg, "fromhead");
                startActivity(intent,options.toBundle());
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String imageUrl = dataSnapshot.child("profile").child(user.getUid()).getValue(String.class);
                Glide.with(MainActivity.this).load(imageUrl).into(circleImageView);
                Glide.with(MainActivity.this).load(imageUrl).into(toolbarImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


        bottomNavigationView = findViewById(R.id.switch_post);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                selectDrawerItem(menuItem);
                return false;
            }
        });

        inItFragment();



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.addbutton, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return  true;
        }
        switch (item.getItemId())
        {
            case R.id.nav_noti:
                Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_acc:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
                return true;
            case R.id.nav_set:
                intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.pick_btn:
                intent = new Intent(MainActivity.this, pickDiary.class);
                startActivity(intent);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        item.setChecked(true);
        drawerLayout.closeDrawer(Gravity.LEFT);
        int id = item.getItemId();
            if (id==R.id.nav_noti) {
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            if(id== R.id.nav_acc)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
                finish();
                return true;
            }
            if( id==R.id.nav_set)
            {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            if(id==R.id.nav_myprofile)
            {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                return true;
            }
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void selectDrawerItem(MenuItem menuItem)
    {
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId())
        {
            case R.id.open_home:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.open_mypost:
                fragmentClass = MypostsFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();


        menuItem.setChecked(true);

        collapsingToolbarLayout.setTitle(menuItem.getTitle());

        drawerLayout.closeDrawers();
    }
    public void inItFragment()
    {
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();
    }
}
