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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class LastTest extends SherlockFragment {

	//public static final String KEY_URL_TOPICS = "http://10.0.2.2:8080/forum_fantasgo/userabc.php";
	public static final String KEY_URL_TOPICS = "http://www.groupofdevelopers.com/forum_fantasgo/userabc.php";
	JSONParser parser=new JSONParser();
	ListView listview_post;
	ArrayList<SingleRow> list;
	JSONParser jsonparser3 = new JSONParser();
	SharedPreferences savedidentity;
	private ProgressDialog pdialog2;
	View v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	v=inflater.inflate(R.layout.mypost_activity, container,false);//setContentView();
	//Toast.makeText(getActivity().getApplicationContext(), "Hi", Toast.LENGTH_LONG).show();
	
	list=new ArrayList<SingleRow>();
	listview_post=(ListView)v.findViewById(R.id.listview1_my_post);
	listview_post.setAdapter(new myAdapter(getActivity().getApplicationContext()));
	listview_post.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			SingleRow singlerowed=list.get(position);
			
			if(singlerowed.topic_id.equals("!!"))
			{
				Toast.makeText(getActivity().getApplicationContext(), "Sorry, No Comments!", Toast.LENGTH_LONG).show();
			}
			else
			{
				Intent intent=new Intent(getActivity().getApplicationContext(),CommentType.class);
				intent.putExtra("id", singlerowed.topic_id);
				startActivity(intent);
			}
		}
	});
	
	listview_post.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			SingleRow singlerowed=list.get(position);
			Bundle bundle=new Bundle();
			bundle.putInt("VAR_ID", 2);
			bundle.putString("THREAD_QUESTION", "FULL POST: "+singlerowed.questions);
			bundle.putString("THREAD_DATE", "DATE: "+singlerowed.datess);
			bundle.putString("THREAD_TIME", "TIME: "+"12000");
			Intent intent=new Intent(getActivity().getApplicationContext(),CategoryLongClick.class);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		}
	});
	savedidentity=getActivity().getSharedPreferences("FANTASGO_USER_DETAILS",getActivity().MODE_PRIVATE);
	fetched();
	
	return v;
	}
	
	public void fetched()
	{
		String user_id="";
		user_id=savedidentity.getString("FANTASGO_USER_ID", "");
			//Toast.makeText(getActivity().getApplicationContext(), "secy "+user_id, Toast.LENGTH_LONG).show();
			new gettopics().execute(new String[]{user_id});
			
	}
	 
	class SingleRow
	{
		String questions,datess,topic_id;
		public SingleRow(String first,String second ,String id) {
			this.questions=first;
			this.datess=second;
			this.topic_id=id;
			
		}
	}
	
	class myAdapter extends BaseAdapter
	{
		Context context;
		public myAdapter(Context c) {
			context=c;
		
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
			
			List<NameValuePair> id = new ArrayList<NameValuePair>();
			id.add(new BasicNameValuePair("user_id", id1[0]));
			JSONObject jasono = parser.makeHttpRequest(KEY_URL_TOPICS, "GET", id);
				
				return jasono;
				
			
		}

		@Override
		protected void onPostExecute(JSONObject resultd) {
	
			// TODO Auto-generated method stub
			
			
			//Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
		int success;
		try {
			
			success =  resultd.getInt("success");
			//Toast.makeText(getActivity().getApplicationContext(),success+" Succesdddddyyy "+resultd.toString(), Toast.LENGTH_LONG).show();
			if(success == 0){
				JSONArray topics = resultd.getJSONArray("user_topics");
				//Toast.makeText(getActivity().getApplicationContext(),"Succesdddddeeee "+success, Toast.LENGTH_LONG).show();
				listview_post.setAdapter(new myAdapter(getActivity().getApplicationContext()));
				for(int k = 0; k <topics.length();k++){
					JSONArray onet = topics.getJSONArray(k);
					list.add(new SingleRow(onet.getString(1),onet.getString(2),onet.getString(0)));
				}
				
			}else {
					//Toast.makeText(getActivity().getApplicationContext(), "This is else", Toast.LENGTH_LONG).show();
					listview_post.setAdapter(new myAdapter(getActivity().getApplicationContext()));
					list.add(new SingleRow("No Topics ! Be The first to post", "", "!!"));
					}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getActivity().getApplicationContext(), "sorry"+e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e("THE EXCEPTION HAS OCCURED", e.getLocalizedMessage());
			//Toast.makeText(getApplicationContext(), "EXCEPTION"+e.getMessage(), Toast.LENGTH_LONG).show();
			
		}
		pdialog2.dismiss();
		super.onPostExecute(resultd);
		
		}


		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog2 = new ProgressDialog(getActivity());
			pdialog2.setMessage("Please wait loading Topics...");
			pdialog2.setIndeterminate(false);
			pdialog2.setCancelable(true);
			//pdialog2.show();
		}}


}
