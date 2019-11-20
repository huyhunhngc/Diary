package com.huyproduct.diary;

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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user;
    private FirebaseAuth auth;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;
    private TextView displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        NavigationView navigationView = findViewById(R.id.nav_menu);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        String email = auth.getCurrentUser().getUid();
        displayName = header.findViewById(R.id.email_display);
        displayName.setText(email);


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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pickDiary.class);
                startActivity(intent);
            }
        });

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
        //getMenuInflater().inflate(R.menu.menu_action, menu);

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
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_acc:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
                return true;
            case R.id.nav_set:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
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

        setTitle(menuItem.getTitle());

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
