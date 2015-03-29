package org.javaprotrepticon.android.cbrinformer.fragment;

import java.sql.SQLException;

import org.javaprotrepticon.android.cbrinformer.fragment.base.BaseEntityListFragment;
import org.javaprotrepticon.android.cbrinformer.storage.model.Region;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.View.OnClickListener;

public class RegionListFragment extends BaseEntityListFragment<Region> { 

	@Override
	protected Adapter<?> createAdapter() {
		return new DefaultAdapter() {
			
			@Override
			public void onBindViewHolder(DefaultViewHolder holder, int position) {
				final Region region = mEntityList.get(position);
				
				holder.title.setText(region.getName() + " (" + region.getId() + ")");
				holder.title.setTypeface(mRobotoCondensedBold); 
				
				holder.subtitle.setText("");
				holder.subtitle.setTypeface(mRobotoCondensedRegular);
				holder.subtitle.setVisibility(View.GONE); 
				
				holder.description.setText(""); 
				holder.description.setTypeface(mRobotoCondensedBold);
				holder.description.setVisibility(View.GONE); 
				
				holder.itemView.setOnClickListener(new OnClickListener() { 
					
					@Override
					public void onClick(View view) {
					}
				});
				
				holder.itemView.setId(region.getId());  
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
	protected Class<Region> getType() {
		return Region.class;
	}
    
}
