package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/15/2016.
 */
public class FragmentFoodEdit extends Fragment implements View.OnClickListener, DBDataListener {

	private int mSelectedIndex;
	private Foods mFood;
	private ArrayList<ServingSizes> mServingSizes;

	private TextView mTitle;
	private TextView mCalorie;
	private TextView mCarbs;
	private TextView mPro;
	private TextView mFats;

	private Spinner mServingSpin;
	private Spinner mTypeSpin;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		mSelectedIndex = 0;
		String[] mSizes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		mFood = FragmentDay.sAddedFoodList.get(getArguments().getInt("Index"));
		mServingSizes = new ArrayList<>();

		mTitle = (TextView) getActivity().findViewById(R.id.fragment_food_title);
		mCalorie = (TextView) getActivity().findViewById(R.id.fragment_food_value_calorie);
		mCarbs = (TextView) getActivity().findViewById(R.id.fragment_food_value_carbs);
		mPro = (TextView) getActivity().findViewById(R.id.fragment_food_value_protein);
		mFats = (TextView) getActivity().findViewById(R.id.fragment_food_value_fats);

		mServingSpin = (Spinner) getActivity().findViewById(R.id.fragment_food_spinner_serving);
		mTypeSpin = (Spinner) getActivity().findViewById(R.id.fragment_food_spinner_serving_type);
		Button submit = (Button) getActivity().findViewById(R.id.fragment_food_btn_submit);

		submit.setText("Update");
		mTitle.setText(mFood.getName());
		mServingSpin.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mSizes));

		mTypeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (mServingSizes.size() != 0) {
					mSelectedIndex = i;
					mCalorie.setText(String.valueOf(mServingSizes.get(i).getCalories() * (mServingSpin.getSelectedItemPosition() + 1)));
					mCarbs.setText(String.valueOf(mServingSizes.get(i).getCarbs() * (mServingSpin.getSelectedItemPosition() + 1)));
					mPro.setText(String.valueOf(mServingSizes.get(i).getProteins() * (mServingSpin.getSelectedItemPosition() + 1)));
					mFats.setText(String.valueOf(mServingSizes.get(i).getFats() * (mServingSpin.getSelectedItemPosition() + 1)));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		mServingSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (mServingSizes.size() != 0) {
					mCalorie.setText(String.valueOf(mServingSizes.get(mSelectedIndex).getCalories() * (i + 1)));
					mCarbs.setText(String.valueOf(mServingSizes.get(mSelectedIndex).getCarbs() * (i + 1)));
					mPro.setText(String.valueOf(mServingSizes.get(mSelectedIndex).getProteins() * (i + 1)));
					mFats.setText(String.valueOf(mServingSizes.get(mSelectedIndex).getFats() * (i + 1)));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			}
		});
		submit.setOnClickListener(this);

		MainActivity.getDBData(ServingSizes.class, this, "FoodId", mFood.getId());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_food, container, false);
	}

	@Override
	public void onClick(View view) {
		FragmentDay.sSelectedFoodDay.setServingSizeId(mServingSizes.get(mSelectedIndex).getId());
		FragmentDay.sSelectedFoodDay.setNumberOfServings(mServingSpin.getSelectedItemPosition() + 1);
		MainActivity.updateDBData(Food_Day.class, FragmentDay.sSelectedFoodDay);
		MainActivity.nextFragment(this, new FragmentDay(), getArguments(), true, false, 2);
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		if (isVisible()) {
			String[] type = new String[data.size()];

			for (Object o : data) {
				type[mServingSizes.size()] = ((ServingSizes) o).getServingSizeType();
				mServingSizes.add((ServingSizes) o);
			}

			mTypeSpin.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, type));
			mTypeSpin.setSelection(0);
			mServingSpin.setSelection(0);

			mCalorie.setText(String.valueOf(mServingSizes.get(0).getCalories()));
			mCarbs.setText(String.valueOf(mServingSizes.get(0).getCarbs()));
			mPro.setText(String.valueOf(mServingSizes.get(0).getProteins()));
			mFats.setText(String.valueOf(mServingSizes.get(0).getFats()));
		}
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		Toast.makeText(getActivity(), "FoodAdd - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() {
		// GetArguments returns the date back to the Day fragment
		//MainActivity.nextFragment(this, new FragmentDay(), getArguments(), true, true, 2);
	}

	@Override
	public void onGoodInsertReturn(Object obj) { /* Ignore */ }
}
