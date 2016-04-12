package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.Callbacks.DeleteFoodListener;
import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.ListViewAdapters.AdapterDayFood;

import java.util.ArrayList;

/**
 * Created by Marcus on 2/15/2016.
 */
public class FragmentDay extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, DBDataListener {

	/**
	 * Foods added by the FragmentFoodAdd class are placed here to be displayed in the Fragment Day listview
	 **/
	public static ArrayList<Foods> sAddedFoodList;
	public static ListView sFoodList;

	private ProgressBar mCalBar;
	private ProgressBar mProBar;
	private ProgressBar mCarBar;
	private ProgressBar mFatBar;

	private Bundle mBundle;
	private ArrayList<Food_Day> mFoodDayList;
	private ArrayList<ServingSizes> mServingList;
	private ArrayList<Foods> mFoodsList;

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

		mFoodDayList = new ArrayList<>();
		mServingList = new ArrayList<>();
		mFoodsList = new ArrayList<>();
		sAddedFoodList = new ArrayList<>();

		String date = getMonth(month) + " " + day + ", " + year;

		TextView title = (TextView) getActivity().findViewById(R.id.fragment_calendar_day_textview_title);
		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_calendar_fab);
		mCalBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_calorie);
		mProBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_proteins);
		mCarBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_carbs);
		mFatBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_fats);


		title.setText(date);
		fab.setOnClickListener(this);

		sFoodList = (ListView) getActivity().findViewById(R.id.fragment_calendar_day_listview);
		sFoodList.setAdapter(new AdapterDayFood(getActivity(), sAddedFoodList, new DeleteFoodListener()));
		sFoodList.setOnItemClickListener(this);

		Toast.makeText(getActivity(), "Getting Food days", Toast.LENGTH_SHORT).show();
		MainActivity.getDBData(Food_Day.class, this, "DayId", FragmentCalendar.currentDay.getId());
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

	private void getServings() {
		for (Food_Day o : mFoodDayList) {
			MainActivity.getDBData(ServingSizes.class, this, "id", o.getServingSizeId());
		}
		getFoods(mFoodDayList.size());
	}

	private void getFoods(int count) {
		if (mServingList.size() == count) {
			for (ServingSizes o : mServingList) {
				MainActivity.getDBData(Foods.class, this, "id", o.getFoodId());
			}
			sAddedFoodList.clear();
			updateListviewData(mServingList.size());
		}
	}

	private void updateListviewData(int count) {
		if (count == mServingList.size()) {
			((AdapterDayFood) sFoodList.getAdapter()).setData(sAddedFoodList);
			updateProgressBars();
		}
	}

	private void updateProgressBars() {

		Float cal = 0f;
		Float pro = 0f;
		Float car = 0f;
		Float fat = 0f;

		for (ServingSizes s : mServingList) {
			cal += s.getCalories();
			pro += s.getProteins();
			car += s.getCarbs();
			fat += s.getFats();
		}

		mCalBar.setProgress(Math.round(cal));
		mProBar.setProgress(Math.round(pro));
		mCarBar.setProgress(Math.round(car));
		mFatBar.setProgress(Math.round(fat));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_calendar_fab:
				MainActivity.nextFragment(this, new FragmentFoodSearch(), mBundle, true, false);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		getArguments().putInt("Index", i);
		MainActivity.nextFragment(this, new FragmentFoodEdit(), getArguments(), true, false);
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		if (!data.isEmpty()) {
			if (data.get(0) instanceof Food_Day) {
				for (Object o : data) {
					mFoodDayList.add((Food_Day) o);
				}
				Toast.makeText(getActivity(), "Getting Serving", Toast.LENGTH_SHORT).show();
				getServings();
			} else if (data.get(0) instanceof ServingSizes) {
				// In this case, there should only be one value returned at a time
				mServingList.add((ServingSizes) data.get(0));
				getFoods(mFoodDayList.size());
			} else { // Foods
				Toast.makeText(getActivity(), "Getting Food", Toast.LENGTH_SHORT).show();
				// In this case, there should only be one value returned at a time
				sAddedFoodList.add((Foods) data.get(0));
				updateListviewData(mServingList.size());
			}
		}
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		Toast.makeText(getActivity(), "Day - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() {

	}

	@Override
	public void onGoodInsertReturn(Object obj) {

	}
}
