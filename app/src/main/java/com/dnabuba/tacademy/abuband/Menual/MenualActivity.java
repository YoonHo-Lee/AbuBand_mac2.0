package com.dnabuba.tacademy.abuband.Menual;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dnabuba.tacademy.abuband.R;

public class MenualActivity extends AppCompatActivity {

    private MenualAdapter mMenualAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menual);

        setTitle(getString(R.string.title_menual));

        mMenualAdapter = new MenualAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mMenualAdapter);

    }
}
