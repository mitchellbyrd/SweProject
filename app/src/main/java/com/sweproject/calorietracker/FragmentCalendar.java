package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

/**
 * Created by Marcus on 2/5/2016.
 */
public class FragmentCalendar extends Fragment implements CalendarView.OnDateChangeListener {

	public static final String YEAR = "YEAR";
	public static final String MONTH = "MONTH";
	public static final String DAY = "DAY";
	private CalendarView mCalendarView;
	private TextView mCalendarTitle;
	private Long mSelectedDate;

	@Override
	public void onActivityCreated(Bundle savedInstance){
		super.onActivityCreated(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_calendar, container, false);
		this.mCalendarTitle = (TextView) root.findViewById(R.id.fragment_calendar_title);
		this.mCalendarTitle.setText(MainActivity.CurrentUser.NameFirst);
		mCalendarView = (CalendarView) root.findViewById(R.id.fragment_calendar_calendar);
		mCalendarView.setOnDateChangeListener(this);
		mSelectedDate = mCalendarView.getDate();

		return root;
	}

	@Override
	public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
		// Function is also called when the user scrolls to another month. This will call the fragment only when the date changes
		if (mCalendarView.getDate() != mSelectedDate) {
			mSelectedDate = mCalendarView.getDate();

			Bundle bun = new Bundle();
			bun.putInt(YEAR, year);
			bun.putInt(MONTH, month);
			bun.putInt(DAY, dayOfMonth);
			MainActivity.nextFragment(this, new FragmentDay(), bun, true, false);
		}
	}
}