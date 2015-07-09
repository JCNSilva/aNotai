package es.view.anotai;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        addClickListenerDisciplines();
        addClickListenerTasks();
        addClickListenerExams();
        
        Button btHomework = (Button) findViewById(R.id.bt_add_homework);
        btHomework.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);		
				startActivity(intent);			
			}
		});
        
        
    }

	private void addClickListenerExams() {
		Button btExams = (Button) findViewById(R.id.bt_add_exam);
        btExams.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ExamsActivity.class);		
				startActivity(intent);
			}
		});
	}

	private void addClickListenerTasks() {
		Button btTasks = (Button) findViewById(R.id.bt_tasks);
        btTasks.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TasksActivity.class);
				startActivity(intent);
			}        	
        });
 
	}

	private void addClickListenerDisciplines() {
		Button btDisciplines = (Button) findViewById(R.id.bt_disciplines); 
        btDisciplines.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisciplinesActivity.class);
                startActivity(intent);
            }
        });
	}
}
