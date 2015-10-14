package es.view.anotai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.database.anotai.TaskPersister;
import es.model.anotai.Task;
import es.model.anotai.Task.Priority;

public class TasksActivity extends Activity {

	private enum SortOrderTask {
		DATE, PRIORITY, DATE_ASC, DATE_DESC, PRIORITY_ASC, PRIORITY_DESC
	}

	private TaskPersister tPersister;
	private List<Task> tasks;
	private ListView lvTasks;
	private TaskAdapter adapter;
	private SortOrderTask currentSortEstrat; // TODO Strategy
	private ActionMode mActionMode;
	private ActionMode.Callback mActionModeCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Recupera estado salvo
		super.onCreate(savedInstanceState);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Seleciona xml correspondente à View
		setContentView(R.layout.activity_tasks);

		// Recupera elementos
		lvTasks = (ListView) findViewById(R.id.activity_tasks_lv_tasks);
		tPersister = new TaskPersister(this);
		tasks = tPersister.retrieveAll();
		currentSortEstrat = SortOrderTask.DATE_ASC;
		
		loadList();

		// Cria menu de contexto
		ImageButton sortButton = (ImageButton) findViewById(R.id.activity_tasks_ibt_sort);
		sortButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupMenu popup = new PopupMenu(TasksActivity.this, v);

				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case R.id.ordering_tasks_date:
							changeSortEstrat(SortOrderTask.DATE);
							loadList();
							return true;
						case R.id.ordering_tasks_priority:
							changeSortEstrat(SortOrderTask.PRIORITY);
							loadList();
							return true;
						default:
							return false;
						}
					}

					
				});

				popup.inflate(R.menu.ordering_tasks);
				popup.show();
			}
		});

		lvTasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// a tela que exibe os detalhes da tarefa
				Intent intent = new Intent(TasksActivity.this,
						TaskActivity.class);
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
	
	private void loadList() {
		switch(currentSortEstrat) {
		case DATE_ASC:
			Collections.sort(tasks, new DateComparator());
			Log.d("TasksActivity", "Ordenaçao por data crescente");
			break;
		case DATE_DESC:
			Collections.sort(tasks, new DateComparator());
			Collections.reverse(tasks);
			Log.d("TasksActivity", "Ordenaçao por data decrescente");
			break;
		case PRIORITY_ASC:
			Collections.sort(tasks, new PriorityComparator());
			Log.d("TasksActivity", "Ordenaçao por prioridade crescente");
			break;
		case PRIORITY_DESC:
			Collections.sort(tasks, new PriorityComparator());
			Collections.reverse(tasks);
			Log.d("TasksActivity", "Ordenaçao por prioridade decrescente");
			break;
		default:
			break;
		}
		
		adapter = new TaskAdapter(TasksActivity.this, tasks);
		lvTasks.setAdapter(adapter);
	}

	private void changeSortEstrat(SortOrderTask newSortEstrat) {
		if(newSortEstrat == SortOrderTask.DATE) {
			if(currentSortEstrat == SortOrderTask.DATE_DESC){
				currentSortEstrat = SortOrderTask.DATE_ASC;
			} else {
				currentSortEstrat = SortOrderTask.DATE_DESC;
			}
			
		} else {
			if(currentSortEstrat == SortOrderTask.PRIORITY_DESC) {
				currentSortEstrat = SortOrderTask.PRIORITY_ASC;
			} else {
				currentSortEstrat = SortOrderTask.PRIORITY_DESC;
			}
		}
	}
	
	
	private class DateComparator implements Comparator<Task> {

		@Override
		public int compare(Task left, Task right) {
			 if(left.getDeadlineDateMillis() < right.getDeadlineDateMillis()){
				 return -1;
			 } else if (left.getDeadlineDateMillis() > right.getDeadlineDateMillis()) {
				 return 1;
			 } else {
				 return new PriorityComparator().compare(left, right);
			 }
		}
	}
	
	
	private class PriorityComparator implements Comparator<Task> {

		@Override
		public int compare(Task left, Task right) {
			if(left.getPriority() == Priority.LOW) {
				
				if(right.getPriority() == Priority.LOW){
					return 0;
				} else {
					return -1;
				}
				
			} else if (left.getPriority() == Priority.HIGH) {
				
				if(right.getPriority() == Priority.HIGH){
					return 0;
				} else {
					return 1;
				}
				
			} else {
				
				if(right.getPriority() == Priority.LOW) {
					return 1;
				} else if (right.getPriority() == Priority.NORMAL) {
					return 0;
				} else {
					return -1;
				}
				
			}
		}
		
	}
}
