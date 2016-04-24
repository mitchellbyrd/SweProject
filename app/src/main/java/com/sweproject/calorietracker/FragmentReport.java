package com.sweproject.calorietracker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;
import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Marcus on 4/20/2016.
 */
public class FragmentReport extends Fragment implements FillFormatter, CompoundButton.OnCheckedChangeListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, DBDataListener {

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

	private Long mStartTime;
	private Long mEndTime;

	private Button mBtn;

	private DatePickerDialog mDatePickerDialog;

	private ArrayList<Entry> mCalEntries;
	private ArrayList<Entry> mCarEntries;
	private ArrayList<Entry> mProEntries;
	private ArrayList<Entry> mFatEntries;

	private ArrayList<ILineDataSet> mDataSets;
	private ArrayList<Days> mDays;

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

		mBtn = (Button) getActivity().findViewById(R.id.fragment_report_execute_btn);
		mBtn.setOnClickListener(this);

		mDatePickerDialog = new DatePickerDialog(getActivity(), this, 2016, 0, 1);
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 0, 1);
		mDatePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());

		cal.set(2016, 0, 1);
		mStartTime = cal.getTimeInMillis();

		cal.set(2016, 0, 2);
		mEndTime = cal.getTimeInMillis();

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

		mDays = new ArrayList<>();

		//mChart.setData(data);
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
		for (int i = 0; i < mDays.size(); i++) {
			xVals.add("Day " + String.valueOf(i + 1));
		}

		mChart.clear();
		if (mDataSets != null) {
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
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_report_start_edit:
				isStartSelected = true;
				Calendar cal = Calendar.getInstance();
				cal.set(2015, 0, 1);
				mDatePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
				mDatePickerDialog.show();
				break;
			case R.id.fragment_report_end_edit:
				isEndSelected = true;
				mDatePickerDialog.show();
				break;
			case R.id.fragment_report_execute_btn:
				String end[] = mEnd.getText().toString().split("/", 3);
				String start[] = mStart.getText().toString().split("/", 3);

				if (Integer.valueOf(end[2]) > Integer.valueOf(start[2])) {
					getTotals();

				} else if (Integer.valueOf(end[2]).intValue() == Integer.valueOf(start[2]).intValue()) {
					if (Integer.valueOf(end[0]) > Integer.valueOf(start[0])) {
						getTotals();

					} else if (Integer.valueOf(end[0]).intValue() == Integer.valueOf(start[0]).intValue()) {
						if (Integer.valueOf(end[1]) >= Integer.valueOf(start[1])) {
							getTotals();

						} else {
							Toast.makeText(getActivity(), "InValid Day Range", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(), "Invalid Month Range", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "Invalid Year Range", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	private void getTotals() {
		ExecutableQuery<?> query = new ExecutableQuery<>();
		query.field("UserId").eq(MainActivity.CurrentUser.Id).and()
				.field("Date").ge(new SimpleDateFormat("yyyy-MM-dd").format(mStartTime));

		MainActivity.getDBData(Days.class, this, query);
		Toast.makeText(getActivity(), "Gathering data", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
		String month = String.valueOf(selectedMonth + 1);
		String day = String.valueOf(selectedDay);
		String year = String.valueOf(selectedYear);

		String date = month + "/" + day + "/" + year;

		Calendar cal = Calendar.getInstance();
		cal.set(selectedYear, selectedMonth, selectedDay);
		long time = cal.getTimeInMillis();

		if (isStartSelected) {
			mStart.setText(date);
			mDatePickerDialog.getDatePicker().setMinDate(time);
			mStartTime = time;
			isStartSelected = false;
		} else if (isEndSelected) {
			if (time > mDatePickerDialog.getDatePicker().getMinDate()) {
				mEnd.setText(date);
				mEndTime = time;
			}
			isEndSelected = false;
		}
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		mDays.clear();
		mCalEntries.clear();
		mCarEntries.clear();
		mProEntries.clear();
		mFatEntries.clear();

		mCalView.setText("0");
		mCarView.setText("0");
		mProView.setText("0");
		mFatView.setText("0");

		if (!data.isEmpty()) {
			for (Object o : data) {
				if (((Days) o).getDate().compareToIgnoreCase(new SimpleDateFormat("yyyy-MM-dd").format(mEndTime)) <= 0) {
					mDays.add((Days) o);
				}
			}
			populateChart();
			toggleVisibleData();
		} else {
			Toast.makeText(getActivity(), "No days found in range", Toast.LENGTH_SHORT).show();
		}
	}

	private void populateChart() {

		for (int i = 0; i < mDays.size(); i++) {

			mCalEntries.add(new Entry(mDays.get(i).getCurrentTotalCalorie(), i));
			mCarEntries.add(new Entry(mDays.get(i).getCurrentTotalCarb(), i));
			mProEntries.add(new Entry(mDays.get(i).getCurrentTotalProtein(), i));
			mFatEntries.add(new Entry(mDays.get(i).getCurrentTotalFat(), i));

			mCalView.setText(String.valueOf(Float.valueOf(mCalView.getText().toString()) + mDays.get(i).getCurrentTotalCalorie()));
			mCarView.setText(String.valueOf(Float.valueOf(mCarView.getText().toString()) + mDays.get(i).getCurrentTotalCarb()));
			mProView.setText(String.valueOf(Float.valueOf(mProView.getText().toString()) + mDays.get(i).getCurrentTotalProtein()));
			mFatView.setText(String.valueOf(Float.valueOf(mFatView.getText().toString()) + mDays.get(i).getCurrentTotalFat()));
		}

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
		for (int i = 0; i < mDays.size(); i++) {
			xVals.add("Day " + String.valueOf(i + 1));
		}

		mChart.setData(new LineData(xVals, mDataSets));
		mChart.invalidate();
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() {
	}

	@Override
	public void onGoodInsertReturn(Object obj) {
	}
}