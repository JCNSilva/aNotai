package es.view.anotai;

import projeto.es.view.anotai.R;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;
import es.model.anotai.Task;

public class DisciplineActivity extends Activity {
	private Discipline discipline;
	private TaskPersister tPersister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discipline);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		discipline = (Discipline) extras.get("discipline");
		tPersister = new TaskPersister(this);

		ListView listView = (ListView) findViewById(R.id.activity_discipline_lv_tasks);

		final List<Task> tasks = tPersister.retrieveAll(discipline);
		TaskAdapter adapter = new TaskAdapter(DisciplineActivity.this, tasks);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// a tela que exibe os detalhes da tarefa
				Intent intent = new Intent(DisciplineActivity.this, TaskActivity.class);
				intent.putExtra("TASK", tasks.get(position));
				startActivity(intent);
			}
		});

		discipline.makeChartLayout(this, (LinearLayout) findViewById(R.id.item_chart_linear_layout));
	}

	@Override
	protected void onDestroy() {
		if (tPersister != null) {
			try {
				tPersister.close();
			} catch (Exception e) {
				Log.e("DisciplineActivity", e.getMessage());
			}
		}

		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
