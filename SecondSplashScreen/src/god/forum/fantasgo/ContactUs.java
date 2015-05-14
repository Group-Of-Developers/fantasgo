package god.forum.fantasgo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ContactUs extends SherlockActivity {
	
	ActionBar action;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.contact_us_xml);
		
		action=getSupportActionBar();
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    	case android.R.id.home:
		    		this.finish();
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
}
