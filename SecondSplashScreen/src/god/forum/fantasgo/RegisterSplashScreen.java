package god.forum.fantasgo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class RegisterSplashScreen extends SherlockActivity {
	
	ImageView signup_register_text,its_all_free;
	Button next_version;
	Animation signup_back,its_frd;
	ActionBar action;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.register_splash_screen);
		
		action=getSupportActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		
		signup_register_text=(ImageView)findViewById(R.id.signupimg);
		its_all_free=(ImageView)findViewById(R.id.itsfree);
		signup_back=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.signup_animation);
		its_frd=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.its_movement);
		long time=AnimationUtils.currentAnimationTimeMillis();
		
		signup_register_text.invalidate();
		its_all_free.invalidate();
		signup_back.setStartTime(time);
		its_frd.setStartTime(time);
		
		next_version=(Button)findViewById(R.id.next_register_click);
		next_version.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				signup_register_text.startAnimation(signup_back);
				its_all_free.startAnimation(its_frd);
				
			
			
				
			}
		});
		
		signup_back.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent=new Intent(getApplicationContext(),TermsAndConditions.class);
				startActivity(intent);
				finish();
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
