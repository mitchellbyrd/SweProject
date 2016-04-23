package com.sweproject.calorietracker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Marcus on 4/20/2016.
 */
public class FragmentReport extends Fragment implements FillFormatter, CompoundButton.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

	private LineChart mChart;

	private Switch mCalSwitch;
	private Switch mCarSwitch;
	private Switch mProSwitch;
	private Switch mFatSwitch;

	private TextView mCalView;
	private TextView mCarView;
	private TextView mProView;
	private TextView mFatView;

	private TextView mStart;
	private TextView mEnd;

	private DatePickerDialog mDatePickerDialog;

	private ArrayList<Entry> mCalEntries;
	private ArrayList<Entry> mCarEntries;
	private ArrayList<Entry> mProEntries;
	private ArrayList<Entry> mFatEntries;

	private ArrayList<ILineDataSet> mDataSets;

	private LineDataSet mCalSet;
	private LineDataSet mCarSet;
	private LineDataSet mProSet;
	private LineDataSet mFatSet;

	private boolean isStartSelected;
	private boolean isEndSelected;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		mChart = (LineChart) getActivity().findViewById(R.id.fragment_report_chart);
		mStart = (TextView) getActivity().findViewById(R.id.fragment_report_start_edit);
		mEnd = (TextView) getActivity().findViewById(R.id.fragment_report_end_edit);

		mDatePickerDialog = new DatePickerDialog(getActivity(), this, 2016, 3, 23);

		mStart.setOnClickListener(this);
		mEnd.setOnClickListener(this);

		isStartSelected = false;
		isEndSelected = false;

		mCalSwitch = (Switch) getActivity().findViewById(R.id.fragment_report_total_cal_switch);
		mCarSwitch = (Switch) getActivity().findViewById(R.id.fragment_report_total_carb_switch);
		mProSwitch = (Switch) getActivity().findViewById(R.id.fragment_report_total_pro_switch);
		mFatSwitch = (Switch) getActivity().findViewById(R.id.fragment_report_total_fat_switch);

		mCalSwitch.setOnCheckedChangeListener(this);
		mCarSwitch.setOnCheckedChangeListener(this);
		mProSwitch.setOnCheckedChangeListener(this);
		mFatSwitch.setOnCheckedChangeListener(this);

		mCalView = (TextView) getActivity().findViewById(R.id.fragment_report_total_cal_amount_view);
		mCarView = (TextView) getActivity().findViewById(R.id.fragment_report_total_carb_amount_view);
		mProView = (TextView) getActivity().findViewById(R.id.fragment_report_total_pro_amount_view);
		mFatView = (TextView) getActivity().findViewById(R.id.fragment_report_total_fat_amount_view);

		mCalEntries = new ArrayList<>();
		mCarEntries = new ArrayList<>();
		mProEntries = new ArrayList<>();
		mFatEntries = new ArrayList<>();

		mCalEntries.add(new Entry(0, 0));
		mCalEntries.add(new Entry(4, 1));
		mCalEntries.add(new Entry(10, 2));
		mCalEntries.add(new Entry(12, 3));
		mCalEntries.add(new Entry(14, 4));
		mCalEntries.add(new Entry(16, 5));
		mCalEntries.add(new Entry(50, 6));
		mCalEntries.add(new Entry(0, 7));
		mCalEntries.add(new Entry(14, 8));
		mCalEntries.add(new Entry(20, 9));

		mCarEntries.add(new Entry(10, 0));
		mCarEntries.add(new Entry(5, 1));
		mCarEntries.add(new Entry(31, 2));
		mCarEntries.add(new Entry(12, 3));
		mCarEntries.add(new Entry(6, 4));
		mCarEntries.add(new Entry(11, 5));
		mCarEntries.add(new Entry(57, 6));
		mCarEntries.add(new Entry(28, 7));
		mCarEntries.add(new Entry(9, 8));
		mCarEntries.add(new Entry(44, 9));

		mProEntries.add(new Entry(14, 0));
		mProEntries.add(new Entry(60, 1));
		mProEntries.add(new Entry(40, 2));
		mProEntries.add(new Entry(22, 3));
		mProEntries.add(new Entry(34, 4));
		mProEntries.add(new Entry(56, 5));
		mProEntries.add(new Entry(0, 6));
		mProEntries.add(new Entry(0, 7));
		mProEntries.add(new Entry(17, 8));
		mProEntries.add(new Entry(55, 9));

		mFatEntries.add(new Entry(17, 0));
		mFatEntries.add(new Entry(55, 1));
		mFatEntries.add(new Entry(33, 2));
		mFatEntries.add(new Entry(19, 3));
		mFatEntries.add(new Entry(59, 4));
		mFatEntries.add(new Entry(17, 5));
		mFatEntries.add(new Entry(56, 6));
		mFatEntries.add(new Entry(35, 7));
		mFatEntries.add(new Entry(1, 8));
		mFatEntries.add(new Entry(3, 9));

		mCalSet = new LineDataSet(mCalEntries, "Calorie intake");
		mCalSet.setLineWidth(2f);
		mCalSet.setDrawFilled(true);
		mCalSet.setColor(Color.BLACK);
		mCalSet.setCircleColor(Color.BLACK);
		mCalSet.setCircleRadius(5f);
		mCalSet.setValueTextSize(0);
		mCalSet.setFillColor(Color.BLACK);
		mCalSet.setFillFormatter(this);
		mCalSet.setAxisDependency(YAxis.AxisDependency.LEFT);

		mCarSet = new LineDataSet(mCarEntries, "Fat intake");
		mCarSet.setLineWidth(2f);
		mCarSet.setDrawFilled(true);
		mCarSet.setColor(Color.BLUE);
		mCarSet.setCircleColor(Color.BLUE);
		mCarSet.setCircleRadius(5f);
		mCarSet.setValueTextSize(0);
		mCarSet.setFillColor(Color.BLUE);
		mCarSet.setFillFormatter(this);
		mCarSet.setAxisDependency(YAxis.AxisDependency.LEFT);

		mProSet = new LineDataSet(mProEntries, "Carb intake");
		mProSet.setLineWidth(2f);
		mProSet.setDrawFilled(true);
		mProSet.setColor(Color.RED);
		mProSet.setCircleColor(Color.RED);
		mProSet.setCircleRadius(5f);
		mProSet.setValueTextSize(0);
		mProSet.setFillColor(Color.RED);
		mProSet.setFillFormatter(this);
		mProSet.setAxisDependency(YAxis.AxisDependency.LEFT);

		mFatSet = new LineDataSet(mFatEntries, "Protein intake");
		mFatSet.setLineWidth(2f);
		mFatSet.setDrawFilled(true);
		mFatSet.setColor(Color.GREEN);
		mFatSet.setCircleColor(Color.GREEN);
		mFatSet.setCircleRadius(5f);
		mFatSet.setValueTextSize(0);
		mFatSet.setFillColor(Color.GREEN);
		mFatSet.setFillFormatter(this);
		mFatSet.setAxisDependency(YAxis.AxisDependency.LEFT);

		// use the interface ILineDataSet
		mDataSets = new ArrayList<>();
		mDataSets.add(mCalSet);
		mDataSets.add(mCarSet);
		mDataSets.add(mProSet);
		mDataSets.add(mFatSet);

		ArrayList<String> xVals = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			xVals.add("Day " + String.valueOf(i));
		}

		LineData data = new LineData(xVals, mDataSets);
		mChart.setData(data);
		mChart.getAxisRight().setEnabled(false);
		mChart.getAxisRight().setAxisMinValue(0);
		mChart.getAxisRight().setAxisMaxValue(60);
		mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
		mChart.setDescription("");
		mChart.invalidate(); // refresh
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_report, container, false);
	}

	@Override
	public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
		return 0;
	}

	@Override
	public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
		toggleVisibleData();
	}

	private void toggleVisibleData() {
		ArrayList<String> xVals = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			xVals.add("Day " + String.valueOf(i));
		}

		mChart.clear();
		mDataSets.clear();

		if (mCalSwitch.isChecked()) {
			mDataSets.add(mCalSet);
		}
		if (mCarSwitch.isChecked()) {
			mDataSets.add(mCarSet);
		}
		if (mProSwitch.isChecked()) {
			mDataSets.add(mProSet);
		}
		if (mFatSwitch.isChecked()) {
			mDataSets.add(mFatSet);
		}

		mChart.setData(new LineData(xVals, mDataSets));
		mChart.invalidate(); // refresh
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_report_start_edit:
				isStartSelected = true;
				Calendar cal = Calendar.getInstance();
				cal.set(2010, 0, 0);
				mDatePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
				break;
			case R.id.fragment_report_end_edit:
				isEndSelected = true;
				break;
		}
		mDatePickerDialog.show();
	}

	@Override
	public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
		String month = String.valueOf(selectedMonth);
		String day = String.valueOf(selectedDay);
		String year = String.valueOf(selectedYear);

		String date = month + "/" + day + "/" + year;

		Calendar cal = Calendar.getInstance();
		cal.set(selectedYear, selectedMonth, selectedDay);
		long time = cal.getTimeInMillis();

		if (isStartSelected) {
			mStart.setText(date);
			mDatePickerDialog.getDatePicker().setMinDate(time);
			isStartSelected = false;
		} else if (isEndSelected) {
			if (time > mDatePickerDialog.getDatePicker().getMinDate()) {
				mEnd.setText(date);
			}
			isEndSelected = false;
		}
	}
}