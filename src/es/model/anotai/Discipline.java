package es.model.anotai;

import java.io.Serializable;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import es.database.anotai.TaskPersister;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import projeto.es.view.anotai.R;

public class Discipline implements Serializable {

	private static final long serialVersionUID = -2699314244245902531L;
	private long id;
	private String name;
	private String teacher;

	public Discipline() {
		this(0, "", "");
	}

	public Discipline(String name, String teacher) {
		this(0, name, teacher);
	}

	// TODO refactor
	public Discipline(final long newId, String name, String teacher) {
		if (name == null)
			throw new IllegalArgumentException("Name can't be null");
		if (teacher == null)
			throw new IllegalArgumentException("Teacher can't be null");

		this.setId(newId);
		this.name = name;
		this.teacher = teacher;
	}

	public long getId() {
		return id;
	}

	public void setId(long iDisciplineId) {
		if (id >= 0) {
			this.id = iDisciplineId;
		}
	}

	public String getName() {
		return name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Discipline)) {
			return false;
		}

		Discipline other = (Discipline) obj;
		return this.name.equals(other.getName()) && this.teacher.equals(other.getTeacher());
	}

	@Override
	public String toString() {
		return getName();
	}

	private final List<Task> getTasks(Context context) {
		TaskPersister tPersister = new TaskPersister(context);
		return tPersister.retrieveAll(this);
	}

	private final double[] getGrades(List<Task> tasks) {
		double[] grades = new double[tasks.size()];
		for (int i = 0; i < grades.length; i++) {
			grades[i] = (float) tasks.get(i).getGrade();
		}
		return grades;
	}

	private final int[] getValues(List<Task> tasks) {
		int[] x = new int[tasks.size()];
		for (int i = 0; i < x.length; i++) {
			x[i] = i;
		}
		return x;
	}

	private final String[] getLableGrades(List<Task> tasks) {
		String[] mNotas = new String[tasks.size()];
		for (int i = 0; i < mNotas.length; i++) {
			mNotas[i] = "" + tasks.get(i).getGrade();
		}
		return mNotas;
	}

	public void makeChartLayout(Context context, LinearLayout chartContainer) {
		List<Task> tasks = getTasks(context);

		int[] x = getValues(tasks);
		double[] expense = getGrades(tasks);

		// Creating an XYSeries for Expense
		XYSeries expenseSeries = new XYSeries("Notas");

		if (x.length == 0) {
			expenseSeries.setTitle(getName() + context.getResources().getString(R.string.no_grades));
		}

		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			expenseSeries.add(i, expense[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		// Adding Expense Series to dataset
		dataset.addSeries(expenseSeries);

		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.DKGRAY);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2);
		expenseRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setChartTitle(getName());
		multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
		multiRenderer.setXLabels(0);

		/***
		 * Customizing graphs
		 */
		// setting text size of the title
		multiRenderer.setChartTitleTextSize(22);
		// setting text size of the axis title
		multiRenderer.setAxisTitleTextSize(20);
		// setting text size of the graph lable
		multiRenderer.setLabelsTextSize(20);

		multiRenderer.setLegendTextSize(25);

		multiRenderer.setXLabelsColor(Color.DKGRAY);

		// setting zoom buttons visiblity
		multiRenderer.setZoomButtonsVisible(true);
		// setting pan enablity which uses graph to move on both axis
		multiRenderer.setPanEnabled(true, false);
		// setting click false on graph
		multiRenderer.setClickEnabled(false);
		// setting zoom to false on both axis
		multiRenderer.setZoomEnabled(false, false);
		// setting lines to display on y axis
		multiRenderer.setShowGridY(true);
		// setting lines to display on x axis
		multiRenderer.setShowGridX(true);
		// setting legend to fit the screen size
		multiRenderer.setFitLegend(true);
		// setting displaying line on grid
		multiRenderer.setShowGrid(false);
		// setting zoom to false
		multiRenderer.setZoomEnabled(false);
		// setting external zoom functions to false
		multiRenderer.setExternalZoomEnabled(false);
		// setting displaying lines on graph to be formatted(like using
		// graphics)
		multiRenderer.setAntialiasing(true);
		// setting to in scroll to false
		multiRenderer.setInScroll(false);
		// setting to set legend height of the graph
		multiRenderer.setLegendHeight(30);
		// setting x axis label align
		multiRenderer.setXLabelsAlign(Align.CENTER);
		// setting y axis label to align
		multiRenderer.setYLabelsAlign(Align.LEFT);
		// setting text style
		multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
		// setting no of values to display in y axis
		multiRenderer.setYLabels(3);

		multiRenderer.setYAxisMin(0);
		// setting y axis max value, Since i'm using static values inside the
		// graph so i'm setting y max value to 4000.
		// if you use dynamic values then get the max y value and set here
		multiRenderer.setYAxisMax(10);
		// setting used to move the graph on xaxiz to .5 to the right
		multiRenderer.setXAxisMin(-1);
		// setting max values to be display in x axis
		multiRenderer.setXAxisMax(5);
		// setting bar size or space between two bars
		multiRenderer.setBarSpacing(1);

		multiRenderer.setBarWidth(55);

		// Setting background color of the graph to transparent
		multiRenderer.setBackgroundColor(Color.TRANSPARENT);
		// Setting margin color of the graph to transparent
		multiRenderer.setMarginsColor(context.getResources().getColor(R.color.transparent_background));
		multiRenderer.setApplyBackgroundColor(true);

		// setting the margin size for the graph in the order top, left, bottom,
		// right
		multiRenderer.setMargins(new int[] { 45, 30, 30, 30 });

		String[] mLabels = getLableGrades(tasks);

		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mLabels[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(expenseRenderer);

		// this part is used to display graph on the xml
		// LinearLayout chartContainer = (LinearLayout) ((Activity)
		// context).findViewById(R.id.Layout_chart);

		// remove any views before u paint the chart
		chartContainer.removeAllViews();

		// drawing bar chart
		View viewAux = ChartFactory.getBarChartView(context, dataset, multiRenderer, Type.DEFAULT);
		// adding the view to the linearlayout
		chartContainer.addView(viewAux);
	}

}
