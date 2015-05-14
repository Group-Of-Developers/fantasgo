package god.forum.fantasgo;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class SplashScreen extends SherlockActivity {

	int width,height,i;
	ImageView splashview;
	Button register;
	EditText username__,password__;
	TextView logotext;
	Typeface typeface;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash_screen);
        width=getWindowManager().getDefaultDisplay().getWidth();
        height=getWindowManager().getDefaultDisplay().getWidth();
        i=height/2;
        
        //username__
        username__=(EditText)findViewById(R.id.loginfield);
        username__.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			   if(hasFocus)
				   username__.setBackgroundResource(R.drawable.custom_edit_selected);
			   else
				   username__.setBackgroundResource(R.drawable.custom_edit_text);
			}
		});
        //password__
        password__=(EditText)findViewById(R.id.passwordfield);
        password__.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			   if(hasFocus)
				   password__.setBackgroundResource(R.drawable.custom_edit_selected);
			   else
				   password__.setBackgroundResource(R.drawable.custom_edit_text);
			}
		});

        //logotext
        logotext=(TextView)findViewById(R.id.fantasgo);
        logotext.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceLarge);
        typeface=Typeface.createFromAsset(getAssets(), "Blazed.ttf");
        logotext.setTypeface(typeface);
       // logotext.setTextSize(getFontSize(this));
        
        //splashview
       // splashview=(ImageView)findViewById(R.id.splashimage);
        
        //Register
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(getApplicationContext(),SecondSplashScreen.class);
				startActivity(intent);
			
				/*
				register.requestFocus();
				if(username__.getText().toString().equals(""))
					username__.setBackgroundResource(R.drawable.error_list_view);
					
				if(password__.getText().toString().equals(""))
					password__.setBackgroundResource(R.drawable.error_list_view);
					
				andimationin();
					*/
			}
		});
        
       // animateAccordingly();
       //handler.postDelayed(run, 10L);
        
    }
    
    /*
    public int getFontSize(Activity activity)
    {
    	 DisplayMetrics dMetrics = new DisplayMetrics();
    	 activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
    	 final float WIDE=activity.getResources().getDisplayMetrics().widthPixels;
    	 
    	 int valuewide=(int)(WIDE/32.0f/(dMetrics.scaledDensity));
    	 
    	 return valuewide;
    }
    */
    
    public void andimationin()
    {
    	final Animation in =new AlphaAnimation(1, 0);
    	//in.setInterpolator(new DecelerateInterpolator());
    	in.setDuration(2000);
    	TextView logotext1=(TextView)findViewById(R.id.overlayed);
    	logotext1.startAnimation(in);
    	
    }
    
    TranslateAnimation animation;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void animateAccordingly()
    {
    	try
    	{
    		animation=new TranslateAnimation(0,50,0,100);
    		animation.setDuration(10L);
    		animation.setFillAfter(true);
    		animation.setAnimationListener(new Movement());
    		splashview.startAnimation(animation);
    		
    	}catch(Exception e){}
    }
    
    Handler handler=new Handler();
    Runnable run=new Runnable() {
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void run() {
			if((height/2)-110>=1)
			{
				if(i>=((height/2)-110))
				{
					try {
						i-=4;
						splashview.setY(i);
						handler.postDelayed(this, 1);
					} catch (Exception e) {
						
					}
				}
			}
			
		}
	};


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }
    
    public class Movement implements AnimationListener{

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void onAnimationEnd(Animation arg0) {
			splashview.clearAnimation();
		//	LayoutParams params=new LayoutParams(splashview.getWidth(), splashview.getHeight());
			//params.setMargins(50, 100, 0, 0);
			//splashview.setLayoutParams(params);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
			
		}
    	
    }
    
}
