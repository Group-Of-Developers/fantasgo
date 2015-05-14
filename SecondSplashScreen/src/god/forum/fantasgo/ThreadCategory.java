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
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ThreadCategory extends SherlockActivity {
	
	public static final String KEY_SUCCESS = "success";
	//public static final String KEY_URL_TOPICS = "http://10.0.2.2:8080/forum_fantasgo/category.php";
	public static final String KEY_URL_TOPICS = "http://www.groupofdevelopers.com/forum_fantasgo/category.php";
	private static final String KEY_ID = "id";
	public static final String KEY_TOPIC = "topic";
	private static final String KEY_CAT_ID = "cat_id";
	public static final String KEY_COUNTING = "counting";
	private ProgressDialog pdialog;
	String id1;
	ListView listview;
	ActionBar actionbar;
	ArrayList<SingleRow> list;
	JSONParser jsonparser = new JSONParser();
	String id,cat_total,posi,nege,general,topic_id_g,cat_id_g;
	ImageButton add_your_post,refresh_your_post;
	SharedPreferences savedidentity;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_thread);
        
        savedidentity=getSharedPreferences("FANTASGO_USER_DETAILS", MODE_PRIVATE);
		
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!= null){id = b.get(KEY_ID).toString();
        	cat_total = (String)b.get("cat_id");
        }else { id  = "NOT FOUND ID";}
       // Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
		list=new ArrayList<SingleRow>();
		
		add_your_post=(ImageButton)findViewById(R.id.post_thread_img);
		add_your_post.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getApplicationContext(), AddCommentorThread.class);
				Bundle bundle=new Bundle();
				bundle.putString("cat_id", id);
				bundle.putString("id_to_change_topic_name", "0");
				
				
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		listview=(ListView)findViewById(R.id.listview1);
		listview.setAdapter(new myAdapter(this));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				SingleRow singlerowed=list.get(position);
				
				if(singlerowed.topic_id.equals("!!"))
				{
					Intent intent=new Intent(getApplicationContext(),AddCommentorThread.class);
					startActivity(intent);
				}
				else
					
				{	//Toast.makeText(getApplicationContext(), "topic_single row"+singlerowed.topic_id, Toast.LENGTH_LONG).show();
				String[] a= {singlerowed.topic_id};
				//Toast.makeText(getApplicationContext(), "t----------->"+a[0], Toast.LENGTH_LONG).show();
				topic_id_g = singlerowed.topic_id;
				cat_id_g = singlerowed.cat_id;
				new getcount().execute(a);
				
				}
			}
		});
		new gettopics().execute(id);
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				
				SingleRow singlerowed=list.get(position);
				Bundle bundle=new Bundle();
				bundle.putInt("VAR_ID", 2);
				bundle.putString("THREAD_QUESTION", "FULL POST: "+singlerowed.questions);
				bundle.putString("THREAD_DATE", "DATE: "+singlerowed.datess);
				//bundle.putString("THREAD_TIME", "TIME: "+"12000");
				Intent intent=new Intent(getApplicationContext(),CategoryLongClick.class);
				intent.putExtras(bundle);
				startActivity(intent);
				return true;
			}
		});
		
		refresh_your_post=(ImageButton)findViewById(R.id.post_thread_refresh);
		refresh_your_post.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				listview.removeViews(0, list.size());
	        	list.clear();
	    		new gettopics().execute(id);
				
			}
		});
		
	}
	protected void fetch()
	{
		String user_id=savedidentity.getString("FANTASGO_USER_ID", "");
		String user_name=savedidentity.getString("FANTASGO_USER_NAME", "");
		//Toast.makeText(getApplicationContext(), user_id+" "+user_name, Toast.LENGTH_LONG).show();
	}
	  
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    	case android.R.id.home:
		    		this.finish();
		    	return true;
		       /* case REFRESH_MORE:
		        	Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_LONG).show();
		        	listview.removeViews(0, list.size());
		        	list.clear();
		    		new gettopics().execute(id);

		        	 	return true;*/
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	    
		
	class SingleRow
	{
		String questions,datess,topic_id,cat_id;
		public SingleRow(String first,String second ,String id,String cat_id) {
			this.questions=first;
			this.datess=second;
			this.topic_id=id;
			this.cat_id = cat_id;
		}
	}
	
	class myAdapter extends BaseAdapter
	{
		Context context;
		public myAdapter(Context c) {
			context=c;
		/*
		String question[]=new String[]{"h  ","jlalalalalalalalalalalalalalalalalaaja"};
		String dates[]=new String[]{"25/10/13","15/101666"};
		for(int i=0;i<2;i++)
			list.add(new SingleRow(question[i],dates[i]));
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
			View row=inflator.inflate(R.layout.second_screen_list, parent,false);
			TextView ques=(TextView)row.findViewById(R.id.questions);
			TextView dat=(TextView)row.findViewById(R.id.dates);
			
			SingleRow temp=list.get(position);
			ques.setText(temp.questions);
			dat.setText(temp.datess);
			
			return row;
		}
		
	}
	class gettopics extends AsyncTask<String, String, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... id1) {
			//String arg1 = "1";
			// TODO Auto-generated method stub
			
			List<NameValuePair> id = new ArrayList<NameValuePair>();
			id.add(new BasicNameValuePair("id", id1[0]));
			JSONObject jasono = jsonparser.makeHttpRequest(KEY_URL_TOPICS, "GET", id);
					//return jasono.getString("topic");
				return jasono;
				
			
		}

		
		@Override
		protected void onPostExecute(JSONObject result) {
	
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
		String success;
		try {
			success =  result.getString(KEY_SUCCESS);
			if(success.equals("0")){
				JSONArray topics = result.getJSONArray(KEY_TOPIC);
				JSONArray count = result.getJSONArray(KEY_COUNTING);
				posi = count.getString(0);
				nege = count.getString(1);
				general = count.getString(2);
				listview.setAdapter(new myAdapter(getApplicationContext()));
				for(int k = 0; k <topics.length();k++){
					JSONArray onet = topics.getJSONArray(k);
					list.add(new SingleRow(onet.getString(1),onet.getString(2),onet.getString(0),onet.getString(3)));
				}
				
			}else {
					//Toast.makeText(getApplicationContext(), "This is else", Toast.LENGTH_LONG).show();
					listview.setAdapter(new myAdapter(getApplicationContext()));
					list.add(new SingleRow("No Topics ! Be The first to post", "", "!!",""));
					}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("THE EXCEPTION HAS OCCURED", e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), "Sorry "+e.getMessage(), Toast.LENGTH_LONG).show();
			
		}
		
		pdialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(ThreadCategory.this);
			pdialog.setMessage("Please wait loading Topics...");
			pdialog.setIndeterminate(false);
			pdialog.setCancelable(false);
			pdialog.show();
		}
		}

	
	
	
	class getcount extends AsyncTask<String, String, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... id2) {
			//String arg1 = "1";
			// TODO Auto-generated method stub
			
			List<NameValuePair> id = new ArrayList<NameValuePair>();
			id.add(new BasicNameValuePair("topic_id", id2[0]));
			id.add(new BasicNameValuePair("id", "1")); //id is always  as we only need counting in this claass this can be optimised
			
			JSONObject jasono = jsonparser.makeHttpRequest(KEY_URL_TOPICS, "GET", id);
					//return jasono.getString("topic");
				return jasono;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
	
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
		String success;
		try {
			success =  result.getString(KEY_SUCCESS);
			if(success.equals("0")){
				JSONArray count = result.getJSONArray(KEY_COUNTING);
				posi = count.getString(0);
				nege = count.getString(1);
				general = count.getString(2);
				Intent intent=new Intent(getApplicationContext(),CommentType.class);
				intent.putExtra(KEY_ID, topic_id_g);
				intent.putExtra(KEY_CAT_ID,cat_id_g );
				intent.putExtra("positive", posi);
				intent.putExtra("negative", nege);
				intent.putExtra("general", general);
				startActivity(intent);
				
			}else {
				Toast.makeText(getApplicationContext(), "Sorry Please check your internet connection it is very slow", Toast.LENGTH_LONG).show();
					}
		} catch (JSONException e) 
		{
			e.printStackTrace();
			Log.e("THE EXCEPTION HAS OCCURED", e.getLocalizedMessage());
			Toast.makeText(getApplicationContext(), "Sorry "+e.getMessage(), Toast.LENGTH_LONG).show();	
		}
		
		}

		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			super.onPreExecute();
			
		}}

	
	
	
	
	
}
