package org.javaprotrepticon.android.cbrinformer.source;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.javaprotrepticon.android.cbrinformer.storage.Storage;
import org.javaprotrepticon.android.cbrinformer.storage.model.Сurrency;
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

public class CurrencyParser extends AsyncTask<Void, Void, Void> {
	
	private static final String SERVICE_URL = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
	private static final String NAMESPACE = "http://web.cbr.ru/";
	private static final String SOAP_ACTION = "http://web.cbr.ru/EnumValutesXML";
	
	private List<Сurrency> getСurrencies() throws HttpResponseException, IOException, XmlPullParserException {
		SoapObject soapRequest = new SoapObject(NAMESPACE, "EnumValutesXML");
 
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("Seld");
		propertyInfo.setValue("false"); 

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

			Log.e("", "message = " + soapFault.getMessage());
		}
		
		return null;
	}
	
	public List<Сurrency> parseResponse(SoapObject response) {
		List<Сurrency> сurrencies = new ArrayList<Сurrency>();
		
		SoapObject ValuteData = (SoapObject) response.getProperty("ValuteData");
		
		if (ValuteData != null) {
		    for (int index = 0; index < ValuteData.getPropertyCount(); index++) {
		         SoapObject property = (SoapObject) ValuteData.getProperty(index);
		         
		         Сurrency currency = new Сurrency();
		         currency.setInnerCode(property.getPrimitivePropertySafelyAsString("Vcode")); 
		         currency.setName(property.getPrimitivePropertySafelyAsString("Vname"));
		         currency.setEngName(property.getPrimitivePropertySafelyAsString("VEngname"));
		         currency.setNominal(Integer.valueOf(property.getPrimitivePropertySafelyAsString("Vnom")));
		         currency.setCommonCode(property.getPrimitivePropertySafelyAsString("VcommonCode")); 
		         currency.setNumCode(Integer.valueOf((property.getPrimitivePropertySafelyAsString("VnumCode").isEmpty()) ? "0" : property.getPrimitivePropertySafelyAsString("VnumCode")));
		         currency.setCharCode(property.getPrimitivePropertySafelyAsString("VcharCode"));
		         
		         сurrencies.add(currency);
		    }
		}
		
		return сurrencies;
	}
	
	private Context mContext;
	
	public CurrencyParser(Context context) {
		mContext = context;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Void... params) {
		Storage storage = new Storage(mContext);
		
		try {	
			Dao<Сurrency, Integer> dao = (Dao<Сurrency, Integer>) storage.createDao(Сurrency.class);
			
			for (Сurrency сurrency : getСurrencies()) {
				dao.createOrUpdate(сurrency);
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
