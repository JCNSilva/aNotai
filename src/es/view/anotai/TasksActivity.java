package es.view.anotai;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.model.anotai.Task;
import projeto.es.view.anotai.R;

public class TasksActivity extends Activity {

	private List<Task> tasks = new ArrayList<Task>();
	private ListView lvTasks;
	private TaskAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Recupera estado salvo
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Seleciona xml correspondente à View
		setContentView(R.layout.activity_tasks);

		// Recupera elementos
		lvTasks = (ListView) findViewById(R.id.list_tasks);

		// Adiciona adapter para linkar a listView ao xml dos elementos da lista
		adapter = new TaskAdapter(TasksActivity.this, tasks);
		lvTasks.setAdapter(adapter);

		lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// a tela que exibe os detalhes da tarefa
				Intent intent = new Intent(TasksActivity.this, TaskActivity.class);
				intent.putExtra("TASK", tasks.get(position));
				startActivity(intent);
			}
		});

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
