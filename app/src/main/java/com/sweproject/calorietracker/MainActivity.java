package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Foods;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

	private static FragmentManager sFragmentManager;
	private static FrameLayout mContainer;
	public static MobileServiceClient mClient;

	public static ArrayList<Foods> sDBFoodList;
	public static ArrayList<Foods> sAddedFoodList;
	public static ArrayList<Object> dbSearchReuslt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);

		sFragmentManager = getSupportFragmentManager();
		mContainer = (FrameLayout) findViewById(R.id.activity_container);
		sDBFoodList = new ArrayList<>();
		dbSearchReuslt = new ArrayList<>();
		sAddedFoodList = new ArrayList<>();

		Window window = getWindow();

		// On devices with Android Lolipop, change the color of the status bar to the colorPrimaryDark
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		}

		// If first time opening app
		if (savedInstanceState == null) {
			try {
				mClient = new MobileServiceClient("https://ksucaloriecounter.azurewebsites.net", this);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
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

	/**
	 * Allows client to get data from the Azure database.
	 *
	 * @param clazz    The table you want to access (pass Class.class)
	 * @param listener The class that will receive the callbacks
	 */
	public static void getDBData(@NonNull final Class clazz, @NonNull final DBDataListener listener, String field, String filter) {

		if (field != null && filter != null) {
			if (field.isEmpty() || filter.isEmpty()) {
				filter = field = null;
			}
		}
		new AsyncTask<String, Object, Void>() {

			ArrayList<Object> temp = new ArrayList<>();
			@Override
			protected Void doInBackground(String... params) {
				try {

					MobileServiceTable<?> table = mClient.getTable(clazz);
					MobileServiceList<?> result = (params[0] != null && params[1] != null) ? table.where().field(params[0]).eq(params[1]).execute().get() : table.execute().get();

					for (Object item : result) {
						temp.add(item);
					}

				} catch (Exception exception) {
					listener.onBadDataReturn(exception);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (temp.size() != 0) {
					listener.onGoodDataReturn(temp);
				}
			}
		}.execute(field, filter);
	}


	public static void insertDBData(Class clazz, Object item) {
		try {
			mClient.getTable(clazz).insert(clazz.cast(item));
		} catch (Exception e) {
			e.printStackTrace();
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
