package com.androidtrabajo.sqlite.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.androidtrabajo.sqlite.R;
import com.androidtrabajo.sqlite.adapter.EmpListAdapter;
import com.androidtrabajo.sqlite.db.EmpleadoDAO;
import com.androidtrabajo.sqlite.to.Empleado;

public class EmpListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "employee_list";

	Activity activity;
	ListView employeeListView;
	ArrayList<Empleado> empleados;

	EmpListAdapter employeeListAdapter;
	EmpleadoDAO empleadoDAO;

	private GetEmpTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		empleadoDAO = new EmpleadoDAO(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_emp_list, container,
				false);
		findViewsById(view);

		task = new GetEmpTask(activity);
		task.execute((Void) null);

		employeeListView.setOnItemClickListener(this);
		employeeListView.setOnItemLongClickListener(this);
		return view;
	}

	private void findViewsById(View view) {
		employeeListView = (ListView) view.findViewById(R.id.list_emp);
	}

	@Override
	public void onItemClick(AdapterView<?> list, View view, int position,
			long id) {
		Empleado empleado = (Empleado) list.getItemAtPosition(position);

		if (empleado != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("selectedEmployee", empleado);
			CustomEmpDialogFragment customEmpDialogFragment = new CustomEmpDialogFragment();
			customEmpDialogFragment.setArguments(arguments);
			customEmpDialogFragment.show(getFragmentManager(),
					CustomEmpDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Empleado empleado = (Empleado) parent.getItemAtPosition(position);
		// Se utiliza AsyncTask para eliminar de la base de datos
		empleadoDAO.deleteEmployee(empleado);
		employeeListAdapter.remove(empleado);

		return true;
	}

	public class GetEmpTask extends AsyncTask<Void, Void, ArrayList<Empleado>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<Empleado> doInBackground(Void... arg0) {
			ArrayList<Empleado> employeeList = empleadoDAO.getEmployees();
			return employeeList;
		}

		@Override
		protected void onPostExecute(ArrayList<Empleado> empList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				empleados = empList;
				if (empList != null) {
					if (empList.size() != 0) {
						employeeListAdapter = new EmpListAdapter(activity,
								empList);
						employeeListView.setAdapter(employeeListAdapter);
					} else {
						Toast.makeText(activity, "No hay empleados registrados",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from CustomEmpDialogFragment when an empleado record is updated.
	 * This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetEmpTask(activity);
		task.execute((Void) null);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.app_name);
		getActivity().getActionBar().setTitle(R.string.app_name);
		super.onResume();
	}
}
