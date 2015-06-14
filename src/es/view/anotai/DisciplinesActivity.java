package es.view.anotai;

import java.util.ArrayList;
import java.util.List;

import projeto.es.view.anotai.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import es.adapter.anotai.DisciplineAdapter;
import es.model.anotai.Discipline;

public class DisciplinesActivity extends Activity {
    private List<Discipline> disciplines = new ArrayList<Discipline>();
    private ListView lvDisciplines;
    private DisciplineAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplines);
        
        // Recuperando a listview de disciplinas.
        lvDisciplines = (ListView) findViewById(R.id.list_disciplines);
        adapter = new DisciplineAdapter(DisciplinesActivity.this, disciplines);
        lvDisciplines.setAdapter(adapter);
                
        Button btAddDiscipline = (Button) findViewById(R.id.bt_add_discipline);
        btAddDiscipline.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DisciplinesActivity.this);
                dialog.setContentView(R.layout.dialog_add_discipline);
                dialog.setTitle(R.string.new_discipline);

                // recuperando os elementos do dialog
                final EditText editNameContact = (EditText) dialog
                        .findViewById(R.id.et_name_discipline);
                
                final Button btOk = (Button) dialog.findViewById(R.id.bt_ok);
                final Button btCancel = (Button) dialog.findViewById(R.id.bt_Cancel);

                btOk.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        final String nome = editNameContact.getText().toString();

                        if (nome.isEmpty()) {
                         // Mostra mensagem de erro.
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.invalid_name),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // Adiciona a disciplina na lista.
                            Discipline discipline = new Discipline();
                            discipline.setName(nome);
                            disciplines.add(discipline);
                            
                            // Atualiza a lista.
                            loadList();
                            dialog.dismiss();
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
    
    private void loadList() {
        adapter = new DisciplineAdapter(DisciplinesActivity.this, disciplines);
        lvDisciplines.setAdapter(adapter);
    }

}
