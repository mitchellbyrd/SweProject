package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Marcus on 2/15/2016.
 */
public class FragmentDay extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		Bundle bundle = getArguments();

		int day = 0;
		int month = 0;
		int year = 0;

		if (bundle != null) {
			day = bundle.getInt(FragmentCalendar.DAY, -1);
			month = bundle.getInt(FragmentCalendar.MONTH, -1);
			year = bundle.getInt(FragmentCalendar.YEAR, -1);
		}

		String date = getMonth(month) + " " + day + ", " + year;

		TextView title = (TextView) getActivity().findViewById(R.id.fragment_calendar_day_textview_title);
		title.setText(date);

		ListView foodList = (ListView) getActivity().findViewById(R.id.fragment_calendar_day_listview);
		foodList.setAdapter(new AdapterDayFood(getActivity()));
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
}