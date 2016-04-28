package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sweproject.calorietracker.Callbacks.OnDialogDismissListener;
import com.sweproject.calorietracker.DataObjects.ServingSizes;

/**
 * Created by Marcus on 4/5/2016.
 */
public class DialogServingAdd extends DialogFragment implements View.OnClickListener {

	TextView mCalories;
	TextView mProteins;
	TextView mCarbs;
	TextView mFats;
	TextView mType;

	OnDialogDismissListener mListener;

	public DialogServingAdd() { /* Empty constructor required for DialogFragment */ }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.dialog_serving_add, container);

		mCalories = (TextView) root.findViewById(R.id.dialog_serving_add_calories_edit);
		mProteins = (TextView) root.findViewById(R.id.dialog_serving_add_protein_edit);
		mCarbs = (TextView) root.findViewById(R.id.dialog_serving_add_carbs_edit);
		mFats = (TextView) root.findViewById(R.id.dialog_serving_add_fats_edit);
		mType = (TextView) root.findViewById(R.id.dialog_serving_add_serving_type_edit);

		(root.findViewById(R.id.dialog_serving_add_btn_add)).setOnClickListener(this);
		(root.findViewById(R.id.dialog_serving_add_btn_cancel)).setOnClickListener(this);

		getDialog().setTitle("Add Serving Information");

		return root;
	}

	public void setOnDismissListener(OnDialogDismissListener listener) {
		mListener = listener;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.dialog_serving_add_btn_add:
				String calo = mCalories.getText().toString();
				String prot = mProteins.getText().toString();
				String carb = mCarbs.getText().toString();
				String fats = mFats.getText().toString();

				if (calo.isEmpty()) {
					calo = mCalories.getHint().toString();
				}
				if (prot.isEmpty()) {
					prot = mProteins.getHint().toString();
				}
				if (carb.isEmpty()) {
					carb = mCarbs.getHint().toString();
				}
				if (fats.isEmpty()) {
					fats = mFats.getHint().toString();
				}

				float cal = Float.valueOf(calo);
				float pro = Float.valueOf(prot);
				float car = Float.valueOf(carb);
				float fat = Float.valueOf(fats);

				String type = (mType.getText().toString().isEmpty()) ? mType.getHint().toString() : mType.getText().toString();

				mListener.onDismiss(new ServingSizes(cal, pro, car, fat, type));
				break;
		}

		dismiss();
	}
}
