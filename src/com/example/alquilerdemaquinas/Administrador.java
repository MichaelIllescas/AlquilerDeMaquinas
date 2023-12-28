package com.example.alquilerdemaquinas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Administrador extends SQLiteOpenHelper {
	
	public Administrador(Context contexto, String nombre, CursorFactory factory, int version)
	{
		super(contexto, nombre, factory, version); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String sql="Create Table Alquileres(dni integer primary key,nombre text, telefono integer, importeAlquilado float)";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		

	}

}
