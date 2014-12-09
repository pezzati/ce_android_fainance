package com.negar.peyman.myapplication;

/**
 * Created by negar on 12/5/14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScreenSlidePageFragment extends Fragment {

    String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments() != null ? getArguments().getString("salaam") : "fffff";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        View tv = v.findViewById(R.id.date_text);
        ((TextView)tv).setText("Fragment #" + date);

        return v;
//        ViewGroup rootView = (ViewGroup) inflater.inflate(
//                R.layout.fragment_screen_slide_page, container, false);
//
//
//        return rootView;
    }
}
