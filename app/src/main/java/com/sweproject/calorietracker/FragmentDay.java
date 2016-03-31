package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Marcus on 2/15/2016.
 */
public class FragmentDay extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

	private Bundle mBundle;
	public static ListView foodList;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		int day;
		int month;
		int year;

		if (mBundle != null) {
			day = mBundle.getInt(FragmentCalendar.DAY, -1);
			month = mBundle.getInt(FragmentCalendar.MONTH, -1);
			year = mBundle.getInt(FragmentCalendar.YEAR, -1);
		} else {
			mBundle = getArguments();
			day = mBundle.getInt(FragmentCalendar.DAY, -1);
			month = mBundle.getInt(FragmentCalendar.MONTH, -1);
			year = mBundle.getInt(FragmentCalendar.YEAR, -1);
		}

		String date = getMonth(month) + " " + day + ", " + year;

		TextView title = (TextView) getActivity().findViewById(R.id.fragment_calendar_day_textview_title);
		title.setText(date);

		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_calendar_fab);
		fab.setOnClickListener(this);

		foodList = (ListView) getActivity().findViewById(R.id.fragment_calendar_day_listview);
		foodList.setAdapter(new AdapterDayFood(getActivity(), new DeleteFoodListener()));
		foodList.setOnItemClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_calendar_day, container, false);
	}

	private String getMonth(int month) {
		switch (month) {
			case 0:
				return "January";
			case 1:
				return "February";
			case 2:
				return "March";
			case 3:
				return "April";
			case 4:
				return "May";
			case 5:
				return "June";
			case 6:
				return "July";
			case 7:
				return "August";
			case 8:
				return "September";
			case 9:
				return "October";
			case 10:
				return "November";
			case 11:
				return "December";
			default:
				return "Invalid Month";
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_calendar_fab:
				MainActivity.nextFragment(this, new FragmentFoodSearch(), getArguments(), true, false);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Bundle bun = getArguments();
		bun.putInt("Index", i);
		MainActivity.nextFragment(this, new FragmentFoodEdit(), bun, true, false);
	}
}
