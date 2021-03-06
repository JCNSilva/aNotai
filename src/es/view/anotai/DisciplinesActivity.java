package es.view.anotai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import es.adapter.anotai.DisciplineAdapter;
import es.database.anotai.DisciplinePersister;
import es.database.anotai.TaskPersister;
import es.model.anotai.Discipline;

public class DisciplinesActivity extends Activity {
	public static final String CLASS_TAG = "DisciplinesActivity";

	private DisciplinePersister dPersister;
	private TaskPersister tPersister;
	private List<Discipline> disciplines;
	private ListView lvDisciplines;
	private SortOrderDiscipline currentSortEstrat; // TODO Strategy
	private ActionMode mActionMode;
	private ActionMode.Callback mActionModeCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_disciplines);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		initializeFields();

		addClickListenerAddDiscipline();
		initializeActionModeCallback();
		addClickListenerSortButton();
		addClickListenerLvDisciplines();
		addContextMenuLvDisciplines();

		loadList();
	}

	private void initializeFields() {
		tPersister = new TaskPersister(this);
		dPersister = new DisciplinePersister(this);
		currentSortEstrat = SortOrderDiscipline.TOTAL_TASKS_DESC;
		lvDisciplines = (ListView) findViewById(R.id.activity_disciplines_lv_disciplines);
		lvDisciplines.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
	}

	private void addContextMenuLvDisciplines() {
		lvDisciplines.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mActionMode != null) {
					return false;
				}

				// Start the CAB using the ActionMode.Callback defined above
				lvDisciplines.setItemChecked(position, true);
				mActionMode = startActionMode(mActionModeCallback);
				return true;
			}
		});
	}

	private void addClickListenerLvDisciplines() {
		lvDisciplines.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(DisciplinesActivity.this,
						DisciplineActivity.class);
				intent.putExtra("discipline", disciplines.get(position));
				startActivity(intent);
			}
		});
	}

	private void addClickListenerSortButton() {
		ImageButton sortButton = (ImageButton) findViewById(R.id.activity_disciplines_ibt_sort);
		sortButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PopupMenu popup = new PopupMenu(DisciplinesActivity.this, v);

				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()) {
						case R.id.ordering_disciplines_alphabetical:
							changeSortEstrat(SortOrderDiscipline.ALPHABETICAL);
							loadList();
							return true;
						case R.id.ordering_disciplines_total_tasks:
							changeSortEstrat(SortOrderDiscipline.TOTAL_TASKS);
							loadList();
							return true;
						default:
							return false;
						}
					}
				});

				popup.inflate(R.menu.ordering_disciplines);
				popup.show();
			}
		});
	}

	// Criando menu de contexto para listview
	private void initializeActionModeCallback() {
		mActionModeCallback = new ActionMode.Callback() {

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				mActionMode = null;
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.options, menu);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.menu_options_edit:
					EditDisciplineDialogFragment myEditDiscDF = new EditDisciplineDialogFragment();
					myEditDiscDF.show(getFragmentManager(),
							myEditDiscDF.getTag());
					mode.finish();
					return true;
				case R.id.menu_options_delete:
					ConfirmDeleteDialogFragment myConfirmDelDF = new ConfirmDeleteDialogFragment();
					myConfirmDelDF.show(getFragmentManager(),
							myConfirmDelDF.getTag());
					mode.finish();
					return true;
				default:
					return false;
				}
			}
		};
	}

	private void addClickListenerAddDiscipline() {
		Button btAddDiscipline = (Button) findViewById(R.id.activity_disciplines_bt_add_discipline);
		btAddDiscipline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(DisciplinesActivity.this);
				dialog.setContentView(R.layout.dialog_add_discipline);
				dialog.setTitle(R.string.new_discipline);

				// recuperando os elementos do dialog
				final EditText editNameContact = (EditText) dialog
						.findViewById(R.id.dialog_add_disc_et_name_discipline);

				final Button btOk = (Button) dialog
						.findViewById(R.id.dialog_add_disc_bt_ok);
				final Button btCancel = (Button) dialog
						.findViewById(R.id.dialog_add_disc_bt_cancel);

				btOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						final String name = editNameContact.getText()
								.toString();

						if (name.isEmpty()) {
							// Mostra mensagem de erro.
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.invalid_name),
									Toast.LENGTH_LONG).show();
						} else {
							Discipline discipline = new Discipline(name, "");
							dPersister.create(discipline);
							Log.i(CLASS_TAG, "Salva disciplina: " + name);

							dialog.dismiss();
							loadList();
						}
					}
				});

				btCancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});
	}

	private void changeSortEstrat(SortOrderDiscipline newSortEstrat) {
		if (newSortEstrat == SortOrderDiscipline.ALPHABETICAL) {

			if (currentSortEstrat == SortOrderDiscipline.ALPHABETICAL_ASC) {
				currentSortEstrat = SortOrderDiscipline.ALPHABETICAL_DESC;
			} else {
				currentSortEstrat = SortOrderDiscipline.ALPHABETICAL_ASC;
			}

		} else {

			if (currentSortEstrat == SortOrderDiscipline.TOTAL_TASKS_DESC) {
				currentSortEstrat = SortOrderDiscipline.TOTAL_TASKS_ASC;
			} else {
				currentSortEstrat = SortOrderDiscipline.TOTAL_TASKS_DESC;
			}
		}
	}

	private void loadList() {
		disciplines = dPersister.retrieveAll();

		switch (currentSortEstrat) {
		case ALPHABETICAL_ASC:
			Collections.sort(disciplines, new AlphabeticalComparator());
			Log.d(CLASS_TAG, "Ordena��o alfab�tica crescente");
			break;
		case ALPHABETICAL_DESC:
			Collections.sort(disciplines, new AlphabeticalComparator());
			Collections.reverse(disciplines);
			Log.d(CLASS_TAG, "Ordena��o alfab�tica decrescente");
			break;
		case TOTAL_TASKS_ASC:
			Collections.sort(disciplines, new TotalTasksComparator());
			Log.d(CLASS_TAG, "Ordena��o por tarefas crescente");
			break;
		case TOTAL_TASKS_DESC:
			Collections.sort(disciplines, new TotalTasksComparator());
			Collections.reverse(disciplines);
			Log.d(CLASS_TAG, "Ordena��o por tarefas decrescente");
			break;
		default:
			break;
		}

		DisciplineAdapter adapter = new DisciplineAdapter(this, disciplines);
		lvDisciplines.setAdapter(adapter);
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

	private enum SortOrderDiscipline {
		ALPHABETICAL, ALPHABETICAL_ASC, ALPHABETICAL_DESC, TOTAL_TASKS, TOTAL_TASKS_ASC, TOTAL_TASKS_DESC
	}

	private class EditDisciplineDialogFragment extends DialogFragment {

		private int checkedPosition = lvDisciplines.getCheckedItemPosition();
		private Discipline discSelected = (Discipline) lvDisciplines
				.getItemAtPosition(checkedPosition);

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			final LayoutInflater inflater = getActivity().getLayoutInflater();
			final View dialogView = inflater.inflate(R.layout.dialog_edit_discipline, null);
			final EditText etNameDiscipline = (EditText) dialogView
					.findViewById(R.id.dialog_edit_disc_et_name);
			final EditText etNameTeacher = (EditText) dialogView
					.findViewById(R.id.dialog_edit_disc_et_teacher);

			builder.setView(dialogView);
			builder.setTitle(R.string.edit_discipline);

			builder.setPositiveButton(R.string.confirm,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							discSelected.setName(etNameDiscipline.getText()
									.toString());
							discSelected.setTeacher(etNameTeacher.getText()
									.toString());
							dPersister.update(discSelected);
							loadList();
							Log.i(CLASS_TAG, "Disciplina atualizada: "
									+ discSelected.getName());
						}
					});

			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// N�o fazer nada

						}
					});

			return builder.create();

		}
	}

	private class ConfirmDeleteDialogFragment extends DialogFragment {

		private int checkedPosition = lvDisciplines.getCheckedItemPosition();
		private Discipline discSelected = (Discipline) lvDisciplines
				.getItemAtPosition(checkedPosition);

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setMessage(R.string.confirm_delete_discipline);

			builder.setPositiveButton(R.string.confirm,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dPersister.delete(discSelected);
							loadList();
							Log.i(CLASS_TAG, "Disciplina Removida: "
									+ discSelected.getName());
						}
					});

			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							// Dialogo cancelado, n�o fa�a nada
						}
					});

			return builder.create();
		}
	}

	private class AlphabeticalComparator implements Comparator<Discipline> {

		@Override
		public int compare(Discipline left, Discipline right) {
			return left.getName().compareTo(right.getName());
		}
	}

	private class TotalTasksComparator implements Comparator<Discipline> {

		@Override
		public int compare(Discipline left, Discipline right) {
			int leftTasks = tPersister.retrieveAll(left).size();
			int rightTasks = tPersister.retrieveAll(right).size();
			if (leftTasks > rightTasks) {
				return 1;
			} else if (leftTasks < rightTasks) {
				return -1;
			} else {
				return new AlphabeticalComparator().compare(left, right);
			}
		}
	}
}
