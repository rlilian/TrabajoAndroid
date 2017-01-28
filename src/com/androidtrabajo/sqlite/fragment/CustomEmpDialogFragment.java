package com.androidtrabajo.sqlite.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidtrabajo.sqlite.MainActivity;
import com.androidtrabajo.sqlite.R;
import com.androidtrabajo.sqlite.db.DepartmentoDAO;
import com.androidtrabajo.sqlite.db.EmpleadoDAO;
import com.androidtrabajo.sqlite.to.Departamento;
import com.androidtrabajo.sqlite.to.Empleado;

public class CustomEmpDialogFragment extends DialogFragment {

	// UI references
	private EditText empNameEtxt;
	private EditText empSalaryEtxt;
	private EditText empDobEtxt;
	private Spinner deptSpinner;
	private LinearLayout submitLayout;

	private Empleado empleado;

	EmpleadoDAO empleadoDAO;
	ArrayAdapter<Departamento> adapter;

	public static final String ARG_ITEM_ID = "emp_dialog_fragment";
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	/*
	 * Callback used to communicate with EmpListFragment to notify the list adapter.
	 * MainActivity implements this interface and communicates with EmpListFragment.
	 */
	public interface CustomEmpDialogFragmentListener {
		void onFinishDialog();
	}

	public CustomEmpDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		empleadoDAO = new EmpleadoDAO(getActivity());

		Bundle bundle = this.getArguments();
		empleado = bundle.getParcelable("selectedEmployee");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.fragment_add_emp,
				null);
		builder.setView(customDialogView);

		empNameEtxt = (EditText) customDialogView.findViewById(R.id.etxt_name);
		empSalaryEtxt = (EditText) customDialogView
				.findViewById(R.id.etxt_salary);
		empDobEtxt = (EditText) customDialogView.findViewById(R.id.etxt_dob);
		deptSpinner = (Spinner) customDialogView
				.findViewById(R.id.spinner_dept);
		submitLayout = (LinearLayout) customDialogView
				.findViewById(R.id.layout_submit);
		submitLayout.setVisibility(View.GONE);
		setValue();

		builder.setTitle(R.string.update_emp);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.update,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							empleado.setDateOfBirth(formatter.parse(empDobEtxt.getText().toString()));
						} catch (ParseException e) {
							Toast.makeText(getActivity(),
									"Invalid date format!",
									Toast.LENGTH_SHORT).show();
							return;
						}
						empleado.setName(empNameEtxt.getText().toString());
						empleado.setSalary(Double.parseDouble(empSalaryEtxt
								.getText().toString()));
						Departamento dept = (Departamento) adapter
								.getItem(deptSpinner.getSelectedItemPosition());
						empleado.setDepartment(dept);
						long result = empleadoDAO.update(empleado);
						if (result > 0) {
							MainActivity activity = (MainActivity) getActivity();
							activity.onFinishDialog();
						} else {
							Toast.makeText(getActivity(),
									"Unable to update empleado",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();

					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	private void setValue() {
		DepartmentoDAO departmentoDAO = new DepartmentoDAO(getActivity());

		List<Departamento> departamentos = departmentoDAO.getDepartments();
		adapter = new ArrayAdapter<Departamento>(getActivity(),
				android.R.layout.simple_list_item_1, departamentos);
		deptSpinner.setAdapter(adapter);
		int pos = adapter.getPosition(empleado.getDepartment());

		if (empleado != null) {
			empNameEtxt.setText(empleado.getName());
			empSalaryEtxt.setText(empleado.getSalary() + "");
			empDobEtxt.setText(formatter.format(empleado.getDateOfBirth()));
			deptSpinner.setSelection(pos);
		}
	}
}
