package org.javaprotrepticon.android.cbrinformer.activity;

import java.sql.SQLException;

import org.javaprotrepticon.android.cbrinformer.R;
import org.javaprotrepticon.android.cbrinformer.storage.Storage;
import org.javaprotrepticon.android.cbrinformer.storage.model.Bank;
import org.javaprotrepticon.android.cbrinformer.storage.model.Country;
import org.javaprotrepticon.android.cbrinformer.storage.model.ExchangeRate;
import org.javaprotrepticon.android.cbrinformer.storage.model.Region;
import org.javaprotrepticon.android.cbrinformer.storage.model.Сurrency;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.j256.ormlite.table.TableUtils;

public class LauncherActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.launcher_activity);
		
		new PrepareStorage().execute();
	}
	
	public class PrepareStorage extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Storage storage = new Storage(getBaseContext());
			
			try {
				TableUtils.createTableIfNotExists(storage.getConnection(), Country.class);
				TableUtils.createTableIfNotExists(storage.getConnection(), Region.class);
				TableUtils.createTableIfNotExists(storage.getConnection(), Bank.class);
				TableUtils.createTableIfNotExists(storage.getConnection(), Сurrency.class);
				TableUtils.createTableIfNotExists(storage.getConnection(), ExchangeRate.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			storage.closeConnection();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
			
			startActivity(intent); 
		}
		
	}
	
}
