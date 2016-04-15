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

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.sweproject.calorietracker.DataObjects.Users;

import java.util.concurrent.ExecutionException;

/**
 * Created by danny.oneal on 2/10/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

	private String userName;
	public  MobileServiceTable<Users> users;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		Bundle lilBundle = getArguments();
		users = MainActivity.mClient.getTable(Users.class);

		Button submitBtn = (Button) getActivity().findViewById(R.id.fragment_login_submitBtn);
		submitBtn.setOnClickListener(this);

		TextView registerText = (TextView) getActivity().findViewById(R.id.fragement_login_registerText);
		registerText.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_login, container, false);
		return root;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_login_submitBtn:
				try {
					Login(view);
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case R.id.fragement_login_registerText:
				MainActivity.nextFragment(this, new RegistrationFragment(), null, false, false, 0);
				break;
		}
	}

	private void Login(View view) throws ExecutionException, InterruptedException {
		final String password = ((TextView) getActivity().findViewById(R.id.fragment_login_passwordField)).getText().toString();
		final String emailAddress = ((TextView) getActivity().findViewById(R.id.fragment_login_userNameField)).getText().toString();
		final ProgressBar spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar);
		spinner.setVisibility(View.VISIBLE);


		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Looper.prepare();
					final MobileServiceList<Users> queriedUsers = users.where().field("EmailAddress").eq(emailAddress).and().field("Password").eq(password).execute().get();
					if(queriedUsers.size() == 0) {
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "Incorrect Email Address or Password!", Toast.LENGTH_SHORT).show();
								spinner.setVisibility(View.INVISIBLE);

							}
						});
						return null;
					}
					else {

						MainActivity.CurrentUser = queriedUsers.get(0);

						MainActivity.nextFragment(new LoginFragment(), new FragmentCalendar(), null, false, false, 0);
					}
				} catch (Exception exception) {
					Toast.makeText(getActivity(), "Uh Oh Something Happened. Please try again!", Toast.LENGTH_SHORT).show();
				}
				return null;
			}
		}.execute();

	}
}
