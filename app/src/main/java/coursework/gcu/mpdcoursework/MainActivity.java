package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
import android.annotation.SuppressLint;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static Button back;
    static int currentFragment = 0;
    Timer timer;
    private Toolbar mTopToolbar;
    Boolean ft = true;
    static Boolean darkMode = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_canvas);
        loadFragment(new SecondFragment());


        ConstraintLayout cl = findViewById(R.id.cl);

        timedTask(15);
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Make toolbar show navigation button (i.e back button with arrow icon)
        if (darkMode) {
            cl.setBackgroundColor(Color.rgb(35, 52, 70));
        } else {
            cl.setBackgroundColor(Color.rgb(255, 255, 255));
        }

        //cl.setBackgroundColor(Color.rgb(0, 0, 60));




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if (id == R.id.action_favorite){
            loadFragment(new SecondFragment());
            return true;
        }

        if (id == android.R.id.home) {

            if (currentFragment == 0){
                System.out.println("eeeeeeeeeee");
                return true;
            }
            if (currentFragment == 1) {
                loadFragment(new SecondFragment());
                return true;
            }
            if (currentFragment == 2) {
                loadFragment(new FirstFragment());
                return true;
            }
            if (currentFragment == 3) {
                loadFragment(new Page1Fragment());
                return true;
            }

            return true;
        }
        if (id == R.id.action_1){
            ConstraintLayout cl = findViewById(R.id.cl);
           cl.setBackgroundColor(Color.rgb(35, 52, 70));
            darkMode = true;
            setMode();
            return true;
        }

        if (id == R.id.action_2){
            ConstraintLayout cl = findViewById(R.id.cl);
            cl.setBackgroundColor(Color.rgb(255, 255, 255));
            darkMode = false;
            setMode();
            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


    }

//Called when toggling Light/Dark Mode re-starts the fragment to make changes visible
    void setMode() {

        if (currentFragment == 0){
            loadFragment(new SecondFragment());

        }
        if (currentFragment == 1) {
            loadFragment(new FirstFragment());

        }
        if (currentFragment == 2) {
            loadFragment(new Page1Fragment());

        }
        if (currentFragment == 3) {
            loadFragment( new IndViewerFragment());
        }
    }


// empties the lists containing the roadwork / incident data
    public static void empytLists() {
        SecondFragment.rlist = null;
        SecondFragment.prlist = null;
        SecondFragment.cilist = null;
    }
    //Called every 15 mins to update the data
    public  class timedTaskClass extends TimerTask {
        public void run() {

            if (!ft && isThereSignal()) {
                MainActivity.empytLists();
                SecondFragment s = new SecondFragment();
                s.updateLists("https://trafficscotland.org/rss/feeds/roadworks.aspx");
                s.updateLists("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
                s.updateLists("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
            }
            else {
                ft = false;
            }
        }
    }

//Sets a timer to call a method repeatedly, used to update information automatically
   public  void timedTask(int minutes) {
        Timer timer = new Timer();
        timedTaskClass t = new timedTaskClass();
        timer.scheduleAtFixedRate(t, 0, minutes * 1000 * 60 );
       System.out.println("start");
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }


    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private boolean isThereSignal() {
        ConnectivityManager manager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }


} // End of MainActivity