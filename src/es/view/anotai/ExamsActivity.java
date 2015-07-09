package es.view.anotai;

import java.util.Calendar;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.model.anotai.Discipline;
import es.model.anotai.Exam;
import es.model.anotai.Task.Priority;

public class ExamsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_exams);
		
		Button saveExam = (Button) findViewById(R.id.bt_save_exam);
		
		saveExam.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//TODO		
			}
			
		});
		
		
	}
	
	private void createExam(Discipline disc, String subject, Priority priority, Calendar deadline){
		Exam newExam = new Exam(0, "", deadline, priority, subject); //FIXME mudar ID, Exam não precisa de nome
		disc.addTask(newExam);
		//TODO persistir
	}
}
