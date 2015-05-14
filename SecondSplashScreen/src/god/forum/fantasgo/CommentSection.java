package god.forum.fantasgo;

import god.forum.fantasgo.library.JSONParser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class CommentSection extends SherlockActivity {
	//public static String KEY_URL_TOPICS = "http://10.0.2.2:8080/forum_fantasgo/topic.php";
	public static String KEY_URL_TOPICS ="http://www.groupofdevelopers.com/forum_fantasgo/topic.php";
	public static final String KEY_TOPICS = "topics";
	private static final String KEY_ID = "id";
	//private static final String KEY_CAT_ID = "cat_id";
	ArrayList<SingleRow> list;
	ListView listview;
	JSONParser jsonParser = new JSONParser();
	String topic_id;
	JSONObject jasono;
	ImageButton add_your_comment,your_comment_refresh;
	ActionBar actionbar;
	private ProgressDialog pDialog;
	//String cat_id;
	//String[] pass;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_comment);
        
        actionbar=getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        
        list=new ArrayList<SingleRow>();
		Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!= null){
        topic_id = (String) b.get(KEY_ID);
        KEY_URL_TOPICS=b.getString("choice_id");
        //Toast.makeText(getApplicationContext(), "this is 3"+"topic id is"+topic_id, Toast.LENGTH_LONG).show();
        listview=(ListView)findViewById(R.id.list_comment_section);
		listview.setAdapter(new myAdapter(getApplicationContext()));
        //cat_id = (String) b.get(KEY_CAT_ID);
       // pass[0] = topic_id;
        //pass[1] = cat_id;
        }else {
       // cat_id  = null;
        topic_id = null;}
		new getcom().execute();
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				SingleRow singlerowed=list.get(position);
				Bundle bundle=new Bundle();
				bundle.putInt("VAR_ID", 3);
				bundle.putString("COM_USER", "COMMENT BY: "+singlerowed.userid);
				bundle.putString("COM_COMMENT", "COMMENT: "+singlerowed.user_comm);
				bundle.putString("COM_DATE", "DATE: "+singlerowed.comm_date);
				//bundle.putString("CAT_TIME","TIME	: "+singlerowed.comm_time);
				Intent intent=new Intent(getApplicationContext(),CategoryLongClick.class);
				intent.putExtras(bundle);
				startActivity(intent);
				return true;
						}
		});
		add_your_comment=(ImageButton)findViewById(R.id.comment_post_img);
		add_your_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getApplicationContext(), AddCommentorThread.class);
				Bundle bundle=new Bundle();
				bundle.putString("comm_id", topic_id);
				bundle.putString("id_to_change_topic_name", "1");
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		your_comment_refresh=(ImageButton)findViewById(R.id.comment_post_refresh);
		your_comment_refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				listview.removeViews(0, list.size());
	        	list.clear();
	        	new getcom().execute();
	    		
				
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
	
	class SingleRow
	{
		String userid,user_comm,comm_date;
		int imgthumb;
		public SingleRow(String _userid,String _user_comm,String _comm_date,int _img ) {
			this.userid=_userid;
			this.user_comm=_user_comm;
		//	this.comm_time=_comm_time;
			this.comm_date=_comm_date;
			this.imgthumb=_img;
		}
	}
	
	class myAdapter extends BaseAdapter
	{
		
		Context context;
		public myAdapter(Context c) {
			context=c;
			/*
		String userid_[]=new String[]{"user_123_","user_1112_1121314"};
		String user_comm_[]=new String[]{"Awanishjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj Verma Sitting here at","kay kya kya kya"};
		String comm_date_[]=new String[]{"25/10/13","15/101666"};
		String comm_time_[]=new String[]{"11:45 PM","12:30 AM"};
		int img[]=new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher};
		for(int i=0;i<40;i++)
			list.add(new SingleRow(userid_[0],user_comm_[0],comm_time_[0],comm_date_[0],img[0]));
		
			*/
		
		}
		
		@Override
		public int getCount() {
		
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
		
			return list.get(arg0);
		}

		@Override
		public long getItemId(int position) {
		
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflator.inflate(R.layout.third_screen_list, parent,false);
			TextView __userid=(TextView)row.findViewById(R.id.username_comment);
			TextView __user_comm=(TextView)row.findViewById(R.id.user_comment);
			TextView __user_date=(TextView)row.findViewById(R.id.comment_dates);
			//TextView __user_time=(TextView)row.findViewById(R.id.comment_time);
			ImageView __image=(ImageView)row.findViewById(R.id.imagethumbnail);
			
			SingleRow temp=list.get(position);
			__userid.setText(temp.userid);
			__user_comm.setText(temp.user_comm);
			__user_date.setText(temp.comm_date);
			//__user_time.setText(temp.comm_time);
			__image.setImageResource(temp.imgthumb);
			
			return row;
		}
		
	}
	//to get the comments
	class getcom extends AsyncTask<String, String, JSONObject>{

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			try {
				JSONArray array = result.getJSONArray(KEY_TOPICS);
				//Toast.makeText(getApplicationContext(),"this is 4"+ result.toString(), Toast.LENGTH_LONG).show();
				/*Toast.makeText(getApplicationContext(),"this is 1"+ array.toString(), Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(),"this is 2"+ array.getString(2), Toast.LENGTH_LONG).show();
				*/
				/*Toast.makeText(getApplicationContext(), "len "+array.length(), Toast.LENGTH_LONG).show();
				JSONArray p = array.getJSONArray(0);
				Toast.makeText(getApplicationContext(), ""+p.getString(1), Toast.LENGTH_LONG).show();
				*/
				listview.setAdapter(new myAdapter(getApplicationContext()));	
					for(int k=0;k<array.length();k++)
					{	
						JSONArray p = array.getJSONArray(k);
						list.add(new SingleRow(p.getString(5), p.getString(1),p.getString(2),R.drawable.ic_launcher));
						
					
					}
					
					//Toast.makeText(getActivity().getApplicationContext(), (CharSequence) p.getString(1), Toast.LENGTH_LONG).show();
					
					//list.add(new SingleRow("Mew", "hey buddy"));
								
				
					//String p = c.getString(k);
				
				//String abc = b.getString(0); 
	//			Toast.makeText(getApplicationContext(), ""+p,Toast.LENGTH_LONG ).show();	
				
				
			//Toast.makeText(getApplicationContext(), ""+abc,Toast.LENGTH_LONG ).show();	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(CommentSection.this);
			pDialog.setMessage("Please Wait loading all the comments...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			//int success = 1;
			List<NameValuePair> login_details = new ArrayList<NameValuePair>();
			login_details.add(new BasicNameValuePair("id",topic_id));
			//login_details.add(new BasicNameValuePair("cat_id",params[1].toString()));
			jasono = jsonParser.makeHttpRequest(KEY_URL_TOPICS, "GET",login_details );
			return jasono;
		}
		
		
	}
	

}
