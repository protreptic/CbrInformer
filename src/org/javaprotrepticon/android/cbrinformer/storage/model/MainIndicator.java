package org.javaprotrepticon.android.cbrinformer.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MainIndicator {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private String name;
	
}
