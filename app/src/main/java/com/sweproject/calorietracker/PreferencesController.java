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

public class PreferencesController extends Fragment implements View.OnClickListener {

    private MobileServiceTable<Users> userTable;
    private MobileServiceList<Users> currentUsers;
    private static ProgressBar spinner;



    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        Button submitBtn = (Button) getActivity().findViewById(R.id.fragment_preferences_submitButton);
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
                try {
                    UpdatePreferences();
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

    public void UpdatePreferences() throws InterruptedException, ExecutionException, MobileServiceException {
        final Users newUser = GetNewUserFromView();
        boolean isUserValid = Validate(newUser);
        try {
            if(isUserValid) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            MainActivity.CurrentUser.PreferedCalorieLimit = newUser.PreferedCalorieLimit;
                            MainActivity.CurrentUser.PreferedFatLimit = newUser.PreferedFatLimit;
                            MainActivity.CurrentUser.PreferedCarbLimit = newUser.PreferedCarbLimit;
                            MainActivity.CurrentUser.PreferedProteinLimit = newUser.PreferedProteinLimit;
                            userTable.update(MainActivity.CurrentUser);

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
        MainActivity.CurrentUser = newUser;
        MainActivity.nextFragment(new RegistrationFragment(), new FragmentCalendar(), null, false, false);
    }

    private Users GetNewUserFromView() {
        Users newUser = new Users();
        try {

            newUser.PreferedCalorieLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_preferences_caloriesInput)).getText().toString().trim());
            newUser.PreferedProteinLimit =Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_preferences_proteinInput)).getText().toString().trim());
            newUser.PreferedCarbLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_preferences_carbsInput)).getText().toString().trim());
            newUser.PreferedFatLimit = Integer.parseInt(((TextView) getActivity().findViewById(R.id.fragment_preferences_fatsInput)).getText().toString().trim());
        }catch(NumberFormatException n) {
            Toast.makeText(getActivity(), "You must enter a number for Calories, Protein, Carbs, and Fat!", Toast.LENGTH_SHORT).show();
            n.printStackTrace();
        }

        return newUser;
    }

    private boolean Validate(Users newUser) throws MobileServiceException, ExecutionException, InterruptedException {
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
}
