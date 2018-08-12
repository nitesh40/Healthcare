package com.nitesh.firstapp.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nitesh.firstapp.TabFragment.FirstAidFragment;
import com.nitesh.firstapp.TabFragment.HomFragment;

/**
 * Created by Nitesh on 8/11/2018.
 */

public class TabsfragmentAdapter  extends FragmentPagerAdapter {
    private Context context;
    public TabsfragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return HomFragment.getInstance();
        else if (position == 1)
            return FirstAidFragment.getInstance();
//        else if (position == 2)
//            return TankAccsesoriesfragment.getInstance();
        else
            return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Home Page";
            case 1:
                return "First Aid";
//            case 2:
//                return "Tank Accsesories";

        }
        return "";
    }
}
