package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.widget.TextView;

/**
 * Created by Marcus on 2/5/2016.
 */
public class FragmentCalendar extends Fragment implements CalendarView.OnDateChangeListener, DBDataListener {

	public static final String YEAR = "YEAR";
	public static final String MONTH = "MONTH";
	public static final String DAY = "DAY";

	public static Days currentDay;
	private Bundle mBun;
	private CalendarView mCalendarView;
	private TextView mCalendarTitle;
	private Long mSelectedDate;

	String mFormattedDate;

	@Override
	public void onActivityCreated(Bundle savedInstance){
		super.onActivityCreated(savedInstance);
		mBun = new Bundle();
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

			mFormattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(mSelectedDate);

			mBun.putInt(YEAR, year);
			mBun.putInt(MONTH, month);
			mBun.putInt(DAY, dayOfMonth);

			Toast.makeText(getActivity(), "Calendar - Looking for day", Toast.LENGTH_SHORT).show();
			MainActivity.getDBData(Days.class, this, "Date", mFormattedDate);
		}
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		if (data.size() != 0) {
			currentDay = (Days) data.get(0);
			MainActivity.nextFragment(this, new FragmentDay(), mBun, true, false);
		} else {
			Toast.makeText(getActivity(), "Calendar - Making new day", Toast.LENGTH_SHORT).show();
			MainActivity.insertDBData(Days.class, this, new Days(mFormattedDate, "0fad13aa-400f-4785-bcc8-eb79f7755733"), true);
		}

	}

	@Override
	public void onBadDataReturn(Exception exception) {
		Toast.makeText(getActivity(), "Calendar - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() { /* Ignore */ }

	@Override
	public void onGoodInsertReturn(Object obj) {
		Toast.makeText(getActivity(), "Calendar - Good", Toast.LENGTH_SHORT).show();
		currentDay = (Days) obj;
		MainActivity.nextFragment(this, new FragmentDay(), mBun, true, false);
	}
}