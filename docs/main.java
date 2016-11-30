Main_Activity.java
package jafi.sqlite;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener {
        EditText editLegajo, editNombre, editNotas;
        Spinner turnoSpinner;
        Button btnAgrega, btnBorra, btnModifica, btnVer, btnVerTodo, btnMostrarInfo;
        SQLiteDatabase db;
        /** Called when the activity is first created. */
        @
        Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            editLegajo = (EditText) findViewById(R.id.editLegajo);
            editNombre = (EditText) findViewById(R.id.editNombre);
            editNotas = (EditText) findViewById(R.id.editNotas);
            turnoSpinner = (Spinner) findViewById(R.id.turnoSpinner);
            btnAgrega = (Button) findViewById(R.id.btnAgrega);
            btnBorra = (Button) findViewById(R.id.btnBorra);
            btnModifica = (Button) findViewById(R.id.btnModifica);
            btnVer = (Button) findViewById(R.id.btnVer);
            btnVerTodo = (Button) findViewById(R.id.btnViewTodo);
            btnMostrarInfo = (Button) findViewById(R.id.btnMostrarInfo);
            btnAgrega.setOnClickListener(this);
            btnBorra.setOnClickListener(this);
            btnModifica.setOnClickListener(this);
            btnVer.setOnClickListener(this);
            btnVerTodo.setOnClickListener(this);
            btnMostrarInfo.setOnClickListener(this);
            db = openOrCreateDatabase("DBEstudiantes", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS estudiante(legajo VARCHAR,nombre
                VARCHAR, notas VARCHAR, turno VARCHAR);
            ");
            // Legajo 0, nombre 1, notas 2, turno 3
            armarSpinnerTurnos();
        }
        private void armarSpinnerTurnos() {
            ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource(this,
                R.array.turno_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            this.turnoSpinner.setAdapter(adapter);
            this.turnoSpinner.setOnItemSelectedListener(this);
        }
        public void onItemSelected(AdapterView <? > parent, View view, int pos, long id) {}
        public void onNothingSelected(AdapterView <? > parent) {}

        public String obtenerTurnoSpinnerString() {
            return turnoSpinner.getSelectedItem().toString();
        }
        public int recorrerSpinner(Spinner spinner1, Cursor cursor1) {
            String valor = cursor1.getString(3);
            for (int i = 0; i < 4; i++) {
                if (spinner1.getItemAtPosition(i).toString().equals(valor))
                    return i;
            }
            return 0;
        }
        public void clearTurnoSpinner() {
            turnoSpinner.setSelection(0);
        }
        public void onClick(View view) {
                if (view == btnAgrega) {
                    if (editLegajo.getText().toString().trim().length() == 0 ||
                        editNombre.getText().toString().trim().length() == 0 ||
                        editNotas.getText().toString().trim().length() == 0 ||
                        obtenerTurnoSpinnerString().equals("Ingrese turno")) {
                        showMessage("Error", "Ingrese todos los valores");
                        return;
                    }
                    db.execSQL("INSERT INTO estudiante VALUES('" + editLegajo.getText() + "','" + editNombre.getText() +
                        "','" + editNotas.getText() + "','" + turnoSpinner.getSelectedItem().toString() + "');");
                    showMessage("Exito", "Registro agregado");
                    clearText();
                    clearTurnoSpinner();
                }

                if (view == btnBorra) {
                    if (editLegajo.getText().toString().trim().length() == 0) {
                        showMessage("Error", "Por favor, ingrese legajo");
                        return;
                    }
                    Cursor c = db.rawQuery("SELECT * FROM estudiante WHERE
                        legajo = '"+editLegajo.getText()+"'
                        ", null);
                        if (c.moveToFirst()) {
                            db.execSQL("DELETE FROM estudiante WHERE legajo='" + editLegajo.getText() + "'");
                            showMessage("Exito", "Registro borrado");
                        } else {
                            showMessage("Error", "Legajo no valido");
                        }
                        clearText(); clearTurnoSpinner();
                    }
                    if (view == btnModifica) {
                        if (editLegajo.getText().toString().trim().length() == 0) {
                            showMessage("Error", "Ingrese legajo");
                            return;
                        }
                        Cursor c = db.rawQuery("SELECT * FROM estudiante WHERE
                            legajo = '"+editLegajo.getText()+"'
                            ", null);
                            if (c.moveToFirst()) {
                                db.execSQL("UPDATE estudiante SET nombre='" + editNombre.getText() + "',notas='" + editNotas.getText() + "', turno='" +
                                    obtenerTurnoSpinnerString() + "' WHERE legajo='" + editLegajo.getText() + "'");
                                showMessage("Exito", "Registro Modificado");
                            } else {
                                showMessage("Error", "Legajo invalido");
                            }
                            clearText(); clearTurnoSpinner();
                        }
                        if (view == btnVer) {
                            if (editLegajo.getText().toString().trim().length() == 0) {
                                showMessage("Error", "Ingrese legajo");
                                return;
                            }
                            Cursor c = db.rawQuery("SELECT * FROM estudiante WHERE
                                legajo = '"+editLegajo.getText()+"'
                                ", null);
                                if (c.moveToFirst()) {
                                    editNombre.setText(c.getString(1));
                                    editNotas.setText(c.getString(2));
                                    int index = recorrerSpinner(turnoSpinner, c);
                                    turnoSpinner.setSelection(index);
                                } else {
                                    showMessage("Error", "Legajo invalido");
                                    clearText();
                                    clearTurnoSpinner();
                                }
                            }
                            if (view == btnVerTodo) {
                                Cursor c = db.rawQuery("SELECT * FROM estudiante", null);
                                if (c.getCount() == 0) {
                                    showMessage("Error", "No hay registros");
                                    return;
                                }
                                StringBuffer buffer = new StringBuffer();
                                while (c.moveToNext()) {
                                    buffer.append("Legajo: " + c.getString(0) + "\n");
                                    buffer.append("Nombre: " + c.getString(1) + "\n");
                                    buffer.append("Notas: " + c.getString(2) + "\n");
                                    buffer.append("Turno: " + c.getString(3) + "\n");
                                    buffer.append("___________________" + "\n");
                                }
                                showMessage("Detalle de estudiantes", buffer.toString());
                            }
                            if (view == btnMostrarInfo) {
                                showMessage("Super sistem 3000", "La chechi no sabe que poner aca." + "\n" + "=(");
                            }
                        }
                        public void showMessage(String title, String message) {
                            Builder builder = new Builder(this);
                            builder.setCancelable(true);
                            builder.setTitle(title);
                            builder.setMessage(message);
                            builder.show();
                        }
                        public void clearText() {
                            editLegajo.setText("");
                            editNombre.setText("");
                            editNotas.setText("");
                            editLegajo.requestFocus();
                        }
                    }
