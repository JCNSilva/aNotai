package es.view.anotai;

import es.adapter.anotai.TaskAdapter;
import es.model.anotai.Discipline;
import projeto.es.view.anotai.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ListView;

public class DisciplineActivity extends Activity {
	private Discipline discipline;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discipline);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		discipline = (Discipline) extras.get("discipline");
		
		ListView listView = (ListView) findViewById(R.id.list_tasks);
		
		TaskAdapter adapter = new TaskAdapter(DisciplineActivity.this, discipline.getTasks());
		listView.setAdapter(adapter);
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
