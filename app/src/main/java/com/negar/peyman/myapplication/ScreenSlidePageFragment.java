package com.negar.peyman.myapplication;

/**
 * Created by negar on 12/5/14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



public class ScreenSlidePageFragment extends Fragment {

    String day;
    String month;
    ImageButton next;

    String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    String monthPer[] = {};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments() != null ? getArguments().getString("day") : "fffff";
        month = getArguments() != null ? getArguments().getString("month") : "fffff";
        String montht = months[0];
        System.out.println("@@@@@@@@");
        System.out.println(montht);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        View tv = v.findViewById(R.id.date_text);
        ((TextView) tv).setText(day + " - " + month);

        return v;
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_screen_slide_page, container, false);
//
//
//        return rootView;
    }
}
