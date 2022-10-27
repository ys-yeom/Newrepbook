package com.example.newrepbook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.newrepbook.Fragment.Fragment5;
import com.example.newrepbook.Fragment.Fragment6;
import com.example.newrepbook.Fragment.Fragment7;
import com.example.newrepbook.Fragment.Fragment8;

public class pagerAdapter2 extends FragmentStatePagerAdapter {
    public pagerAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment5();
            case 1:
                return new Fragment6();
            case 2:
                return new Fragment7();
            case 3:
                return new Fragment8();
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
