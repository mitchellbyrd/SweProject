package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by danny.oneal on 2/10/2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

	private String userName;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
		Bundle lilBundle = getArguments();
		userName = lilBundle != null ? lilBundle.getString("userName") : "";
		boolean isNewUser = isNewUser(userName);

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

	public boolean isNewUser(String userName) {
	    return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_login_submitBtn:
				MainActivity.nextFragment(this, new FragmentCalendar(), null, false, false);
				break;
			case R.id.fragement_login_registerText:
				MainActivity.nextFragment(this, new RegistrationFragment(), null, false, false);
				break;
		}
	}
}
