package es.view.anotai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import es.adapter.anotai.TaskAdapter;
import es.database.anotai.TaskPersister;
import es.model.anotai.Task;
import es.model.anotai.Task.Priority;
import projeto.es.view.anotai.R;

public class TasksActivity extends Activity {

	private enum SortOrderTask {
		DATE, PRIORITY, DATE_ASC, DATE_DESC, PRIORITY_ASC, PRIORITY_DESC
	}

	private TaskPersister tPersister;
	private List<Task> tasks;
	private ListView lvTasks;
	private TaskAdapter adapter;
	private SortOrderTask currentSortEstrat; // TODO Strategy
	private Task taskSelected = null;

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
		sortButton.setOnClickListener(new View.OnClickListener() {

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
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				// a tela que exibe os detalhes da tarefa
				Intent intent = new Intent(TasksActivity.this, TaskActivity.class);
				intent.putExtra("TASK", tasks.get(position));
				startActivity(intent);
			}
		});

		registerForContextMenu(lvTasks);
		lvTasks.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
				taskSelected = (Task) adapterView.getItemAtPosition(position);
				return false;
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
		tasks = tPersister.retrieveAll();
		switch (currentSortEstrat) {
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
		if (newSortEstrat == SortOrderTask.DATE) {
			if (currentSortEstrat == SortOrderTask.DATE_DESC) {
				currentSortEstrat = SortOrderTask.DATE_ASC;
			} else {
				currentSortEstrat = SortOrderTask.DATE_DESC;
			}

		} else {
			if (currentSortEstrat == SortOrderTask.PRIORITY_DESC) {
				currentSortEstrat = SortOrderTask.PRIORITY_ASC;
			} else {
				currentSortEstrat = SortOrderTask.PRIORITY_DESC;
			}
		}
	}

	private class DateComparator implements Comparator<Task> {

		@Override
		public int compare(Task left, Task right) {
			if (left.getDeadlineDateMillis() < right.getDeadlineDateMillis()) {
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
			if (left.getPriority() == Priority.LOW) {

				if (right.getPriority() == Priority.LOW) {
					return 0;
				} else {
					return -1;
				}

			} else if (left.getPriority() == Priority.HIGH) {

				if (right.getPriority() == Priority.HIGH) {
					return 0;
				} else {
					return 1;
				}

			} else {

				if (right.getPriority() == Priority.LOW) {
					return 1;
				} else if (right.getPriority() == Priority.NORMAL) {
					return 0;
				} else {
					return -1;
				}

			}
		}

	}

	@Override
	public final void onCreateContextMenu(final ContextMenu menu, final View view, final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);

		getMenuInflater().inflate(R.menu.menu_task, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.it_delet:
			deleteTask();
			break;
		case R.id.it_edit:
			Intent intent = new Intent(TasksActivity.this, TaskActivity.class);
			intent.putExtra("TASK", taskSelected);
			startActivity(intent);

			break;
		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void deleteTask() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(
				getResources().getString(R.string.confirm_delete_task) + " \"" + taskSelected.getTitle() + "\"");

		builder.setPositiveButton(R.string.yes, new OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, final int which) {

				tPersister.delete(taskSelected);
				loadList();
				taskSelected = null;
			}
		});

		builder.setNegativeButton(R.string.not, null);
		final AlertDialog dialog = builder.create();
		;
		dialog.show();
	}
}
