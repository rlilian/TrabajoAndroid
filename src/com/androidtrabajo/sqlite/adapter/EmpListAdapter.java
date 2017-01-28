package com.androidtrabajo.sqlite.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidtrabajo.sqlite.R;
import com.androidtrabajo.sqlite.to.Empleado;

public class EmpListAdapter extends ArrayAdapter<Empleado> {

	private Context context;
	List<Empleado> empleados;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	public EmpListAdapter(Context context, List<Empleado> empleados) {
		super(context, R.layout.list_item, empleados);
		this.context = context;
		this.empleados = empleados;
	}

	private class ViewHolder {
		TextView empIdTxt;
		TextView empNameTxt;
		TextView empDobTxt;
		TextView empSalaryTxt;
		TextView empDeptNameTxt;
	}

	@Override
	public int getCount() {
		return empleados.size();
	}

	@Override
	public Empleado getItem(int position) {
		return empleados.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			
			holder.empIdTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_id);
			holder.empNameTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_name);
			holder.empDobTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_dob);
			holder.empSalaryTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_salary);
			holder.empDeptNameTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_dept);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Empleado empleado = (Empleado) getItem(position);
		holder.empIdTxt.setText(empleado.getId() + "");
		holder.empNameTxt.setText(empleado.getName());
		holder.empSalaryTxt.setText(empleado.getSalary() + "");
		holder.empDeptNameTxt.setText(empleado.getDepartment().getName());
		
		holder.empDobTxt.setText(formatter.format(empleado.getDateOfBirth()));	
		
		return convertView;
	}

	@Override
	public void add(Empleado empleado) {
		empleados.add(empleado);
		notifyDataSetChanged();
		super.add(empleado);
	}

	@Override
	public void remove(Empleado empleado) {
		empleados.remove(empleado);
		notifyDataSetChanged();
		super.remove(empleado);
	}	
}

