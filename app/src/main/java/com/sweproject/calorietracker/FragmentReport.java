package com.sweproject.calorietracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Random;

/**
 * Created by Marcus on 4/20/2016.
 */
public class FragmentReport extends Fragment implements FillFormatter {

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		LineChart chart = (LineChart) getActivity().findViewById(R.id.fragment_report_chart);

		ArrayList<Entry> valsComp1 = new ArrayList<>();
		ArrayList<Entry> valsComp2 = new ArrayList<>();
		ArrayList<Entry> valsComp3 = new ArrayList<>();
		ArrayList<Entry> valsComp4 = new ArrayList<>();

		int seed = 0;
		Random rand = new Random();

		valsComp1.add(new Entry(0, 0));
		valsComp1.add(new Entry(4, 1));
		valsComp1.add(new Entry(10, 2));
		valsComp1.add(new Entry(12, 3));
		valsComp1.add(new Entry(14, 4));
		valsComp1.add(new Entry(16, 5));
		valsComp1.add(new Entry(50, 6));
		valsComp1.add(new Entry(0, 7));
		valsComp1.add(new Entry(14, 8));
		valsComp1.add(new Entry(20, 9));

		valsComp2.add(new Entry(10, 0));
		valsComp2.add(new Entry(5, 1));
		valsComp2.add(new Entry(31, 2));
		valsComp2.add(new Entry(12, 3));
		valsComp2.add(new Entry(6, 4));
		valsComp2.add(new Entry(11, 5));
		valsComp2.add(new Entry(57, 6));
		valsComp2.add(new Entry(28, 7));
		valsComp2.add(new Entry(9, 8));
		valsComp2.add(new Entry(44, 9));

		valsComp3.add(new Entry(14, 0));
		valsComp3.add(new Entry(60, 1));
		valsComp3.add(new Entry(40, 2));
		valsComp3.add(new Entry(22, 3));
		valsComp3.add(new Entry(34, 4));
		valsComp3.add(new Entry(56, 5));
		valsComp3.add(new Entry(0, 6));
		valsComp3.add(new Entry(0, 7));
		valsComp3.add(new Entry(17, 8));
		valsComp3.add(new Entry(55, 9));

		valsComp4.add(new Entry(17, 0));
		valsComp4.add(new Entry(55, 1));
		valsComp4.add(new Entry(33, 2));
		valsComp4.add(new Entry(19, 3));
		valsComp4.add(new Entry(59, 4));
		valsComp4.add(new Entry(17, 5));
		valsComp4.add(new Entry(56, 6));
		valsComp4.add(new Entry(35, 7));
		valsComp4.add(new Entry(1, 8));
		valsComp4.add(new Entry(3, 9));

		LineDataSet setComp1 = new LineDataSet(valsComp1, "Calorie intake");
		setComp1.setLineWidth(2f);
		setComp1.setDrawFilled(true);
		setComp1.setColor(Color.BLACK);
		setComp1.setCircleColor(Color.BLACK);
		setComp1.setCircleRadius(5f);
		setComp1.setValueTextSize(0);
		setComp1.setFillColor(Color.BLACK);
		setComp1.setFillFormatter(this);
		setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

		LineDataSet setComp2 = new LineDataSet(valsComp2, "Fat intake");
		setComp2.setLineWidth(2f);
		setComp2.setDrawFilled(true);
		setComp2.setColor(Color.BLUE);
		setComp2.setCircleColor(Color.BLUE);
		setComp2.setCircleRadius(5f);
		setComp2.setValueTextSize(0);
		setComp2.setFillColor(Color.BLUE);
		setComp2.setFillFormatter(this);
		setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);

		LineDataSet setComp3 = new LineDataSet(valsComp3, "Carb intake");
		setComp3.setLineWidth(2f);
		setComp3.setDrawFilled(true);
		setComp3.setColor(Color.RED);
		setComp3.setCircleColor(Color.RED);
		setComp3.setCircleRadius(5f);
		setComp3.setValueTextSize(0);
		setComp3.setFillColor(Color.RED);
		setComp3.setFillFormatter(this);
		setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);

		LineDataSet setComp4 = new LineDataSet(valsComp4, "Pro intake");
		setComp4.setLineWidth(2f);
		setComp4.setDrawFilled(true);
		setComp4.setColor(Color.GREEN);
		setComp4.setCircleColor(Color.GREEN);
		setComp4.setCircleRadius(5f);
		setComp4.setValueTextSize(0);
		setComp4.setFillColor(Color.GREEN);
		setComp4.setFillFormatter(this);
		setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);

		// use the interface ILineDataSet
		ArrayList<ILineDataSet> dataSets = new ArrayList<>();
		dataSets.add(setComp1);
		dataSets.add(setComp2);
		dataSets.add(setComp3);
		dataSets.add(setComp4);

		ArrayList<String> xVals = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			xVals.add("Day " + String.valueOf(i));
		}

		LineData data = new LineData(xVals, dataSets);
		chart.setData(data);

		chart.getAxisRight().setEnabled(false);
		chart.getAxisRight().setAxisMinValue(0);
		chart.getAxisRight().setAxisMaxValue(60);
		chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
		chart.setDescription("");
		chart.invalidate(); // refresh
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_report, container, false);
	}

	@Override
	public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
		return 0;
	}
}
