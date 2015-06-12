package es.adapter.anotai;

import java.util.List;

import projeto.es.view.anotai.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import es.model.anotai.Discipline;
import es.model.anotai.Task;

public class TaskAdapter extends BaseAdapter {
	
	private final List<Task> tasks;
	private final LayoutInflater inflater;
	
	public TaskAdapter(final Context context, final List<Task> tasks){
		inflater = LayoutInflater.from(context);
		this.tasks = tasks;
	}

	@Override
	public int getCount() {
		return tasks.size();
	}

	@Override
	public Object getItem(int index) {
		return tasks.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup group) {
		View viewAux = view;
		
		if(viewAux == null){
			viewAux = inflater.inflate(R.layout.item_task,
                    group, false);
		}
		
		TextView tvNameTask = (TextView) viewAux.findViewById(R.id.tv_name_task);
		tvNameTask.setText(((Discipline) getItem(index)).getName());
		
		return viewAux;
	}

}
