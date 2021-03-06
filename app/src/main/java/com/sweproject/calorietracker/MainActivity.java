package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;
import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Users;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static FragmentManager sFragmentManager;
	private static FrameLayout mContainer;
	public static MobileServiceClient mClient;
	public static Users CurrentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);

		sFragmentManager = getSupportFragmentManager();
		mContainer = (FrameLayout) findViewById(R.id.activity_container);

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
			nextFragment(null, new LoginFragment(), null, false, false, 0);
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
		if (MainActivity.CurrentUser != null) {
			switch (item.getItemId()) {
				case R.id.action_settings:
					nextFragment(null, new PreferencesController(), null, true, false, 0);
					break;
				case R.id.action_reports:
					nextFragment(null, new FragmentReport(), null, true, false, 0);
					break;
				case R.id.action_thanks:
					nextFragment(null, new FragmentSpecialThanks(), null, true, false, 0);
					break;
			}
		} else {
			Toast.makeText(this, "You must be signed in to use this feature", Toast.LENGTH_SHORT).show();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Commits replacement transactions with fragments.
	 *
	 * @param fromFrag Fragment that should be replaced. Pass null transition should be sliding left to right instead of up and down
	 * @param toFrag   Instance of the fragment that should be called next.
	 * @param bun      Data that should be set to the fragment or null if none.
	 * @param add      True if fragment should be added to the backstack.
	 * @param clear    True if system should clear backstack then add fragment
	 * @param popCount Number of fragments that should be popped off the top of the stack
	 */
	public static void nextFragment(Fragment fromFrag, Fragment toFrag, Bundle bun, boolean add, boolean clear, int popCount) {

		if (bun != null) {
			toFrag.setArguments(bun);
		}
		if (clear) {
			// Clears the back stack, leaving the first fragment added to be displayed
			mContainer.removeAllViews();
			sFragmentManager.popBackStack(sFragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if (popCount != 0 && sFragmentManager.getBackStackEntryCount() - popCount >= 0) {
			mContainer.removeAllViews();
			sFragmentManager.popBackStack(sFragmentManager.getBackStackEntryAt(sFragmentManager.getBackStackEntryCount() - popCount).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if (add) {
			sFragmentManager.beginTransaction()
					.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
							R.anim.slide_in_fade_from_left, R.anim.slide_out_fade_from_right)
					.replace(R.id.activity_container, toFrag)
					.addToBackStack(null)
					.commit();
		} else {
			sFragmentManager.beginTransaction()
					.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
							R.anim.slide_in_fade_from_left, R.anim.slide_out_fade_from_right)
					.replace(R.id.activity_container, toFrag)
					.commit();
		}
	}

	/**
	 * Allows client to get data from the Azure database.
	 *
	 * @param clazz         The table you want to access (pass Class.class)
	 * @param listener      The class that will receive the callbacks
	 */
	public static void getDBData(@NonNull final Class clazz, @NonNull final DBDataListener listener, String field, String filter) {

		if (field != null && filter != null) {
			if (field.isEmpty() || filter.isEmpty()) {
				field = filter = null;
			}
		}
		new AsyncTask<String, Object, Void>() {

			ArrayList<Object> temp = new ArrayList<>();
			private boolean error = false;
			private Exception exception;

			@Override
			protected Void doInBackground(String... params) {
				try {

					MobileServiceTable<?> table = mClient.getTable(clazz);
					MobileServiceList<?> result = (params[0] != null && params[1] != null) ? table.where().field(params[0]).eq(params[1]).execute().get() : table.execute().get();

					for (Object item : result) {
						temp.add(item);
					}

				} catch (Exception e) {
					error = true;
					exception = e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (!error) {
					listener.onGoodDataReturn(temp);
				} else {
					listener.onBadDataReturn(exception);
				}
			}
		}.execute(field, filter);
	}

	/**
	 * Allows client to get data from the Azure database.
	 *
	 * @param clazz       The table you want to access (pass Class.class)
	 * @param listener    The class that will receive the callbacks
	 * @param fieldFilter Holds multiple field (first) and filter (second) values to provide constraints on the data returned.
	 *                    If any data is null or empty, all the values will be ignored and no filtering will be performed
	 */
	public static void getDBData(@NonNull final Class clazz, @NonNull final DBDataListener listener, Pair<String, String>... fieldFilter) {

		for (Pair<String, String> p : fieldFilter) {
			if (p.first != null && p.second != null) {
				if (p.first.isEmpty() || p.second.isEmpty()) {
					fieldFilter = null;
					break;
				}
			}
		}
		new AsyncTask<Object, Object, Void>() {

			ArrayList<Object> temp = new ArrayList<>();
			private boolean error = false;
			private Exception exception;

			@Override
			protected Void doInBackground(Object... params) {
				boolean allPairsValid = true;
				try {
					MobileServiceTable<?> table = mClient.getTable(clazz);
					MobileServiceList<?> result;
					if (params[0] != null) {
						ExecutableQuery<?> query = table.where();
						for (Object o : params) {
							if (o instanceof Pair) {
								Pair<String, String> pair = (Pair<String, String>) o;
								query = query.field(pair.first).eq(pair.second);
							} else {
								Log.e("Azure", "Object not instance of Pair");
								allPairsValid = false;
								break;
							}
						}
						if (allPairsValid) {
							result = query.execute().get();
						} else {
							result = table.execute().get();
						}

					} else {
						result = table.execute().get();
					}

					for (Object item : result) {
						temp.add(item);
					}

				} catch (Exception e) {
					error = true;
					exception = e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (!error) {
					listener.onGoodDataReturn(temp);
				} else {
					listener.onBadDataReturn(exception);
				}
			}
		}.execute(fieldFilter);
	}

	public static void getDBData(@NonNull final Class clazz, @NonNull final DBDataListener listener, ExecutableQuery<?> query) {

		new AsyncTask<ExecutableQuery<?>, Void, Void>() {

			ArrayList<Object> temp = new ArrayList<>();
			private boolean error = false;
			private Exception exception;

			@Override
			protected Void doInBackground(ExecutableQuery<?>... params) {
				try {
					MobileServiceTable<?> table = mClient.getTable(clazz);
					MobileServiceList<?> result;
					if (params[0] != null) {
						params[0].setTable(mClient.getTable(clazz));
						result = params[0].execute().get();

					} else {
						result = table.execute().get();
					}

					for (Object item : result) {
						temp.add(item);
					}

				} catch (Exception e) {
					error = true;
					exception = e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (!error) {
					listener.onGoodDataReturn(temp);
				} else {
					listener.onBadDataReturn(exception);
				}
			}
		}.execute(query);
	}

	public static void insertDBData(@NonNull final Class clazz, @NonNull final DBDataListener listener, Object item, Boolean getData) {

		new AsyncTask<Object, Object, Void>() {

			private Object obj;
			private boolean getData;
			private boolean error = false;
			private Exception exception;

			@Override
			protected Void doInBackground(Object... params) {

				try {
					obj = mClient.getTable(clazz).insert(clazz.cast(params[0])).get();
					getData = (Boolean) params[1];
				} catch (Exception e) {
					error = true;
					exception = e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (!error) {
					if (getData)
						listener.onGoodInsertReturn(obj);
					else
						listener.onGoodInsert();
				} else {
					listener.onBadDataReturn(exception);
				}
			}

		}.execute(item, getData);
	}

	public static void deleteDBData(@NonNull final Class clazz, Object item) {

		new AsyncTask<Object, Object, Void>() {

			@Override
			protected Void doInBackground(Object... params) {

				try {
					mClient.getTable(clazz).delete(params[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
			}

		}.execute(item);
	}

	public static void updateDBData(@NonNull final Class clazz, Object item) {

		new AsyncTask<Object, Object, Void>() {

			@Override
			protected Void doInBackground(Object... params) {

				try {
					mClient.getTable(clazz).update(params[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
			}

		}.execute(item);
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
