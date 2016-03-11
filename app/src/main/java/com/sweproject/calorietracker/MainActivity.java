package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private MobileServiceClient mClient;
	private EditText mName;
	private ArrayList<Foods> listings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
		setSupportActionBar(toolbar);

		Button saveBtn = (Button) findViewById(R.id.activity_save_btn);
		saveBtn.setOnClickListener(this);
		Button loadBtn = (Button) findViewById(R.id.activity_load_btn);
		loadBtn.setOnClickListener(this);

		mName = (EditText) findViewById(R.id.activity_name);

		listings = new ArrayList<>();

		Window window = getWindow();

		// On devices with Android Lolipop, change the color of the status bar to the colorPrimaryDark
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
		}

		try {
			mClient = new MobileServiceClient("https://ksucaloriecounter.azurewebsites.net", this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.activity_save_btn:
				Foods item = new Foods();
				item.Name = mName.getText().toString();
				//item.Number =  mNumber.getText().toString();

				mClient.getTable(Foods.class).insert(item);
				Toast.makeText(this, "Added to DB", Toast.LENGTH_SHORT).show();
				break;
			case R.id.activity_load_btn:
				Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT).show();
				new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						try {
							final MobileServiceList<Foods> result = mClient.getTable(Foods.class).execute().get();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									for (Foods item : result) {
										listings.add(item);
									}
								}
							});
						} catch (Exception exception) {
							exception.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void aVoid) {
						if (listings.size() != 0) {
							// This grabs the first item in the db. change the 0 to grab another item
							mName.setText(listings.get(0).Name);

							Toast.makeText(MainActivity.this, "Loaded to DB ID: " + listings.get(0).Id, Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(MainActivity.this, "No data found in DB", Toast.LENGTH_SHORT).show();
						}
					}
				}.execute();
				break;
		}
	}
}
