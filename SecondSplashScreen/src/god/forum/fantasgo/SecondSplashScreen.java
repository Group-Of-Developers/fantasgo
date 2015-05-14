package god.forum.fantasgo;

import god.forum.fantasgo.library.JSONParser;
import god.forum.fantasgo.library.Networkcheck;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.internal.widget.IcsProgressBar;

public class SecondSplashScreen extends SherlockActivity {


	public static final String KEY_SUCCESS = "success";
	//public static final String URL_LOGIN = "http://10.0.2.2:8080/forum_fantasgo/signin.php";
	public static final String URL_LOGIN ="http://www.groupofdevelopers.com/forum_fantasgo/signin.php";
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_USER_ID = "user_id" ;
	public static final String KEY_USER_INFO = "user_info";
	ImageView fantasgo_second,fantas_font; 
	Button register,loginclick;
	EditText username__,password__;
	IcsProgressBar progress;
	private SharedPreferences savedarea,saveduseridentity;
	public String username,uname,uid;
	public String pass2;
	JSONParser jsonparser = new JSONParser();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		if(Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
		{
			setTheme(R.style.Theme_Sherlock_Light_NoActionBar);
			   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		               WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.splash_screen_second);
		savedarea=getSharedPreferences("FANTASGO_VALUE", MODE_PRIVATE);
		saveduseridentity=getSharedPreferences("FANTASGO_USER_DETAILS", MODE_PRIVATE);
		
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

        //Register
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Networkcheck.getInstance(getApplicationContext()).isOnline(getApplicationContext()))
				{
				Intent intent=new Intent(getApplicationContext(),RegisterSplashScreen.class);
				startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "Fantasgo in not able to connect ..Please check your Internet settings", Toast.LENGTH_LONG).show();
				}
					
			}
		});
        
        progress=(IcsProgressBar)findViewById(R.id.spinner);
        loginclick=(Button)findViewById(R.id.login);
        loginclick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Networkcheck.getInstance(getApplicationContext()).isOnline(getApplicationContext())){
				if(!username__.getText().toString().equals("") && !password__.getText().toString().equals(""))
				{
					username__.setVisibility(View.INVISIBLE);
					password__.setVisibility(View.INVISIBLE);
					loginclick.setVisibility(View.INVISIBLE);
					register.setVisibility(View.INVISIBLE);
					progress.invalidate();
					progress.setVisibility(View.VISIBLE);
					 setData();
					 new login().execute();
				}
				
				else if(username__.getText().toString().equals(""))
					username__.setBackgroundResource(R.drawable.error_list_view);
					
				if(password__.getText().toString().equals(""))
					password__.setBackgroundResource(R.drawable.error_list_view);
			}else
			{
				Toast.makeText(getApplicationContext(), "Fantasgo in not able to connect ..Please check your Internet settings", Toast.LENGTH_LONG).show();
			}
				
			}
		});
        

		username__.setVisibility(View.INVISIBLE);
		password__.setVisibility(View.INVISIBLE);
		loginclick.setVisibility(View.INVISIBLE);
		register.setVisibility(View.INVISIBLE);
		progress.setVisibility(View.INVISIBLE);

		fantasgo_second=(ImageView)findViewById(R.id.overlayed_sec);
		fantas_font=(ImageView)findViewById(R.id.fantasgo_sec);
		
		fantasgo_second.setImageResource(R.drawable.background);
		fantas_font.setImageResource(R.drawable.font_image);
		
		Animation animation=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movement_animation);
		fantas_font.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
						
			@Override
			public void onAnimationEnd(Animation animation) {
				/*username__.setVisibility(View.VISIBLE);
				password__.setVisibility(View.VISIBLE);
				loginclick.setVisibility(View.VISIBLE);
				register.setVisibility(View.VISIBLE);
				 
				progress.setVisibility(View.VISIBLE);
				progress.requestFocus();
				*/
				readsharedThing();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});
		
	}
	
	protected void readsharedThing()
	{
		try{
		String user_saved=savedarea.getString("FANTASGO_USERNAME", null);
		String user_pass=savedarea.getString("FANTASGO_PASSWORD", null);
		if(user_saved==null)
		{
			username__.setVisibility(View.VISIBLE);
			password__.setVisibility(View.VISIBLE);
			loginclick.setVisibility(View.VISIBLE);
			register.setVisibility(View.VISIBLE); 
			////////////////////////////////////////////////////////////////////////
		}
		else
		{
			username__.setText(user_saved);
			password__.setText(user_pass);
			progress.invalidate();
			progress.setVisibility(View.VISIBLE);
			new login().execute();
			
		//Toast.makeText(getApplicationContext(), user_saved+" "+user_pass, Toast.LENGTH_LONG).show();
		}
		}catch(Exception e){}
	}
	
	protected void setData()
	{
		try{
		SharedPreferences.Editor prefedit=savedarea.edit();
		prefedit.putString("FANTASGO_USERNAME", username__.getText().toString());
		prefedit.putString("FANTASGO_PASSWORD", password__.getText().toString());
		prefedit.commit();
		}catch(Exception e){}
	}
	class login extends AsyncTask<String, String, JSONObject>{

		@Override
		protected void onPostExecute(JSONObject jsono) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsono);
			int success1 = 1;
			//String message1;
			
			try {
				success1 = jsono.getInt(KEY_SUCCESS);
				//message1 = jsono.getString("message");
				uname = jsono.getString("user_name");
				uid = jsono.getString("user_id");
				//Toast.makeText(getApplicationContext(), "this is OPE"+" "+uname+" "+uid, Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
				
				e.printStackTrace();
			}
			//message1 = jsono.getString(KEY_MESSAGE);
			if(success1 == 0 || success1 == 4 ){
				SharedUsername();
				progress.setVisibility(View.VISIBLE);
				//Toast.makeText(getApplicationContext(), "Welcome!"+" "+uname, Toast.LENGTH_LONG).show();
				Intent to_main = new Intent(getApplicationContext(),MainScreen.class);
				startActivity(to_main);
				finish();
			}else {Toast.makeText(getApplicationContext(), "Sorry... Please check your username and password!", Toast.LENGTH_LONG).show();
				
			username__.setVisibility(View.VISIBLE);
			password__.setVisibility(View.VISIBLE);
			loginclick.setVisibility(View.VISIBLE);
			register.setVisibility(View.VISIBLE); 
			progress.setVisibility(View.INVISIBLE);
			}
			
			
			//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}

		@Override
		protected JSONObject doInBackground(String... pnum) {
			// TODO Auto-generated method stub
			username = SecondSplashScreen.this.username__.getText().toString();
			pass2 = SecondSplashScreen.this.password__.getText().toString();
			//phone = "a";
			//pass2 = "a";
			/*int success1 = 1;
			String message1="NI"; //= "Some Error Happened.....please let us know at www.groupofdevelopers.com";
		*/	List<NameValuePair> login_details = new ArrayList<NameValuePair>();
			login_details.add(new BasicNameValuePair("user_name", username));
			login_details.add(new BasicNameValuePair("user_pass", pass2));
			JSONObject jsono = jsonparser.makeHttpRequest(URL_LOGIN,"POST",login_details);
			
			return jsono;
		//do in bg ends	
		}
		}
	
	protected void SharedUsername()
	{
		String user_id=saveduseridentity.getString("FANTASGO_USER_ID", "");
		String user_name=saveduseridentity.getString("FANTASGO_USER_NAME", "");
	//Toast.makeText(getApplicationContext(), user_id+" "+user_name+" "+uname+" "+uid, Toast.LENGTH_LONG).show();
		if(!user_id.equals(uname) || !user_name.equals(uid))
		{
			if(uname!=null  && uid!=null)
			{
			SharedPreferences.Editor prefedit=saveduseridentity.edit();
			prefedit.putString("FANTASGO_USER_ID", uid);
			prefedit.putString("FANTASGO_USER_NAME", uname);
			prefedit.commit();
			}
		}
		
	}
	
}
