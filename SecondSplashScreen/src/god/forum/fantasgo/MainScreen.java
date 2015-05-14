package god.forum.fantasgo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainScreen extends SherlockFragmentActivity {
	
	 ActionBar actionBar;
	 EditText textbox;
		private ViewPager viewPager;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
			if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
			{
				setTheme(R.style.Theme_Sherlock_Light);
			}

	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.footer);
	        
	        viewPager=(ViewPager)findViewById(R.id.pager);
	        viewPager.setOnPageChangeListener(onPageChangeListener);
	        viewPager.setAdapter(new PagerAdapters(getSupportFragmentManager()));
	        
	       actionBar = getSupportActionBar();
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        
	        actionBar.setDisplayShowTitleEnabled(true);
	        
	        
	        Tab tab = actionBar.newTab()
	        					.setText("Categories")
	        					.setTabListener(new TabListnr())
	        					;
	        
	        actionBar.addTab(tab);
	        
	        tab = actionBar.newTab()
					.setText("My Posts")
					.setTabListener(new TabListnr())
					;
	        
	        actionBar.addTab(tab);
	        
	        
	    }
	    
	   // private final int SEARCH_ID=Menu.FIRST;
	    private final int REFRESH_MORE=Menu.FIRST;
	    private final int CONTACT_US=Menu.FIRST+1;
	    private final int REQUEST_CAT=Menu.FIRST+2;
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	
	    	super.onCreateOptionsMenu(menu);
	    	//menu.add(Menu.NONE, SEARCH_ID, Menu.NONE, "Search").setIcon(android.R.drawable.ic_menu_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	    	menu.add(Menu.NONE,REFRESH_MORE, Menu.NONE, "Refresh").setIcon(android.R.drawable.ic_popup_sync).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	    	menu.add(Menu.NONE,CONTACT_US, Menu.NONE, "Contact Us").setIcon(android.R.drawable.ic_menu_info_details).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	    	menu.add(Menu.NONE,REQUEST_CAT, Menu.NONE, "Request Category").setIcon(android.R.drawable.ic_menu_upload).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	    	
	    	return true;
	    }
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		       /* case SEARCH_ID:
		        	
		        	Intent intent=new Intent(getApplicationContext(),SearchThread.class);
		        	startActivity(intent);
		        	
		            return true;*/
		        case REFRESH_MORE:
		        	viewPager.setCurrentItem(0);
		        	 viewPager.setAdapter(new PagerAdapters(getSupportFragmentManager()));
		  	       
		        	return true;
		        case CONTACT_US:
		        	Intent intented=new Intent(getApplicationContext(),ContactUs.class);
		        	startActivity(intented);
		        	return true;

		        case REQUEST_CAT:
		        	Intent inten=new Intent(getApplicationContext(),AddCommentorThread.class);
		        	Bundle bundle=new Bundle();
		        	bundle.putString("id_to_change_topic_name", "3");
		        	inten.putExtras(bundle);
		        	startActivity(inten);
		        	return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	    
	        private ViewPager.SimpleOnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
	    		@Override
	    		public void onPageSelected(int position) {
	    			super.onPageSelected(position);
	    			actionBar.setSelectedNavigationItem(position);
	    		}
	    	};
	        
	        private class TabListnr implements ActionBar.TabListener{

	    		@Override
	    		public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    			viewPager.setCurrentItem(tab.getPosition());
	    				
	    		}

	    		@Override
	    		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	    			// TODO Auto-generated method stub
	    			
	    		}

	    		@Override
	    		public void onTabReselected(Tab tab, FragmentTransaction ft) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	        	
	        	
	        }
	        
	    }
