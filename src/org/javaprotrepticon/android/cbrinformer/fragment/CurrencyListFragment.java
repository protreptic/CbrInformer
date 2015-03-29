package org.javaprotrepticon.android.cbrinformer.fragment;

import java.sql.SQLException;

import org.javaprotrepticon.android.cbrinformer.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.cbrinformer.storage.model.Сurrency;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.View.OnClickListener;

public class CurrencyListFragment extends BaseEntityListFragment<Сurrency> { 

	@Override
	protected Adapter<?> createAdapter() {
		return new DefaultAdapter() {
			
			@Override
			public void onBindViewHolder(DefaultViewHolder holder, int position) {
				final Сurrency сurrency = mEntityList.get(position);
				
				holder.title.setText("");
				holder.title.setTypeface(mRobotoCondensedBold); 
				holder.title.setVisibility(View.GONE); 
				
				holder.subtitle.setText(сurrency.getName());
				holder.subtitle.setTypeface(mRobotoCondensedRegular);
				
				holder.description.setText(""); 
				holder.description.setTypeface(mRobotoCondensedBold);
				holder.description.setVisibility(View.GONE); 
				
				holder.itemView.setOnClickListener(new OnClickListener() { 
					
					@Override
					public void onClick(View view) {}
					
				});
			}
			
		};
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
	protected Class<Сurrency> getType() {
		return Сurrency.class;
	}
	
}
