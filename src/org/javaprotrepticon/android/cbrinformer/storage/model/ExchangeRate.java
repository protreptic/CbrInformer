package org.javaprotrepticon.android.cbrinformer.storage.model;

import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ExchangeRate implements Parcelable {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField
	private Date date;
	
	@DatabaseField
	private String valuteId;
	
	@DatabaseField
	private String numCode;
	
	@DatabaseField
	private String charCode;
	
	@DatabaseField
	private Float nominal;
	
	@DatabaseField
	private String name;

	@DatabaseField
	private Float value;

	public ExchangeRate() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getValuteId() {
		return valuteId;
	}

	public void setValuteId(String valuteId) {
		this.valuteId = valuteId;
	}

	public String getNumCode() {
		return numCode;
	}

	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}

	public Float getNominal() {
		return nominal;
	}

	public void setNominal(Float nominal) {
		this.nominal = nominal;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	private ExchangeRate(Parcel parcel) {
		setId(parcel.readInt());
		setName(parcel.readString());
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getName());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<ExchangeRate> CREATOR = new Parcelable.Creator<ExchangeRate>() {
		public ExchangeRate createFromParcel(Parcel parcel) {
			return new ExchangeRate(parcel);
		}

		public ExchangeRate[] newArray(int size) {
			return new ExchangeRate[size];
		}
	};

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + "]";
	}

}
