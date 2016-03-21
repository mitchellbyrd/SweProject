package com.sweproject.calorietracker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by jedwa145 on 2/23/2016.
 */
public class CreateFood extends Fragment {

	public String tag = "test create food";

	public static final String DEBUG_TAG = "foodData";

	EditText editFoodName, editCal,editCarbs,editProtien,editFats;
	ImageButton btnAddData;

	private SQLiteDatabase db;
	private SQLiteOpenHelper foodDBHelper;
	DatabaseHelperClass foodDB;


	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		foodDB = new DatabaseHelperClass(getActivity());



		editFoodName=(EditText)getView().findViewById(R.id.editText3);
		editCal =(EditText)getView().findViewById(R.id.editText4);
		editProtien=(EditText)getView().findViewById(R.id.editText5);
		editFats=(EditText)getView().findViewById(R.id.editText7);
		btnAddData = (ImageButton)getView().findViewById(R.id.imageButton);
		Log.i(tag, "values saved");

		AddData();
		Log.i(tag,"added");
	}

	public void AddData()
	{
		btnAddData.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						foodDB.insertData(editFoodName.getText().toString(), editCal.getText().toString(), editProtien.getText().toString(), editFats.getText().toString());

						}
				}
		);
	}
	//@Override
//	public void onAttach(Activity activity)
	//{
	//	super.onAttach(activity);
	//	foodDb = new DatabaseHelperClass(getActivity());
	//}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View root = inflater.inflate(R.layout.create_food, container, false);
		return root;
	}
}
