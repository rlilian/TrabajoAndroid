package com.androidtrabajo.sqlite.to;

import android.os.Parcel;
import android.os.Parcelable;

public class Departamento implements Parcelable {
	private int id;
	private String name;

	public Departamento() {
		super();
	}

	public Departamento(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Departamento(String name) {
		this.name = name;
	}

	private Departamento(Parcel in) {
		super();
		this.id = in.readInt();
		this.name = in.readString();
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

	@Override
	public String toString() {
		return "id:" + id + ", name:" + name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
	}

	public static final Parcelable.Creator<Departamento> CREATOR = new Parcelable.Creator<Departamento>() {
		public Departamento createFromParcel(Parcel in) {
			return new Departamento(in);
		}

		public Departamento[] newArray(int size) {
			return new Departamento[size];
		}
	};

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
		Departamento other = (Departamento) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
