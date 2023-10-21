package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditarTareaActivity extends AppCompatActivity {

    private EditText editTextDescripcion;
    private Button btnGuardarCambios;
    private Button btnEliminarTarea;
    private int tareaId; // El tareaId recibido desde la actividad principal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);
        btnEliminarTarea = findViewById(R.id.btnEliminarTarea);

        tareaId = getIntent().getIntExtra("tareaId", -1);
        if (tareaId == -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("No se ha proporcionado un ID de tarea válido.");
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                // Acción a realizar al hacer clic en Aceptar (puede ser cerrar la actividad).
                finish(); // Esto cierra la actividad actual.
            });
            builder.show();
        } else {
            // El tareaId es válido, cargar la tarea desde la base de datos y mostrar los detalles
            cargarTarea();

            btnGuardarCambios.setOnClickListener(v -> guardarCambios());
            btnEliminarTarea.setOnClickListener(v -> eliminarTarea());
        }
    }

    private void cargarTarea() {

        if (tareaId >= 0) {
            // Accede a la base de datos y obtén los detalles de la tarea
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Define las columnas que deseas recuperar
            String[] columns = {DatabaseHelper.COLUMN_TAREA};

            // Define la cláusula WHERE para seleccionar la tarea con el tareaId
            String selection = DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(tareaId)};

            // Realiza la consulta
            Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            // Verifica si se obtuvieron resultados
            if (cursor != null && cursor.moveToFirst()) {
                // Recupera la descripción de la tarea desde el cursor
                @SuppressLint("Range") String tareaDescripcion = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TAREA));

                // Muestra la descripción de la tarea en el EditText o TextView correspondiente
                editTextDescripcion.setText(tareaDescripcion);
            } else {
                // Si no se encontraron resultados, muestra un mensaje de error o maneja la situación según tus necesidades.
                Toast.makeText(this, "No se pudo cargar la tarea", Toast.LENGTH_SHORT).show();
            }

            // Cierra el cursor y la base de datos cuando hayas terminado de usarlos
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        } else {
            // Si el tareaId no es válido, muestra un mensaje de error o maneja la situación según tus necesidades.
            Toast.makeText(this, "Tarea no válida", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarCambios() {

        String nuevaDescripcion = editTextDescripcion.getText().toString().trim();

        // Verifica que la descripción no esté vacía
        if (!nuevaDescripcion.isEmpty()) {
            // Crea una instancia de tu DatabaseHelper
            DatabaseHelper dbHelper = new DatabaseHelper(this);

            // Actualiza la tarea en la base de datos
            boolean cambiosGuardados = dbHelper.actualizarTarea(tareaId, nuevaDescripcion);

            if (cambiosGuardados) {
                // Los cambios se guardaron con éxito, puedes mostrar un mensaje de éxito o realizar alguna otra acción.
                Toast.makeText(this, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();
            } else {
                // Hubo un error al guardar los cambios, muestra un mensaje de error o maneja la situación según tus necesidades.
                Toast.makeText(this, "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
            }
        } else {
            // La descripción está vacía, muestra un mensaje de error o maneja la situación según tus necesidades.
            Toast.makeText(this, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarTarea() {

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Intenta eliminar la tarea de la base de datos
        boolean tareaEliminada = dbHelper.eliminarTarea(tareaId);

        if (tareaEliminada) {
            // La tarea se eliminó con éxito, puedes mostrar un mensaje de éxito o realizar alguna otra acción.
            Toast.makeText(this, "Tarea eliminada con éxito", Toast.LENGTH_SHORT).show();

            // Opcional: Puedes cerrar la actividad actual y volver a la actividad principal u otra actividad.
            finish(); // Cierra esta actividad.
        } else {
            // Hubo un error al eliminar la tarea, muestra un mensaje de error o maneja la situación según tus necesidades.
            Toast.makeText(this, "Error al eliminar la tarea", Toast.LENGTH_SHORT).show();
        }
    }
}