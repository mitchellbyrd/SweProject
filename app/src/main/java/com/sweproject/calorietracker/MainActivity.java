package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{

	private static FragmentManager sFragmentManager;
	private static TabLayout sTabLayout;
	private static ViewPager sViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sFragmentManager = getSupportFragmentManager();

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		boolean isNewUser = this.isNewUser("");
		if(isNewUser)
		{
			//login

		}

		SetUpMainActivity(tabAdapter);

	}

	private void SetUpMainActivity(TabAdapter tabAdapter) {
		sViewPager = (ViewPager) findViewById(R.id.activity_viewpager);
		sViewPager.setAdapter(tabAdapter);

		sTabLayout = (TabLayout) findViewById(R.id.activity_tabs);
		sTabLayout.setupWithViewPager(sViewPager);
	}

	public void Submit(View view)
	{
		LoginFragment loginFragment = new LoginFragment();
		loginFragment.onSubmit(view);
	}

	public boolean isNewUser(String username)
	{
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/** Commits replacement transactions with fragments.
	 *
	 * @param fromFrag Fragment that should be replaced. Pass null transition should be sliding left to right instead of up and down
	 * @param toFrag Instance of the fragment that should be called next.
	 * @param bun Data that should be set to the fragment or null if none.
	 * @param add True if fragment should be added to the backstack.
	 * @param clear True if system should clear backstack then add fragment
	 */
	public static void nextFragment(Fragment fromFrag, Fragment toFrag, Bundle bun, boolean add, boolean clear){

		sTabLayout.setVisibility(View.GONE);
		sViewPager.setVisibility(View.GONE);

		if (bun != null){
			toFrag.setArguments(bun);
		}
		if (clear){
			// Clears the back stack, leaving the first fragment added to be displayed
			sFragmentManager.popBackStack(sFragmentManager.getBackStackEntryAt(0).getId(), 0);
		}
		if (add){
			sFragmentManager.beginTransaction()
					.replace(R.id.activity_container, toFrag)
					.addToBackStack(null)
					.commit();
		} else {
			sFragmentManager.beginTransaction()
					.replace(R.id.activity_container, toFrag)
					.commit();
		}
	}

	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			getSupportFragmentManager().popBackStack();
			sTabLayout.setVisibility(View.VISIBLE);
			sViewPager.setVisibility(View.VISIBLE);
		} else {
			super.onBackPressed();
		}
	}
}
