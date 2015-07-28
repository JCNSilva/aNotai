package es.view.anotai;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        addClickListenerDisciplines();
        addClickListenerTasks();
        addClickListenerExams();
        
        Button btWhatsApp = (Button) findViewById(R.id.bt_performance);
        btWhatsApp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
		        if (isWhatsappInstalled) {
		            Uri uri = Uri.parse("smsto:" + "988806054");
		            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
		            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hai Good Morning");
		            sendIntent.setType("text/plain");
		            sendIntent.setPackage("com.whatsapp");
		            startActivity(sendIntent);
		        } else {
		            Toast.makeText(MainActivity.this, "WhatsApp not Installed",
		                    Toast.LENGTH_SHORT).show();
		            Uri uri = Uri.parse("market://details?id=com.whatsapp");
		            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		            startActivity(goToMarket);
		        }
			}
		});
        
        Button btHomework = (Button) findViewById(R.id.bt_add_homework);
        btHomework.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);		
				startActivity(intent);			
			}
		});
        
        
    }
    
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

	private void addClickListenerExams() {
		Button btExams = (Button) findViewById(R.id.bt_add_test);
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
