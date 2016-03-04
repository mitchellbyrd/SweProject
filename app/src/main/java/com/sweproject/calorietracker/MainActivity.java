package com.sweproject.calorietracker;

import android.database.sqlite.SQLiteDatabase;
import android.net.NetworkInfo;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.sweproject.calorietracker.DataAccessLayer.ConnectionClass;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

	private static FragmentManager sFragmentManager;
	private MobileServiceClient mClient;
	protected SQLiteDatabase sqLiteDatabase;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;
	ConnectionClass connectionClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Connection con = connectionClass.CONN();
		String query = "Select * from dbo.Foods";
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ResultSet rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);

		sFragmentManager = getSupportFragmentManager();

		Window window = getWindow();

		// On devices with Android Lolipop, change the color of the status bar to the colorPrimaryDark
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		}

		// If first time opening app
		if (savedInstanceState == null) {
			nextFragment(null, new LoginFragment(), null, false, false);
		}
		// else will show the last visible fragment (Android destroys activity during rotation)
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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


	/**
	 * Commits replacement transactions with fragments.
	 *
	 * @param fromFrag Fragment that should be replaced. Pass null transition should be sliding left to right instead of up and down
	 * @param toFrag   Instance of the fragment that should be called next.
	 * @param bun      Data that should be set to the fragment or null if none.
	 * @param add      True if fragment should be added to the backstack.
	 * @param clear    True if system should clear backstack then add fragment
	 */
	public static void nextFragment(Fragment fromFrag, Fragment toFrag, Bundle bun, boolean add, boolean clear) {

		if (bun != null) {
			toFrag.setArguments(bun);
		}
		if (clear) {
			// Clears the back stack, leaving the first fragment added to be displayed
			sFragmentManager.popBackStack(sFragmentManager.getBackStackEntryAt(0).getId(), 0);
		}
		if (add) {
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

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.sweproject.calorietracker/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.sweproject.calorietracker/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}
