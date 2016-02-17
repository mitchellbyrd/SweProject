package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

	private String userName;
	private int dailyCalorieGoal;
	private int dailyProteinGoal;
	private int dailyCarbGoal;
	
	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		Button submitBtn = (Button) getActivity().findViewById(R.id.fragment_registration_submitBtn);
		submitBtn.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_registration, container, false);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_registration_submitBtn:
				MainActivity.nextFragment(this, new FragmentCalendar(), null, false, false);
				break;
		}
	}

	public void Register(View view)
	{
		//register user
	}
}
