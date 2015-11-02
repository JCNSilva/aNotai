package es.view.anotai;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import es.database.anotai.TaskPersister;
import es.model.anotai.Task;
import projeto.es.view.anotai.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addClickListenerDisciplines();
		addClickListenerTasks();
		addClickListenerExams();
		addClickListenerPerformance();
		addClickListenerHomeWork();

		updateNextTask();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateNextTask();
	}

	private void updateNextTask() {
		TaskPersister persister = new TaskPersister(this);
		Task task = getNextTask(persister);
		TextView tvInfoTask = (TextView) findViewById(R.id.activity_main_tv_info_tasks);
		if (task != null) {
			tvInfoTask.setText(getResources().getString(R.string.next_activity));

			TextView tvNameTask = (TextView) findViewById(R.id.activity_main_tv_name_task);
			tvNameTask.setText(getResources().getString(R.string.title) + ": " + task.getTitle());

			TextView tvDescription = (TextView) findViewById(R.id.activity_main_tv_description);
			tvDescription.setText(getResources().getString(R.string.description) + task.getDescription());

			TextView tvNameDiscipline = (TextView) findViewById(R.id.activity_main_tv_related_discipline);
			tvNameDiscipline
					.setText(getResources().getString(R.string.discipline) + ": " + task.getDiscipline().getName());

			TextView tvDeadLine = (TextView) findViewById(R.id.activity_main_tv_deadline);
			tvDeadLine.setText(getResources().getString(R.string.date) + ": " + task.getDeadlineDateText());
		} else {
			tvInfoTask.setText(getResources().getString(R.string.no_next_activity));
		}
	}

	private Task getNextTask(TaskPersister persister) {
		List<Task> tasks = persister.retrieveAll();
		if (!tasks.isEmpty()) {
			Task next = tasks.get(0);
			for (Task t : persister.retrieveAll()) {
				if (t.getDeadlineDate().before(next.getDeadlineDate())) {
					next = t;
				}
			}
			return next;
		}
		return null;
	}

	private void addClickListenerHomeWork() {
		Button btHomework = (Button) findViewById(R.id.activity_main_bt_add_homework);
		btHomework.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addClickListenerPerformance() {
		Button btPerformance = (Button) findViewById(R.id.activity_main_bt_performance);
		btPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PerfomanceActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addClickListenerExams() {
		Button btExams = (Button) findViewById(R.id.activity_main_bt_add_test);
		btExams.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ExamsActivity.class);
				startActivity(intent);
			}
		});
	}

	private void addClickListenerTasks() {
		Button btTasks = (Button) findViewById(R.id.activity_main_bt_tasks);
		btTasks.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TasksActivity.class);
				startActivity(intent);
			}
		});

	}

	private void addClickListenerDisciplines() {
		Button btDisciplines = (Button) findViewById(R.id.activity_main_bt_disciplines);
		btDisciplines.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DisciplinesActivity.class);
				startActivity(intent);
			}
		});
	}
}
