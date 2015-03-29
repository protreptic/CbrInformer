package org.javaprotrepticon.android.cbrinformer.storage.model;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Summary {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private Date date;
	
}
