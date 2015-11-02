package es.adapter.anotai;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import es.model.anotai.Discipline;
import projeto.es.view.anotai.R;

public class ChartAdapter extends BaseAdapter {

	private final List<Discipline> disciplines;

	private final LayoutInflater mInflater;

	private final Context context;

	public ChartAdapter(final Context context, final List<Discipline> disciplinesList) {
		mInflater = LayoutInflater.from(context);
		disciplines = disciplinesList;
		this.context = context;
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
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		View viewAux = view;
		if (viewAux == null) {
			viewAux = mInflater.inflate(R.layout.item_chart, viewGroup, false);
		}

		disciplines.get(position).makeChartLayout(context,
				(LinearLayout) viewAux.findViewById(R.id.item_chart_linear_layout));

		return viewAux;
	}
}
