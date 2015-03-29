package org.javaprotrepticon.android.cbrinformer.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Bank {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Country country;
	
	@DatabaseField
	private String name;
	
}
