package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SQLiteCRUDactivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextTarea;
    private Button btnAgregar;
    private ListView listViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_crudactivity);

        dbHelper = new DatabaseHelper(this);
        editTextTarea = findViewById(R.id.editTextTarea);
        btnAgregar = findViewById(R.id.btnAgregar);
        listViewTareas = findViewById(R.id.listViewTareas);

        mostrarTareas();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregarTarea();
            }
        });

        listViewTareas.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            if (cursor != null && cursor.moveToFirst()) {

                int tareaId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String tareaNombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TAREA));

                if (tareaId >= 0 && tareaNombre != null) {
                    // Aquí puedes implementar la lógica para actualizar o eliminar la tarea
                    mostrarOpcionesTareas(tareaId, tareaNombre);
                } else {
                    // Muestra un mensaje de error específico si los datos son inválidos.
                    Toast.makeText(this, "Error: Datos de tarea inválidos", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Muestra un mensaje de error específico si hay un problema con el cursor.
                Toast.makeText(this, "Error al acceder a la tarea seleccionada", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void mostrarTareas() {
        Cursor cursor = dbHelper.obtenerTareas();

        if (cursor != null) {
            // Verifica si el cursor está vacío
            if (cursor.getCount() > 0) {
                String[] fromColumns = {DatabaseHelper.COLUMN_TAREA};
                int[] toViews = {android.R.id.text1};
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, fromColumns, toViews, 0);
                listViewTareas.setAdapter(adapter);
            } else {
                // Si el cursor está vacío, muestra un mensaje de que no hay tareas.
                listViewTareas.setAdapter(null); // Borra cualquier adaptador existente.
                Toast.makeText(this, "No hay tareas disponibles", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Si el cursor es nulo, muestra un mensaje de error.
            listViewTareas.setAdapter(null); // Borra cualquier adaptador existente.
            Toast.makeText(this, "Error al acceder a la tarea seleccionada", Toast.LENGTH_SHORT).show();
        }
    }


    private void agregarTarea() {
        String tarea = editTextTarea.getText().toString().trim();
        if (!tarea.isEmpty()) {
            if (dbHelper.agregarTarea(tarea)) {
                Toast.makeText(this, "Tarea agregada", Toast.LENGTH_SHORT).show();
                editTextTarea.getText().clear();
                mostrarTareas();
            } else {
                Toast.makeText(this, "Error al agregar la tarea", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese una tarea válida", Toast.LENGTH_SHORT).show();
        }

    }

    private void mostrarOpcionesTareas(int tareaId, String tareaNombre) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones para la tarea: " + tareaNombre);
        builder.setItems(new CharSequence[]{"Editar", "Eliminar"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Opción de edición: Inicia una actividad de edición con la tarea seleccionada.
                    Intent intent = new Intent(this, EditarTareaActivity.class);
                    intent.putExtra("tareaId", tareaId); // Pasa el "tareaId" a la actividad de edición.
                    startActivity(intent);

                    break;
                case 1:
                    // Opción de eliminación: Solicita una confirmación y elimina la tarea si el usuario confirma.
                    AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
                    confirmDialog.setMessage("¿Desea eliminar esta tarea?");
                    confirmDialog.setPositiveButton("Sí", (confirmDialogInterface, confirmDialogInt) -> {
                        // Elimina la tarea de la base de datos y actualiza la lista.
                        boolean tareaEliminada = dbHelper.eliminarTarea(tareaId);
                        if (tareaEliminada) {
                            mostrarTareas();
                        } else {
                            Toast.makeText(this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
                        }
                    });
                    confirmDialog.setNegativeButton("No", null);
                    confirmDialog.show();
                    break;
            }
        });

        builder.create().show();
    }
}