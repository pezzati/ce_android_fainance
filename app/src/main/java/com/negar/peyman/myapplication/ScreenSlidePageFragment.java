package com.negar.peyman.myapplication;

/**
 * Created by negar on 12/5/14.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;


public class ScreenSlidePageFragment extends Fragment {

    String day;
    String month;
    String monthInt;
    //InputItem inputItem;
    //OutputItem outputItem;
    //OvalButton ovalButton;
    String income;
    String outcome;
    LayoutInflater Myinflater;
    ViewGroup MyContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        day = getArguments() != null ? getArguments().getString("day") : "fffff";
        month = getArguments() != null ? getArguments().getString("month") : "fffff";
        monthInt = getArguments() != null ? getArguments().getString("monthInt") : "fffff";
        income = getArguments() != null ? getArguments().getString("income") : "fffff";
        outcome = getArguments() != null ? getArguments().getString("outcome") : "fffff";

        System.out.println("$%$%$%$%$%$% Day:"  + day);

       // View v = Myinflater.inflate(R.layout.fragment_screen_slide_page, MyContainer, false);


        //System.out.println("**T&^%&**(&Y*&^%&%*&^   record.month   day" + records.get(0).month + " " + records.get(0).day + "  " + records.size());

        //ovalButton.setPercent(50);
        //setPercentByDay();
        super.onCreate(savedInstanceState);

    }
/*
    private void setPercentByDay(){
        List<DayRecord> records =  DayRecord.find(DayRecord.class, "month = ? and day = ?", monthInt, day);
        if(!records.get(0).income.equals("0")){

            int IntIncome = Integer.valueOf(records.get(0).income);
            int IntOutcome = Integer.valueOf(records.get(0).outcome);
            IntOutcome  = IntOutcome * 100;
            ovalButton.setPercent(IntOutcome / IntIncome);
            //System.out.println("^^^^^^^^^^^^ " + ovalButton.getPercent());
        }
    }
*/
    View tv, v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Myinflater = inflater;
        MyContainer = container;
        v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        tv = v.findViewById(R.id.date_text);
        ((TextView) tv).setText(day + " - " + month);
        tv = v.findViewById(R.id.textView_income);
        ((TextView) tv).setText(income + " " + "تومان");
        tv = v.findViewById(R.id.textView_outcome);
        ((TextView) tv).setText(outcome+ " " + "تومان");
        tv = v.findViewById(R.id.textView_precent);
        int a = 0;
        if(Integer.valueOf(income) != 0){
            a = (Integer.valueOf(outcome)*100)/Integer.valueOf(income);
        }
        ((TextView) tv).setText(a + " " + "٪");
//        Button dayView = (Button) v.findViewById(R.id.button_dayView);
//        dayView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<DayRecord> allDays = DayRecord.listAll(DayRecord.class);
//                for(int i = 0; i < allDays.size(); i++) {
//                    allDays.get(i).income = "0";
//                    allDays.get(i).outcome = "0";
//                    allDays.get(i).save();
//                }
//                System.out.println("!!!!!!!!!!!!!!! dayView: " + day);
//            }
//        });

        /*
        ovalButton = (OvalButton) v.findViewById(R.id.pager_OvalButton);





        inputItem = new InputItem( "درآمد");
        ovalButton.addEditableItemAtIndex(inputItem, 0);

        outputItem = new OutputItem( "هزینه");
        ovalButton.addEditableItemAtIndex(outputItem, 1);
        */
        return v;
    }

    public void set(String income, String outcome){
        tv = v.findViewById(R.id.textView_income);
        ((TextView) tv).setText(income + " " + "تومان");
        tv = v.findViewById(R.id.textView_outcome);
        ((TextView) tv).setText(outcome + " " + "تومان");
        tv = v.findViewById(R.id.textView_precent);
        int a = 0;
        if(Integer.valueOf(income) != 0){
            a = (Integer.valueOf(outcome)*100)/Integer.valueOf(income);
        }

        ((TextView) tv).setText(a + " " + "٪");
    }
/*
    class InputItem extends EditableItem{
        public InputItem(String s){
            super();
            label = s;
        }

        @Override
        public void onTouchEvent() {
            super.onTouchEvent();
            System.out.println("#######Fuck you " + day);
            ////Open dialog

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.get_dialog);
            dialog.setTitle("مبلغ");

            final EditText editText_fee = (EditText) dialog.findViewById(R.id.editText_fee);
            Button button_note = (Button) dialog.findViewById(R.id.button_set);


            dialog.show();

            button_note.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ////
                    System.out.println("####### Note for day is:" + editText_fee.getText() + " " + day);

                    List<DayRecord> records =  DayRecord.find(DayRecord.class, "month = ? and day = ?", monthInt, day);
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

            System.out.println("##########Output         " + day);

            final Dialog dialog = new Dialog(getActivity());
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
                    List<DayRecord> records =  DayRecord.find(DayRecord.class, "month = ? and day = ?", monthInt, day);
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

    */
}


