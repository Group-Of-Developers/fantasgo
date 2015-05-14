package god.forum.fantasgo;

import god.forum.fantasgo.library.JSONParser;
import god.forum.fantasgo.library.Networkcheck;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class RegisterForum extends SherlockActivity {
	
	//public static final String KEY_URL_SIGNUP ="http://10.0.2.2:8080/forum_fantasgo/signup.php";
	public static final String KEY_URL_SIGNUP ="http://www.groupofdevelopers.com/forum_fantasgo/signup.php";
	public static final String KEY_SUCCESS = "success";
	Button okid;
	ActionBar action;
	EditText E_name,E_pass,E_phone,E_email,E_confirmpass;
	DatePicker E_dob;
	String S_name,S_pass,S_phone,S_email;
	int S_dob;
	JSONParser jsonParser = new JSONParser();
	private ProgressDialog pdialog4;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.register_layout);
		
		
		
		action=getSupportActionBar();
		action.setDisplayHomeAsUpEnabled(true);
		E_name=(EditText)findViewById(R.id.fulname);
		E_phone =(EditText)findViewById(R.id.mobileno);
		E_pass =(EditText)findViewById(R.id.password);
		//E_dob = (DatePicker)findViewById(R.id.datePicker1);
		E_email =(EditText)findViewById(R.id.email_address);
		E_confirmpass=(EditText)findViewById(R.id.username);
		
		okid=(Button)findViewById(R.id.ok);
		okid.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Networkcheck.getInstance(getApplicationContext()).isOnline(getApplicationContext()))
				{if(check()){
				new register().execute();
				}else
				Toast.makeText(getApplicationContext(), "Please fill all the fields completely.", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Fantasgo in not able to connect ..Please check your Internet settings", Toast.LENGTH_LONG).show();
					
				}
			}
		});
	}
	public boolean check()
	{
		boolean isAllFiled=true;
		if(E_name.getText().toString().equals(""))
		{
			E_name.setBackgroundResource(R.drawable.error_list_view);
			isAllFiled=false;
		}
		/*if(E_email.getText().toString().equals(""))
		{
			E_email.setBackgroundResource(R.drawable.error_list_view);
			isAllFiled=false;
		}*/
		if(E_phone.getText().toString().equals(""))
		{
			E_phone.setBackgroundResource(R.drawable.error_list_view);
			isAllFiled=false;
		}
		if(E_confirmpass.getText().toString().equals("") || E_pass.getText().toString().equals(""))
		{
			E_pass.setBackgroundResource(R.drawable.error_list_view);
			E_confirmpass.setBackgroundResource(R.drawable.error_list_view);
			
			if(!E_confirmpass.getText().toString().equals(E_pass.getText().toString()))
				Toast.makeText(getApplicationContext(), "Your Password Didn't match", Toast.LENGTH_LONG).show();
			isAllFiled=false;
		}
		return isAllFiled;
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
class register extends AsyncTask<String, String, JSONObject>
{
	@Override
	protected JSONObject doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		S_name = E_name.getText().toString();
		S_phone = E_phone.getText().toString();
		S_pass = E_pass.getText().toString();
		//S_dob = E_dob.getDayOfMonth();
		S_email = E_email.getText().toString();
		List<NameValuePair> add_details = new ArrayList<NameValuePair>();
		add_details.add(new BasicNameValuePair("user_name",S_phone));
		add_details.add(new BasicNameValuePair("user_pass",S_pass));
		add_details.add(new BasicNameValuePair("user_phone",S_name));
		add_details.add(new BasicNameValuePair("user_dob","1994-11-17"));
		add_details.add(new BasicNameValuePair("user_email",S_email));
		//add_details.add(new BasicNameValuePair("user_sex","M"));
		
		//login_details.add(new BasicNameValuePair("cat_id",params[1].toString()));
		JSONObject jasono = jsonParser.makeHttpRequest(KEY_URL_SIGNUP, "POST",add_details );
		return jasono;
		
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		try {
			int success = result.getInt(KEY_SUCCESS);
			if(success == 0){
				pdialog4.dismiss();
				Toast.makeText(getApplicationContext(), "Congratulations and Thank you for registration!", Toast.LENGTH_LONG).show();
				finish();
				//Intent i = new Intent();
				/*Intent intent=new Intent(getApplicationContext(),SecondSplashScreen.class);
				startActivity(intent);*/
			}else if (success == 2){
				pdialog4.dismiss();
				Toast.makeText(getApplicationContext(), "This Phone is already registered!!cannot use the same phone number", Toast.LENGTH_LONG).show();
				
			}
			else{Toast.makeText(getApplicationContext(), "Not Registered try again please", Toast.LENGTH_LONG).show();
				pdialog4.dismiss();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "something went wrong try again or contact us", Toast.LENGTH_LONG).show();
			
		}
		pdialog4.dismiss();
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pdialog4 = new ProgressDialog(RegisterForum.this);
		pdialog4.setMessage("Registering you...Please wait");
		pdialog4.setIndeterminate(false);
		pdialog4.setCancelable(false);
		pdialog4.show();
		
	}
}
}
