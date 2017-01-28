package com.androidtrabajo.sqlite.to;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Empleado implements Parcelable {

	private int id;
	private String name;
	private Date dateOfBirth;
	private double salary;

	private Departamento departamento;

	public Empleado() {
		super();
	}

	private Empleado(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
		this.dateOfBirth = new Date(in.readLong());
		this.salary = in.readDouble();

		this.departamento = in.readParcelable(Departamento.class.getClassLoader());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Departamento getDepartment() {
		return departamento;
	}

	public void setDepartment(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", name=" + name + ", dateOfBirth="
				+ dateOfBirth + ", salary=" + salary + ", departamento="
				+ departamento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
		parcel.writeLong(getDateOfBirth().getTime());
		parcel.writeDouble(getSalary());
		parcel.writeParcelable(getDepartment(), flags);
	}

	public static final Parcelable.Creator<Empleado> CREATOR = new Parcelable.Creator<Empleado>() {
		public Empleado createFromParcel(Parcel in) {
			return new Empleado(in);
		}

		public Empleado[] newArray(int size) {
			return new Empleado[size];
		}
	};

}
