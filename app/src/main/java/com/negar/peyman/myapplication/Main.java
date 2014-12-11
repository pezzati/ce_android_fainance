package com.negar.peyman.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.hollowsoft.library.slidingdrawer.OnDrawerCloseListener;
import com.hollowsoft.library.slidingdrawer.OnDrawerOpenListener;
import com.hollowsoft.library.slidingdrawer.OnDrawerScrollListener;
import com.hollowsoft.library.slidingdrawer.SlidingDrawer;
import com.negar.peyman.myapplication.R;

import java.util.Calendar;
import java.util.Date;

public class Main extends FragmentActivity implements OnDrawerScrollListener, OnDrawerOpenListener,
        OnDrawerCloseListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 365;
   // FinanceRecord fRecords ;
    int startPosition = 30;
    ImageButton next;
    ImageButton prev;
    int currentDay;
    int currentMonth;
    int currentYear;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    PagerAdapter mPagerAdapter;

    private void setRecords(){
        Calendar cal = Calendar.getInstance();
        int month = 1;
        int day = 1;
        for (int i = 0; i < NUM_PAGES; i++) {
            //fRecords = new FinanceRecord(String.valueOf(day), String.valueOf(month), "2014", String.valueOf(i*4), "0");


            if(day == 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) ){
                month++;
                month = month % 12;
                day = 1;
            }
            else if(day == 29 && month == 2){
                month++;
                day = 1;
            }
            else if(day == 30){
                month++;
                day = 1;
            }
            else
                day++;

        }

    }
    private int getItem(int i) {
        return mPager.getCurrentItem() + i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //save a record
        getActionBar().setTitle("تمرین دوم");
        Calendar cal = Calendar.getInstance();
        currentDay = cal.get(Calendar.DAY_OF_MONTH);
        currentMonth = cal.get(Calendar.MONTH);
        currentYear = cal.get(Calendar.YEAR);
        startPosition = cal.get(Calendar.DAY_OF_YEAR);

        setContentView(R.layout.activity_main);

//        final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.sliding_drawer);
//
//        slidingDrawer.setOnDrawerScrollListener(this);
//        slidingDrawer.setOnDrawerOpenListener(this);
//        slidingDrawer.setOnDrawerCloseListener(this);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(startPosition);

//        setContentView(R.layout.fragment_screen_slide_page);
        next = (ImageButton)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
            }
        });

        prev = (ImageButton)findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.developer1:
                AlertDialog alertDialog = new AlertDialog.Builder(Main.this).create(); //Read Update
                alertDialog.setTitle("نگار ابوالحسنی");
                alertDialog.setMessage("۹۰۱۰۵۳۵۶");
                alertDialog.show();
                return true;
            case R.id.developer2:
                alertDialog = new AlertDialog.Builder(Main.this).create();
                alertDialog.setTitle("پیمان عزتی");
                alertDialog.setMessage("۹۰۱۱۰۸۲۳");
                alertDialog.show();
                return true;

        }
        return false;
    }

    @Override
    public void onScrollStarted() {

    }

    @Override
    public void onScrollEnded() {

    }

    @Override
    public void onDrawerClosed() {

    }

    @Override
    public void onDrawerOpened() {

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private String[] getDayMonth(int position){
            String[] date = new String[2];

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_YEAR, position);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            Date d = new Date();
            d.setDate(day);
            d.setMonth(month);
            d.setYear(cal.YEAR);
            Shamsi shamsi = new Shamsi(d);

            date[0] = String.valueOf(shamsi.date);
            date[1] = shamsi.strMonth;
            return date;
        }

        @Override
        public Fragment getItem(int position)
        {
            System.out.println("%%%%%%");
            System.out.println(position);
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            String[] date = getDayMonth(position);
            System.out.println("AAAAAAAA");
            System.out.println(date[0] + " " + date[1]);
//            bundle.putString("income", FinanceRecord.find(FinanceRecord.class, "day = ? and month = ?", date[0], date[1]).get(0).income );
//            bundle.putString("outcome", FinanceRecord.find(FinanceRecord.class, "day = ? and month = ?", date[0], date[1]).get(0).outcome);
            bundle.putString("day", date[0]);
            bundle.putString("month", date[1]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}