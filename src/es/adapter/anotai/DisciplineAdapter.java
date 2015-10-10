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

public class DisciplineAdapter extends BaseAdapter{
    
    private final List<Discipline> disciplines;
    
    private final LayoutInflater mInflater;
    
    public DisciplineAdapter(final Context context,
            final List<Discipline> disciplinesList) {
        mInflater = LayoutInflater.from(context);
        disciplines = disciplinesList;
    }

    @Override
    public int getCount() {
        return disciplines.size();
    }

    @Override
    public Object getItem(int position) {
        return disciplines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return disciplines.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View viewAux = view;
        
        if (viewAux == null) {
            viewAux = mInflater.inflate(R.layout.item_discipline,
                    viewGroup, false);
        }
        
        TextView tvNameDiscipline = (TextView) viewAux
                .findViewById(R.id.item_discipline_tv_name_discipline);
        
        tvNameDiscipline.setText(((Discipline) getItem(position)).getName());
        
        return viewAux;
    }

}
