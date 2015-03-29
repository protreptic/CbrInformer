package org.javaprotrepticon.android.cbrinformer.source;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.javaprotrepticon.android.cbrinformer.storage.Storage;
import org.javaprotrepticon.android.cbrinformer.storage.model.Region;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

public class SummaryParser extends AsyncTask<Void, Void, Void> {
	
	private static final String SERVICE_URL = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
	private static final String NAMESPACE = "http://web.cbr.ru/";
	private static final String SOAP_ACTION = "http://web.cbr.ru/AllDataInfoXML";
	
	private List<Region> getRegions() throws HttpResponseException, IOException, XmlPullParserException {
		SoapObject soapRequest = new SoapObject(NAMESPACE, "AllDataInfoXML");
 
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
	
	public List<Region> parseResponse(SoapObject response) {
		List<Region> regions = new ArrayList<Region>();
		
		SoapObject AllData = (SoapObject) response.getProperty("AllData");
		
		if (AllData != null) {
		    for (int index = 0; index < AllData.getPropertyCount(); index++) {
		         SoapObject property = (SoapObject) AllData.getProperty(index);
		         
		         Region region = new Region();
		         region.setId(Integer.valueOf(property.getPrimitivePropertySafelyAsString("RegCode"))); 
		         region.setName(property.getPrimitivePropertySafelyAsString("CNAME"));
		         
		         regions.add(region);
		    }
		}
		
		return regions;
	}
	
	private Context mContext;
	
	public SummaryParser(Context context) {
		mContext = context;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Void... params) {
		Storage storage = new Storage(mContext);
		
		try {	
			Dao<Region, Integer> dao = (Dao<Region, Integer>) storage.createDao(Region.class);
			
			for (Region regions : getRegions()) {
				dao.createOrUpdate(regions);
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