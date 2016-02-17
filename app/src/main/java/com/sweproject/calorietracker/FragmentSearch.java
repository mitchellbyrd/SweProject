
package com.sweproject.calorietracker;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by James on 2/13/2016.
 */
public class FragmentSearch extends Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}
}

/*
public class FragmentSearch extends Fragment {

    private ListView foodData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchView search = (SearchView) getView().findViewById(R.id.searchView);
        foodData = (ListView)getView().findViewById(R.id.listView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new JSONTask().execute("http://api.nal.usda.gov/ndb/search/?format=json&q=butter&sort=n&max=25&offset=0&api_key=DEMO_KEY");
            }

        });
    }

public class JSONTask extends AsyncTask<String,String,String>
{
    @Override
    public String doInBackground(String... params)
    {
        HttpURLConnection connection=null;
        BufferedReader reader=null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";


            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void onPostExecute(String result)
    {
        super.onPostExecute(result);
        // foodData.setText(result); This sets text view ( I need something for list view)
        // foodData.setSelection(result);
    }
}
*/

