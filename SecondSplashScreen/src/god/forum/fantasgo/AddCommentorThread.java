package god.forum.fantasgo;

import god.forum.fantasgo.library.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class AddCommentorThread extends SherlockActivity {
	
	public static final String KEY_SUCCESS = "success";
	//private static final String KEY_URL_ADD_TOPICS = "http://10.0.2.2:8080/forum_fantasgo/create_topic.php";
	//private static final String KEY_URL_ADD_COMMENTS= "http://10.0.2.2:8080/forum_fantasgo/reply.php";
	private static final String KEY_URL_ADD_TOPICS = "http://www.groupofdevelopers.com/forum_fantasgo/create_topic.php";
	private static final String KEY_URL_ADD_COMMENTS= "http://www.groupofdevelopers.com/forum_fantasgo/reply.php";
	public static final String KEY_MESSAGE = "message";
	EditText text;
	Button button;
	TextView viewed;
	JSONParser jsonparser,jsonparser2; 
	ProgressDialog pdialog1,pdialog2;
	RadioButton postive,negative,general;
	int COMMENT_TYPE=2;
	SharedPreferences saveduseridentity;
	String user_id,topic_id,user_name,topic_added,cat_id,comment_added;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light_Dialog);
		setContentView(R.layout.addcommentorthread);
		
		saveduseridentity=getSharedPreferences("FANTASGO_USER_DETAILS", MODE_PRIVATE);
			fetch();
			
		jsonparser = new JSONParser();
		
		viewed=(TextView)findViewById(R.id.addtextview);
		button=(Button)findViewById(R.id.addpost);
		text = (EditText)findViewById(R.id.addedittext);
		postive=(RadioButton)findViewById(R.id.ptypepos);
		negative=(RadioButton)findViewById(R.id.ptypeneg);
		general=(RadioButton)findViewById(R.id.ptypegen);
		
		postive.setOnClickListener(radioClick);
		negative.setOnClickListener(radioClick);
		general.setOnClickListener(radioClick);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle.getString("id_to_change_topic_name").equals("0"))
		{
			cat_id = bundle.getString("cat_id");
		button.setText("Post");
		viewed.setText("Add Topic");
		RadioGroup a;
		a = (RadioGroup) findViewById(R.id.comment_type);
		a.setVisibility(View.INVISIBLE);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(text.getText().toString().equals(""))
					Toast.makeText(getApplicationContext(), "Please write your topic", Toast.LENGTH_LONG).show();
				else{
					topic_added = text.getText().toString();
					//Toast.makeText(getApplicationContext(),"Topic ADDED--->"+topic_added , Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(),"Categorgy ---->"+ cat_id, Toast.LENGTH_LONG).show();
					//Toast.makeText(getApplicationContext(), "user id --->"+user_id, Toast.LENGTH_LONG).show();
					
					new input_topic().execute();}
				
			}
		});
		
		}else if(bundle.getString("id_to_change_topic_name").equals("1")){
			button.setText("Comment!");
			viewed.setText("Add A Comment");
			topic_id = bundle.getString("comm_id");
			
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(text.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), "Please write your topic.cannot be blank", Toast.LENGTH_LONG).show();
					}else{
						
						
						comment_added = text.getText().toString();
						/*Toast.makeText(getApplicationContext(), "Type "+COMMENT_TYPE, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(),"Comment ADDED--->"+comment_added , Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(),"comment Categorgy ---->"+ topic_id, Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), "comment user id --->"+user_id, Toast.LENGTH_LONG).show();
				*///		choice = (RadioGroup) findViewById(R.id.comment_type);
				//		int choice_selected = choice.getCheckedRadioButtonId();
				//		radio_choice = (RadioButton)findViewById(choice_selected);
					//	Toast.makeText(getApplicationContext(),radio_choice.getText(),Toast.LENGTH_LONG).show();
						new input_comment().execute();
						
					
					}
						
					}
					
				
			});
			
			}
		else if(bundle.getString("id_to_change_topic_name").equals("3")){
			button.setText("Request category");
			viewed.setText("Request a category");
			RadioGroup a;
			a = (RadioGroup) findViewById(R.id.comment_type);
			a.setVisibility(View.INVISIBLE);
			
			button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(text.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), "Please write your category.cannot be blank", Toast.LENGTH_LONG).show();
					}else{	
						
						Toast.makeText(getApplicationContext(), "Your Category has been send to admin for approval.", Toast.LENGTH_LONG).show();
					
					}
						
					}
					
				
			});
			
			}
		
		else{
				Toast.makeText(getApplicationContext(), "Something Went wrong please contact Admin error code 5X23098.", Toast.LENGTH_LONG).show();
				}
		
	
	}
	protected void fetch()
	{
		user_id=saveduseridentity.getString("FANTASGO_USER_ID", "");
		user_name=saveduseridentity.getString("FANTASGO_USER_NAME", "");
	//Toast.makeText(getApplicationContext(), "userid"+user_id+" "+user_name, Toast.LENGTH_LONG).show();
	}
	
	RadioButton.OnClickListener radioClick=new RadioButton.OnClickListener() {
		
		@Override
		public void onClick(View arg) {

			boolean checked=((RadioButton) arg).isChecked();
			
			switch(arg.getId())
			{
			case R.id.ptypepos:
				if(checked)
				{
					COMMENT_TYPE=0;
					negative.setChecked(false);
					general.setChecked(false);
					
				}
				break;
				
			case R.id.ptypeneg:
				if(checked)
				{
					COMMENT_TYPE=1;
					postive.setChecked(false);
					general.setChecked(false);
				}
				break;
				
			case R.id.ptypegen:
				if(checked)
				{
					COMMENT_TYPE=2;
					negative.setChecked(false);
					postive.setChecked(false);
				}
				break;

			}
			
		}
	};
	
	class input_topic extends AsyncTask<String, String, JSONObject>{

		

		@Override
		protected JSONObject doInBackground(String... arg) {
			List<NameValuePair> add_topic = new ArrayList<NameValuePair>();
			add_topic.add(new BasicNameValuePair("topic_subject",topic_added));
			add_topic.add(new BasicNameValuePair("topic_cat",cat_id));
			add_topic.add(new BasicNameValuePair("user_id",user_id));
			JSONObject jasono = jsonparser.makeHttpRequest(KEY_URL_ADD_TOPICS, "POST", add_topic);
			//return jasono.getString("topic");
		return jasono;
			
		}

		@Override
		protected void onPostExecute(JSONObject result) { 
			
			super.onPostExecute(result);
			//Toast.makeText(getApplicationContext(), "This is on sucess-->"+result.getString(KEY_SUCCESS), Toast.LENGTH_LONG).show();
			pdialog1.dismiss();
			finish();
			
		}

		@Override
		protected void onPreExecute() {
			pdialog1 = new ProgressDialog(AddCommentorThread.this);
			pdialog1.setMessage("Please wait sending your topic for authorization....");
			pdialog1.setIndeterminate(false);
			pdialog1.setCancelable(false);
			pdialog1.show();
			super.onPreExecute();
		}
	}
		class input_comment extends AsyncTask<String, String, JSONObject>{

			@Override
			protected JSONObject doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				List<NameValuePair> add_comment = new ArrayList<NameValuePair>();
				add_comment.add(new BasicNameValuePair("reply_content",comment_added));
				add_comment.add(new BasicNameValuePair("id",topic_id));
				add_comment.add(new BasicNameValuePair("user_id",user_id));
				add_comment.add(new BasicNameValuePair("post_type",COMMENT_TYPE+""));
				JSONObject jasono = jsonparser.makeHttpRequest(KEY_URL_ADD_COMMENTS, "GET", add_comment);
				return jasono;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				try {
					//Toast.makeText(getApplicationContext(), "This is on success-->"+result.getString(KEY_SUCCESS), Toast.LENGTH_LONG).show();
					if(result.getString(KEY_SUCCESS).equals("0"))
					{
						Toast.makeText(getApplicationContext(), "Your comment has been added ", Toast.LENGTH_LONG).show();
						finish();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//Toast.makeText(getApplicationContext(), "This is on sucess-->"+e.getMessage(), Toast.LENGTH_LONG).show();
					Toast.makeText(getApplicationContext(), "OOPS!! Some error occured", Toast.LENGTH_LONG).show();
					
					e.printStackTrace();
				}
				pdialog2.dismiss();
				
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				pdialog2 = new ProgressDialog(AddCommentorThread.this);
				pdialog2.setMessage("Please wait sending your comment for authorization....");
				pdialog2.setIndeterminate(false);
				pdialog2.setCancelable(false);
				pdialog2.show();
				super.onPreExecute();
				
				
				
			}
			
			
			
		
	
	}
}
