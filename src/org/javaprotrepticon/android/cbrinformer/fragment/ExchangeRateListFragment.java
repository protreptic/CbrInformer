package org.javaprotrepticon.android.cbrinformer.fragment;

import java.sql.SQLException;

import org.javaprotrepticon.android.cbrinformer.R;
import org.javaprotrepticon.android.cbrinformer.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.cbrinformer.storage.model.ExchangeRate;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExchangeRateListFragment extends BaseEntityListFragment<ExchangeRate> { 

	public class FragmentAdapter extends RecyclerView.Adapter<RateExchangeViewHolder> {
		
		@Override
		public int getItemCount() {
			return mEntityList.size();
		}
		
		@Override
		public void onBindViewHolder(RateExchangeViewHolder holder, int position) {
			final ExchangeRate exchangeRate = mEntityList.get(position);
			
			holder.title.setText(exchangeRate.getCharCode());
			holder.title.setTypeface(mRobotoCondensedRegular); 
			
			holder.title1.setText(exchangeRate.getValue().toString());
			holder.title1.setTypeface(mRobotoCondensedRegular); 
			
			holder.title2.setText("+0.2345"); 
			holder.title2.setTypeface(mRobotoCondensedLight); 
		}

		@Override
		public RateExchangeViewHolder onCreateViewHolder(ViewGroup parent, int position) {
			return new RateExchangeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rate_exchange, parent, false));
		}
		
	}
	
	@Override
	protected Adapter<?> createAdapter() {
		return new FragmentAdapter();
	}

	public static class RateExchangeViewHolder extends RecyclerView.ViewHolder {
		
		public TextView title;
		public TextView title1;
		public TextView title2;
		
		public RateExchangeViewHolder(View itemView) {
			super(itemView); 
			
			title = (TextView) itemView.findViewById(R.id.title);
			title1 = (TextView) itemView.findViewById(R.id.TextView01);
			title2 = (TextView) itemView.findViewById(R.id.description);
		}
	}
	
	@Override
	protected void refreshData(boolean animated) {
		new DataLoader(animated) {

			@Override
			protected Void doInBackground(Void... params) {
				super.doInBackground(params);
				
				try {
					mQueryBuilder.where()
						.like("name", mQueryText);
					
					mQueryBuilder.orderBy("name", true);
					
					mEntityList.addAll(mQueryBuilder.query());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return null;
			}
			
		}.execute();
	}

	@Override
	protected Class<ExchangeRate> getType() {
		return ExchangeRate.class;
	}
    
}
