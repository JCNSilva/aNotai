package es.view.anotai;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import es.adapter.anotai.ChartAdapter;
import es.model.anotai.Discipline;
import projeto.es.view.anotai.R;

public class Perfomance extends Activity {

	private ListView lvChart;
	private ChartAdapter adapter;
	private List<Discipline> disciplines = new ArrayList<Discipline>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_performance);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// DISCIPLINAS PARA TESTAR A CRIAÇÃO DOS GRAFÍCOS.
		disciplines.add(new Discipline("matemática", "alguem"));
		disciplines.add(new Discipline("português", "alguem"));
		disciplines.add(new Discipline("geografia", "alguem"));		
		
		// Recuperando a listview de disciplinas.
		lvChart = (ListView) findViewById(R.id.activity_performance_list_chart);
		adapter = (ChartAdapter) new ChartAdapter(Perfomance.this, disciplines);
		lvChart.setAdapter(adapter);
	}
}