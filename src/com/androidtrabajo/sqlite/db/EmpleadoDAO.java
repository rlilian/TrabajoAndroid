package com.androidtrabajo.sqlite.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.androidtrabajo.sqlite.to.Departamento;
import com.androidtrabajo.sqlite.to.Empleado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
//import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;	

public class EmpleadoDAO extends EmpleadoDBDAO {

	public static final String EMPLOYEE_ID_WITH_PREFIX = "emp.id";
	public static final String EMPLOYEE_NAME_WITH_PREFIX = "emp.name";
	public static final String DEPT_NAME_WITH_PREFIX = "dept.name";
	
	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN + " =?";
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public EmpleadoDAO(Context context) {
		super(context);
	}

	public long save(Empleado empleado) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, empleado.getName());
		values.put(DataBaseHelper.EMPLOYEE_DOB, formatter.format(empleado.getDateOfBirth()));
		values.put(DataBaseHelper.EMPLOYEE_SALARY, empleado.getSalary());
		values.put(DataBaseHelper.EMPLOYEE_DEPARTMENT_ID, empleado.getDepartment().getId());

		return database.insert(DataBaseHelper.EMPLOYEE_TABLE, null, values);
	}

	public long update(Empleado empleado) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, empleado.getName());
		values.put(DataBaseHelper.EMPLOYEE_DOB, formatter.format(empleado.getDateOfBirth()));
		values.put(DataBaseHelper.EMPLOYEE_SALARY, empleado.getSalary());
		values.put(DataBaseHelper.EMPLOYEE_DEPARTMENT_ID, empleado.getDepartment().getId());

		long result = database.update(DataBaseHelper.EMPLOYEE_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(empleado.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;
	}

	public int deleteEmployee(Empleado empleado) {
		return database.delete(DataBaseHelper.EMPLOYEE_TABLE, WHERE_ID_EQUALS,
				new String[] { empleado.getId() + "" });
	}
	
	// Usa rawQuery () para consultar varias tablas
	public ArrayList<Empleado> getEmployees() {
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
				+ EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
				+ "," + DataBaseHelper.EMPLOYEE_SALARY + ","
				+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
				+ DEPT_NAME_WITH_PREFIX + " FROM "
				+ DataBaseHelper.EMPLOYEE_TABLE + " emp, "
				+ DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
				+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
				+ DataBaseHelper.ID_COLUMN;

		Log.d("query", query);
		Cursor cursor = database.rawQuery(query, null);
		while (cursor.moveToNext()) {
			Empleado empleado = new Empleado();
			empleado.setId(cursor.getInt(0));
			empleado.setName(cursor.getString(1));
			try {
				empleado.setDateOfBirth(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				empleado.setDateOfBirth(null);
			}
			empleado.setSalary(cursor.getDouble(3));

			Departamento departamento = new Departamento();
			departamento.setId(cursor.getInt(4));
			departamento.setName(cursor.getString(5));

			empleado.setDepartment(departamento);

			empleados.add(empleado);
		}
		return empleados;
	}
	
}
