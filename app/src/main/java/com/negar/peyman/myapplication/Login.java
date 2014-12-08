package com.negar.peyman.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;


public class Login extends Activity {

    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    String[] rank;
    String[] stdID;
    int[] flag;

    CirclePageIndicator mIndicator;
    Button start;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.viewpager_main);

        // Generate sample data
        rank = new String[] { "1", "2"};

        stdID = new String[] { "90105356",
                "90110823" };


        flag = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };


        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);

        System.out.println(viewPager.isInEditMode());
        //viewPager.setEnabled(true);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(Login.this, rank, stdID,
                flag);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new ReaderViewPagerTransformer(ReaderViewPagerTransformer.TransformType.FLOW));
        // ViewPager Indicator
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);

        //button to redirect
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent activityChangeIntent = new Intent(Login.this, Main.class);
                activityChangeIntent.putExtra("ID", stdID[0]);
                Login.this.startActivity(activityChangeIntent);
            }
        });

        Thread timer = new Thread() {
            public void run() {
                try {
                    // sleep(R.integer.SplashActivityTime);
                    sleep(5000);
                } catch (InterruptedException iEx) {
                    iEx.printStackTrace();
                } finally {
                    Intent mainActivity = new Intent(Login.this,
                            Main.class);

                    startActivity(mainActivity);
                    finish();
                }
            }
        };
        timer.start();

    }
}
