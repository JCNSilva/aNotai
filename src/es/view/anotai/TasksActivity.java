package es.view.anotai;

import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.database.anotai.TaskPersister;
import es.model.anotai.Task;

public class TasksActivity extends Activity {
	
	private TaskPersister tPersister; 
	private List<Task> tasks;
	private ListView lvTasks;
	private TaskAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Recupera estado salvo
		super.onCreate(savedInstanceState);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Seleciona xml correspondente à View
		setContentView(R.layout.activity_tasks);
		
		//Recupera elementos
		lvTasks = (ListView) findViewById(R.id.list_tasks);
		tPersister = new TaskPersister(this);
		tasks = tPersister.retrieveAll();
		
		//Adiciona adapter para linkar a listView ao xml dos elementos da lista
		adapter = new TaskAdapter(TasksActivity.this, tasks);
		lvTasks.setAdapter(adapter);
		
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
