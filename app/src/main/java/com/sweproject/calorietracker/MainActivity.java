package com.sweproject.calorietracker;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

	private static FragmentManager sFragmentManager;
	private static FrameLayout mContainer;
	protected SQLiteDatabase sqLiteDatabase;
	public static ArrayList<String> foodList = new ArrayList<>();
	public static MobileServiceClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			mClient = new MobileServiceClient(
                    "https://ksucaloriecounter.azurewebsites.net",
                    this
            );
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);

		sFragmentManager = getSupportFragmentManager();

		mContainer = (FrameLayout) findViewById(R.id.activity_container);

		foodList.add("Monely");
		foodList.add("Sumer");
		foodList.add("Werst");
		foodList.add("Lioner");
		foodList.add("Jacker");
		foodList.add("Pie");
		foodList.add("Queen");
		foodList.add("Niel");
		foodList.add("Asbol");
		foodList.add("Polstim");
		foodList.add("Salt");
		foodList.add("Pepper");
		foodList.add("Cheese");
		foodList.add("Cake");
		foodList.add("Muffin");
		foodList.add("Fires");
		foodList.add("Lop");
		foodList.add("Spam");

		Window window = getWindow();

		// On devices with Android Lolipop, change the color of the status bar to the colorPrimaryDark
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		}

		// If first time opening app
		if (savedInstanceState == null) {
			nextFragment(null, new LoginFragment(), null, false, false);
		}
		// else will show the last visible fragment (Android destroys activity during rotation)
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

		if (bun != null){
			toFrag.setArguments(bun);
		}
		if (clear){
			// Clears the back stack, leaving the first fragment added to be displayed
			mContainer.removeAllViews();
			sFragmentManager.popBackStack(sFragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
		if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
			getSupportFragmentManager().popBackStack();
		} else {
			super.onBackPressed();
		}
	}
}
