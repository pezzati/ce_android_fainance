package com.negar.peyman.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageButton;

import com.hollowsoft.library.slidingdrawer.OnDrawerCloseListener;
import com.hollowsoft.library.slidingdrawer.OnDrawerOpenListener;
import com.hollowsoft.library.slidingdrawer.OnDrawerScrollListener;
import com.hollowsoft.library.slidingdrawer.SlidingDrawer;
import com.negar.peyman.myapplication.R;
import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    OvalButton ovalButton;
    int onProgressDay;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    PagerAdapter mPagerAdapter;


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
        onProgressDay = startPosition;



        System.out.println("#### AT first Day is: " + onProgressDay);
        setContentView(R.layout.main_activity);

        final SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.sliding_drawer);

        slidingDrawer.setOnDrawerScrollListener(this);
        slidingDrawer.setOnDrawerOpenListener(this);
        slidingDrawer.setOnDrawerCloseListener(this);

       // LineChart chart = (LineChart) findViewById(R.id.chart);

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
                //onProgressDay++;
                adjustPercentView();

            }
        });

        prev = (ImageButton)findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
                //onProgressDay--;
                adjustPercentView();
            }
        });


        //DayRecord dayRecord1 = new DayRecord("0","0","0","0");
        //dayRecord1.save();

        if(DayRecord.listAll(DayRecord.class).isEmpty()){
            for(int i = 1; i <= 12; i++){
                int dayOfMonth = 31;
                if(i > 6)
                    dayOfMonth = 30;
                if(i == 12)
                    dayOfMonth = 29;
                for(int j = 1; j <= dayOfMonth; j++){
                    DayRecord dayRecord = new DayRecord(String.valueOf(i), String.valueOf(j), "0","0");
                    dayRecord.save();
                }
            }
        }

       // DayRecord.deleteAll(DayRecord.class);
        //SugarRecord.deleteAll(DayRecord.class);

        ovalButton = (OvalButton) findViewById(R.id.Oval_Button_Main);

        InputItem inputItem = new InputItem("درآمد");
        ovalButton.addEditableItemAtIndex(inputItem, 0);

        OutputItem outputItem = new OutputItem("هزینه");
        ovalButton.addEditableItemAtIndex(outputItem, 1);

        adjustPercentView();
    }







    class InputItem extends EditableItem{
        public InputItem(String s){
            super();
            label = s;
        }

        @Override
        public void onTouchEvent() {
            super.onTouchEvent();
            //System.out.println("#######Fuck you " + day);
            ////Open dialog

            final Dialog dialog = new Dialog(Main.this);
            dialog.setContentView(R.layout.get_dialog);
            dialog.setTitle("مبلغ");

            final EditText editText_fee = (EditText) dialog.findViewById(R.id.editText_fee);
            Button button_note = (Button) dialog.findViewById(R.id.button_set);


            dialog.show();

            button_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ////
                    //System.out.println("####### Note for day is:" + editText_fee.getText() + " " + day);

                    String[] d = ((ScreenSlidePagerAdapter)mPagerAdapter).getDayMonth(onProgressDay);
                    List<DayRecord> records =  DayRecord.find(DayRecord.class, "month = ? and day = ?", d[2], d[0]);
                    records.get(0).income = String.valueOf(Integer.valueOf(records.get(0).income) + Integer.valueOf(String.valueOf(editText_fee.getText())));
                    records.get(0).save();
                    System.out.println("$$$$$$$$$  month: " + records.get(0).month + " day: " + records.get(0).day +"  income: " + records.get(0).income);

                    if(!records.get(0).income.equals("0")){
                        int IntIncome = Integer.valueOf(records.get(0).income);
                        int IntOutcome = Integer.valueOf(records.get(0).outcome);
                        IntOutcome  = IntOutcome * 100;
                        ovalButton.setPercent(IntOutcome / IntIncome);
                        //System.out.println("^^^^^^^^^^^^ " + ovalButton.getPercent());
                    }


                    dialog.dismiss();
                }
            });


        }
    }


    class OutputItem extends EditableItem{
        public OutputItem(String s){
            super();
            label = s;
        }
        @Override
        public void onTouchEvent() {
            super.onTouchEvent();

            //System.out.println("##########Output         " + day);

            final Dialog dialog = new Dialog(Main.this);
            dialog.setContentView(R.layout.get_dialog);
            dialog.setTitle("مبلغ");

            final EditText editText_fee = (EditText) dialog.findViewById(R.id.editText_fee);
            Button button_note = (Button) dialog.findViewById(R.id.button_set);


            dialog.show();

            button_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ////
                    System.out.println("####### Note is:" + editText_fee.getText());
                    String[] d = ((ScreenSlidePagerAdapter)mPagerAdapter).getDayMonth(onProgressDay);
                    List<DayRecord> records =  DayRecord.find(DayRecord.class, "month = ? and day = ?", d[2], d[0]);
                    records.get(0).outcome = String.valueOf(Integer.valueOf(records.get(0).outcome) + Integer.valueOf(String.valueOf(editText_fee.getText())));
                    records.get(0).save();
                    System.out.println("$$$$$$$$$  month: " + records.get(0).month + " day: " + records.get(0).day + "  outcome: " + records.get(0).outcome);

                    if(!records.get(0).income.equals("0")){
                        int IntIncome = Integer.valueOf(records.get(0).income);
                        int IntOutcome = Integer.valueOf(records.get(0).outcome);
                        IntOutcome  = IntOutcome * 100;
                        ovalButton.setPercent(IntOutcome / IntIncome);
                        //System.out.println("^^^^^^^^^^^^ " + ovalButton.getPercent());
                    }


                    dialog.dismiss();
                }
            });
        }
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


    private void adjustPercentView(){
        String[] d = ((ScreenSlidePagerAdapter)mPagerAdapter).getDayMonth(onProgressDay);
        List<DayRecord> records = DayRecord.find(DayRecord.class, "month = ? and day = ?", d[2], d[0]);
        if(!records.get(0).income.equals("0")){
            int income = Integer.valueOf(records.get(0).income);
            int outcome = Integer.valueOf(records.get(0).outcome);
            outcome = outcome * 100;
            ovalButton.setPercent(outcome/ income);
            return;
        }
        else
            ovalButton.setPercent(0);

        return;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public String[] getDayMonth(int position){
            String[] date = new String[3];

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
            date[2] = String.valueOf(shamsi.month);
            return date;
        }



        @Override
        public Fragment getItem(int position)
        {
            System.out.println("%%%%%%");
            System.out.println(position);
            if(position - onProgressDay >= 2) {
                onProgressDay++;
                System.out.println("##### Day ++ : " + onProgressDay);
                adjustPercentView();
            }
            else if(onProgressDay - position >= 2) {
                onProgressDay--;
                System.out.println("##### Day -- : " + onProgressDay);
                adjustPercentView();
            }
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle bundle = new Bundle();
            String[] date = getDayMonth(position);
            System.out.println("AAAAAAAA");
            System.out.println(date[0] + " " + date[1]);
//            bundle.putString("income", FinanceRecord.find(FinanceRecord.class, "day = ? and month = ?", date[0], date[1]).get(0).income );
//            bundle.putString("outcome", FinanceRecord.find(FinanceRecord.class, "day = ? and month = ?", date[0], date[1]).get(0).outcome);
            bundle.putString("day", date[0]);
            bundle.putString("month", date[1]);
            bundle.putString("monthInt", date[2]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}