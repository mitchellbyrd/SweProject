package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.sweproject.calorietracker.DataObjects.Users;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
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
				Register(view);
				MainActivity.nextFragment(this, new FragmentCalendar(), null, false, false);
				break;
		}
	}

	public void Register(View view) {
		final Users newUser = new Users();
		TextView name = (TextView) getActivity().findViewById(R.id.fragment_registration_name);
		TextView password = (TextView) getActivity().findViewById(R.id.fragment_registration_passwordInput);
		String[] nameArray = name.getText().toString().split(" ");
		if (nameArray.length < 1) {
			newUser.NameFirst = nameArray[0];
			newUser.NameLast = nameArray[1];
		} else {
			newUser.NameFirst = nameArray[0];
		}

		newUser.Password = password.getText().toString();

		try {

						newUser.BirthDate = "01/22/1992";
						newUser.EmailAddress = "doneal2014@gmail.com";
						newUser.PreferedCalorieLimit = 100;
						newUser.PreferedCarbLimit = 100;
						newUser.PreferedFatLimit = 100;
						newUser.PreferedProteinLimit = 100;
						newUser.Weight = 100;
						newUser.NameLast = "";
						userTable.insert(newUser);

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
}
