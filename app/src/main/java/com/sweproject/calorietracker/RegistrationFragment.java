package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.sweproject.calorietracker.DataObjects.Users;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

	private String userName;
	private int dailyCalorieGoal;
	private int dailyProteinGoal;
	private int dailyCarbGoal;
	private MobileServiceTable<Users> userTable;


	
	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		userTable =  MainActivity.mClient.getTable(Users.class);

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
				boolean isRegisterComplete = Register();
				if(isRegisterComplete)
					MainActivity.nextFragment(this, new FragmentCalendar(), null, false, false);
				break;
		}
	}

	public boolean Register() {
		Users newUser = GetNewUserFromView();
		boolean isUserValid = Validate(newUser);
		try {
			if(isUserValid) {
				userTable.insert(newUser);
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Users GetNewUserFromView() {
		Users newUser = new Users();
		String name = ((TextView) getActivity().findViewById(R.id.fragment_registration_name)).getText().toString();
		try {
			newUser.Password = ((TextView) getActivity().findViewById(R.id.fragment_registration_passwordInput)).getText().toString();
			newUser.EmailAddress = ((TextView) getActivity().findViewById(R.id.fragment_registration_userNameInput)).getText().toString();
			newUser.PreferedCalorieLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_caloriesInput)).getText().toString());
			newUser.PreferedProteinLimit =Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_proteinInput)).getText().toString());
			newUser.PreferedCarbLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_carbsInput)).getText().toString());
			newUser.PreferedFatLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_fatsInput)).getText().toString());
			newUser.Weight = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_weight)).getText().toString());
		}catch(NumberFormatException n) {
			Toast.makeText(getActivity(), "You must enter a number for Calories, Protein, Carbs, and Fat!", Toast.LENGTH_SHORT).show();
			n.printStackTrace();
		}
		String[] nameArray = name.toString().split(" ");
		SetName(newUser, nameArray);

		return newUser;
	}

	private boolean Validate(Users newUser) {
		if(newUser.NameFirst.length() == 0) {
			Toast.makeText(getActivity(), "You must enter a name", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(newUser.Password.length() == 0) {
			Toast.makeText(getActivity(), "You must enter a password", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(newUser.PreferedCalorieLimit == 0) {
			Toast.makeText(getActivity(), "You must enter a preferred calorie limit", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(newUser.PreferedProteinLimit == 0) {
			Toast.makeText(getActivity(), "You must enter a preferred protein limit", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(newUser.PreferedCarbLimit == 0) {
			Toast.makeText(getActivity(), "You must enter a preferred carb limit", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(newUser.PreferedFatLimit == 0) {
			Toast.makeText(getActivity(), "You must enter a preferred fat limit", Toast.LENGTH_SHORT).show();
			return false;
		}


		return true;
	}

	private void SetName(Users newUser, String[] nameArray) {
		if (nameArray.length < 1) {
			newUser.NameFirst = nameArray[0];
			newUser.NameLast = nameArray[1];
		} else {
			newUser.NameFirst = nameArray[0];
		}
	}
}
