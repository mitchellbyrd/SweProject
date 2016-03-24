package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

	private MobileServiceTable<Users> userTable;
	private MobileServiceList<Users> currentUsers;
	private static ProgressBar spinner;


	
	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		Button submitBtn = (Button) getActivity().findViewById(R.id.fragment_registration_submitBtn);
		spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar2);
		userTable = MainActivity.mClient.getTable(Users.class);
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
				spinner.setVisibility(View.VISIBLE);
				try {
					Register();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (MobileServiceException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	public void Register() throws InterruptedException, ExecutionException, MobileServiceException {
		final Users newUser = GetNewUserFromView();
		boolean isUserValid = Validate(newUser);
		try {
			if(isUserValid) {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						try {
							MobileServiceList<Users> users = userTable.where().field("EmailAddress").eq(newUser.EmailAddress).execute().get();
							if(users.getTotalCount() == 0) {
								userTable.insert(newUser);
								users = userTable.where().field("EmailAddress").eq(newUser.EmailAddress).execute().get();
								MainActivity.CurrentUser = users.get(0);
								MainActivity.nextFragment(new RegistrationFragment(), new FragmentCalendar(), null, false, false);
							}
							else {
								new Handler(Looper.getMainLooper()).post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(getActivity(), "A user with that email address already exists!", Toast.LENGTH_SHORT).show();
										spinner.setVisibility(View.INVISIBLE);
									}
								});
							}
						} catch (Exception exception) {
							new Handler(Looper.getMainLooper()).post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(getActivity(), "Uh Oh Something Happened. Please try again!", Toast.LENGTH_SHORT).show();
								}
							});
						}
						return null;
					}
				}.execute();
			}
		}
		catch(Exception e) {
			spinner.setVisibility(View.INVISIBLE);
			e.printStackTrace();
		}

	}

	private Users GetNewUserFromView() {
		Users newUser = new Users();
		String name = ((TextView) getActivity().findViewById(R.id.fragment_registration_name)).getText().toString();
		try {
			newUser.Password = ((TextView) getActivity().findViewById(R.id.fragment_registration_passwordInput)).getText().toString().trim();
			newUser.EmailAddress = ((TextView) getActivity().findViewById(R.id.fragment_registration_userNameInput)).getText().toString().trim();
			newUser.PreferedCalorieLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_caloriesInput)).getText().toString().trim());
			newUser.PreferedProteinLimit =Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_proteinInput)).getText().toString().trim());
			newUser.PreferedCarbLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_carbsInput)).getText().toString().trim());
			newUser.PreferedFatLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_fatsInput)).getText().toString().trim());
			newUser.Weight = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_registration_weight)).getText().toString().trim());
		}catch(NumberFormatException n) {
			Toast.makeText(getActivity(), "You must enter a number for Calories, Protein, Carbs, and Fat!", Toast.LENGTH_SHORT).show();
			n.printStackTrace();
		}
		String[] nameArray = name.toString().split(" ");
		SetName(newUser, nameArray);

		return newUser;
	}

	private boolean Validate(Users newUser) throws MobileServiceException, ExecutionException, InterruptedException {
		if(newUser.NameFirst.length() == 0) {
			Toast.makeText(getActivity(), "You must enter a name", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!newUser.EmailAddress.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
			Toast.makeText(getActivity(), "You must enter a valid email address", Toast.LENGTH_SHORT).show();
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
		if(newUser.Weight == 0) {
			Toast.makeText(getActivity(), "You msut enter a weight!", Toast.LENGTH_SHORT).show();
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
