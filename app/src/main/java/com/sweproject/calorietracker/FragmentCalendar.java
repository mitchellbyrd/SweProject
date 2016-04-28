package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;
import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Marcus on 2/5/2016.
 */
public class FragmentCalendar extends Fragment implements CalendarView.OnDateChangeListener, DBDataListener, View.OnClickListener {

	public static final String YEAR = "YEAR";
	public static final String MONTH = "MONTH";
	public static final String DAY = "DAY";

	public static Days currentDay;
	private Bundle mBun;
	private CalendarView mCalendarView;
	private ProgressBar mLoadingProgress;
	private ProgressBar mTodayProgress;
	private TextView mCalendarTitle;
	private TextView mCalorieView;
	private Button mBtn;
	private Long mSelectedDate;

	String mFormattedDate;
	boolean initialDayLoaded;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		mBun = new Bundle();

		initialDayLoaded = false;
		mSelectedDate = mCalendarView.getDate();
		mFormattedDate = new SimpleDateFormat("yyyy-MM-dd").format(mSelectedDate);

		String date[] = mFormattedDate.split("-", 3);

		mBun.putInt(YEAR, Integer.valueOf(date[0]));
		mBun.putInt(MONTH, Integer.valueOf(date[1]) - 1);
		mBun.putInt(DAY, Integer.valueOf(date[2]));

		ExecutableQuery<?> query = new ExecutableQuery<>();
		query.field("UserId").eq(MainActivity.CurrentUser.Id).and().field("Date").eq(mFormattedDate);
		MainActivity.getDBData(Days.class, this, query);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_calendar, container, false);
		this.mCalendarTitle = (TextView) root.findViewById(R.id.fragment_calendar_title);
		this.mCalendarTitle.setText(MainActivity.CurrentUser.NameFirst);

		mCalendarView = (CalendarView) root.findViewById(R.id.fragment_calendar_calendar);
		mCalorieView = (TextView) root.findViewById(R.id.fragment_calendar_calorie);
		mLoadingProgress = (ProgressBar) root.findViewById(R.id.fragment_calendar_progress_loading);
		mTodayProgress = (ProgressBar) root.findViewById(R.id.fragment_calendar_progressbar);
		mBtn = (Button) root.findViewById(R.id.fragment_calendar_btn);

		mLoadingProgress.setVisibility(View.GONE);
		mTodayProgress.setMax(MainActivity.CurrentUser.PreferedCalorieLimit);

		mCalendarView.setOnDateChangeListener(this);
		mBtn.setOnClickListener(this);

		return root;
	}

	@Override
	public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
		// Function is also called when the user scrolls to another month. This will call the fragment only when the date changes
		if (mCalendarView.getDate() != mSelectedDate) {
			mSelectedDate = mCalendarView.getDate();

			mFormattedDate = new SimpleDateFormat("yyyy-MM-dd").format(mSelectedDate);

			mBun.putInt(YEAR, year);
			mBun.putInt(MONTH, month);
			mBun.putInt(DAY, dayOfMonth);
		}
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {

		if (!initialDayLoaded) {
			if (!data.isEmpty()) {
				mTodayProgress.setProgress((int) ((Days) data.get(0)).getCurrentTotalCalorie());
				String t = ((Days) data.get(0)).getCurrentTotalCalorie() + "/" + MainActivity.CurrentUser.PreferedCalorieLimit;
				mCalorieView.setText(t);
			} else {
				mCalorieView.setText("0/" + MainActivity.CurrentUser.PreferedCalorieLimit);
			}

			initialDayLoaded = true;
			return;
		}

		if (data.size() != 0 && isVisible()) {
			for (Object o : data) {
				if (((Days) o).getDate().equals(mFormattedDate)) {
					currentDay = (Days) o;
					mLoadingProgress.setVisibility(View.GONE);
					MainActivity.nextFragment(this, new FragmentDay(), mBun, true, false, 0);
					return;
				}
			}
		}
		MainActivity.insertDBData(Days.class, this, new Days(mFormattedDate, MainActivity.CurrentUser.Id), true);
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		if (isVisible()) {
			mLoadingProgress.setVisibility(View.GONE);
		}
		Toast.makeText(getActivity(), "Calendar - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() { /* Ignore */ }

	@Override
	public void onGoodInsertReturn(Object obj) {
		currentDay = (Days) obj;
		mLoadingProgress.setVisibility(View.GONE);
		MainActivity.nextFragment(this, new FragmentDay(), mBun, true, false, 0);
	}

	@Override
	public void onClick(View view) {
		mLoadingProgress.setVisibility(View.VISIBLE);
		MainActivity.getDBData(Days.class, this, "UserId", MainActivity.CurrentUser.Id);
	}
}