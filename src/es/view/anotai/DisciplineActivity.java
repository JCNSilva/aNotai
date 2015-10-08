package es.view.anotai;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import es.adapter.anotai.TaskAdapter;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;

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
		
		ListView listView = (ListView) findViewById(R.id.list_tasks);
		
		TaskAdapter adapter = new TaskAdapter(DisciplineActivity.this, tPersister.retrieveAll(discipline));
		listView.setAdapter(adapter);
	}
	
	@Override
	protected void onDestroy() {
		if(tPersister != null){
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
