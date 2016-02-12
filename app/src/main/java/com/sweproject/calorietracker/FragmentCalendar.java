package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

/**
 * Created by Marcus on 2/5/2016.
 */
public class FragmentCalendar extends Fragment{

	private CalendarView mCalendarView;

	@Override
	public void onActivityCreated(Bundle savedInstance){
		super.onActivityCreated(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_calendar, container, false);

		mCalendarView = (CalendarView) root.findViewById(R.id.fragment_calendar_calendar);

		return root;
	}
}
