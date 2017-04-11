package com.wordpress.yassinemalti.kooora.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.wordpress.yassinemalti.kooora.BuildConfig;
import com.wordpress.yassinemalti.kooora.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrincipaleActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    MaintenantFragment.OnFragmentInteractionListener,
                    AjourdhuiFragment.OnFragmentInteractionListener,
                    DemainFragment.OnFragmentInteractionListener,
                    HierFragment.OnFragmentInteractionListener,
                    ActualitesFragment.OnFragmentInteractionListener,
                    LiensFragment.OnFragmentInteractionListener,
                    AproposFragment.OnFragmentInteractionListener{

    SettingSQLiteDatabase mySettingSQLiteDatabase;
    private FirebaseRemoteConfig remoteConfig;
    private static final String TAG = "PrincipaleActivity";

    private boolean viewIsAtHome;
    boolean doubleBackToExitPressedOnce = false;
    private int currentViewID;

    private static String last_update_date;
    private static String maintenant_page_url;
    private static String aujourdhui_page_url;
    private static String demain_page_url;
    private static String hier_page_url;
    private static String actualites_page_url;
    private static String liens_page_url;
    private static String apropos_page_url;

    private static String lien1_url;
    private static String lien2_url;
    private static String lien3_url;
    private static String lien4_url;
    private static String lien5_url;

    public static String getmaintenant_page_url() {
        return maintenant_page_url;
    }
    public static String getaujourdhui_page_url() {
        return aujourdhui_page_url;
    }
    public static String getdemain_page_url() {
        return demain_page_url;
    }
    public static String gethier_page_url() {
        return hier_page_url;
    }
    public static String getactualites_page_url() {
        return actualites_page_url;
    }
    public static String getliens_page_url() {
        return liens_page_url;
    }
    public static String getapropos_page_url() {
        return apropos_page_url;
    }

    public static String getlien1_url() {
        return lien1_url;
    }
    public static String getlien2_url() {
        return lien2_url;
    }
    public static String getlien3_url() {
        return lien3_url;
    }
    public static String getlien4_url() {
        return lien4_url;
    }
    public static String getlien5_url() {
        return lien5_url;
    }

    public static Date convertStringToDate (String stringDate ) {
        //String dateString = "03/26/2012 11:49:00 AM";
        String dateString = stringDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, convertedDate.toString());
        return convertedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(homeClickListener);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mySettingSQLiteDatabase = new SettingSQLiteDatabase(this);

        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();

        remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setDefaults(R.xml.remote_config_defaults);
        remoteConfig.setConfigSettings(remoteConfigSettings);

        firebaseConfigurationFetch();
        subscribeToPushService();
        navigationView.setCheckedItem(R.id.maintenant);
        displayView(R.id.maintenant);

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.maintenant);
            displayView(R.id.maintenant);
        } else {
            if (doubleBackToExitPressedOnce){
                moveTaskToBack(true);
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "الرجاء الضغط مرة أخرى للخروج", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        currentViewID = viewId;

        switch (viewId) {
            case R.id.maintenant:
                fragment = new MaintenantFragment();
                title  = "مباريات الآن";
                viewIsAtHome = true;
                break;
            case R.id.aujourdhui:
                fragment = new AjourdhuiFragment();
                title  = "مباريات اليوم";
                viewIsAtHome = false;
                break;
            case R.id.demain:
                fragment = new DemainFragment();
                title  = "مباريات الغد";
                viewIsAtHome = false;
                break;
            case R.id.hier:
                fragment = new HierFragment();
                title  = "مباريات أمس";
                viewIsAtHome = false;
                break;
            case R.id.actualites:
                fragment = new ActualitesFragment();
                title  = "أخبار الرياضة";
                viewIsAtHome = false;
                break;
            case R.id.liens:
                fragment = new ActualitesFragment();
                title  = "روابط البث";
                viewIsAtHome = false;
                break;
            case R.id.partager:
                fragment = null;
                title  = getSupportActionBar().getTitle().toString();
                viewIsAtHome = false;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.wordpress.yassinemalti.kooora");
                startActivity(Intent.createChooser(sharingIntent, "شارك التطبيق عبر..."));
                break;
            case R.id.apropos:
                fragment = new ActualitesFragment();
                title  = "بخصوص التطبيق";
                viewIsAtHome = false;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    private View.OnClickListener homeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.END);
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(currentViewID);
        displayView(currentViewID);

    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    private void firebaseConfigurationFetch() {

        long cacheExpiration = 3600;
        if (remoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 1;
        }

        remoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            String myCurrentValue;

                            myCurrentValue = mySettingSQLiteDatabase.dataReadParameter("last_update_date");
                            last_update_date = remoteConfig.getString("last_update_date");
                            if(myCurrentValue.length() == 0) {
                                mySettingSQLiteDatabase.dataInsertParameter("last_update_date", last_update_date);
                                mySettingSQLiteDatabase.dataReadParameter("last_update_date");
                            }
                            else
                                if(convertStringToDate(last_update_date).before(convertStringToDate(myCurrentValue)))
                                {
                                    mySettingSQLiteDatabase.dataUpdateParameter("last_update_date",last_update_date);
                                    mySettingSQLiteDatabase.dataReadParameter("last_update_date");
                                }

                            mySettingSQLiteDatabase.dataReadParameter("last_update_date");

                            maintenant_page_url = remoteConfig.getString("maintenant_page_url");
                            aujourdhui_page_url = remoteConfig.getString("aujourdhui_page_url");
                            demain_page_url = remoteConfig.getString("demain_page_url");
                            hier_page_url = remoteConfig.getString("hier_page_url");
                            actualites_page_url = remoteConfig.getString("actualites_page_url");
                            liens_page_url = remoteConfig.getString("liens_page_url");
                            apropos_page_url = remoteConfig.getString("apropos_page_url");

                            lien1_url = remoteConfig.getString("lien1_url");
                            lien2_url = remoteConfig.getString("lien2_url");
                            lien3_url = remoteConfig.getString("lien3_url");
                            lien4_url = remoteConfig.getString("lien4_url");
                            lien5_url = remoteConfig.getString("lien5_url");

                            remoteConfig.activateFetched();

                        } else {
                            Log.e(TAG, "Fetch failed.");
                        }
                    }
                });
    }
}