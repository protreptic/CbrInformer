package org.javaprotrepticon.android.cbrinformer.source;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.javaprotrepticon.android.cbrinformer.storage.Storage;
import org.javaprotrepticon.android.cbrinformer.storage.model.ExchangeRate;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

public class ExchangeRateParser extends AsyncTask<Void, Void, Void> {
	
	private static final String SERVICE_URL = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
	private static final String NAMESPACE = "http://web.cbr.ru/";
	private static final String SOAP_ACTION = "http://web.cbr.ru/GetCursOnDateXML";
	
	private List<ExchangeRate> getExchangeRates() throws HttpResponseException, IOException, XmlPullParserException {
		SoapObject soapRequest = new SoapObject(NAMESPACE, "GetCursOnDateXML");
 
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("On_date");
		propertyInfo.setValue("2015-02-21"); 

		soapRequest.addProperty(propertyInfo);

		SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
		soapEnvelope.setOutputSoapObject(soapRequest); 
		soapEnvelope.dotNet = true;
		
		HttpTransportSE httpTransportSE = new HttpTransportSE(SERVICE_URL);
		httpTransportSE.call(SOAP_ACTION, soapEnvelope);
		
		if (soapEnvelope.bodyIn instanceof SoapObject) {
			SoapObject soapResponse = (SoapObject) soapEnvelope.getResponse();
			
			return parseResponse(soapResponse);
		} else if (soapEnvelope.bodyIn instanceof SoapFault) {
			SoapFault soapFault = (SoapFault) soapEnvelope.getResponse();

			Log.d("", "message = " + soapFault.getMessage());
		}
		
		return null;
	}
	
	public List<ExchangeRate> parseResponse(SoapObject response) {
		List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();
		
		SoapObject valuteData = (SoapObject) response.getProperty("ValuteData");
		
		if (valuteData != null) {
		    for (int index = 0; index < valuteData.getPropertyCount(); index++) {
		         SoapObject property = (SoapObject) valuteData.getProperty(index);
		         
		         ExchangeRate exchangeRate = new ExchangeRate();
		         exchangeRate.setName(property.getPrimitivePropertySafelyAsString("Vname"));
		         exchangeRate.setCharCode(property.getPrimitivePropertySafelyAsString("VchCode"));
		         exchangeRate.setValue(Float.parseFloat(property.getPrimitivePropertySafelyAsString("Vcurs"))); 
		         
		         exchangeRates.add(exchangeRate);
		    }
		}
		
		return exchangeRates;
	}
	
	private Context mContext;
	
	public ExchangeRateParser(Context context) {
		mContext = context;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Void... params) {
		Storage storage = new Storage(mContext);
		
		try {	
			Dao<ExchangeRate, Integer> managerDao = (Dao<ExchangeRate, Integer>) storage.createDao(ExchangeRate.class);
			managerDao.setObjectCache(false); 
			managerDao.delete(managerDao.queryForAll());
			
			for (ExchangeRate exchangeRate : getExchangeRates()) {
				managerDao.createOrUpdate(exchangeRate);
			}
		} catch (SQLException e) {
			e.printStackTrace(); 
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		storage.closeConnection();
		
		return null;
	}
	
}
