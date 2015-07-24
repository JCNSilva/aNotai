package es.view.anotai;

import java.util.ArrayList;
import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.model.anotai.Task;

public class TasksActivity extends Activity {
	
	private List<Task> tasks = new ArrayList<Task>();
	private ListView lvTasks;
	private TaskAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Recupera estado salvo
		super.onCreate(savedInstanceState);
		
		//Seleciona xml correspondente à View
		setContentView(R.layout.activity_tasks);
		
		//Recupera elementos
		lvTasks = (ListView) findViewById(R.id.list_tasks);
		
		//Adiciona adapter para linkar a listView ao xml dos elementos da lista
		adapter = new TaskAdapter(TasksActivity.this, tasks);
		lvTasks.setAdapter(adapter);
		
	}
	
	

}
