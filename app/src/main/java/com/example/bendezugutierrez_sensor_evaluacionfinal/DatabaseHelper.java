package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tareas.db";
    public static final String TABLE_NAME = "tareas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAREA = "tarea";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TAREA + " TEXT NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean agregarTarea(String tarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAREA, tarea);
        long resultado = db.insert(TABLE_NAME, null, contentValues);
        return  resultado != -1;
    }

    public Cursor obtenerTareas() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{COLUMN_ID + " AS _id", COLUMN_TAREA};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    public  boolean actualizarTarea(int id, String nuevaTarea) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAREA, nuevaTarea);
        int filasActualizadas = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return filasActualizadas > 0;
    }

    public boolean eliminarTarea(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasEliminadas = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return filasEliminadas > 0;
    }
}
