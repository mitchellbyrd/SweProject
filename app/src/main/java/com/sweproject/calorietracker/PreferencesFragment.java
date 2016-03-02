package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by danny.oneal on 2/23/2016.
 */
public class PreferencesFragment extends Fragment implements View.OnClickListener {
    private TextView caloriesInput;
    private TextView proteinInput;
    private TextView carbsInput;
    private TextView fatsInput;

    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        Button submitBtn = (Button) getActivity().findViewById(R.id.fragment_preferences_submitButton);
        submitBtn.setOnClickListener(this);
        this.caloriesInput = (TextView) getActivity().findViewById(R.id.fragment_preferences_caloriesInput);
        this.proteinInput = (TextView) getActivity().findViewById(R.id.fragment_preferences_proteinInput);
        this.carbsInput = (TextView) getActivity().findViewById(R.id.fragment_preferences_carbsInput);
        this.fatsInput = (TextView) getActivity().findViewById(R.id.fragment_preferences_fatsInput);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_registration_submitBtn:
                updatePreferences();
                MainActivity.nextFragment(this, new FragmentCalendar(), null, false, false);
                break;
        }
    }

    private void updatePreferences()
    {
        int numberOfCalories = getIntValue(this.caloriesInput);
        int numberOfProteins = getIntValue(this.proteinInput);
        int numberOfCarbs = getIntValue(this.carbsInput);
        int numberOfFats = getIntValue(this.fatsInput);
    }

    private int getIntValue(TextView view)
    {
        return Integer.parseInt(view.getText().toString());
    }


}
