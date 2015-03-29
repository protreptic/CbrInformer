package org.javaprotrepticon.android.cbrinformer.activity;

import org.javaprotrepticon.android.cbrinformer.R;
import org.javaprotrepticon.android.cbrinformer.fragment.CurrencyListFragment;
import org.javaprotrepticon.android.cbrinformer.fragment.ExchangeRateListFragment;
import org.javaprotrepticon.android.cbrinformer.fragment.RegionListFragment;
import org.javaprotrepticon.android.cbrinformer.source.CurrencyParser;
import org.javaprotrepticon.android.cbrinformer.source.ExchangeRateParser;
import org.javaprotrepticon.android.cbrinformer.source.RegionParser;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	protected ActionBarDrawerToggle mDrawerToggle;
	protected DrawerLayout mDrawerLayout;
	protected LinearLayout mLeftDrawer;
	protected ListView mRightDrawer;
	protected ListView mLeftDrawerList;
	
	protected Toolbar mToolBar;
	
    private int[] mMenuIcons = new int[] { R.drawable.money, R.drawable.folder, R.drawable.folder };
    private int[] mMenuItems = new int[] { R.string.exchangeRates, R.string.regions, R.string.currencies };
	
	public static class MenuItemViewHolder {

		public ImageView image1;
		public TextView textView;
		
	}
	
    private class MenuItemAdapter extends BaseAdapter {

		private Typeface mRobotoCondensedBold;
		
		public MenuItemAdapter() {
			mRobotoCondensedBold = Typeface.createFromAsset(getAssets(), "typeface/RobotoCondensed-Bold.ttf");
		}
    	
		@Override
		public int getCount() {
			return mMenuItems.length;
		}
		
		@Override
		public Object getItem(int position) {
			return mMenuItems[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MenuItemViewHolder holder;
			
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.drawer_list_item, parent, false);
				
				holder = new MenuItemViewHolder();
				holder.image1 = (ImageView) convertView.findViewById(R.id.image1);
				
				holder.textView = (TextView) convertView.findViewById(R.id.text1);
				holder.textView.setTypeface(mRobotoCondensedBold);
				
				convertView.setTag(holder); 
			} else {
				holder = (MenuItemViewHolder) convertView.getTag();
			}
			
			holder.textView.setText(getString((Integer) getItem(position))); 
			holder.image1.setImageDrawable(getResources().getDrawable(mMenuIcons[position])); 
			
			return convertView;
		}
    	
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(new DefaultDrawerListener());
        mDrawerLayout.setDrawerTitle(GravityCompat.START, getString(R.string.drawer_title));
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        
        mLeftDrawer = (LinearLayout) findViewById(R.id.left_drawer);
        
        mLeftDrawerList = (ListView) findViewById(R.id.left_drawer_list);
        mLeftDrawerList.setAdapter(new MenuItemAdapter()); 
        mLeftDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        initToolbar();
        
        selectItem(0); 
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
    @Override
    public void onBackPressed() {
    	if (mDrawerLayout.isDrawerOpen(mLeftDrawer)) {
    		mDrawerLayout.closeDrawer(mLeftDrawer); return;
    	}
    	
    	super.onBackPressed();
    }
    
	private void initToolbar() {
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mToolBar.setTitle("");
		mToolBar.setSubtitle("");
		
	    setSupportActionBar(mToolBar);
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
        
    }
    
    private ExchangeRateListFragment mExchangeRateListFragment;
    private RegionListFragment mRegionListFragment;
    private CurrencyListFragment mCurrencyListFragment;
    
    private void selectItem(int position) {
    	mLeftDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mLeftDrawer);
    	      
        Fragment fragment = null;
        
        switch (position) {
			case 0: {
				if (mExchangeRateListFragment == null) {
					mExchangeRateListFragment = new ExchangeRateListFragment();
				}
				
				fragment = mExchangeRateListFragment;
			} break;
			case 1: {
				if (mRegionListFragment == null) {
					mRegionListFragment = new RegionListFragment();
				}
				
				fragment = mRegionListFragment;
			} break;
			case 2: {
				if (mCurrencyListFragment == null) {
					mCurrencyListFragment = new CurrencyListFragment();
				}
				
				fragment = mCurrencyListFragment;
			} break;
			default: {
				return;
			}
		}
        
        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
        	getSupportFragmentManager().popBackStackImmediate();
        }
        
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
    
	public class DefaultDrawerListener implements DrawerLayout.DrawerListener {
		
        @Override
        public void onDrawerOpened(View drawerView) {
            mDrawerToggle.onDrawerOpened(drawerView);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mDrawerToggle.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            mDrawerToggle.onDrawerStateChanged(newState);
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity, menu); 
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: {
				mDrawerLayout.openDrawer(mLeftDrawer);
			} break;
			case R.id.actionUpdate: {
				new ExchangeRateParser(getBaseContext()).execute(); 
				new RegionParser(getBaseContext()).execute(); 
				new CurrencyParser(getBaseContext()).execute(); 
			} break;
			default: {
				return super.onOptionsItemSelected(item);
			}
		}
		
		return super.onOptionsItemSelected(item);
	}
	
}
