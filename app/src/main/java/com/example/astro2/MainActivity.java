package com.example.astro2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private AstroViewModel astroViewModel;

    Thread thread,refret;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout=findViewById(R.id.tab_lay);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2_lay);

        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        TextView longitude=findViewById(R.id.longitude_lay);
        TextView latitude=findViewById(R.id.latitude_lay);
        TextView time=findViewById(R.id.time_lay);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if(position==0)
                {
                    tab.setText("SUN");
                }
                else if(position==1)
                {
                    tab.setText("MOON");
                }
                else
                {
                    tab.setText("SETTINGS");
                }
            }
        }).attach();



        LocalDateTime timeNow = LocalDateTime.now();
        double longi=19.942222;
        double lati=51.900278;
        int ref=1;


        AstroCalculator.Location loc = new AstroCalculator.Location(lati,longi);
        AstroDateTime astroDateTime = new AstroDateTime(timeNow.getYear(),timeNow.getMonth().getValue(),timeNow.getDayOfMonth(),timeNow.getHour(),timeNow.getMinute(),timeNow.getSecond(),2,false);

        AstroCalculator astroCalculator= new AstroCalculator(astroDateTime,loc);

        astroViewModel = new ViewModelProvider(this).get(AstroViewModel.class);
        astroViewModel.setLatitude(lati);
        astroViewModel.setLongitude(longi);
        astroViewModel.setRefresh(ref);

        astroViewModel.setAstro(astroCalculator);




        astroViewModel.getLatitude().observe(this,item->{
            latitude.setText(astroViewModel.getLatitude().getValue().toString());

            astroCalculator.setLocation(new AstroCalculator.Location(astroViewModel.getLatitude().getValue(),astroViewModel.getLongitude().getValue()));

            astroViewModel.setAstro(astroCalculator);

        });

        astroViewModel.getLongitude().observe(this,item->{
            longitude.setText(astroViewModel.getLongitude().getValue().toString());

            astroCalculator.setLocation(new AstroCalculator.Location(astroViewModel.getLatitude().getValue(),astroViewModel.getLongitude().getValue()));

            astroViewModel.setAstro(astroCalculator);

        });

        astroViewModel.getRefresh().observe(this,item ->
        {
            time.setText(astroViewModel.getRefresh().getValue().toString());

            astroCalculator.setLocation(new AstroCalculator.Location(astroViewModel.getLatitude().getValue(),astroViewModel.getLongitude().getValue()));
            astroViewModel.setRefresh(astroViewModel.getRefresh().getValue());

            refret.interrupt();

            refret= new Thread(new RefreshTimer(ref,astroCalculator));
            refret.setDaemon(true);
            refret.start();


            astroViewModel.setAstro(astroCalculator);
        });

        thread= new Thread(new Timer(time));
        thread.setDaemon(true);
        thread.start();

        refret= new Thread(new RefreshTimer(ref,astroCalculator));
        refret.setDaemon(true);
        refret.start();
    }

    class Timer implements Runnable{
        private TextView field;
        public Timer(TextView field){
            this.field=field;
        }
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            Date currentTime;
            while(true){
                try {
                    currentTime = Calendar.getInstance().getTime();
                    String hours = Integer.toString(currentTime.getHours());
                    String min = Integer.toString(currentTime.getMinutes());
                    String sec = Integer.toString(currentTime.getSeconds());
                    field.setText(hours+":"+min+":"+sec);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    class RefreshTimer implements Runnable
    {
        int refreshTime;
        AstroCalculator astroCalculator;

        public RefreshTimer(int refreshTime, AstroCalculator astroCalculator) {
            this.refreshTime = refreshTime;
            this.astroCalculator = astroCalculator;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {

            try
            {
                astroCalculator.setLocation(new AstroCalculator.Location(astroViewModel.getLatitude().getValue(), astroViewModel.getLongitude().getValue()));

                astroViewModel.getAstro().postValue(astroCalculator);

                while (true) {
                    TimeUnit.MINUTES.sleep(refreshTime);
                    LocalDateTime timeNow = LocalDateTime.now();

                    AstroDateTime astroDateTime = new AstroDateTime(timeNow.getYear(), timeNow.getMonth().getValue(), timeNow.getDayOfMonth(), timeNow.getHour(), timeNow.getMinute(), timeNow.getSecond(), 2, false);

                    astroCalculator.setDateTime(astroDateTime);
                    astroViewModel.getAstro().postValue(astroCalculator);

                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

}



