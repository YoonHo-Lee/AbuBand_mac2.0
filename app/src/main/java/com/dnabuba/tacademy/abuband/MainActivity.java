package com.dnabuba.tacademy.abuband;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dnabuba.tacademy.abuband.Alarm.AlarmListFragment;
import com.dnabuba.tacademy.abuband.Baby.BabyListFragment;
import com.dnabuba.tacademy.abuband.Member.LoginFragment;
import com.dnabuba.tacademy.abuband.Setting.SettingFragment;
import com.dnabuba.tacademy.abuband.SickReport.SickReportListFragment;
import com.dnabuba.tacademy.abuband.Temperature.TemperatureFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context mContext;

    NavigationView navigationView;
    TextView text_navi_name, text_navi_birth;
    ImageView image_navi;
    DisplayImageOptions options;

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

    public static final int TAG_NAVI_CODE = 777;


    //아이리스트 클릭시 네비의 정보 변경
    public void setNeviText(String image, String name, String birth)   {
//        Log.e("MainActivity", "LogTest");
//        birth = birth.substring(0,4)+"."+birth.substring(4,6)+"."+birth.substring(6,8);
        ImageLoader.getInstance().displayImage(image, image_navi, options);
        text_navi_name.setText(name);
        text_navi_birth.setText(birth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        setMainTitle(getString(R.string.title_temperature));
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

//        Intent intent = getIntent();
//        String birth = intent.getStringExtra(LoginFragment.TAG_BABY_BIRTH);
//        if(birth==null) {
//            birth = "12345678";
//        }
//        birth = birth.substring(0,4)+"."+birth.substring(4,6)+"."+birth.substring(6,8);
//        String name = intent.getStringExtra(LoginFragment.TAG_BABY_NAME);
//        String image = intent.getStringExtra(LoginFragment.TAG_BABY_IMAGE);

//        Log.e("MainActivity", birth + name + image);

        //네비게이션 헤더를 만들어서 셋팅해줌
        //선택된아이의 사진, 이름, 생년월일 셋팅
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        text_navi_name = (TextView) hView.findViewById(R.id.text_navi_name);
        text_navi_birth = (TextView) hView.findViewById(R.id.text_navi_birth);
        image_navi = (ImageView) hView.findViewById(R.id.image_navi);

//        text_navi_name.setText(name);
//        text_navi_birth.setText(birth);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
//                .displayer(new RoundedBitmapDisplayer(100)) //곡률 50:모서리곡선인정사각형, 100원형
                //위 소스의 이미지 늘어나는 버그를 잡고, 정 가운데를 크롭
                .displayer(new BitmapDisplayer() {
                    @Override
                    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
                        Bitmap centerCroppedBitmap;
                        if (bitmap.getWidth() >= bitmap.getHeight()) { //in case of width larger than height
                            centerCroppedBitmap = Bitmap.createBitmap(
                                    bitmap,
                                    bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                                    0,
                                    bitmap.getHeight(),
                                    bitmap.getHeight()
                            );
                        } else { //in case of height larger than width
                            centerCroppedBitmap = Bitmap.createBitmap(
                                    bitmap,
                                    0,
                                    bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                                    bitmap.getWidth(),
                                    bitmap.getWidth()
                            );
                        }
                        //** then apply bitmap rounded **//
                        RoundedBitmapDrawable circledDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), centerCroppedBitmap);
//                        circledDrawable.setCircular(true);
                        circledDrawable.setCornerRadius(50);
                        circledDrawable.setAntiAlias(true);
                        imageAware.setImageDrawable(circledDrawable);
                    }
                }).build();



//        ImageLoader.getInstance().displayImage(image, image_navi, options);


        setNeviText(PropertyManager.getInstance().getPrefBaby_Image(),PropertyManager.getInstance().getPrefBaby_Name(),PropertyManager.getInstance().getPrefBaby_Birth());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MainActivity","onActivityResult 메인"+PropertyManager.getInstance().getPrefBaby_Image() + " / " + PropertyManager.getInstance().getPrefBaby_Name()+ " / " + PropertyManager.getInstance().getPrefBaby_Birth());
        setNeviText(PropertyManager.getInstance().getPrefBaby_Image(), PropertyManager.getInstance().getPrefBaby_Name(), PropertyManager.getInstance().getPrefBaby_Birth());
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


//    MenuItem menu = (MenuItem) findViewById(R.id.menu_temperatrue_refresh);
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_temperature, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())   {
            case R.id.menu_temperatrue_refresh :
                Toast.makeText(MainActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
