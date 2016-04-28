package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.Callbacks.OnDialogDismissListener;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.ListViewAdapters.AdapterCreateFood;

import java.util.ArrayList;

/**
 * Created by jedwa145 on 2/23/2016.
 */
public class CreateFood extends Fragment implements View.OnClickListener, OnDialogDismissListener, DBDataListener {

	// Counts how many times goodInsert was called to determine if all inserts were done
	private int mCount = 0;

	private TextView mFoodName;
	private ListView mCreateList;
	private FloatingActionButton mFab;
	private Button mCreateBtn;
	private ProgressBar mProgBar;

	private ArrayList<ServingSizes> mServingSizes;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		mServingSizes = new ArrayList<>();

		mFoodName = (TextView) getActivity().findViewById(R.id.create_food_name_edit);
		mCreateList = (ListView) getActivity().findViewById(R.id.create_food_listview);
		mFab = (FloatingActionButton) getActivity().findViewById(R.id.create_food_fab);
		mCreateBtn = (Button) getActivity().findViewById(R.id.create_food_btn);
		mProgBar = (ProgressBar) getActivity().findViewById(R.id.create_food_prog_bar);

		mFab.setOnClickListener(this);
		mCreateBtn.setOnClickListener(this);
		mProgBar.setVisibility(View.GONE);
		mCreateList.setAdapter(new AdapterCreateFood(getActivity(), mServingSizes));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.create_food, container, false);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.create_food_fab:
				DialogServingAdd dialogServingAdd = new DialogServingAdd();
				dialogServingAdd.setOnDismissListener(this);
				dialogServingAdd.show(getActivity().getSupportFragmentManager(), null);
				break;
			case R.id.create_food_btn:
				if (validate()) {
					mProgBar.setVisibility(View.VISIBLE);
					// After a successful insert, the callback, onGoodInsertReturn, will be called with the id needed
					MainActivity.insertDBData(Foods.class, this, new Foods(mFoodName.getText().toString(), MainActivity.CurrentUser.Id), true);
				}
				break;
		}
	}

	private boolean validate() {
		if (mFoodName.getText().toString().isEmpty()) {
			Toast.makeText(getActivity(), "A food must have a name", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "May I suggest Waffles?", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mServingSizes.isEmpty()) {
			Toast.makeText(getActivity(), "You need at least one serving", Toast.LENGTH_SHORT).show();
			Toast.makeText(getActivity(), "Tap the + button to add one", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	@Override
	public void onDismiss(ServingSizes obj) {
		mServingSizes.add(obj);
		((AdapterCreateFood) mCreateList.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) { /* Ignore */ }

	@Override
	public void onBadDataReturn(Exception exception) {
		mProgBar.setVisibility(View.GONE);
		Toast.makeText(getActivity(), "CreateFood - Server Error", Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() {
		mCount++;
		if (mCount == mServingSizes.size()) {
			mProgBar.setVisibility(View.GONE);
			MainActivity.nextFragment(this, new FragmentFoodSearch(), getArguments(), true, false, 2);
		}
	}

	@Override
	public void onGoodInsertReturn(Object obj) {
		String foodID = ((Foods) obj).getId();
		for (ServingSizes s : mServingSizes) {
			s.setFoodId(foodID);
			MainActivity.insertDBData(ServingSizes.class, this, s, false);
		}
	}
}
