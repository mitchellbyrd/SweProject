package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by danny.oneal on 2/10/2016.
 */
public class LoginFragment extends Fragment {
    private String userName;
    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);
        Bundle lilBundle = getArguments();
        userName = lilBundle.getString("userName");
        boolean isNewUser = isNewUser(userName);

        if(isNewUser)
        {
            RegistrationFragment regFragment = new RegistrationFragment();
            MainActivity.nextFragment(this, regFragment, null, true, false);
        }
        else{
            FragmentCalendar calendarFragment = new FragmentCalendar();
            MainActivity.nextFragment(this, calendarFragment, null, true, false);
        }
    }


    public boolean isNewUser(String userName)
    {
        return true;
    }
}
