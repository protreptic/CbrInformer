package org.javaprotrepticon.android.cbrinformer.storage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Справочник по кодам валют, содержит полный перечень валют котируемых Банком России.
 * 
 * @see <a href="http://www.cbr.ru/scripts/Root.asp?PrtId=DWS">Банком России</a>
 * 
 * @author petronic
 *
 */
@DatabaseTable
public class Сurrency {
	
	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Country country;
	
	/**
	 * Внутренний код валюты - код для идентификации валют, является локальным и 
	 * уникальным идентификатором валюты в данной базе, необходим для использования 
	 * в качестве параметра для методов GetCursDynamic (GetCursDynamicXML).
	 * 
	 */
	@DatabaseField
	private String innerCode; 
	
	/**
	 * Название валюты
	 */
	@DatabaseField
	private String name; 
	
	/**
	 * Англ. название валюты
	 */
	@DatabaseField
	private String engName; 
	
	/**
	 * Номинал
	 */
	@DatabaseField
	private Integer nominal; 
	
	/**
	 * Внутренний код валюты, являющейся 'базовой'.
	 * Этот код используется для связи, при изменениях кодов или 
	 * названий фактически одной и той же валюты.
	 */
	@DatabaseField
	private String commonCode; 
	
	/**
	 * цифровой код ISO
	 */
	@DatabaseField
	private Integer numCode; 
	
	/**
	 * 3х буквенный код ISO
	 */
	@DatabaseField
	private String charCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public Integer getNominal() {
		return nominal;
	}

	public void setNominal(Integer nominal) {
		this.nominal = nominal;
	}

	public String getCommonCode() {
		return commonCode;
	}

	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}

	public Integer getNumCode() {
		return numCode;
	}

	public void setNumCode(Integer numCode) {
		this.numCode = numCode;
	}

	public String getCharCode() {
		return charCode;
	}

	public void setCharCode(String charCode) {
		this.charCode = charCode;
	}
	
}
