package es.adapter.anotai;

import java.util.List;

import projeto.es.view.anotai.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import es.model.anotai.Classmate;

public class ClassmateAdapter extends BaseAdapter {
	
	private final List<Classmate> classmates;
	private final LayoutInflater mInflater;
	
	public ClassmateAdapter(final Context context, final List<Classmate> classmateList) {
		classmates = classmateList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return classmates.size();
	}

	@Override
	public Object getItem(int position) {
		return classmates.get(position);
	}

	@Override
	public long getItemId(int position) {
		return classmates.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View viewAux = convertView;
        
        if (viewAux == null) {
            viewAux = mInflater.inflate(R.layout.item_classmate,
                    parent, false);
        }
        
        TextView tvNameDiscipline = (TextView) viewAux
                .findViewById(R.id.item_classmate_tv_name_classmate);
        
        tvNameDiscipline.setText(((Classmate) getItem(position)).getName());
        
        return viewAux;
	}

}
