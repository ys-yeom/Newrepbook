package com.example.newrepbook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.newrepbook.Fragment.Fragment1;
import com.example.newrepbook.Fragment.Fragment2;
import com.example.newrepbook.Fragment.Fragment3;
import com.example.newrepbook.Fragment.Fragment4;

public class pagerAdapter extends FragmentStatePagerAdapter {
    public pagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // total page count
        return 4;
    }

}
