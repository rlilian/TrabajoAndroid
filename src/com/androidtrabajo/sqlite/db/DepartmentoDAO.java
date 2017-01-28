package com.androidtrabajo.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import com.androidtrabajo.sqlite.to.Departamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DepartmentoDAO extends EmpleadoDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";

	public DepartmentoDAO(Context context) {
		super(context);
	}

	public long save(Departamento departamento) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, departamento.getName());

		return database.insert(DataBaseHelper.DEPARTMENT_TABLE, null, values);
	}

	public long update(Departamento departamento) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, departamento.getName());

		long result = database.update(DataBaseHelper.DEPARTMENT_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(departamento.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	public int deleteDept(Departamento departamento) {
		return database.delete(DataBaseHelper.DEPARTMENT_TABLE,
				WHERE_ID_EQUALS, new String[] { departamento.getId() + "" });
	}

	public List<Departamento> getDepartments() {
		List<Departamento> departamentos = new ArrayList<Departamento>();
		Cursor cursor = database.query(DataBaseHelper.DEPARTMENT_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			Departamento departamento = new Departamento();
			departamento.setId(cursor.getInt(0));
			departamento.setName(cursor.getString(1));
			departamentos.add(departamento);
		}
		return departamentos;
	}

	public void loadDepartments() {
		Departamento departamento = new Departamento("Desarollo");
		Departamento department1 = new Departamento("R and D");
		Departamento department2 = new Departamento("Recursos Humanos");
		Departamento department3 = new Departamento("Ventas");
		Departamento department4 = new Departamento("Marketing");
		Departamento department5 = new Departamento("Almacen");

		List<Departamento> departamentos = new ArrayList<Departamento>();
		departamentos.add(departamento);
		departamentos.add(department1);
		departamentos.add(department2);
		departamentos.add(department3);
		departamentos.add(department4);
		departamentos.add(department5);
		for (Departamento dept : departamentos) {
			ContentValues values = new ContentValues();
			values.put(DataBaseHelper.NAME_COLUMN, dept.getName());
			database.insert(DataBaseHelper.DEPARTMENT_TABLE, null, values);
		}
	}

}
