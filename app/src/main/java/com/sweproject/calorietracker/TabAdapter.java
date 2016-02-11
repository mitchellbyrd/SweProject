package com.sweproject.calorietracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * This is where the viewpager goes to get its views.
 * Created by Marcus on 2/3/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {

	public TabAdapter(FragmentManager fm) { super(fm); }

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		switch (position){
			case 1:
				return new TabFragment();
			case 2:
				return new TabFragment();
			case 3:
				return new TabFragment();
			default:
				return new TabFragment();
		}
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case 0:
				return "SECTION 1";
			case 1:
				return "SECTION 2";
			case 2:
				return "SECTION 3";
		}
		return null;
	}
}
