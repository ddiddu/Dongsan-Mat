package com.example.mc_week1_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar, toolbartab;
    ViewPager viewPager;
    TabLayout tabLayout;

    PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ask_contact_Permissions();
        ask_music_Permissions();

        while (!checkPermission()){
            //ㅈㄴ 임시방편임
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(getApplicationContext(), LoadingActivity.class);
        startActivity(intent);

        //toolbar=(Toolbar)findViewById(R.id.toolbar); //맨위에 toolbar 지정
        toolbartab=(Toolbar)findViewById(R.id.toolbartab);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);

        setSupportActionBar(toolbar);

        pageAdapter=new PageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new ContactFragment(),"연락처");
        pageAdapter.addFragment(new PhotoFragment(), "사진");
        pageAdapter.addFragment(new MapFragment(), "지도");

        viewPager.setAdapter(pageAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void ask_contact_Permissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},1);
        }
    }

    private void ask_music_Permissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
}

