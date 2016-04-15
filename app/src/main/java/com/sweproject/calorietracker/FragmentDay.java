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
import com.sweproject.calorietracker.Callbacks.DeleteFoodListenerResponse;
import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.ListViewAdapters.AdapterDayFood;

import java.util.ArrayList;

/**
 * Created by Marcus on 2/15/2016.
 */
public class FragmentDay extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, DBDataListener, DeleteFoodListenerResponse {

	/**
	 * Foods added by the FragmentFoodAdd class are placed here to be displayed in the Fragment Day listview
	 **/
	public static ArrayList<Foods> sAddedFoodList;
	public static ListView sFoodList;
	public static Food_Day sSelectedFoodDay;

	private ProgressBar mCalBar;
	private ProgressBar mProBar;
	private ProgressBar mCarBar;
	private ProgressBar mFatBar;
	private ProgressBar mLoading;

	private Bundle mBundle;
	private ArrayList<Food_Day> mFoodDayList;
	private ArrayList<ServingSizes> mServingList;
	private ArrayList<Foods> mFoodsList;

	private DeleteFoodListener listener;

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

		listener = new DeleteFoodListener(this);

		String date = getMonth(month) + " " + day + ", " + year;

		TextView title = (TextView) getActivity().findViewById(R.id.fragment_calendar_day_textview_title);
		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_calendar_fab);
		mCalBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_calorie);
		mProBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_proteins);
		mCarBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_carbs);
		mFatBar = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progressbar_fats);
		mLoading = (ProgressBar) getActivity().findViewById(R.id.fragment_calendar_day_progress_loading);

		title.setText(date);
		fab.setOnClickListener(this);

		sFoodList = (ListView) getActivity().findViewById(R.id.fragment_calendar_day_listview);
		sFoodList.setAdapter(new AdapterDayFood(getActivity(), sAddedFoodList, listener));
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
		//getFoods(mFoodDayList.size());
	}

	private void getFoods(int count) {
		if (mServingList.size() == count) {
			sAddedFoodList.clear();
			for (ServingSizes o : mServingList) {
				MainActivity.getDBData(Foods.class, this, "id", o.getFoodId());
			}
			updateListviewData(sAddedFoodList.size());
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

		for (int i = 0; i < mFoodDayList.size(); i++) {
			if (mFoodDayList.get(i).getServingSizeId().equals(mServingList.get(i).getId())) {
				cal += (mServingList.get(i).getCalories() * mFoodDayList.get(i).getNumberOfServings());
				pro += (mServingList.get(i).getProteins() * mFoodDayList.get(i).getNumberOfServings());
				car += (mServingList.get(i).getCarbs() * mFoodDayList.get(i).getNumberOfServings());
				fat += (mServingList.get(i).getFats() * mFoodDayList.get(i).getNumberOfServings());
			}
		}

		mCalBar.setProgress(Math.round(cal));
		mProBar.setProgress(Math.round(pro));
		mCarBar.setProgress(Math.round(car));
		mFatBar.setProgress(Math.round(fat));
		mLoading.setVisibility(View.GONE);
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
		for (ServingSizes s : mServingList) {
			if (s.getFoodId().equals(sAddedFoodList.get(i).getId())) {
				for (Food_Day f : mFoodDayList) {
					if (f.getServingSizeId().equals(s.getId())) {
						sSelectedFoodDay = f;
						MainActivity.nextFragment(this, new FragmentFoodEdit(), getArguments(), true, false);
						return;
					}
				}
			}
		}
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		if (!data.isEmpty() && isVisible()) {
			if (data.get(0) instanceof Food_Day) {
				for (Object o : data) {
					mFoodDayList.add((Food_Day) o);
				}
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
		} else {
			mLoading.setVisibility(View.GONE);
		}
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		mLoading.setVisibility(View.GONE);
		Toast.makeText(getActivity(), "Day - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() {
	}

	@Override
	public void onGoodInsertReturn(Object obj) {
	}

	@Override
	public void onFoodDeleted(ServingSizes s, Food_Day d) {
		mCalBar.setProgress(mCalBar.getProgress() - (int) (s.getCalories() * d.getNumberOfServings()));
		mProBar.setProgress(mProBar.getProgress() - (int) (s.getProteins() * d.getNumberOfServings()));
		mCarBar.setProgress(mCarBar.getProgress() - (int) (s.getCarbs() * d.getNumberOfServings()));
		mFatBar.setProgress(mFatBar.getProgress() - (int) (s.getFats() * d.getNumberOfServings()));
	}
}
