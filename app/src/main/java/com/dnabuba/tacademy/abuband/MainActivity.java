package com.dnabuba.tacademy.abuband;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnabuba.tacademy.abuband.Alarm.AlarmListFragment;
import com.dnabuba.tacademy.abuband.Baby.BabyListFragment;
import com.dnabuba.tacademy.abuband.Setting.SettingFragment;
import com.dnabuba.tacademy.abuband.SickReport.SickReportListFragment;
import com.dnabuba.tacademy.abuband.Temperature.TemperatureFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    TextView text_navi_name, text_navi_birthY, text_navi_birthMD;
    ImageView image_navi;

    public void finish() {
        finish();
    }

    public static final int RESULT_CODE_0 = 0;
    public static final int RESULT_CODE_1 = 1;
    public static final int RESULT_CODE_2 = 2;
    public static final int RESULT_CODE_3 = 3;

    private static final String TAG_TEMP = "temp";
    private static final String TAG_SICKREPORT = "sickreport";
    private static final String TAG_ALARM = "alarm";
    private static final String TAG_BABYADD = "babyadd";
    private static final String TAG_SETTING = "setting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setMainTitle(getString(R.string.title_temperature));
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        //네비게이션 헤더를 만들어서 셋팅해줌
        //선택된아이의 사진, 이름, 생년월일 셋팅
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        text_navi_name = (TextView)hView.findViewById(R.id.text_navi_name);
        text_navi_birthY = (TextView)hView.findViewById(R.id.text_navi_birthY);
        text_navi_birthMD = (TextView)hView.findViewById(R.id.text_navi_birthMD);
        text_navi_name.setText("new text");
        text_navi_birthY.setText("2015");
        text_navi_birthMD.setText("1126");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        getBabyinfo()


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new TemperatureFragment(), TAG_TEMP).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.navi_temperature) {
            setMainTitle(getString(R.string.title_temperature));
            emptyBackStack();

        } else if (id == R.id.navi_sickreport) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_SICKREPORT);

            if (old == null) {
                setMainTitle(getString(R.string.title_sick_repork));
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SickReportListFragment(), TAG_SICKREPORT).addToBackStack(null).commit();
            }

        } else if (id == R.id.navi_alarm) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALARM);

            if (old == null) {
                setMainTitle(getString(R.string.title_alarm));
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new AlarmListFragment(), TAG_ALARM).addToBackStack(null).commit();
            }

        } else if (id == R.id.navi_babyadd) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_BABYADD);

            if (old == null) {
                setMainTitle(getString(R.string.title_baby_list));
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new BabyListFragment(), TAG_BABYADD).addToBackStack(null).commit();
            }


        } else if (id == R.id.navi_setting) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_SETTING);

            if (old == null) {
                setMainTitle(getString(R.string.title_setting));
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SettingFragment(), TAG_SETTING).addToBackStack(null).commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void emptyBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setMainTitle(String title)  {
        setTitle(title);
    }


}
