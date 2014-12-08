package com.negar.peyman.myapplication;

/**
 * Created by negar on 12/5/14.
 */
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] rank;
    String[] stdID;
    int[] flag;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] rank,
                            String[] stdID, int[] flag) {
        this.context = context;
        this.rank = rank;
        this.stdID = stdID;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return rank.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView txtrank;
        TextView txt_id;

        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        //txtrank = (TextView) itemView.findViewById(R.id.hw);
        txt_id = (TextView) itemView.findViewById(R.id.label_id);


        // Capture position and set to the TextViews
        //txtrank.setText(rank[position]);
        txt_id.setText(stdID[position]);


        // Locate the ImageView in viewpager_item.xml
        //imgflag = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView
       // imgflag.setImageResource(flag[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }


    public Fragment getItem(int position) {
        Fragment frag = new ScreenSlidePageFragment();
        switch (position) {
            case 0:
                frag = new ScreenSlidePageFragment(); //Fragment of first Page
                break;
            case 1:
                return new ScreenSlidePageFragment(); //Fragment of second page along with the position so the fragment could do something with it
            case 3:
                frag = new ScreenSlidePageFragment();
                break;
            case 4:
                frag = new ScreenSlidePageFragment();
                break;
        }
        return frag;
    }
}
