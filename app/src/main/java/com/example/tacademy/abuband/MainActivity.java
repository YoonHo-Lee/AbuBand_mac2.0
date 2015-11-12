package com.example.tacademy.abuband;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tacademy.abuband.Alarm.AlarmListFragment;
import com.example.tacademy.abuband.Baby.BabyListFragment;
import com.example.tacademy.abuband.Setting.SettingFragment;
import com.example.tacademy.abuband.SickReport.SickReportListFragment;
import com.example.tacademy.abuband.Temperature.TemperatureFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        setMainTitle("체온측정");
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.navi_temperature) {
            setMainTitle("체온측정");
            emptyBackStack();

        } else if (id == R.id.navi_sickreport) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_SICKREPORT);

            if (old == null) {
                setMainTitle("아픔일지");
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new SickReportListFragment(), TAG_SICKREPORT).addToBackStack(null).commit();
            }

        } else if (id == R.id.navi_alarm) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_ALARM);

            if (old == null) {
                setMainTitle("알람");
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new AlarmListFragment(), TAG_ALARM).addToBackStack(null).commit();
            }

        } else if (id == R.id.navi_babyadd) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_BABYADD);

            if (old == null) {
                setMainTitle("아이등록");
                emptyBackStack();
                getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, new BabyListFragment(), TAG_BABYADD).addToBackStack(null).commit();
            }


        } else if (id == R.id.navi_setting) {
            Fragment old = getSupportFragmentManager().findFragmentByTag(TAG_SETTING);

            if (old == null) {
                setMainTitle("설정");
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
