package org.javaprotrepticon.android.cbrinformer.fragment;

import java.sql.Date;

public class SummaryFragment {
	
	public class MainIndicatorsVR {
		
		private String title;
		private Currency currency;
		
		public class Currency {
			
			private String title;
			private Date lastUpdate;
			private Date onDate;
			private Float usdRate;
			private Float eurRate;
			
			
		}

		public class Metall {}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Currency getCurrency() {
			return currency;
		}

		public void setCurrency(Currency currency) {
			this.currency = currency;
		}
		
	}
	
}
