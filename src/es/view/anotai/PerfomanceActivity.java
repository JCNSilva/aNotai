package es.view.anotai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import es.adapter.anotai.ChartAdapter;
import es.database.anotai.DisciplinePersister;
import projeto.es.view.anotai.R;

public class PerfomanceActivity extends Activity {

	private ListView lvChart;
	private ChartAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_performance);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		DisciplinePersister dPersister = new DisciplinePersister(this);
		
		lvChart = (ListView) findViewById(R.id.activity_performance_list_chart);
		adapter = (ChartAdapter) new ChartAdapter(PerfomanceActivity.this, dPersister.retrieveAll());
		lvChart.setAdapter(adapter);
	}
}