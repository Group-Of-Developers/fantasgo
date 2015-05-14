package god.forum.fantasgo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class TermsAndConditions extends SherlockActivity {
	
	Button continued;
	CheckBox acceptcheckboed;
	ActionBar action;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.terms_conditions_layout);
        
        action=getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);
        
    	//Continued
        continued=(Button)findViewById(R.id.acceptandcontinue);
        continued.setEnabled(false);
        continued.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplicationContext(),RegisterForum.class);
				startActivity(intent);
				finish();
			}
		});
        
        //acceptcheckboed
        
        acceptcheckboed=(CheckBox)findViewById(R.id.acceptcheckbox);
        acceptcheckboed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					continued.setEnabled(isChecked);
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
